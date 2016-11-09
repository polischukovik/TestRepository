package polischukovik.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import polischukovik.domain.enums.NumeratorType;
import polischukovik.domain.enums.PropertyName;
import polischukovik.domain.enums.PropertyType;

public class PropertyComponent implements Serializable {
	
	private PropertyName name;	
	private String group;	
	private PropertyType type;
	
	@NotEmpty
	private String value;
	private boolean bool;
	private List<String> selectValues;
	
	public PropertyComponent() {
		super();
	}

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
					.collect(Collectors.toList());
			break;
		case BOOLEAN:
			this.bool = Boolean.valueOf(value);
			break;

		default:
			this.selectValues = Arrays.asList("");
			break;
		}
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
		if(PropertyType.BOOLEAN.equals(this.type)){
			this.setBool(Boolean.valueOf(value));
		}
		this.value = value;
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public List<String> getSelectValues() {
		return selectValues;
	}

	public void setSelectValues(List<String> selectValues) {
		this.selectValues = selectValues;
	}

	public static List<String> parseSelectProperty(String value){
		return Arrays.asList(value.split(","));
	}
	public boolean getBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	@Override
	public String toString() {
		return "PropertyComponent [name=" + name + ", group=" + group + ", type=" + type + ", value=" + value
				+ ", bool=" + bool + ", selectValues=" + selectValues + "]";
	}

	
}
