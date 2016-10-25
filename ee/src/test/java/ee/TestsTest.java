package ee;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import polischukovik.AppConfiguration;
import polischukovik.domain.QuestionRaw;
import polischukovik.domain.enums.PropertyNames;
import polischukovik.msformating.SimpleKeysComposer;
import polischukovik.msformating.SimpleTitleComposer;
import polischukovik.msformating.SimpleVariantComposer;
import polischukovik.msformating.interfaces.DocumentComponentComposer;
import polischukovik.msformating.interfaces.DocumentFactory;
import polischukovik.mslibrary.IOTools;
import polischukovik.mslibrary.Properties;
import polischukovik.services.QuestioRawnHandler;
import polischukovik.services.TestFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfiguration.class)
public class TestsTest {
		
	@Autowired
	private Properties prop;	
	@Autowired
	private QuestioRawnHandler questioRawnHandler;	
	@Autowired
	private TestFactory testFactory;
	@Autowired
	private DocumentFactory documentFactory;
	@Autowired
	private SimpleTitleComposer simpleTitleComposer;
	@Autowired
	private SimpleVariantComposer simpleVariantComposer; 
	@Autowired
	private SimpleKeysComposer simpleKeysComposer;
	@Autowired
	private IOTools documentTools;
	
	private List<QuestionRaw> questions;
	private polischukovik.domain.Test test;
	private List<DocumentComponentComposer> componentComposers;
	private XWPFDocument doc;
	
	@Test
	public void entetiesWired() {
		assertNotNull(prop);
		assertNotNull(questioRawnHandler);
		assertNotNull(testFactory);
		assertNotNull(documentFactory);
		assertNotNull(simpleTitleComposer);
		assertNotNull(simpleVariantComposer);
		assertNotNull(simpleKeysComposer);
		assertNotNull(documentTools);
	}
	@Test
	public void documentCreated() throws IOException {
		questions = questioRawnHandler.parseSource(prop.get(PropertyNames.IO_SOURCE_FILE_NAME));
		test = testFactory.createTest(questions);
		
		componentComposers = new ArrayList<>();
		componentComposers.add(simpleTitleComposer);
		componentComposers.add(simpleVariantComposer);
		componentComposers.add(simpleKeysComposer);
				
		try {
			doc = documentFactory.createDocument(test, componentComposers);
		} catch (ClassNotFoundException e) {
			assertEquals(true, false);
		}
		
		File file = new File(prop.get(PropertyNames.IO_DEST_FILE_NAME));
		if(file.exists()){
			assertEquals(true, file.delete());
		}	
				
		try {
			documentTools.write(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(true, file.exists());		
	}
}
