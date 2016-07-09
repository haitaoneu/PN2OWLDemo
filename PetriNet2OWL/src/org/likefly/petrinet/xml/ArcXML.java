package org.likefly.petrinet.xml;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;


public class ArcXML {
	
	@XmlElement(name="sourceId")
	public String sourceId;
	
	@XmlElement(name="destinationId")
	public String destinationId;
	
	@XmlElement(name="multiplicity")
	public int multiplicity;
	
	@XmlElement(name="breakPoint")
	public List<PointXML> breakPoints = new LinkedList<PointXML>();
	
	@XmlElement(name="realSourceId")
	public String realSourceId;
	
	@XmlElement(name="realDestinationId")
	public String realDestinationId;
}
