package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlElement;


public class ReferencePlaceXML extends NodeXML {
	
	@XmlElement(name="connectedPlaceId")
	public String connectedPlaceId;
	
}
