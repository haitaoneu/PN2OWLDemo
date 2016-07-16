package org.likefly.petrinet.xml;

import java.util.HashMap;
import java.util.Map;


public class IdToXmlObject {
	
	private DocumentXML xmlDocument;
	
	public IdToXmlObject(DocumentXML xmlDocument) {
		this.xmlDocument = xmlDocument;
	}
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public Object getXmlObject(String id) {
		if (id.equals(null)) {
			return null;
		}
		if (id.equals("")) {
			return null;
		}
		if (map.containsKey(id)) {
			return map.get(id);
		}
		Object xmlObject = getXmlObjectFromXmlSubnet(id, xmlDocument.rootSubnet);
		if (xmlObject != null) {
			map.put(id, xmlObject);
		}
		return xmlObject;
	}
	
	private Object getXmlObjectFromXmlSubnet(String id, NetXML xmlSubnet) {
		for (PlaceXML xmlPlace : xmlSubnet.places) {
			if (xmlPlace.id.equals(id)) {
				return xmlPlace;
			}
		}
		for (TransitionXML xmlTransition : xmlSubnet.transitions) {
			if (xmlTransition.id.equals(id)) {
				return xmlTransition;
			}
		}
		for (ReferencePlaceXML xmlReference : xmlSubnet.referencePlaces) {
			if (xmlReference.id.equals(id)) {
				return xmlReference;
			}
		}
		for (NetXML xmlSubSubnet : xmlSubnet.subnets) {
			if (xmlSubSubnet.id.equals(id)) {
				return xmlSubSubnet;
			}
			Object xmlObject = getXmlObjectFromXmlSubnet(id, xmlSubSubnet);
			if (xmlObject != null) {
				return xmlObject;
			}
		}
		return null;
	}
	
}
