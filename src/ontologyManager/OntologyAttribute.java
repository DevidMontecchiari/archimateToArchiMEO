package ontologyManager;


public class OntologyAttribute {


	@Override
	public String toString() {
		return "OntologyAttribute [name=" + name + ", type=" + type + ", value=" + value + "]";
	}

	private String name;
	private String type;
	private String value;

	public OntologyAttribute(String name, String type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getNameWithoutPrefix() {
		String[] arraySplittate = this.name.split(":");
		return arraySplittate[1];
	}



}