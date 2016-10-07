package polischukovik.mslibrary;

import java.util.HashMap;
import java.util.Map;

import polischukovik.domain.enums.NumeratorType;
import polischukovik.domain.enums.PropertyNames;

public class Properties {

	private Map<PropertyNames, String> properties;
	private Map<PropertyNames, String> defaults;
	
//	private final static TreeMap<String, NAMES> namingMap = new TreeMap<>();
//	
//	static{
//		namingMap.put("VARIANTS", Properties.NAMES.VARIANTS);
//		namingMap.put("QUESTIONS", Properties.NAMES.QUESTIONS);
//		namingMap.put("MARK", Properties.NAMES.MARK);
//		namingMap.put("MIX_ANSWERS", Properties.NAMES.MIX_ANSWERS);
//		namingMap.put("OUTPUT_FILE_NAME", Properties.NAMES.OUTPUT_FILE_NAME);
//		namingMap.put("TEST_NAME", Properties.NAMES.TEST_NAME);
//		namingMap.put("QUESTION_PUNCTUATION", Properties.NAMES.QUESTION_PUNCTUATION);
//		namingMap.put("ANSWER_PUNCTUATION", Properties.NAMES.ANSWER_PUNCTUATION);
//		namingMap.put("VARIANT_NAME", Properties.NAMES.VARIANT_NAME);
//		namingMap.put("F_QUESTION_BOLD", Properties.NAMES.F_QUESTION_BOLD);
//		namingMap.put("F_QUESTION_SPACING", Properties.NAMES.F_QUESTION_SPACING);
//		namingMap.put("P_VARIANT_NUMERATION", Properties.NAMES.P_VARIANT_NUMERATION);
//		namingMap.put("P_QUESTION_NUMERATION", Properties.NAMES.P_QUESTION_NUMERATION);
//		namingMap.put("P_ANSWER_NUMERATION", Properties.NAMES.P_ANSWER_NUMERATION);
//		
//	}
	
	public Properties() {
		super();
		this.properties = new HashMap<>();
		this.defaults = new HashMap<>();
	}

	public void add(PropertyNames nameEnum, String value, String defaultVal){
		properties.put(nameEnum, value);
		defaults.put(nameEnum, defaultVal);
	}

	public String get(PropertyNames nameEnum){
		return properties.containsKey(nameEnum) ? properties.get(nameEnum) : defaults.get(nameEnum);		
	}
}