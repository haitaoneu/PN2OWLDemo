package org.likefly.petrinet.xml;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;


public class NetXML extends NodeXML {
	
	@XmlElement(name="label")
	public String label;
	
	@XmlElement(name="place")
	public List<PlaceXML> places = new LinkedList<PlaceXML>();
	
	@XmlElement(name="transition")
	public List<TransitionXML> transitions = new LinkedList<TransitionXML>();
	
	@XmlElement(name="arc")
	public List<ArcXML> arcs = new LinkedList<ArcXML>();
	
	@XmlElement(name="net")
	public List<NetXML> subnets = new LinkedList<NetXML>();
	
	@XmlElement(name="referencePlace")
	public List<ReferencePlaceXML> referencePlaces = new LinkedList<ReferencePlaceXML>();
	
	@XmlElement(name="referenceArc")
	public List<ReferenceArcXML> referenceArcs = new LinkedList<ReferenceArcXML>();
	
}
