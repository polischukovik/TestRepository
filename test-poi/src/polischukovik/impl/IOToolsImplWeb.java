package polischukovik.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

@Component("advanced")
public class IOToolsImplWeb implements IOTools, RequiredPropertyNameProvider  {

	@Autowired
	Properties prop;
	
	private List<PropertyName> requiredProps = Arrays.asList(
			 PropertyName.FILE_UPLOAD);

	private String pSrcString;
		
	public IOToolsImplWeb() {
		prop = Main.ctx.getBean(Properties.class);
		pSrcString = prop.get(PropertyName.FILE_UPLOAD);
	}
	
	@Override
	public String read() throws IOException{		
		return pSrcString;
	}

	public void write(XWPFDocument doc) throws IllegalStateException, IOException {
		//....
	}

	@Override
	public XWPFDocument readDoc(String path) throws IOException {
		FileInputStream fis = null;
        File file = new File(path);
		try {
			fis = new FileInputStream(file.getAbsolutePath());
		} catch (IOException e) {
			throw e;
		} finally {
			fis.close();
		}
       
		return new XWPFDocument(fis);
	}
	
	@Override
	public List<PropertyName> getRequiredProperties(){
		return new ArrayList<>(requiredProps);
	}
}
