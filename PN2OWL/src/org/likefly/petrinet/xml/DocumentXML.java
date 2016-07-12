package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="pnml")
public class DocumentXML {
	
	@XmlElement(name="net")
	public NetXML rootSubnet;
	
//	@XmlElement(name="left")
//	public int left;
	
//	@XmlElement(name="top")
//	public int top;
}
