package polischukovik.web.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import polischukovik.ui.Response;
import polischukovik.ui.UserInterfaceSet;

@Service
public class TestService {
	
	@Autowired
	UserInterfaceSet currentUISet;

	public Response createTest() {
		Response response = new Response(0, "success");
		try {		
			currentUISet.getiOTools().write(
					currentUISet.getDocumentFactory().createDocument(
							currentUISet.getTestFactory().createTest(
									currentUISet.getQuestionDataSource().parseSource(
											currentUISet.getiOTools().read())), currentUISet.getComponentComposers()));			
		} catch (FileNotFoundException | ClassNotFoundException | IllegalStateException e) {
			String reason = String.format("Service error: %s is thrown<br>Reason: %s",e.getClass().getName(),e.getMessage());
			response = new Response(1, reason);
		} catch (IOException e) {
			String reason = String.format("Service error: %s is thrown<br>Reason: %s",e.getClass().getName(),e.getMessage());
			response = new Response(1, reason);
		} catch (Exception e){
			String reason = String.format("Service error: %s is thrown<br>Reason: %s",e.getClass().getName(),e.getMessage());
			response = new Response(1, reason);
		}
		return response;
	}

}
