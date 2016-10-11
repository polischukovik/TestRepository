package ee;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import polischukovik.AppConfiguration;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.mslibrary.DocumentTools;
import polischukovik.mslibrary.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfiguration.class)
public class TestsTest {
		
	@Autowired
	private Properties prop;	
	@Autowired
	private XWPFDocument doc;
	@Autowired
	private DocumentTools dt;
	@Test
	public void entetiesWired() {
		assertNotNull(prop);
		assertNotNull(doc);
		assertNotNull(dt);
	}
	@Test
	public void documentCreated() throws IOException {

		File file = new File(prop.get(PropertyNames.IO_DEST_FILE_NAME));
		if(file.exists()){
			assertEquals(true, file.delete());
		}	
		
		dt.write(doc);
		
		assertEquals(true, file.exists());
		
	}

}
