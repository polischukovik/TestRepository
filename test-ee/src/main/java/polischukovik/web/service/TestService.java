package polischukovik.web.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import polischukovik.ui.UserInterfaceSet;

@Service
public class TestService {
	
	@Autowired
	UserInterfaceSet currentUISet;

	public String createTest() {
		try {
//			String s = currentUISet.getiOTools().read();
//			List<QuestionRaw> q = currentUISet.getQuestionDataSource().parseSource(s);
//			Test t = currentUISet.getTestFactory().createTest(q);
//			XWPFDocument d = currentUISet.getDocumentFactory().createDocument(t, currentUISet.getComponentComposers());
//			currentUISet.getiOTools().write(d);			
			currentUISet.getiOTools().write(
					currentUISet.getDocumentFactory().createDocument(
							currentUISet.getTestFactory().createTest(
									currentUISet.getQuestionDataSource().parseSource(
											currentUISet.getiOTools().read())), currentUISet.getComponentComposers()));			
		} catch (FileNotFoundException | ClassNotFoundException | IllegalStateException e) {
			e.printStackTrace();
			return "Error in Service1: " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return "Error in Service2: " + e.getMessage();
		} catch (Exception e){
			e.printStackTrace();
			return "Error in Service3: " + e.getMessage();			
		}
		return "Operation completed successfully";
	}

}
