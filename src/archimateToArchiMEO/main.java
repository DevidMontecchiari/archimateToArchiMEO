package archimateToArchiMEO;

import java.nio.file.Files;
import java.nio.file.Paths;

public class main {

	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
		//String raw=readFileAsString("resources//test.xml");
		String raw=readFileAsString("resources//MDM.xml");
		xmlModel xmlModel=new xmlModel(raw);
		
		archimateDiagram model=new archimateDiagram(xmlModel);
		//System.out.println(model.toString());
		
		String pathArchiMEO = "resources//ArchiMEO//";
		String pathArchimate = "resources//ARCHIMATE//";
		String pathEO = "resources//EO//";
		String pathBMC = "resources//BMC//";
		
		Operation ontology = new Operation();
		
		ontology.parseOntology(pathArchiMEO + "ArchiMEO.ttl");
		ontology.parseOntology(pathArchiMEO + "ARCHIMATE//archimate.ttl");
		ontology.parseOntology(pathArchiMEO + "EO//EO.ttl");
		
//		System.out.println(ontology.archimate.toString());
//		System.out.println(ontology.enterpriseOntology.toString());
		
		Mapper mapper=new Mapper(ontology, model);
		
		System.out.print(mapper.diagramToRDF());
	}
	
	public static String readFileAsString(String fileName)throws Exception 
	{ 
		String data = ""; 
		data = new String(Files.readAllBytes(Paths.get(fileName))); 
		return data; 
	}

}
