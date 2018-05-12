package polischukovik;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.NotEmpty;

public class PropertyComponent implements Serializable {
	
	private PropertyName name;	
	@NotEmpty
	private String value;
	private PropertyType type;
	private String selectValues;
	
	
	
	public PropertyComponent() {
		super();
	}

	public PropertyComponent(PropertyName name, PropertyType type) {
		super();
		this.name = name;
		this.type = type;
		
		switch (type) {
		case SELECT_NT:
			this.selectValues = Arrays.asList(
					NumeratorType.class.getEnumConstants())
					.stream()
					.map(q->String.valueOf(q))
					.collect(Collectors.joining(","));
			break;

		default:
			this.selectValues = "";
			break;
		}
	}
	
	public PropertyName getName() {
		return name;
	}

	public void setName(PropertyName name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public String getSelectValues() {
		return selectValues;
	}

	public void setSelectValues(String selectValues) {
		this.selectValues = selectValues;
	}

	public static List<String> parseSelectProperty(String value){
		return Arrays.asList(value.split(","));
	}

	@Override
	public String toString() {
		return "PropertyComponent [name=" + name + ", value=" + value + ", type=" + type + ", selectValues="
				+ selectValues + "]";
	}

	
}
