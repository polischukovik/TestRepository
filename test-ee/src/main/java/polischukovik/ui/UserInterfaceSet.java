package polischukovik.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import polischukovik.impl.IOToolsImpl;
import polischukovik.properties.RequiredPropertyNameProvider;
import polischukovik.services.DocumentComponentComposer;
import polischukovik.services.DocumentFactory;
import polischukovik.services.IOTools;
import polischukovik.services.QuestionDataSource;
import polischukovik.services.TestFactory;

public class UserInterfaceSet {
	private int id;
	private QuestionDataSource questionDataSource;
	private TestFactory testFactory;
	private DocumentFactory documentFactory;
	private IOTools iOTools;
	private List<DocumentComponentComposer> componentComposers;

	
	@SuppressWarnings("unused")
	private UserInterfaceSet() {
		super();
	}

	public UserInterfaceSet(int id, QuestionDataSource questionDataSource, TestFactory testFactory,
			DocumentFactory documentFactory, IOTools iOTools, List<DocumentComponentComposer> componentComposers) {
		super();
		this.id = id;
		this.questionDataSource = questionDataSource;
		this.testFactory = testFactory;
		this.documentFactory = documentFactory;
		this.iOTools = iOTools;
		this.componentComposers = componentComposers;
	}
	
	public List<RequiredPropertyNameProvider> getPropertyProviders(){
		List<RequiredPropertyNameProvider> result = new ArrayList<>(
				Arrays.asList(
					 (RequiredPropertyNameProvider)questionDataSource 
					,(RequiredPropertyNameProvider)testFactory
					,(RequiredPropertyNameProvider)documentFactory
					,(RequiredPropertyNameProvider)iOTools
					));
		result.addAll(componentComposers.stream().map(e -> (RequiredPropertyNameProvider)e).collect(Collectors.toList()));
		return result;
	}
	
	public QuestionDataSource getQuestionDataSource() {
		return questionDataSource;
	}

	public void setQuestionDataSource(QuestionDataSource questionDataSource) {
		this.questionDataSource = questionDataSource;
	}

	public TestFactory getTestFactory() {
		return testFactory;
	}

	public void setTestFactory(TestFactory testFactory) {
		this.testFactory = testFactory;
	}

	public DocumentFactory getDocumentFactory() {
		return documentFactory;
	}

	public void setDocumentFactory(DocumentFactory documentFactory) {
		this.documentFactory = documentFactory;
	}

	public List<DocumentComponentComposer> getComponentComposers() {
		return componentComposers;
	}

	public void setComponentComposers(List<DocumentComponentComposer> componentComposers) {
		this.componentComposers = componentComposers;
	}	
	
	public IOTools getiOTools() {
		return iOTools;
	}

	public void setiOTools(IOTools iOTools) {
		this.iOTools = iOTools;
	}

	public int getId() {
		return id;
	}
}
