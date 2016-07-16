package org.likefly.petrinet.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="text")
public class TextXML {

	public String text;

	public TextXML(){
		
	}
	
	public TextXML(String content) {
		super();
		this.text = content;
	}
	
	
}
