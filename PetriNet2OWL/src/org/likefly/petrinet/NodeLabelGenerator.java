package org.likefly.petrinet;

import java.util.Collection;



public class NodeLabelGenerator {
	
	private PetriNet petriNet;

	public NodeLabelGenerator(PetriNet petriNet) {
		this.petriNet = petriNet;
	}
	
	private String getPrefix(Node node) {
		String prefix;
		if (node instanceof Place) {
			prefix = "p";
		}
		else if (node instanceof Transition) {
			prefix = "t";
		}
		else if (node instanceof Net) {
			prefix = "n";
		}
		else  {
			throw new RuntimeException("Cant't label to a ReferencePlace?");
		}

		return prefix;
	}

	public void setLabelToNewlyCreatedNode(Node node) {
	}

	public void setLabelsToPastedContent(Collection<Element> elements) {

	}

	public void setLabelsToReplacedSubnet(Net subnet) {
		for (Node node : subnet.getNodesRecursively()) {
			if (!(node instanceof ReferencePlace)) {
			}
		}
	}

	public void setLabelsOfConversionTransitionToSubnet(Transition transition, Net subnet) {

		subnet.setLabel(transition.getLabel());
		transition.setLabel(null);
	}
	
	public void cloneLabel(Node newNode, Node oldNode) {

		newNode.setLabel(oldNode.getLabel());
	}

	public void fixFutureUniqueLabels() {
		int maxPlaceNumber = 0;
		int maxTransitionNumber = 0;
		int maxSubnetNumber = 0;
		
		for (Place place : petriNet.getRootSubnet().getPlacesRecursively()) {
			String placeLabel = place.getLabel();
			if (placeLabel != null && placeLabel.startsWith(getPrefix(place))) {
				try {
					int placeNumber = Integer.parseInt(placeLabel.substring(1));
					if (placeNumber > maxPlaceNumber) {
						maxPlaceNumber = placeNumber;
					}
				}
				catch (NumberFormatException ex) {
				}
			}
		}
		for (Transition transition : petriNet.getRootSubnet().getTransitionsRecursively()) {
			String transitionLabel = transition.getLabel();
			if (transitionLabel != null && transitionLabel.startsWith(getPrefix(transition))) {
				try {
					int transitionNumber = Integer.parseInt(transitionLabel.substring(1));
					if (transitionNumber > maxTransitionNumber) {
						maxTransitionNumber = transitionNumber;
					}
				}
				catch (NumberFormatException ex) {
				}
			}
		}
		for (Net subnet : petriNet.getRootSubnet().getSubnetsRecursively()) {
			String subnetLabel = subnet.getLabel();
			if (subnetLabel != null && subnetLabel.startsWith(getPrefix(subnet))) {
				try {
					int subnetNumber = Integer.parseInt(subnetLabel.substring(1));
					if (subnetNumber > maxSubnetNumber) {
						maxSubnetNumber = subnetNumber;
					}
				}
				catch (NumberFormatException ex) {
				}
			}
		}
	}
}
