package org.likefly.petrinet.xml;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


public class ArcXML {
	
	@XmlAttribute(name="source")
	public String sourceId;
	
	@XmlAttribute(name="target")
	public String destinationId;
	
	@XmlElement(name="inscription")
	public TextXML inscription;
	
//	@XmlElement(name="multiplicity")
//	public int multiplicity;
//	
//	@XmlElement(name="breakPoint")
//	public List<PointXML> breakPoints = new LinkedList<PointXML>();
//	
//	@XmlElement(name="realSourceId")
//	public String realSourceId;
//	
//	@XmlElement(name="realDestinationId")
//	public String realDestinationId;
}
