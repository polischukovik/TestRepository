package polischukovik.mslibrary;

import java.util.TreeMap;

import polischukovik.domain.enums.NumeratorType;

public class Numerator {
	private NumeratorType type;
	private int cnt;
	private final static TreeMap<Integer, String> romanDictMap = new TreeMap<>();
	private final static TreeMap<String, NumeratorType> namingMap = new TreeMap<>();
	static {

        romanDictMap.put(1000, "M");
        romanDictMap.put(900, "CM");
        romanDictMap.put(500, "D");
        romanDictMap.put(400, "CD");
        romanDictMap.put(100, "C");
        romanDictMap.put(90, "XC");
        romanDictMap.put(50, "L");
        romanDictMap.put(40, "XL");
        romanDictMap.put(10, "X");
        romanDictMap.put(9, "IX");
        romanDictMap.put(5, "V");
        romanDictMap.put(4, "IV");
        romanDictMap.put(1, "I");
        
        namingMap.put("ROMAN", NumeratorType.ROMAN);
        namingMap.put("NUMERIC",NumeratorType.NUMERIC);
        namingMap.put("ALPHABETIC", NumeratorType.ALPHABETIC);
        namingMap.put("ALPHABETIC_CAPS", NumeratorType.ALPHABETIC_CAPS);	
    }
	
	public Numerator(NumeratorType type) {
		super();
		this.type = type;
		cnt = 0;
	}
	
	public String getNext(){
		switch (type) {
		case ROMAN:
			return nextGreece();
		case NUMERIC:
			return nextInt();
		case ALPHABETIC:
			return nextLetter();
		case ALPHABETIC_CAPS:
			return nextLetter().toUpperCase();

		default:
			return "0";
		}
	}

	private String nextInt(){
		return String.valueOf(++cnt);		
	}
	
	private String nextLetter() {
		int first = 96;
		++cnt;
		
		return String.valueOf(Character.toChars(first + cnt));
	}
	
	private String nextGreece(){
		return toRoman(++cnt);		
	}

    private final static String toRoman(int number) {
        int l =  romanDictMap.floorKey(number);
        if ( number == l ) {
            return romanDictMap.get(number);
        }
        return romanDictMap.get(l) + toRoman(number-l);
    }
    
    public static NumeratorType valueOf(String str)  { 
    	NumeratorType type = namingMap.get(str);
    	
    	if(type == null) return null;// throw new InvalidAttributesException(String.format("Unaccepable value %s.", str));
    	
    	return type;		
    }

}
