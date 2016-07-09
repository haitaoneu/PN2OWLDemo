package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlElement;


public class TransitionXML extends NodeXML {
	
	@XmlElement(name="label")
	public String label;

}
