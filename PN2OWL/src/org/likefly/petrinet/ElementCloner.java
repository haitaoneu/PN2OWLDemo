package org.likefly.petrinet;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Copy a Node
 * @author like
 *
 */
public class ElementCloner {

	private PetriNet petriNet;
	private HashMap<Element, Element> clonedElements = new HashMap<Element, Element>();

	public ElementCloner(PetriNet petriNet) {
		this.petriNet = petriNet;
	}
	
	public static <E extends Element> Set<E> getClones(Collection<E> elements, PetriNet petriNet) {
		Set<E> clones = new HashSet<E>();
		ElementCloner elementCloner = new ElementCloner(petriNet);
		for (E element : elements) {
			clones.add(elementCloner.getClone(element));
		}
		return clones;
	}

	public static <E extends Element> E getClone(E element, PetriNet petriNet) {
		ElementCloner elementCloner = new ElementCloner(petriNet);
		return elementCloner.getClone(element);
	}

	@SuppressWarnings(value="unchecked")
	private <E extends Element> E getClone(E element) {
		if (clonedElements.containsKey(element)) {
			return (E)clonedElements.get(element);
		}
		Element clone;
		if (element instanceof Place) {
			clone = clonePlace((Place)element);
		}
		else if (element instanceof Transition) {
			clone = cloneTransition((Transition)element);
		}
		else if (element instanceof Arc) {
			clone = cloneArc((Arc)element);
		}
		else if (element instanceof Net) {
			clone = cloneSubnet((Net)element);
		}
		else if (element instanceof ReferencePlace) {
			clone = cloneReferencePlace((ReferencePlace)element);
		}
		else if (element instanceof ReferenceArc) {
			clone = cloneReferenceArc((ReferenceArc)element);
		}
		else if (element == null) {
			return null;
		}
		else {
			throw new RuntimeException("unknown Element");
		}
		clonedElements.put((Element)element, clone);
		return (E)clone;
	}
	
	private Place clonePlace(Place place) {
		Place newPlace = new Place();
		newPlace.setCenter(place.getCenter());
		petriNet.getNodeSimpleIdGenerator().setUniqueId(newPlace);
		newPlace.setLabel(place.getLabel());
//		newPlace.setStatic(place.isStatic());
//		petriNet.getInitialMarking().setTokens(newPlace, petriNet.getInitialMarking().getTokens(place));
		return newPlace;
	}

	private Transition cloneTransition(Transition transition) {
		Transition newTransition = new Transition();
		newTransition.setCenter(transition.getCenter());
		petriNet.getNodeSimpleIdGenerator().setUniqueId(newTransition);
		newTransition.setLabel(transition.getLabel());
		return newTransition;
	}

	private Arc cloneArc(Arc arc) {
		Arc newArc = new Arc(
			getClone(arc.getPlaceNode()),
			getClone(arc.getTransition()),
			arc.isPlaceToTransition()
		);
		newArc.setBreakPoints(arc.getBreakPoints());
//		newArc.setMultiplicity(arc.getMultiplicity());
		return newArc;
	}

	private ReferencePlace cloneReferencePlace(ReferencePlace referencePlace) {
		ReferencePlace newReferencePlace = new ReferencePlace(
			getClone(referencePlace.getConnectedPlaceNode())
		);
		petriNet.getNodeSimpleIdGenerator().setUniqueId(newReferencePlace);
		newReferencePlace.setCenter(referencePlace.getCenter());
		return newReferencePlace;
	}

	private ReferenceArc cloneReferenceArc(ReferenceArc referenceArc) {
		ReferenceArc newReferenceArc = new ReferenceArc(
			getClone(referenceArc.getPlaceNode()),
			getClone(referenceArc.getSubnet())
		);
		newReferenceArc.setBreakPoints(referenceArc.getBreakPoints());
		return newReferenceArc;
	}

	private Net cloneSubnet(Net subnet) {
		Net newSubnet = new Net();
		newSubnet.setCenter(subnet.getCenter());
		petriNet.getNodeSimpleIdGenerator().setUniqueId(newSubnet);
		newSubnet.setLabel(subnet.getLabel());
		for (Element element : subnet.getElements()) {
			newSubnet.addElement(getClone(element));
		}
		return newSubnet;
	}
	
}
