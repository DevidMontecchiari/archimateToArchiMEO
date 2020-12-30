package ontologyManager;

import java.util.ArrayList;

//This Java-class defines an instance in the Ontology

public class OntologyInstance {
	@Override
	public String toString() {
		return "OntologyInstance [name=" + name + ", types=" + types + ", attributes=" + attributes + "]";
	}
	private String name;
	private ArrayList<String> types;
	private ArrayList<OntologyAttribute> attributes;

	public OntologyInstance(String name,  ArrayList<String> types, ArrayList<OntologyAttribute> attributes) {
		super();
		this.name = name;
		this.types = types;
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public  ArrayList<String> getTypes() {
		return types;
	}
	public void setTypes( ArrayList<String> types) {
		this.types = types;
	}
	public ArrayList<OntologyAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<OntologyAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getNameWithoutPrefix() {
		String[] arraySplittate = this.name.split(":");
		return arraySplittate[1];
	}
	public String getLabel(){
		String result = "";
		for (int i = 0; i < attributes.size(); i++){
			if (attributes.get(i).getName().equals("rdfs:label")){
				result = attributes.get(i).getValue();
			}
		}
		return result.replaceAll("\"","");
	}

	public String toRDF() {
		String rdf="";
		String prefix="mod:";
		rdf+=name + "\r";
		for (int i = 0; i < this.types.size(); i++) {
			rdf+="  rdf:type" +" "+ this.types.get(i) +" ;\r";
		}
		for (int i = 0; i < this.attributes.size(); i++) {
			OntologyAttribute attribute = this.attributes.get(i);
			rdf+=attribute.getName()+" " + attribute.getValue()+" ;\r";
		}
		return rdf+".\n";	}	
}
