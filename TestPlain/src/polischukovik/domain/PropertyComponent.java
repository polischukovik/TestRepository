package polischukovik.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import polischukovik.domain.enums.NumeratorType;
import polischukovik.domain.enums.PropertyName;
import polischukovik.domain.enums.PropertyType;

public class PropertyComponent implements Serializable {
	
	private PropertyName name;	
	private String group;	
	private PropertyType type;
	

	private String value;
	private Boolean bool;
	private List<String> selectValues;
	private MultipartFile file;
	
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
			
		case FILE:
			this.file = null;
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
	public Boolean getBool() {
		return bool == null ? false : bool;
	}

	public void setBool(Boolean bool) {
		this.value = String.valueOf(bool);
		this.bool = bool;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "PropertyComponent [name=" + name + ", group=" + group + ", type=" + type + ", value=" + value
				+ ", bool=" + bool + ", selectValues=" + selectValues + ", file=" + file + "]";
	}
	
}