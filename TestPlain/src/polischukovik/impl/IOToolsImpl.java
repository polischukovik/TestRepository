package polischukovik.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import polischukovik.domain.enums.PropertyName;
import polischukovik.main.Main;
import polischukovik.properties.Properties;
import polischukovik.properties.RequiredPropertyNameProvider;
import polischukovik.services.IOTools;

@Component("primitive")
public class IOToolsImpl implements IOTools, RequiredPropertyNameProvider  {

	@Autowired
	Properties prop;
	
	private List<PropertyName> requiredProps = Arrays.asList(
			 PropertyName.IO_DEST_FILE_NAME,
			 PropertyName.IO_SOURCE_FILE_NAME);
	
	private String pDestFileName;
	private String pSrcFileName;
		
	public IOToolsImpl() {
		prop = Main.ctx.getBean(Properties.class);
	}
	
	public String read() throws IOException{
		pSrcFileName = prop.get(PropertyName.IO_SOURCE_FILE_NAME);
		File file = new File(pSrcFileName);
		System.out.println("Source file path: " + file.getAbsoluteFile());
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(file) );
			char [] buff = new char[1024 * 30];
			reader.read(buff);
			reader.close();
			
			String questionData = new String(buff).replace("\\r?\\n","\n");					
			return questionData;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(e.getMessage() + ": " + 
					file.getAbsoluteFile());
		}
	}

	public void write(XWPFDocument doc) throws IllegalStateException, IOException {
		pDestFileName = prop.get(PropertyName.IO_DEST_FILE_NAME);
		OutputStream os = new FileOutputStream(new File(pDestFileName));

		if(doc == null){
			os.close();
			throw new IllegalStateException("Document reference is null");	
		}
		
		doc.write(os);
		os.close();
	}
	
	public List<PropertyName> getRequiredProperties(){
		return new ArrayList<>(requiredProps);
	}
}
