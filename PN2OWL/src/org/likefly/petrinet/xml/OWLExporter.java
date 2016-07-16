package org.likefly.petrinet.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.likefly.util.PNMLParserException;
import org.likefly.util.PetriNetException;
import org.likefly.util.PetriNetToOWL;


public class OWLExporter {
	

	public OWLExporter() {
	}
	
	private static void transformToOWL(File source, File target, int type) 
		throws PetriNetException {
		try {
			PetriNetToOWL.tansform(source, target, type);
		} catch (PNMLParserException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new PetriNetException("文件导出失败！！！");
		}
	}
	
	public static void transformXml(File source, 
			InputStream xslt, File target, int type) 
		throws IOException, TransformerException, PetriNetException {
		
		Source xmlSource = new StreamSource(source);
		Source xsltSource = new StreamSource(xslt); 
		//File temp = new File("tempFile");
		File temp = File.createTempFile("tempFile", null);
		Result result = new StreamResult(temp);

		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer(xsltSource);
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.transform(xmlSource, result);
		
		//System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSS");
		
		transformToOWL(temp, target, type);
		temp.delete();
		
		//return target;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, TransformerException, PetriNetException {
		OWLExporter.transformXml(new File("d://export2.xml"), new FileInputStream(new File("src/resource/generatePNML2.xslt")), new File("d://generate.txt"), 1);
	}
	
	
}
