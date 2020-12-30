package archimateToArchiMEO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Mapper {

	Operation ontology;
	archimateDiagram diagram;
	public Operation getOntology() {
		return ontology;
	}
	public void setOntology(Operation ontology) {
		this.ontology = ontology;
	}
	public archimateDiagram getDiagram() {
		return diagram;
	}
	public void setDiagram(archimateDiagram diagram) {
		this.diagram = diagram;
	}
	public Mapper(Operation ontology, archimateDiagram diagram) {
		super();
		this.ontology = ontology;
		this.diagram = diagram;
	}
	
	public String diagramToRDF() {
		String rdf="";
		HashMap<String, Elements> elements = this.diagram.elements;
		
		for (Map.Entry<String, Elements> item : elements.entrySet()) {
			String key = item.getKey();
			Elements elem = item.getValue();
//			System.out.print(elem.toString()+"\n");
			
			if(this.ontology.archimate.containsKey("archi:"+elem.getXsiType())){
//				System.out.print(elem.getXsiType()+"\n");
				String instanceName="mod:"+validateRDFid(elem.name.trim());
				
				ArrayList<String> types=new ArrayList<String>();
				types.add("archi:"+elem.getXsiType());
								
				ArrayList<OntologyAttribute> attributes=new ArrayList<OntologyAttribute>();
				OntologyAttribute label= new OntologyAttribute( "rdfs:label","","\""+elem.name.trim()+"\"");
				attributes.add(label);
				
				OntologyInstance c= new OntologyInstance(instanceName, types, attributes);
												
				this.ontology.diagram.put(c.getName(), c);
			//	System.out.print(c.toString()+"\n");
			}else{
				System.out.print("\nMissing!! "+ elem.getXsiType());
			};			
		}
		
		
		
		rdf=ontology.diagram.toString();
		/*
		 * TODO rdf print and eo check
		 */
		
		return rdf;
	}
	public String validateRDFid(String s) {
		String validated= s.replace(" ","_");
		
		return validated;
	}
	
}
