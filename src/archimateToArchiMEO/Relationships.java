package archimateToArchiMEO;

public class Relationships {


	String raw="";
	String name="";
	String identifier="";
	String xsiType="";
	String source="";
	String target="";
	String accessType="";
	String modifier="";
	String isDirected="";
	String propertyRaw="";

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXsiType() {
		return xsiType;
	}

	public void setXsiType(String xsiType) {
		this.xsiType = xsiType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public Relationships(String raw) {
		this.raw=raw;
		updateElementData(raw);
	}

	@Override
	public String toString() {
		return "Relationships [raw=" + raw + ", name=" + name + ", identifier=" + identifier + ", xsiType=" + xsiType
				+ ", source=" + source + ", target=" + target + ", accessType=" + accessType + ", modifier=" + modifier
				+ "]";
	}

	public void updateElementData(String raw) {
		this.raw=raw;
		
		String relNameRaw="";
		if(raw.contains("<name xml:lang=\"en\">")) {
			this.name= (String) this.raw.subSequence(this.raw.indexOf("<name xml:lang=\"en\">")+ ("<name xml:lang=\"en\">").length(), this.raw.indexOf("</name>"));
			relNameRaw=(String) this.raw.subSequence(this.raw.indexOf(">"), this.raw.indexOf("</name>")+ ("</name>").length());

			raw=raw.replace(relNameRaw," />");
			raw=raw.replace("</relationship>"," ");
		}else if(raw.contains("<name>")) {
			this.name= (String) this.raw.subSequence(this.raw.indexOf("<name>")+ ("<name>").length(), this.raw.indexOf("</name>"));
			relNameRaw=(String) this.raw.subSequence(this.raw.indexOf(">"), this.raw.indexOf("</name>")+ ("</name>").length());
			raw=raw.replace(relNameRaw," />");
			raw=raw.replace("</relationship>"," ");
		}
		
		if(raw.contains("<properties>")) {
			this.name= (String) this.raw.subSequence(this.raw.indexOf("<properties>")+ ("<properties>").length(), this.raw.indexOf("</properties>"));
			relNameRaw=(String) this.raw.subSequence(this.raw.indexOf(">"), this.raw.indexOf("</properties>")+ ("</properties>").length());

			raw=raw.replace(relNameRaw," />");
			raw=raw.replace("</relationship>"," ");
			this.propertyRaw=(String) this.raw.subSequence(this.raw.indexOf("<properties>"), this.raw.indexOf("</properties>")+ ("</properties>").length());
		}

		String rawCleaned=raw.replace("</relationships>","").replace("/>","").replace("\"","").trim();

		String splitted[] =rawCleaned.split(" ");

		for (int i = 0; i < splitted.length; i++) {
			
			switch ((String) splitted[i].subSequence(0,5)) {

			case "acces":
				this.accessType=splitted[i].replace("accessType=","").replace("/>","").trim();
				break;

			case "ident":
				this.identifier=splitted[i].replace("identifier=","").trim();
				break;

			case "sourc":
				this.source=splitted[i].replace("source=","").trim();
				break;

			case "targe":
				this.target=splitted[i].replace("target=","").trim();
				break;

			case "xsi:t":

				this.xsiType=splitted[i].replace("xsi:type=","").trim();
				break;	

			case "modif":
				this.modifier=splitted[i].replace("modifier=","").trim();
				break;

			case "isDir":
				this.modifier=splitted[i].replace("isDirected=","").trim();
				break;

			default:
				System.out.println("!!!!!!not matched"+ splitted[i]);
				break;

			}
		}

		this.name=this.source +" To "+this.target;

		//		System.out.println(this.toString()+"\n\n");
	}	
}
