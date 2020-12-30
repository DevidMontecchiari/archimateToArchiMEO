package archimateToArchiMEO;

import java.util.HashMap;

public class archimateDiagram {

	String raw="";
	String name="";
	HashMap<String,Elements> elements=new HashMap<String,Elements>();
	HashMap<String,Relationships> relationships=new HashMap<String,Relationships>();

	public archimateDiagram(xmlModel xml) {
		// TODO Auto-generated constructor stub

		this.raw=xml.raw;

		this.name= xml.name;
		this.elements= updatedElements(xml.elements);
		this.relationships= updateRelationships(xml.relationships);

	}

	@Override
	public String toString() {
		return name + " [elements=" + elements + ",\nrelationships="
				+ relationships + "]";
	}

	private HashMap<String, Relationships> updateRelationships(String rel) {
		HashMap<String, Relationships> rels=new HashMap<String, Relationships>();

		String[] relationships=rel.split("<relationship ");

		for (int i = 0; i < relationships.length; i++) {
			if (relationships[i].contains("identifier=")) {
				
				Relationships relationship=new Relationships(relationships[i]);
				//System.out.println(relationships[i]);	
				rels.put(relationship.identifier, relationship);
			}
		}
		return rels;
	}

	private HashMap<String, Elements> updatedElements(String el) {
		HashMap<String, Elements> elems= new HashMap<String, Elements>();

		String[] elements=el.split("</element>");

		for (int i = 0; i < elements.length; i++) {
			if(elements[i].contains("<element identifier=")) {
				//				System.out.println(elements[i]);	
				Elements element=new Elements(elements[i]);

				elems.put(element.identifier,element);

				//				System.out.println(elements[i]);	
			}
		}


		return elems;
	}
}
