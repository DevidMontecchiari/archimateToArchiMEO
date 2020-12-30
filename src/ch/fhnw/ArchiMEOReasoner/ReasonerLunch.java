package ch.fhnw.ArchiMEOReasoner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.topbraid.shacl.rules.RuleUtil;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.shacl.vocabulary.SH;
import org.topbraid.spin.util.JenaUtil;


import org.apache.jena.reasoner.ValidityReport;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Marker;
//import org.slf4j.MarkerFactory;

import java.nio.file.Path;

import java.util.Iterator;

/**
 * Given a TBOX and ABOX returns inferences
 *
 */
public class ReasonerLunch {
	//	private static Logger logger = LoggerFactory.getLogger(App.class);
	//
	//	private static final Marker WTF_MARKER = MarkerFactory.getMarker("WTF");

	public static void main(String ...args) {

		Path path = Paths.get(".").toAbsolutePath().normalize();

		//Examples
		// RULE EXECUTION / CLASSIFICATION
//				String data = "file:" + path.toFile().getAbsolutePath() + "\\src\\main\\resources\\Examples\\rectangles.ttl";
//				String shape = "file:" + path.toFile().getAbsolutePath() + "\\src\\main\\resources\\Examples\\rectangleRules.ttl";

		// VALIDATION EXAMPLE
//				String data = "file:" + path.toFile().getAbsolutePath() + "\\src\\main\\resources\\Examples\\person.ttl";
//				String shape = "file:" + path.toFile().getAbsolutePath() + "\\src\\main\\resources\\Examples\\personShape.ttl";

		// CLASSIFICATION EXAMPLE
//				String data = "file:" + path.toFile().getAbsolutePath() + "\\src\\main\\resources\\Examples\\bakery.ttl";
//				String shape = "file:" + path.toFile().getAbsolutePath() + "\\src\\main\\resources\\Examples\\bakeryRules.ttl";

		//ArchiMEO MDM
//		String data = "file:" + path.toFile().getAbsolutePath()+ "resources\\ArchiMEO\\ARCHIMATE\\ArchiMate.ttl";
//		String data1 = "file:" + path.toFile().getAbsolutePath()+ "resources\\ArchiMEO\\ARCHIMATE\\MDM\\ArchiMateData.ttl";
//		String shape = "file:" + path.toFile().getAbsolutePath()+ "resources\\ArchiMEO\\ARCHIMATE\\MDM\\ArchiMateShapes.ttl";

		String data1 = "file:" + path.toFile().getAbsolutePath()+ "\\resources\\ArchiMEO\\ARCHIMATE\\MDM\\ArchiMateData-test.ttl";
		String shape = "file:" + path.toFile().getAbsolutePath()+ "\\resources\\ArchiMEO\\ARCHIMATE\\MDM\\ArchiMateShapes-test.ttl";
		
		Model dataModel = JenaUtil.createDefaultModel();
		Model shapeModel = JenaUtil.createDefaultModel();
		
		shapeModel.read(shape);

		executeShaclRules(dataModel, shapeModel, path );
		validateRDF(dataModel, shapeModel, path );
		classifyRDF(dataModel, shapeModel, path );

	}
	
	private static void executeShaclRules(Model dataModel, Model shapeModel, Path outputPath ){
		try {	
			Model inferenceModel = JenaUtil.createDefaultModel();      
			inferenceModel = RuleUtil.executeRules(dataModel, shapeModel, inferenceModel, null);

			String inferences = outputPath.toFile().getAbsolutePath() + "/resources/output/resultRuleExecution.ttl";
			File inferencesFile = new File(inferences);
			inferencesFile.createNewFile();     
			OutputStream reportOutputStream = new FileOutputStream(inferencesFile);

			RDFDataMgr.write(reportOutputStream, inferenceModel, RDFFormat.TTL);
			System.out.println("rule executed");
		} catch (Exception e ) {
			e.printStackTrace();
			//			catch (Throwable t) {
			//			logger.error(WTF_MARKER, t.getMessage(), t);
		}   

	}
	private static void validateRDF(Model dataModel, Model shapeModel, Path outputPath ){
		try {		

			Resource reportResource = ValidationUtil.validateModel(dataModel, shapeModel, true);
			boolean conforms  = reportResource.getProperty(SH.conforms).getBoolean();
			//			logger.trace("Conforms = " + conforms);

			if (!conforms) {
				String report = outputPath.toFile().getAbsolutePath() + "/resources/output/report.ttl";
				File reportFile = new File(report);
				reportFile.createNewFile();     
				OutputStream reportOutputStream = new FileOutputStream(reportFile);

				RDFDataMgr.write(reportOutputStream, reportResource.getModel(), RDFFormat.TTL);
				System.out.println("Validation performed");
			}

		} catch (Exception e ) {
			e.printStackTrace();
			//			catch (Throwable t) {
			//			logger.error(WTF_MARKER, t.getMessage(), t);
		}	
	}
	private static void classifyRDF(Model dataModel, Model shapeModel, Path outputPath ){
		try {	
			Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();

			InfModel infModel = ModelFactory.createInfModel(reasoner, dataModel);
			ValidityReport validity = infModel.validate();
			if (!validity.isValid()) {
				//				logger.trace("Conflicts");
				//				for (Iterator i = validity.getReports(); i.hasNext(); ) {
				//					logger.trace(" - " + i.next());
				//				} 
				String errorLog="";
				for (Iterator i = validity.getReports(); i.hasNext(); ) {
					errorLog+=i.next();
				}
				System.out.println();
			} else {

				Model inferenceModel = JenaUtil.createDefaultModel(); 
				inferenceModel = RuleUtil.executeRules(infModel, shapeModel, inferenceModel, null);        
				String inferences = outputPath.toFile().getAbsolutePath() + "/resources/output/classificationResult.ttl";;
				File inferencesFile = new File(inferences);
				inferencesFile.createNewFile();     
				OutputStream reportOutputStream = new FileOutputStream(inferencesFile);        
				RDFDataMgr.write(reportOutputStream, inferenceModel, RDFFormat.TTL);
				System.out.println("Classification executed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//			catch (Throwable t) {
			//			logger.error(WTF_MARKER, t.getMessage(), t);

		}   

	}


}





