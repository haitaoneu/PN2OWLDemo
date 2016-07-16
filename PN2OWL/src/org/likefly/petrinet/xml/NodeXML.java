package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class NodeXML {
	
//	@XmlElement(name="id")
	@XmlAttribute(name="id")
	public String id;
	
	@XmlElement(name="graphic")
	public Graphic graphic;
}
