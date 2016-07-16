package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlElement;


public class TransitionXML extends NodeXML {
	
	@XmlElement(name="name")
	public TextXML label;

}
