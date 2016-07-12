package org.likefly.editor.canvas;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.likefly.petrinet.Element;
import org.likefly.petrinet.Net;
import org.likefly.petrinet.Node;
import org.likefly.petrinet.PlaceNode;
import org.likefly.petrinet.Transition;
import org.likefly.petrinet.TransitionNode;
import org.likefly.util.CollectionTools;

/**
 * —°‘Ò∂‘œÛ
 * @author like
 *
 */
public class Selection implements Iterable<Element> {
	
	Set<Element> selectedElements = new HashSet<Element>();

	
	public void clear() {
		selectedElements.clear();
	}
	
	public void add(Element element) {
		selectedElements.add(element);
	}

	public void addAll(Collection<? extends Element> elements) {
		selectedElements.addAll(elements);
	}
	
	public boolean isEmpty() {
		return selectedElements.isEmpty();
	}
	
	public boolean contains(Element element) {
		return selectedElements.contains(element);
	}



	public Iterator<Element> iterator() {
		return selectedElements.iterator();
	}
	
	public Set<Element> getElements() {
		return selectedElements;
	}
	
	public Set<Node> getNodes() {
		return CollectionTools.getFilteredByClass(selectedElements, Node.class);
	}
	
	public Set<Transition> getTransitions() {
		return CollectionTools.getFilteredByClass(selectedElements, Transition.class);
	}
	
	public Set<Net> getSubnets() {
		return CollectionTools.getFilteredByClass(selectedElements, Net.class);
	}
	
	public Set<TransitionNode> getTransitionNodes() {
		return CollectionTools.getFilteredByClass(selectedElements, TransitionNode.class);
	}
	
	public Set<Transition> getTransitionsRecursively() {
		Set<Transition> selectedTransitions = new HashSet<Transition>();
		for (Element element : selectedElements) {
			if (element instanceof Net) {
				Net subnet = (Net)element;
				selectedTransitions.addAll(subnet.getTransitionsRecursively());
			}
			else if (element instanceof Transition) {
				selectedTransitions.add((Transition)element);
			}
		}
		return selectedTransitions;
	}
	
	public Set<PlaceNode> getPlaceNodes() {
		return CollectionTools.getFilteredByClass(selectedElements, PlaceNode.class);
	}
	
}
