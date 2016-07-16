package org.likefly.editor.filechooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.likefly.petrinet.Document;
import org.likefly.petrinet.Marking;
import org.likefly.petrinet.PetriNet;
import org.likefly.petrinet.xml.DocumentExporter;
import org.likefly.util.PetriNetException;


public class PNMLFileType extends FileType {
	

	public String getName() {
		return "OWL Ontology";
	}

	@Override
	public String getExtension() {
		return "owl";
	}
	
	

	@SuppressWarnings("deprecation")
	@Override
	public void save(Document document, File file, int type) 
		throws FileNotFoundException, JAXBException, IOException, TransformerException, PetriNetException {
//		
//		System.out.println(type);
		final InputStream xslt = getClass().getResourceAsStream("/resource/generatePNML.xslt");
		PetriNet petriNet = document.petriNet;
		Marking initialMarking = petriNet.getInitialMarking();
		new DocumentExporter(document, initialMarking).writeToFileWithXslt(file, xslt, type);
	}

	@Override
	public String getExtensionPNML() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNamePNML() {
		// TODO Auto-generated method stub
		return null;
	}
}
