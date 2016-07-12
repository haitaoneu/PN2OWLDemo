package org.likefly.editor;

import java.util.HashSet;
import java.util.Set;

import org.likefly.petrinet.ArcEdge;
import org.likefly.petrinet.Element;
import org.likefly.petrinet.ElementCloner;
import org.likefly.petrinet.Net;
import org.likefly.petrinet.Node;
import org.likefly.petrinet.PetriNet;

/**
 * The key class
 * @author like
 *
 */
public class Clipboard {

	private Net subnet = new Net();


	public void setContents(Set<Element> elements, PetriNet petriNet) {
		subnet.removeElements();
		elements = filterOutDisconnectedArcs(elements);
		elements = ElementCloner.getClones(elements, petriNet);
		subnet.addAll(elements);
	}

	public Set<Element> getContents(PetriNet petriNet) {
		return ElementCloner.getClones(subnet.getElements(), petriNet);
	}

	public boolean isEmpty() {
		return subnet.getElements().isEmpty();
	}

	private static Set<Element> filterOutDisconnectedArcs(Set<Element> elements) {
		Set<Element> filteredElements = new HashSet<Element>();
		Set<Node> nodes = getNodes(elements);
		for (Node node : nodes) {
			Set<ArcEdge> connectedArcEdges = node.getConnectedArcEdges();
			for (ArcEdge connectedArcEdge : connectedArcEdges) {
				if (nodes.contains(connectedArcEdge.getPlaceNode()) && nodes.contains(connectedArcEdge.getTransitionNode())) {
					filteredElements.add(connectedArcEdge);
				}
			}
		}
		filteredElements.addAll(nodes);
		return filteredElements;
	}

	public static Set<Node> getNodes(Set<Element> elements) {
		Set<Node> nodes = new HashSet<Node>();
		for (Element element : elements) {
			if (element instanceof Node) {
				nodes.add((Node)element);
			}
		}
		return nodes;
	}

	public Set<Node> getNodes() {
		Set<Node> nodes = new HashSet<Node>();
		for (Element element : subnet.getElements()) {
			if (element instanceof Node) {
				nodes.add((Node)element);
			}
		}
		return nodes;
	}

}
