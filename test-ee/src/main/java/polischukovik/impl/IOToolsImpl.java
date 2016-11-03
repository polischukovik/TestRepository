package polischukovik.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import polischukovik.properties.Properties;
import polischukovik.properties.RequiredPropertyNameProvider;
import polischukovik.services.IOTools;

@Component
public class IOToolsImpl implements IOTools, RequiredPropertyNameProvider  {

	@Autowired
	Properties prop;
	
	private List<PropertyName> requiredProps = Arrays.asList(
			 PropertyName.IO_DEST_FILE_NAME,
			 PropertyName.IO_SOURCE_FILE_NAME);
	
	private String pDestFileName;
	private String pSrcFileName;
		
	public IOToolsImpl() {		
	}
	
	public String read() throws FileNotFoundException{
		pSrcFileName = prop.get(PropertyName.IO_SOURCE_FILE_NAME);
		
		try(Scanner sourceFile = new Scanner(new File(pSrcFileName))) {
			String questionData = "";
			while(sourceFile.hasNext()){
				questionData += sourceFile.nextLine() + "\n";
			}
			sourceFile.close();
			return questionData;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(e.getMessage());
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
