package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlElement;


public class PlaceXML extends NodeXML {
	
	@XmlElement(name="name")
	public TextXML name;
	
	@XmlElement(name="initialMarking")
	public TextXML tokens;

}
