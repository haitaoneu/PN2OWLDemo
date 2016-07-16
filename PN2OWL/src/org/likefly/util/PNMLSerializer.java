package org.likefly.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.likefly.petrinet.owl.PetriNet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class PNMLSerializer {
	
	private Set<Node> netNode;
	private Set<PetriNet> allNet;
	
	public PNMLSerializer() {
		netNode = new HashSet<Node>();
		allNet = new HashSet<PetriNet>();
	}
	
	
	public Set<Node> getNetNode() {
		return netNode;
	}


	public void setNetNode(Set<Node> netNode) {
		this.netNode = netNode;
	}


	public Set<PetriNet> getAllNet() {
		return allNet;
	}


	public void setAllNet(Set<PetriNet> allNet) {
		this.allNet = allNet;
	}


	/**
	 * ������һ�㺯��,����һ��pnml�ļ�������net�ڵ㣨���ܺ��ж����
	 * @param aFile based on xml format
	 * @return netNode
	 * @throws PNMLParserException 
	 */
	public void pnmlParser(File aFile) throws PNMLParserException {
		
		try {
			
			DocumentBuilder builder = null;
			DocumentBuilderFactory buildFactory = 
					DocumentBuilderFactory.newInstance();
			builder = buildFactory.newDocumentBuilder();
			Document document = builder.parse(aFile);
			Element root = document.getDocumentElement();
			NodeList netList = root.getChildNodes();
			if (netList == null) {
				return;
			}
			for (int i = 0; i < netList.getLength(); ++i) {
				Node temp = netList.item(i);
				if ("net".equals(temp.getNodeName())) {
					netNode.add(temp);
				}
			}
			
		}catch (Exception e){
			throw new PNMLParserException("Error: parse error");
		}
	}
	/**
	 * ������ϵͳ��������������֮ǰ��Ҫȷ��:
	 * 1. pnml�ǺϷ���
	 * 2. netNode�Ѿ���һ��������
	 * @throws PNMLParserException 
	 */
	public void buildNetSystem(File aFile) throws PNMLParserException {
		
		if (aFile == null) {
			System.out.print("Error: aFile is null!");
			return;
		}
		
		try {
			pnmlParser(aFile);
		} catch (PNMLParserException e) {
			e.printStackTrace();
		}
		
		if (netNode == null) {
			System.out.print("Error: netNode is null!");
			return;
		}
		
		for (Node netd : netNode) {
			allNet.add(PetriNetNode.getNet(netd));
		}
		
	}
	
	public static void main(String[] args) throws PNMLParserException {
		new PNMLSerializer().buildNetSystem(new File("src/resource/2.xml"));
	}
}
