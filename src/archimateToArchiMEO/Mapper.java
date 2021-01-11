package archimateToArchiMEO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ontologyManager.OntologyAttribute;
import ontologyManager.OntologyInstance;
import ontologyManager.Operation;

public class Mapper {

	Operation ontology;
	public archimateDiagram diagram;
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
		String diagramRDF="mod:"+validateRDFid(this.diagram.name)+"\r";
		diagramRDF+="  rdf:type archi:model ;\r";
		
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
				OntologyAttribute label= new OntologyAttribute( "  rdfs:label","","\""+elem.name.trim()+"\"");
				attributes.add(label);
				OntologyAttribute id= new OntologyAttribute( "  rdfs:comment","","\""+elem.identifier.trim()+"\"");
				attributes.add(id);

				OntologyInstance c= new OntologyInstance(instanceName, types, attributes);

				this.ontology.diagram.put(c.getName(), c);

				rdf+=c.toRDF()+"\n";
				diagramRDF+="  archi:hasModellingElement " +c.getName()+" ;\r";
			}else{
				System.out.print("\nMissing!! "+ elem.getXsiType());
			};			
		}

		HashMap<String, Relationships> relationships = this.diagram.relationships;

		for (Map.Entry<String, Relationships> item : relationships.entrySet()) {
			String key = item.getKey();
			Relationships rel = item.getValue();
//			System.out.print(rel.toString()+"\n");
			
			if(this.ontology.archimate.containsKey("archi:"+rel.getXsiType())){

				

				ArrayList<String> types=new ArrayList<String>();
				types.add("archi:"+rel.getXsiType());

				ArrayList<OntologyAttribute> attributes=new ArrayList<OntologyAttribute>();
				//				OntologyAttribute label= new OntologyAttribute( "rdfs:label","","\""+rel.name.trim()+"\"");
				//				attributes.add(label);

				String domainName=diagram.elements.get(rel.source).name;
				OntologyAttribute domain= new OntologyAttribute( "  rdfs:domain","","mod:"+validateRDFid(domainName));
				attributes.add(domain);

				String rangeName=diagram.elements.get(rel.target).name;
				OntologyAttribute range= new OntologyAttribute( "  rdfs:range","","mod:"+validateRDFid(rangeName));
				attributes.add(range);

				OntologyAttribute id= new OntologyAttribute( "  rdfs:comment","","\""+rel.identifier.trim()+"\"");
				attributes.add(id);

				String instanceName="mod:"+validateRDFid(validateRDFid(domainName)+rel.getXsiType()+validateRDFid(rangeName));
				OntologyInstance c= new OntologyInstance(instanceName, types, attributes);

				/*
				 * TODO: ADD influence modifier as archi:InfluenceSignOrStrenght based on e.g., {++, +, 0, -, --} or [0..10]. Default unspecified.
				 */
				this.ontology.diagram.put(c.getName(), c);

				rdf+=c.toRDF()+"\n";
				diagramRDF+="  archi:hasModellingElement " +c.getName()+" ;\r";
			}else{
				System.out.print("\nMissing!! "+ rel.getXsiType());
			};			
		}

		return rdf + diagramRDF +".\r";
	}
	public String validateRDFid(String s) {
		String newS=s;

		newS=newS.trim().replaceAll("[^a-zA-Z0-9]+","_");

		return newS;
	}



}
