package polischukovik.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		pSrcFileName = prop.get(PropertyName.IO_SOURCE_FILE_NAME);
		pDestFileName = prop.get(PropertyName.IO_DEST_FILE_NAME);
	}
	
	@Override
	public String read() throws IOException{		
		File file = new File(pSrcFileName);
		BufferedReader reader = null;
		String questionData = null;
		System.out.println("Source file path: " + file.getAbsoluteFile());
		try {
			reader = new BufferedReader(new FileReader(file) );
			char [] buff = new char[1024 * 30];
			reader.read(buff);			
			questionData = new String(buff).replace("\\r?\\n","\n");
		} catch (IOException e) {
			throw e;
		} finally {
			reader.close();
		}
		return questionData;
	}	

	@Override
	public XWPFDocument readDoc(String path) throws IOException {
		FileInputStream fis = null;
        File file = new File(path);
		try {
			fis = new FileInputStream(file.getAbsolutePath());
			return new XWPFDocument(fis);
		} catch (IOException e) {
			throw e;
		} finally {
			fis.close();
		}
	}
	
	@Override
	public void write(XWPFDocument doc) throws IOException {
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
