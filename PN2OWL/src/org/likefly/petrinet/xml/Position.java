package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="position")
public class Position {

	@XmlAttribute(name="x")
	public int x;
	
	@XmlAttribute(name="y")
	public int y;

	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Position(){
		
	}

	
}
