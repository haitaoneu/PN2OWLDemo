package org.likefly.petrinet.xml;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.TransformerException;

import org.likefly.editor.Root;
import org.likefly.petrinet.Arc;
import org.likefly.petrinet.Document;
import org.likefly.petrinet.Element;
import org.likefly.petrinet.Marking;
import org.likefly.petrinet.Net;
import org.likefly.petrinet.Place;
import org.likefly.petrinet.ReferenceArc;
import org.likefly.petrinet.ReferencePlace;
import org.likefly.petrinet.Transition;
import org.likefly.util.PetriNetException;

/**
 * OWL导出 key class
 * @author like
 *
 */
public class DocumentExporter {
	
	private DocumentXML documentXML = new DocumentXML();
	private Root root;
	public DocumentExporter(Document document, Marking marking) {
		documentXML.rootSubnet = getXmlSubnet(marking.getPetriNet().getRootSubnet(), marking);
	
			Rectangle bounds = marking.getPetriNet().getRootSubnet().getBoundsRecursively();
		
//		documentXML.left = bounds.x;
//		documentXML.top = bounds.y;
	}
	

	public void writeToFileWithXsltForPNML(File file, InputStream xslt, int type) throws JAXBException, IOException, TransformerException, PetriNetException{
		if (xslt == null) {
			throw new FileNotFoundException("找不到XSLT样式表！");
		}
		//用JAXB技术实现对象到XML的转换
		//Petri Net - > tempFile
		JAXBContext ctx = JAXBContext.newInstance(DocumentXML.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty("jaxb.formatted.output", true);
		//File tempFile = File.createTempFile("export", null);
		File middleResult=new File("D:\\export.txt");
		m.marshal(documentXML, new FileOutputStream(middleResult));
	}
	
	public void writeToFileWithXslt(File file, InputStream xslt, int type) throws FileNotFoundException, JAXBException, IOException, TransformerException, PetriNetException {
		if (xslt == null) {
			throw new FileNotFoundException("找不到XSLT样式表！");
		}
		//用JAXB技术实现对象到XML的转换
		//Petri Net - > tempFile
		JAXBContext ctx = JAXBContext.newInstance(DocumentXML.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty("jaxb.formatted.output", true);
		//File tempFile = File.createTempFile("export", null);
		File middleResult=new File("D:\\export.txt");
		m.marshal(documentXML, new FileOutputStream(middleResult));
		
	//	OWLPrinter.outputFile(file);
		
		/**
		 * 根据XSLT的语法生成PNML文件，再转换成OWL本体文件
		 */
		OWLExporter.transformXml(middleResult, xslt, file, type);

//		OWLPrinter.outputFile(file);
		
		String encoding="GBK";
		File resultfile=new File(middleResult.toString());
		if(resultfile.isFile() && resultfile.exists()){ //判断文件是否存在
            InputStreamReader read = new InputStreamReader(
            new FileInputStream(resultfile),encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            String show="";
            while((lineTxt = bufferedReader.readLine()) != null){
                System.out.println(lineTxt);
                //show+=lineTxt;
                //result.append(lineTxt+"\r\n");
                //result.
            }
            read.close();
           // result.setText(show);
		}	
		
		//tempFile.delete();
	}
	
	private NetXML getXmlSubnet(Net subnet, Marking initialMarking) {
		
		NetXML xmlSubnet = new NetXML();
		xmlSubnet.id = subnet.getId();
		xmlSubnet.label = subnet.getLabel();
//		xmlSubnet.graphic = new Graphic();
//		xmlSubnet.graphic.position = new Position(subnet.getCenter().x, subnet.getCenter().y);
//		xmlSubnet.x = subnet.getCenter().x;
//		xmlSubnet.y = subnet.getCenter().y;
		
		for (Element element : subnet.getElements()) {
			if (element instanceof Transition) {
				xmlSubnet.transitions.add(getXmlTransition((Transition)element));
			}
			else if (element instanceof ReferencePlace) {
				xmlSubnet.referencePlaces.add(getXmlReferencePlace((ReferencePlace)element));
			}
			else if (element instanceof Place) {
				xmlSubnet.places.add(getXmlPlace((Place)element, initialMarking));
			}
			else if (element instanceof ReferenceArc) {
				xmlSubnet.referenceArcs.add(getXmlReferenceArc((ReferenceArc)element));
			}
			else if (element instanceof Arc) {
				xmlSubnet.arcs.add(getXmlArc((Arc)element));
			}
		}
		return xmlSubnet;
	}
	
	private PlaceXML getXmlPlace(Place place, Marking initialMarking) {
		PlaceXML xmlPlace = new PlaceXML();
		xmlPlace.id = place.getId();
		xmlPlace.graphic = new Graphic();
		xmlPlace.graphic.position = new Position(getX(place.getCenter().x), getY(place.getCenter().y));
		xmlPlace.name = new TextXML(place.getLabel());
		xmlPlace.tokens = new TextXML(initialMarking.getTokens(place) + "");
		return xmlPlace;
	}
	
	private int getY(int y) {
		return (y + 400);
	}


	private int getX(int x) {
		return x + 250;
	}


	private TransitionXML getXmlTransition(Transition transition) {
		TransitionXML xmlTransition = new TransitionXML();
		xmlTransition.id = transition.getId();
		xmlTransition.graphic = new Graphic();
		xmlTransition.graphic.position = new Position(transition.getCenter().x, transition.getCenter().y); 
		xmlTransition.label = new TextXML(transition.getLabel());
		return xmlTransition;
	}
	
	private ArcXML getXmlArc(Arc arc) {
		ArcXML xmlArc = new ArcXML();
//		xmlArc.multiplicity = arc.getMultiplicity();
		xmlArc.sourceId = arc.getSource().getId();
		xmlArc.destinationId = arc.getDestination().getId();
		xmlArc.inscription = new TextXML("1");
		
		if (arc.getSource() instanceof ReferencePlace) {
			ReferencePlace referencePlace = (ReferencePlace)arc.getSource();
//			xmlArc.realSourceId = referencePlace.getConnectedPlace().getId();
		}
		else {
//			xmlArc.realSourceId = xmlArc.sourceId;
		}
		if (arc.getDestination() instanceof ReferencePlace) {
			ReferencePlace referencePlace = (ReferencePlace)arc.getDestination();
//			xmlArc.realDestinationId = referencePlace.getConnectedPlace().getId();
		}
		else {
//			xmlArc.realDestinationId = xmlArc.destinationId;
		}
		
		List<Point> breakPoints = arc.getBreakPoints();
		for (Point point : breakPoints) {
			PointXML xmlPoint = new PointXML();
//			xmlPoint.x = point.x;
//			xmlPoint.y = point.y;
//			xmlArc.breakPoints.add(xmlPoint);
		}
		return xmlArc;
	}
	
	private ReferencePlaceXML getXmlReferencePlace(ReferencePlace referencePlace) {
		ReferencePlaceXML xmlReferencePlace = new ReferencePlaceXML();
		xmlReferencePlace.id = referencePlace.getId();
		xmlReferencePlace.graphic = new Graphic();
		xmlReferencePlace.graphic.position = new Position(referencePlace.getCenter().x, referencePlace.getCenter().y);
//		xmlReferencePlace.x = referencePlace.getCenter().x;
//		xmlReferencePlace.y = referencePlace.getCenter().y;
		xmlReferencePlace.connectedPlaceId = referencePlace.getConnectedPlaceNode().getId();
		return xmlReferencePlace;
	}
	
	private ReferenceArcXML getXmlReferenceArc(ReferenceArc referenceArc) {
		ReferenceArcXML xmlReferenceArc = new ReferenceArcXML();
		xmlReferenceArc.placeId = referenceArc.getPlaceNode().getId();
		xmlReferenceArc.subnetId = referenceArc.getSubnet().getId();
		
		List<Point> breakPoints = referenceArc.getBreakPointsCopy();
		if (!referenceArc.isPlaceToTransition()) {
			Collections.reverse(breakPoints);
		}
		for (Point point : breakPoints) {
			PointXML xmlPoint = new PointXML();
//			xmlPoint.x = point.x;
//			xmlPoint.y = point.y;
			xmlReferenceArc.breakPoints.add(xmlPoint);
		}
		return xmlReferenceArc;
	}
}
