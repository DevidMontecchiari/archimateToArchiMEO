package archimateToArchiMEO;

public class Elements {
	/*
	 * <element identifier="Id-Jl1uNK6GAqACZxRB" xsi:type="DataObject">
			<name>Financial Data ERP</name>
		</element>
	 */
	
	String raw="";
	@Override
	public String toString() {
		return name+ " [identifier=" + identifier + ", xsiType=" + xsiType+ "]";
	}

	String identifier="";
	String xsiType="";
	String name="";
	
	public Elements(String raw) {
		super();
		this.raw = raw;
		updateElementData(raw);
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
	public String getXsiType() {
		return xsiType;
	}
	public void setXsiType(String xsiType) {
		this.xsiType = xsiType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void updateElementData(String raw) {
		this.raw=raw;
//		System.out.println(raw);
		this.name= (String) this.raw.subSequence(this.raw.indexOf("<name>")+ ("<name>").length(), this.raw.indexOf("</name>"));
//		System.out.println(this.name);
		this.identifier= (String) this.raw.subSequence(this.raw.indexOf("<element identifier=\"")+ ("<element identifier=\"").length() , this.raw.indexOf("\" xsi:type=\""));
//		System.out.println(this.identifier);
		String xsiTypeRaw=(String) this.raw.subSequence(this.raw.indexOf("xsi:type=\"")+ ("xsi:type=\"").length() , this.raw.indexOf("<name>"));
		this.xsiType=(String) xsiTypeRaw.subSequence(0, xsiTypeRaw.indexOf("\""));
//		System.out.println(this.xsiType);
		
	}

	
}
