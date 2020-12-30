package archimateToArchiMEO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Operation {

	public static int maxcount = 0;
	private ArrayList<OntologyClass> classes; // it contains all the classes of
	// the ontology
	private ArrayList<String> null_values_string;

	public HashMap<String, OntologyClass> archiMEO;
	public HashMap<String, OntologyClass> archimate;
	public HashMap<String, OntologyClass> enterpriseOntology;
	public HashMap<String, OntologyInstance> diagram;

	public ArrayList<OntologyClass> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<OntologyClass> classes) {
		this.classes = classes;
	}

	public ArrayList<OntologyProperty> getProperties() {
		return properties;
	}

	public void setProperties(ArrayList<OntologyProperty> properties) {
		this.properties = properties;
	}

	public ArrayList<OntologyInstance> getInstances() {
		return instances;
	}

	public void setInstances(ArrayList<OntologyInstance> instances) {
		this.instances = instances;
	}

	public ArrayList<String> getOntologyPreamble() {
		return ontologyPreamble;
	}

	public void setOntologyPreamble(ArrayList<String> ontologyPreamble) {
		this.ontologyPreamble = ontologyPreamble;
	}

	public ArrayList<String> getNull_values_string() {
		return null_values_string;
	}

	public void setNull_values_string(ArrayList<String> null_values_string) {
		this.null_values_string = null_values_string;
	}

	private ArrayList<OntologyProperty> properties; // it contains all the
	// properties of the
	// ontology
	private ArrayList<OntologyInstance> instances; // it contains all the
	// instances of the ontology
	private ArrayList<String> ontologyPreamble;


	public Operation() {
		super();
		classes = new ArrayList<OntologyClass>();
		properties = new ArrayList<OntologyProperty>();
		instances = new ArrayList<OntologyInstance>();
		archiMEO= new HashMap<String, OntologyClass>() ;
		archimate= new HashMap<String, OntologyClass>() ;
		enterpriseOntology= new HashMap<String, OntologyClass>() ;
		diagram= new HashMap<String, OntologyInstance>() ;

		ontologyPreamble = new ArrayList<String>();

		null_values_string = new ArrayList<String>();
		null_values_string.add("N/A");
		null_values_string.add("not_specified");
		null_values_string.add("not specified");
		null_values_string.add("not defined");
		null_values_string.add("Not specified");
		null_values_string.add("");
	}

	public static int getMaxcount() {
		return maxcount;
	}

	public static void setMaxcount(int maxcount) {
		Operation.maxcount = maxcount;
	}

	public HashMap<String, OntologyClass> getArchiMEO() {
		return archiMEO;
	}

	public void setArchiMEO(HashMap<String, OntologyClass> archiMEO) {
		this.archiMEO = archiMEO;
	}

	public HashMap<String, OntologyClass> getArchimate() {
		return archimate;
	}

	public void setArchimate(HashMap<String, OntologyClass> archimate) {
		this.archimate = archimate;
	}

	public HashMap<String, OntologyClass> getenterpriseOntology() {
		return enterpriseOntology;
	}

	public void setenterpriseOntology(HashMap<String, OntologyClass> enterpriseOntology) {
		this.enterpriseOntology = enterpriseOntology;
	}

	public HashMap<String, OntologyInstance> getdiagram() {
		return diagram;
	}

	public void setdiagram(HashMap<String, OntologyInstance> diagram) {
		this.diagram = diagram;
	}

	public int[] parseOntology(String path_file) {// this method parse the
		// ontology
		// takes in input the path of the file and it fills the four arrays
		// stored this java-class (classes, properties, instances and the
		// ontologyPreamble)
		// returns an array with the number of classes, properties and instances
		// loaded
		String line = null;
		FileReader reader = null;

		try {
			reader = new FileReader(path_file);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());

		}
		Scanner scanner = new Scanner(reader);
		// Set the variable "startPreamble" to detect when the preamble finish
		boolean preamble = true;
		ArrayList<String> temp_type = null;
		String temp_name = null;
		ArrayList<OntologyAttribute> temp_attributes = null;
		boolean titleLine = true;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();

//			if (preamble) {
//				// here we are in the preamble
//				if (!line.startsWith(".")) {
//					ontologyPreamble.add(line);
//				} else {
//					// here we are in the first dot
//					// where the ontology elements starts
//					preamble = false;
//
//				}
//				// here we are still in the preamble
//				// where the line doesn't start with "."
//
//			} else {
			if (preamble) {
				// here we are in the preamble
				if (!line.startsWith(".")) {
					ontologyPreamble.add(line);
				} else {
					// here we are in the first dot
					// where the ontology elements starts
					preamble = false;

				}
				// here we are still in the preamble
				// where the line doesn't start with "."

			} else {
				// here we are not in the preamble and we start to analyse
				// the elements of the ontology
				if (titleLine && !line.startsWith(".")) {
					// first line of the object of the ontology
					// here we can find prefix:name of the object
					// and we instantiate all the data structures
					temp_type = new ArrayList<String>();
					temp_name = new String();
					temp_attributes = new ArrayList<OntologyAttribute>();

					temp_name = line;
					titleLine = false;
				} else if (!titleLine && line.trim().startsWith(".")) {
					// end of the object of ontology
					titleLine = true;
					for (int t = 0; t < temp_type.size(); t++) {
						if (temp_type.get(t).equals("owl:Class")) {

							// Parsing a Class
							OntologyClass c = new OntologyClass(temp_name, temp_type, temp_attributes);
							if (c.getName().contains("eo:")) {
								enterpriseOntology.put(c.getName(), c);
								//System.out.println(c.getName()+" to enterpriseOntology");
							}else if(c.getName().contains("archi")){
								archimate.put(c.getName(), c);
								//System.out.println(c.getName()+" to archimate");
							}else if(c.getName().contains("archimeo:")){
								archiMEO.put(c.getName(), c);
								//System.out.println(c.getName()+" to archiMEO");
							}

							classes.add(c);
							break;
						} else if (temp_type.get(t).equals("owl:AnnotationProperty")
								|| temp_type.get(t).equals("owl:DatatypeProperty")
								|| temp_type.get(t).equals("owl:DeprecatedProperty")
								|| temp_type.get(t).equals("owl:FunctionalProperty")
								|| temp_type.get(t).equals("owl:ObjectProperty")) {
							// Parsing a Property
							OntologyProperty p = new OntologyProperty(temp_name, temp_type, temp_attributes);
							properties.add(p);
							break;

						} else if (t == temp_type.size() - 1) {
							// Parsing an Istance
							OntologyInstance i = new OntologyInstance(temp_name, temp_type, temp_attributes);
							OntologyClass c = new OntologyClass(temp_name, temp_type, temp_attributes);
							if (i.getName().contains("eo:")) {
								enterpriseOntology.put(i.getName(), c);
								//System.out.println(i.getName()+" to enterpriseOntology");
							}else if(i.getName().contains("archi")){
								archimate.put(i.getName(), c);
								//System.out.println(i.getName()+" to archimate");
							}else if(c.getName().contains("archimeo:")){
								archiMEO.put(i.getName(), c);
								//System.out.println(i.getName()+" to archiMEO");
							}
							instances.add(i);
						}
					}

				} else {
					// body of the object of the ontology
					String[] arraySplittate = parseAttributeName(line);
					if (arraySplittate[0].equals("rdf:type")) {
						temp_type.add(arraySplittate[1].replaceAll(";", "").trim());
					} else {
						// parsing attributes
						OntologyAttribute oa;
						String[] arraySplittate2 = arraySplittate[1].trim().split("\\^\\^");
						/*
						 * splitting the string with "^^" so we can define type and value
						 */

						if (arraySplittate2.length == 2) { // if the split has type and value
							oa = new OntologyAttribute(arraySplittate[0].replaceAll(";", "").trim(),
									arraySplittate2[1].replaceAll(";", "").trim(),
									arraySplittate2[0].replaceAll("\"", "").replaceAll(";", "").trim()); // name,type,value

						} else {
							oa = new OntologyAttribute(arraySplittate[0].replaceAll(";", "").trim(), "",
									arraySplittate2[0].replaceAll(";", "").trim()); // name,type,value
						}
						temp_attributes.add(oa);
					}
				}

			} // end not preamble

		} // end while scanner
		scanner.close();

		return new int[] { classes.size(), properties.size(), instances.size() };

	}// end method

	private String[] parseAttributeName(String attribute_line) { 
		/*
		 * // this method parse the name-value of an Ontology attribute
		 */

		attribute_line.replaceAll(";", "").trim(); // we remove the final ";"
		String[] arraySplittate = attribute_line.trim().split(" ");
		String temp_value = "";
		for (int i = 1; i < arraySplittate.length; i++) {
			temp_value = temp_value + " " + arraySplittate[i].trim();
		}
		return new String[] { arraySplittate[0], temp_value };
	}
}
