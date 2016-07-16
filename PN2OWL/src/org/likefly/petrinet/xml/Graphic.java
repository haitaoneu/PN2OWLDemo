package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="graphics")
public class Graphic {

	@XmlElement(name="position")
	public Position position;
}
