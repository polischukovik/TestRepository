package polischukovik.mslibrary;

import java.util.HashMap;
import java.util.Map;

import polischukovik.domain.enums.NumeratorType;
import polischukovik.domain.enums.PropertyNames;

public class Properties {

	private Map<PropertyNames, String> properties;
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
	}

	public void add(PropertyNames nameEnum, String value){
		properties.put(nameEnum, value);
	}

	public String get(PropertyNames nameEnum, String defaultVal){	
		String value = properties.get(nameEnum);
		
		if(value == null) {
			value = defaultVal;
			//logging.err -> InvalidAttributesException(String.format("Option [%s] value not found.", nameEnum.toString()));
		}
		
		return value;		
	}
	
	public boolean getBoolean(PropertyNames prop, boolean defaultVal){
		String param = this.properties.get(prop);
		boolean pBool = defaultVal;
		if(param.toLowerCase().equals("y") || (param.toLowerCase().equals("n"))){
			pBool = "y".equals(param.toLowerCase());
		}else{
			//logging.err -> IllegalArgumentException(String.format("Cannot convert [%s] to boolean(y/n are permited)", prop.toString()));
		}
		return pBool;
		
	}
	
	public NumeratorType getNumerationStyle(PropertyNames property, NumeratorType defaultStyle) {
		NumeratorType numStyle = defaultStyle; //DEFAULT
//		try{
			String propNumerationStyle = this.get(property, defaultStyle.toString());
//			if(propNumerationStyle == null){
//				throw new IllegalArgumentException(String.format("Warning: There is no property %s. Using default",property));
//			}
		NumeratorType tmpNumerationStyle = Numerator.valueOf(propNumerationStyle); //throws IllegalArgumentException
		
		if(tmpNumerationStyle != null) numStyle = tmpNumerationStyle;
			
//		}catch(IllegalArgumentException e){
//			System.err.println(String.format("Warning: The property %s has inaccepable value. Using default",property));
//			System.err.println(String.format(" Reason:",e.getMessage()));
//		}
//		catch(InvalidAttributesException e){		
//			System.err.println(String.format("Warning: There is no property %s. Using default",property));
//			System.err.println(String.format(" Reason:",e.getMessage()));
//		}
		return numStyle;
	}
}