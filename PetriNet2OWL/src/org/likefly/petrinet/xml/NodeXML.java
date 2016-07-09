package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlElement;

public class NodeXML {
	
	@XmlElement(name="id")
	public String id;
	
	@XmlElement(name="x")
	public int x;
	
	@XmlElement(name="y")
	public int y;
}
