package polischukovik.properties;

import java.util.List;

import polischukovik.domain.enums.PropertyName;

public interface RequiredPropertyNameProvider {
	
	public List<PropertyName> getRequiredProperties();

}
