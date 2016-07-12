package org.likefly.petrinet.xml;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

public class ReferenceArcXML {
	
	@XmlElement(name="placeId")
	public String placeId;
	
	@XmlElement(name="netId")
	public String subnetId;
	
	@XmlElement(name="breakPoint")
	public List<PointXML> breakPoints = new LinkedList<PointXML>();
	
}
