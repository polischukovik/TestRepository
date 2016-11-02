package polischukovik.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import polischukovik.domain.enums.NumeratorType;
import polischukovik.domain.enums.PropertyName;
import polischukovik.domain.enums.PropertyType;

public class PropertyComponent {
	
	private PropertyName name;	
	private String group;
	private String value;
	private PropertyType type;
	private String selectValues;
	
	public PropertyComponent(PropertyName name, String group, PropertyType type) {
		super();
		this.name = name;
		this.group = group;
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
	
	public void addValue(String value){
		this.value = value;
	}
	
	public PropertyName getName() {
		return name;
	}

	public void setName(PropertyName name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
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
		return "PropertyComponent [name=" + name + ", group=" + group + ", value=" + value + ", type=" + type
				+ ", selectValues=" + selectValues + "]";
	}
}
