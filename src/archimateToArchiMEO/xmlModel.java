package archimateToArchiMEO;

import java.nio.file.Files;
import java.nio.file.Paths;


public class xmlModel {

	String raw="";
	String name="";
	String elements="";
	String relationships="";
	String propertyDefinitions="";
	String views="";


	public xmlModel(String raw) throws Exception {
		this.raw=raw;
		updateXML(raw);
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getElements() {
		return elements;
	}

	public void setElements(String elements) {
		this.elements = elements;
	}

	public String getRelationships() {
		return relationships;
	}

	public void setRelationships(String relationships) {
		this.relationships = relationships;
	}

	public String getPropertyDefinitions() {
		return propertyDefinitions;
	}

	public void setPropertyDefinitions(String propertyDefinitions) {
		this.propertyDefinitions = propertyDefinitions;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public void updateXML(String raw) throws Exception {

		this.raw = raw;
		//System.out.println(raw);
		
		if(raw.contains("<name xml:lang=\"en\">")) {
			this.name= (String) this.raw.subSequence(this.raw.indexOf("<name xml:lang=\"en\">")+ ("<name xml:lang=\"en\">").length(), this.raw.indexOf("</name>"));
//			System.out.println("name "+this.name);
		}else {
			this.name= (String) this.raw.subSequence(this.raw.indexOf("<name>")+ ("<name>").length(), this.raw.indexOf("</name>"));
//			System.out.println("name "+this.name);
		}
		
		this.elements= (String) this.raw.subSequence(this.raw.indexOf("<elements>") , this.raw.indexOf("</elements>")+ ("</elements>").length());
		//		System.out.println(this.elements);
		this.relationships= (String) this.raw.subSequence(this.raw.indexOf("<relationships>") , this.raw.indexOf("</relationships>")+ ("</relationships>").length());
		//		System.out.println(this.relationships);
		if(raw.contains("<propertyDefinitions>")) {
			this.propertyDefinitions= (String) this.raw.subSequence(this.raw.indexOf("<propertyDefinitions>") , this.raw.indexOf("</propertyDefinitions>")+ ("</propertyDefinitions>").length());
			//			System.out.println(this.propertyDefinitions);	
		}else {
			this.propertyDefinitions="";
		}
		if(raw.contains("<views>")) {
			this.views= (String) this.raw.subSequence(this.raw.indexOf("<views>") , this.raw.indexOf("</views>")+ ("</views>").length());
			//			System.out.println(this.views);	
		}else {
			this.views="";
		}
	}
}
