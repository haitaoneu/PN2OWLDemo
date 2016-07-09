package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlElement;


public class PlaceXML extends NodeXML {
	
	@XmlElement(name="label")
	public String label;
	
	@XmlElement(name="tokens")
	public int tokens;

}
