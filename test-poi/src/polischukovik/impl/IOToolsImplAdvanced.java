package polischukovik.impl;

import java.io.IOException;
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
public class IOToolsImplAdvanced implements IOTools, RequiredPropertyNameProvider  {

	@Autowired
	Properties prop;
	
	private List<PropertyName> requiredProps = Arrays.asList(
			 PropertyName.FILE_UPLOAD);

	private String pSrcString;
		
	public IOToolsImplAdvanced() {		
		prop = Main.ctx.getBean(Properties.class);
	}
	
	public String read() throws IOException{
		pSrcString = prop.get(PropertyName.FILE_UPLOAD);
		return pSrcString;
	}

	public void write(XWPFDocument doc) throws IllegalStateException, IOException {
		
	}
	
	public List<PropertyName> getRequiredProperties(){
		return new ArrayList<>(requiredProps);
	}
}
