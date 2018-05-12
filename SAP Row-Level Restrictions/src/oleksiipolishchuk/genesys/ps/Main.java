package oleksiipolishchuk.genesys.ps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.sap.sl.sdk.authoring.cms.CmsResourceService;
import com.sap.sl.sdk.authoring.cms.CmsSecurityService;
import com.sap.sl.sdk.authoring.security.DataSecurityProfile;
import com.sap.sl.sdk.authoring.security.SecurityFactory;
import com.sap.sl.sdk.framework.SlContext;
import com.sap.sl.sdk.framework.SlException;
import com.sap.sl.sdk.framework.cms.CmsSessionService;

public class Main {

    private static final String CMS_AUTH = "secEnterprise";
    
    private static SlContext context;
    private static IEnterpriseSession enterpriseSession;
	private static SecurityFactory securityFactory;
	private static CmsSecurityService cmsSecurityService;
	
	private static String CMS_NAME;
    private static String CMS_USER;
    private static String CMS_PASS;
    private static String UNX_PATH;
	private static Path directoryPath;
	
	private static FileWriter log = null;
	private static int done = 0, totalCounter = 0;
	
	//java -jar restrictions.jar "vmws02:6400" "Administrator" "Password" "/Interactive Insights/8.5.0/GI2_Universe.unx" "Modified row level restriction SQL queries_ver2.sql"
	public static void main(String[] args){
		try{
			CMS_NAME = args[0];		
			CMS_USER = args[1];
			CMS_PASS = args[2];
			UNX_PATH = CmsResourceService.UNIVERSES_ROOT + args[3];
			directoryPath = Paths.get(args[4]);
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Invalid arguments");
			System.out.println("java -jar restrictions.jar CMS_NAME CMS_USER CMS_PASS UNX_PATH SCRIPTS_DIR");
			System.out.println("Example:");
			System.out.println("java -jar restrictions.jar \"vmws02:6400\" \"Administrator\" \"Password\" \"/Interactive Insights/8.5.0/GI2_Universe.unx\" \"scripts\"");
			throw new IllegalArgumentException("Invalid arguments");
		}
		
		//Init logger
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String logFileName = String.format("log_%s_%s.log", CMS_NAME.replaceAll(":", "_"), simpleDateFormat.format(new Date()));
		try {
			log = new FileWriter(new File(logFileName));
		} catch (IOException e1) {
			System.out.println("Warning: Unable to create log file");
		}
		
		log("Parameters are:");
		log("  CMS address:" + CMS_NAME);
		log("  CMS username: " + CMS_USER);
		log("  CMS password: " + CMS_PASS);
		log("  Universe path: " + UNX_PATH);
		log("  Scripts directory: " + directoryPath);
		
		try{
			if(Files.list(directoryPath).count() > 0){
				setUp();
			} else{
				throw new IOException("\nNo script files detected");
			}
			
			for(Path script : Files.newDirectoryStream(directoryPath, r -> Files.isRegularFile(r))){
				totalCounter++;
				
				String fileName = script.getFileName().toString();
				String dataSecurityProfileName = fileName.lastIndexOf('.') != -1 ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;

		        createDataSecurityProfile(dataSecurityProfileName, parseSecurityProfile(script));
		        
			}
		} catch(IOException | SDKException e){
			log(e.getMessage());
		} finally {
			tearDown();
		}
		
		log(String.format("\n\nSUMMARY: %d/%d security profiles were processed", done, totalCounter));
	}

	private static void log(String string) {
		if(log != null){
			try {
				log.write(string + System.lineSeparator());
				log.flush();
				System.out.println(string);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//Read file content, trim each line from whitespace and filter out empty lines
	private static String[] parseSecurityProfile(Path path) throws IOException{
		return Files.readAllLines(path).stream()
			.map(e -> e.trim())
			.filter(s -> !s.startsWith("--"))
			.filter(t -> t.length() > 0)
			.collect(Collectors.toList()).toArray(new String[0]);
	}

	private static void createDataSecurityProfile(String dataSecurityProfileName, String[] rowLevelRestrictions) {
		// Creates the data security profile 
        log(String.format("\nCreate new data security profile \t[%s]", dataSecurityProfileName));
        DataSecurityProfile dataSecurityProfile = securityFactory.createDataSecurityProfile();

		dataSecurityProfile.setName(dataSecurityProfileName);

		log("Adding new row restrictions:");
		log(String.format("\t%-25s\t>>\t%s", "TABLE NAME", "WHERE CLAUSE"));

		log(String.format("\t%-27s\t>>\t%-30s", "", "").replace(' ', '-'));
		
		for(String restriction : rowLevelRestrictions){			
			String restrictionName = "";
			try{
				restrictionName = getRestrictionName(restriction);			
			} catch (ArrayIndexOutOfBoundsException e){
				String messageSubstring = restriction;
				if(restriction.length() > 25){
					messageSubstring = restriction.substring(0, 25) + "...";
				}
				String errorMessage = String.format("Lines should start with table name: [%s]", messageSubstring);
				throw new IllegalArgumentException(errorMessage);
			}	   
			
			log(String.format("\t%-25s\t>>\t[%s]", "[" + restrictionName + "]", restriction));			
	        dataSecurityProfile.getRowRestrictions().add(securityFactory.createRowRestriction(restrictionName, restriction));
	        
		}
       
		log("\nApplying data security profile [" + dataSecurityProfile.getName() + "]");
		try{
			cmsSecurityService.attachSecurityProfile(UNX_PATH, dataSecurityProfile);
			log("SUCCESS!: The security profile [" + dataSecurityProfile.getName() + "] was processed.\n\n");

			done++;
		} catch (SlException e){
			log(e.getMessage());
			log("FAILURE!: Skipping security profile [" + dataSecurityProfile.getName() + "]");
		}
	}
	
	//Restriction name is the String before '.'
	private static String getRestrictionName(String restriction){
		return restriction.split("\\.")[0];
	}

	private static void setUp() throws SDKException {
        log("Logging in...");
		context = SlContext.create();
		enterpriseSession = CrystalEnterprise.getSessionMgr().logon(CMS_USER, CMS_PASS, CMS_NAME, CMS_AUTH);
		context.getService(CmsSessionService.class).setSession(enterpriseSession);
		
		cmsSecurityService = context.getService(CmsSecurityService.class);
        securityFactory = context.getService(SecurityFactory.class);
	}
	
	private static void tearDown() {
		if(context != null){
			context.close();
			enterpriseSession.logoff();

			log("\nLogged out");
		}
	}

}
