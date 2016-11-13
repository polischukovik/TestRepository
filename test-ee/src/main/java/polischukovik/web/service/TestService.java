package polischukovik.web.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import polischukovik.domain.QuestionRaw;
import polischukovik.domain.Test;
import polischukovik.services.DocumentComponentComposer;
import polischukovik.ui.Response;
import polischukovik.ui.UserInterfaceSet;

@Service
public class TestService {

	private UserInterfaceSet currentUISet;

	public Response createTest() {
		Response response = new Response(0, "success");
		System.err.println(currentUISet);
		try {
			currentUISet.getiOTools().write(
					currentUISet.getDocumentFactory().createDocument(
							currentUISet.getTestFactory().createTest(
									currentUISet.getQuestionDataSource().parseSource(
											currentUISet.getiOTools().read())), currentUISet.getComponentComposers()));			
		} catch (FileNotFoundException | ClassNotFoundException | IllegalStateException e) {
			String reason = String.format("2Service error: %s is thrown<br>Reason: %s",e.getClass().getName(),e.getMessage());
			response = new Response(1, reason);
		} catch (IOException e) {
			String reason = String.format("3Service error: %s is thrown<br>Reason: %s",e.getClass().getName(),e.getMessage());
			response = new Response(1, reason);
		} catch (Exception e){
			e.printStackTrace();
			String reason = String.format("4Service error: %s is thrown<br>Reason: %s",e.getClass().getName(),e.getMessage());
			response = new Response(1, reason);
		}
		return response;
	}

	public void setCurrentUISet(UserInterfaceSet currentUISet) {
		this.currentUISet = currentUISet;
	}
}
