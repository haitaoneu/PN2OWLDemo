package org.likefly.util;

import java.io.File;

import org.likefly.owl.OWLGenerator;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;


/**
 * Ö÷º¯Êý
 * @author like
 *
 */
public class PetriNetToOWL {
	

	public static void tansform(File PNML, File OWL, int type) throws PNMLParserException {
		
		if (PNML == null) {
			throw new PNMLParserException("PNML is Empty!");
		}
		
		PNMLSerializer ps = new PNMLSerializer();
		ps.buildNetSystem(PNML);
		String URI = "http://www.petri.net/petri.owl";

		try {
			
			OWLGenerator ob = new OWLGenerator(URI);
			ob.generateOWL(ps.getAllNet(), OWL, type);
			
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		PNML.delete();
	}
}
