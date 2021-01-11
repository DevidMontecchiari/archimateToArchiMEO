package main;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import archimateToArchiMEO.Mapper;
import archimateToArchiMEO.archimateDiagram;
import archimateToArchiMEO.xmlModel;
import ontologyManager.Operation;

public class main {

	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
		//String raw=readFileAsString("resources//test.xml");
//		String raw=readFileAsString("resources//MDM.xml");
		
//		String raw=readFileAsString("resources//Interoperability//examples//snippet//Interoperability testing snippet-1.xml");
//		String raw=readFileAsString("resources//Interoperability//examples//snippet//Interoperability testing snippet-2.xml");
//		String raw=readFileAsString("resources//Interoperability//examples//snippet//Interoperability testing snippet-3.xml");
//		String raw=readFileAsString("resources//Interoperability//examples//snippet//Interoperability testing snippet-4.xml");
//		String raw=readFileAsString("resources//Interoperability//examples//snippet//Interoperability testing snippet-5.xml");
//		String raw=readFileAsString("resources//Interoperability//examples//snippet//Interoperability testing snippet-6.xml");
//		String raw=readFileAsString("resources//Interoperability//examples//snippet//Interoperability testing snippet-7.xml");
//		String raw=readFileAsString("resources//Interoperability//examples//snippet//Interoperability testing snippet-8.xml");
//		String raw=readFileAsString("resources//Interoperability//examples//snippet//Interoperability testing snippet-9.xml");
//		String raw=readFileAsString("resources//Interoperability//examples//snippet//Interoperability testing snippet-10.xml");
		String raw=readFileAsString("resources//CheapFlight//Cheapflight Architecture.xml");
			
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
		String imports="# baseURI: http://ikm-group.ch/archiMEO/archimate\r\n" + 
				"# imports: http://ikm-group.ch/archiMEO/emo\r\n" + 
				"# imports: http://ikm-group.ch/archiMEO/eo\r\n" + 
				"# imports: http://ikm-group.ch/archiMEO/top\r\n" + 
				"# imports: http://ikm-group.ch/archimeo/iso42010\r\n" + 
				"\r\n" + 
				"@prefix archi: <http://ikm-group.ch/archiMEO/archimate#> .\r\n" + 
				"@prefix mod: <http://ikm-group.ch/archiMEO/mod#> .\r\n" + 
				"@prefix cc: <http://creativecommons.org/ns#> .\r\n" + 
				"@prefix dc: <http://purl.org/dc/elements/1.1/> .\r\n" + 
				"@prefix emo: <http://ikm-group.ch/archiMEO/emo#> .\r\n" + 
				"@prefix eo: <http://ikm-group.ch/archiMEO/eo#> .\r\n" + 
				"@prefix owl: <http://www.w3.org/2002/07/owl#> .\r\n" + 
				"@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\r\n" + 
				"@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\r\n" + 
				"@prefix rdfsplus: <http://topbraid.org/spin/rdfsplus#> .\r\n" + 
				"@prefix top: <http://ikm-group.ch/archiMEO/top#> .\r\n" + 
				"@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\r\n";
		
		String data=imports+"\n"+mapper.diagramToRDF();
		
		try (PrintStream out = new PrintStream(new FileOutputStream("Resources\\OUTPUT\\"+ mapper.diagram.name +"_Data.ttl"))) {
			out.print(data);
		}
	}
	
	public static String readFileAsString(String fileName)throws Exception 
	{ 
		String data = ""; 
		data = new String(Files.readAllBytes(Paths.get(fileName))); 
		return data; 
	}

}
