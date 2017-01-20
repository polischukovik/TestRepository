package testnm;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class OptionDAO {
	private Map<String, Map<String,  Map<String, String>>> options;
	private Map<String, String> countries = new HashMap<>();
	private Map<String, String> UAcities = new HashMap<>();
	private Map<String, String> UScities = new HashMap<>();
	private Map<String, String> GBcities = new HashMap<>();

	public OptionDAO() {
		super();
		countries.put("EMPTY", "");
		countries.put("UA", "Ukraine");
		countries.put("US", "USA");
		countries.put("GB", "Great Britain");
		
		UAcities.put("Kyiv",   "Kyiv"   );
		UAcities.put("Kharkiv","Kharkiv");
		UAcities.put("Lviv",    "Lviv"    );
		
		UScities.put("Washington","Washington");
		UScities.put("New York",  "New York"  );
		UScities.put("Jersie",     "Jersie"     );
		
		GBcities.put("London",   "London"   );
		GBcities.put("Liverpool","Liverpool");
		GBcities.put("Cardif",    "Cardif"    );
		
		this.options = ImmutableMap.<String, Map<String,  Map<String, String>>>builder()
				.put("country"
						, ImmutableMap.<String, Map<String, String>>builder()
						.put("ALL", countries)
						.build()
						)
				.put("city"
						, ImmutableMap.<String, Map<String, String>>builder()
						.put("UA", UAcities)
						.put("US", UScities)
						.put("GB", GBcities)
						.build()
						)
				.build();
	}
	
	public OptionDAO(Map<String, Map<String,  Map<String, String>>> options) {
		super();
		this.options = options;
	}

	public Map<String, String> find(String type, String value) {		
		Map<String, Map<String, String>> option = options.get(type);
		if(option != null){
			Map<String, String> value_ = option.get(value);
			if(value_ != null){
				return value_;
			}
		}
		return null;
	}
}
