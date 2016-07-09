package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="document")
public class DocumentXML {
	
	@XmlElement(name="subnet")
	public NetXML rootSubnet;
	
	/*@XmlElement(name="left")
	public int left;
	
	@XmlElement(name="top")
	public int top;*/
}
