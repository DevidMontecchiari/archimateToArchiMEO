package archimateToArchiMEO;

public class Relationships {
	
	
	String raw="";
	String identifier="";
	String xsiType="";
	String source="";
	String target="";
	String accessType="";
	
	public Relationships(String raw) {
		this.raw=raw;
		updateElementData(raw);
	}
	
	@Override
	public String toString() {
		return "Relationships [identifier=" + identifier + ", xsiType=" + xsiType + ", source="
				+ source + ", target=" + target + ", accessType=" + accessType + "]";
	}

	public void updateElementData(String raw) {
		this.raw=raw;
//		System.out.println(raw);
		String rawCleaned=raw.replace("</relationships>","").replace("/>","").replace("\"","");
		String splitted[] =rawCleaned.split(" ");
		
		for (int i = 0; i < splitted.length; i++) {
			
						
			switch ((String) splitted[i].subSequence(0,5)) {
			
			case "acces":
				this.accessType=splitted[i].replace("accessType=","").replace("/>","");
				break;
				
			case "ident":
				this.identifier=splitted[i].replace("identifier=","");
				break;

			case "sourc":
				this.source=splitted[i].replace("source=","");
				break;

			case "targe":
				this.target=splitted[i].replace("target=","");
				break;
				
			case "xsi:t":
			
				this.xsiType=splitted[i].replace("xsi:type=","");
				break;

			default:
				System.out.println("!!!!!!not matched"+ splitted[i]);
				break;
				
			}
		}
//		System.out.println(this.toString()+"\n\n");
	}	
}
