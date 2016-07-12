package org.likefly.util;

import org.likefly.petrinet.owl.Arc;
import org.likefly.petrinet.owl.Graphics;
import org.likefly.petrinet.owl.Inscription;
import org.likefly.petrinet.owl.Name;
import org.likefly.petrinet.owl.Offset;
import org.likefly.petrinet.owl.PTMarking;
import org.likefly.petrinet.owl.Page;
import org.likefly.petrinet.owl.PetriNet;
import org.likefly.petrinet.owl.Place;
import org.likefly.petrinet.owl.Position;
import org.likefly.petrinet.owl.RefPlace;
import org.likefly.petrinet.owl.RefTransition;
import org.likefly.petrinet.owl.Transition;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class PetriNetNode {
	
	/**
	 * 根据一个Page结点创建一个新的Page对象
	 * @param page node
	 * @return Page Objects
	 */
	public static Page getPage(Node n) {
		
		if (n == null) {
		//	System.out.print("Error: n is null!");
			return null;
		}
		
		Page pa = new Page();
		NamedNodeMap attr = n.getAttributes();
		
		if (attr != null) {
			for (int i = 0; i < attr.getLength(); ++i) {
				if ("id".equals(attr.item(i).getNodeName())){
					pa.setId(attr.item(i).getNodeValue());
				}
			}
		}
		NodeList nList = n.getChildNodes();
		
		if (nList != null) {
			for (int i = 0; i < nList.getLength(); ++i) {
				
				Node ti = nList.item(i);
				String nodeName = ti.getNodeName();
				
				if ("place".equals(nodeName)) {
					pa.addPlace(getPlace(ti));
				}
				else if ("transition".equals(nodeName)) {
					pa.addTransition(getTransition(ti));
				}
				else if ("arc".equals(nodeName)) {
					pa.addArc(getArc(ti));
				}
				else if ("transition reference".equals(nodeName)) {
					pa.addRefTransition(getRefTransition(ti));
				}
				else if ("place reference".equals(nodeName)) {
					pa.addRefPlace(getRefPlace(ti));
				}
				else if ("graphics".equals(nodeName)) {
					pa.addGraphics(getGraphics(ti));
				}
			}
		}
		return pa;
	}
	/**
	 * PlaceReference对象生成函数
	 * @param place reference node
	 * @return RefPlace Objects
	 */
	public static RefPlace getRefPlace(Node n) {
		
		if (n == null) {
		//	System.out.println("Error: n is null!");
			return null;
		}
		
		RefPlace pr = new RefPlace();
		NamedNodeMap attri = n.getAttributes();
		
		if (attri != null) {
			for (int i = 0; i < attri.getLength(); ++i) {
				Node at = attri.item(i);
				if ("id".equals(at.getNodeName())) {
					pr.setId(at.getNodeValue());
				}
				else if ("IDRef".equals(at.getNodeName())) {
					pr.setIDRef(at.getNodeValue());
				}
			}
		}
		
		NodeList childList = n.getChildNodes();
		
		for (int i = 0; i < childList.getLength(); ++i) {
			Node child = childList.item(i);
			
			if ("name".equals(child.getNodeName())) {
				pr.setName(getName(child));
			}
		}
		
		return pr;
	}
	/**
	 * 根据initialMarking结点创建InitialMarking对象
	 * @param initialMarking node
	 * @return PTMarking Object
	 */
	public static PTMarking getInitialMarking(Node node) {
		
		if (node == null) {
		//	System.out.println("Error: n is null!");
			return null;
		}
		
		NodeList childList = node.getChildNodes();
		PTMarking im = new PTMarking();
		
		if (childList != null) {
			for (int i = 0; i < childList.getLength(); ++i) {
				Node temp = childList.item(i);
				if ("text".equals(temp.getNodeName())) {
					im.setText(temp.getTextContent());
				}
			}
		}
		return im;
	}
	/**
	 * TransitionReference对象生成函数
	 * @param transition reference node
	 * @return RefTransition Object
	 */
	public static RefTransition getRefTransition(Node n) {
		
		if (n == null) {
		//	System.out.println("Error: n is null!");
			return null;
		}
		
		RefTransition tr = new RefTransition();
		NamedNodeMap attri = n.getAttributes();
		
		if (attri != null) {
			for (int i = 0; i < attri.getLength(); ++i) {
				Node at = attri.item(i);
				if ("id".equals(at.getNodeName())) {
					tr.setId(at.getNodeValue());
				}
				else if ("IDRef".equals(at.getNodeName())) {
					tr.setIDRef(at.getNodeValue());
				}
			}
		}
		
		NodeList childList = n.getChildNodes();
		
		for (int i = 0; i < childList.getLength(); ++i) {
			Node child = childList.item(i);
			if ("name".equals(child.getNodeName())) {
				tr.setName(getName(child));
			}
		}
		return tr;
	}
	
	/**
	 * Place对象生成函数
	 * @param place node
	 * @return Place Object
	 */
	public static Place getPlace(Node node) {
		if (node == null) {
		//	System.out.println("Error: n is null!");
			return null;
		}
		
		Place p = new Place();
		NamedNodeMap attribute = node.getAttributes();
		
		if (attribute != null) {
			for (int i = 0; i < attribute.getLength(); ++i) {
				Node ati = attribute.item(i);
				String nodeName = ati.getNodeName();
				if ("id".equals(nodeName)) {
					p.setId(ati.getNodeValue());
				}
			}
		}
		
		NodeList childList = node.getChildNodes();
		
		if (childList != null) {
			for (int i = 0; i < childList.getLength(); ++i) {
				Node child = childList.item(i);
				String nodeName = child.getNodeName();
				if ("name".equals(nodeName)) {
					p.setName(getName(child));
				}
				else if ("graphics".equals(nodeName)) {
					p.addGraphics(getGraphics(child));
				}
				else if ("initialMarking".equals(nodeName)) {
					p.addInitialMarking(getInitialMarking(child));
				}
			}
		}
		return p;
	}
	/**
	 * Transition对象生成函数
	 * @param transition node
	 * @return Transition Object
	 */
	public static Transition getTransition(Node node) {
		if (node == null) {
		//	System.out.println("Error: n is null!");
			return null;
		}
		
		Transition t = new Transition();
		NamedNodeMap attribute = node.getAttributes();
		
		if (attribute != null) {
			for (int i = 0; i < attribute.getLength(); ++i) {
				Node ati = attribute.item(i);
				String nodeName = ati.getNodeName();
				
				if ("id".equals(nodeName)) {
					t.setId(ati.getNodeValue());
				}
			}
		}
		
		NodeList childList = node.getChildNodes();
		
		if (childList != null) {
			
			for (int i = 0; i < childList.getLength(); ++i) {
				Node child = childList.item(i);
				String nodeName = child.getNodeName();
				
				if ("name".equals(nodeName)) {
					t.setName(getName(child));
				}
				else if ("graphics".equals(nodeName)) {
					t.addGraphics(getGraphics(child));
				}
			}
		}
		return t;
	}
	
	/**
	 * 根据一个Arc节点生成一个Arc对象
	 * @param arc node
	 * @return a Arc Object
	 */
	public static Arc getArc(Node node) {
		
		if (node == null) {
		//	System.out.println("Error: n is null!");
			return null;
		}
		
		Arc ar = new Arc();
		NamedNodeMap attr = node.getAttributes();
		
		if (attr != null) {
			for (int i = 0; i < attr.getLength(); ++i) {
				Node at = attr.item(i);
				if ("id".equals(at.getNodeName())) {
					ar.setId(at.getNodeValue());
				}
				else if ("source".equals(at.getNodeName())) {
					ar.setSourceId(at.getNodeValue());
				}
				else if ("target".equals(at.getNodeName())) {
					ar.setTargetId(at.getNodeValue());
				}
			}
		}
		
		NodeList childList = node.getChildNodes();
		
		if (childList != null) {
			for (int i = 0; i < childList.getLength(); ++i) {
				Node child = childList.item(i);
				String nodeName = child.getNodeName();
				if ("inscription".equals(nodeName)) {
					ar.addInscription(getInscription(child));
				}
				else if ("graphics".equals(nodeName)) {
					ar.addGraphics(getGraphics(child));
				}
			}
		}
		return ar;
	}
	/**
	 * Inscription对象生成函数
	 * @param inscription Node
	 * @return Inscription Object
	 */
	public static Inscription getInscription(Node node) {
		
		if (node == null) {
		//	System.out.println("Error: n is null!");
			return null;
		}
		
		NodeList childList = node.getChildNodes();
		Inscription isp = new Inscription();
		
		if (childList != null) {
			for (int i = 0; i < childList.getLength(); ++i) {
				Node child = childList.item(i);
				String nodeName = child.getNodeName();
				if ("graphics".equals(nodeName)) {
					isp.addGraphics(getGraphics(child));
				}
			}
		}
		
		return isp;
	}
	/**
	 * GraphicsDescriptor对象生成函数
	 * @param graphics Node
	 * @return Graphics Objects
	 */
	public static Graphics getGraphics(Node node) {
		
		if (node == null) {
		//	System.out.println("Error: n is null!");
			return null;
		}
		
		Graphics gd = new Graphics();
		NodeList childList = node.getChildNodes();
		
		if (childList != null) {
			for (int i = 0; i < childList.getLength(); ++i) {
				Node child = childList.item(i);
				String nodeName = child.getNodeName();
				if ("position".equals(nodeName)) {
					gd.addPosition(getPosition(child));
				}
				else if ("offset".equals(nodeName)) {
					gd.addOffset(getOffset(child));
				}
			}
		}
		
		return gd;
	}
	/**
	 * 绝对位置对象生成函数
	 * @param position结点
	 * @return position对象
	 */
	public static Position getPosition(Node node) {
		
		if (node == null) {
		//	System.out.println("Error: n is null!");
			return null;
		}
		Position p = new Position();
		
		NamedNodeMap attri = node.getAttributes();
		
		if (attri != null) {
			for (int i = 0; i < attri.getLength(); ++i) {
				Node atr = attri.item(i);
				if ("x".equals(atr.getNodeName())) {
					p.setX(Integer.parseInt(atr.getNodeValue()));
				}
				else if ("y".equals(atr.getNodeName())) {
					p.setY(Integer.parseInt(atr.getNodeValue()));
				}
			}
		}
		
		return p;
	}
	/**
	 * 相对位置对象生成函数
	 * @param offset节点
	 * @return offset对象
	 */
	public static Offset getOffset(Node n) {
		
		if (n == null) {
		//	System.out.println("Error: n is null!");
			return null;
		}
		
		Offset p = new Offset();
		
		NamedNodeMap attri = n.getAttributes();
		
		if (attri != null) {
			for (int i = 0; i < attri.getLength(); ++i) {
				Node atr = attri.item(i);
				if ("x".equals(atr.getNodeName())) {
					p.setX(Integer.parseInt(atr.getNodeValue()));
				}
				else if ("y".equals(atr.getNodeName())) {
					p.setY(Integer.parseInt(atr.getNodeValue()));
				}
			}
		}
		
		return p;
	}
	/**
	 * 根据一个name节点生成一个name对象
	 * @param n
	 * @return  a Name Object
	 */
	public static Name getName(Node n) {
		
		if (n == null) {
		//	System.out.print("Error: n is null");
			return null;
		}
		
		NodeList childNode = n.getChildNodes();
		Name name = new Name();
		
		if (childNode != null) {
			for (int i = 0; i < childNode.getLength(); ++i) {
				
				Node child = childNode.item(i);
				if ("text".equals(child.getNodeName())) {
					String nameText = child.getTextContent();
					name.setText(nameText);
				}
				else if ("graphics".equals(child.getNodeName())) {
					name.addGraphics(getGraphics(child));
				}
				
			}
		}
		
		return name;
	}
	
	
	public static PetriNet getNet(Node node) throws PNMLParserException {
		
		if (node == null) {
			throw new PNMLParserException("Net is Empty!");
		}
		
		PetriNet net = new PetriNet();
		NamedNodeMap attribute = node.getAttributes();
		
		if (attribute != null) {
			
			for (int i = 0; i < attribute.getLength(); ++i) {
				Node attr = attribute.item(i);
				if ("id".equals(attr.getNodeName())) {
					net.setId(attr.getNodeValue());
				}
				else if ("type".equals(attr.getNodeName())) {
					net.setType(attr.getNodeValue());
				}
			}
			
		}
		
		NodeList childNodeList = node.getChildNodes();
		
		if (childNodeList != null) {
			for (int i = 0; i < childNodeList.getLength(); ++i) {
				Node childNode = childNodeList.item(i);
				String nodeName = childNode.getNodeName();
				if ("page".equals(nodeName)) {
					net.addPage(getPage(childNode));
				}
				else if ("place".equals(nodeName)) {
					net.addPlace(getPlace(childNode));
				}
				else if ("transition".equals(nodeName)) {
					net.addTransition(getTransition(childNode));
				}
				else if ("Name".equals(nodeName)) {
					net.setName(getName(childNode));
				}
			}
		}
		return net;
	}
	
}
