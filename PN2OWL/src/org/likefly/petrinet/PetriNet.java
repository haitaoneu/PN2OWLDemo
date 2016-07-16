package org.likefly.petrinet;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;


public class PetriNet {
	
	private Stack<Net> openedSubnets = new Stack<Net>();
	private Net rootSubnet;
	private Marking initialMarking = new Marking(this);
	private NodeSimpleIdGenerator nodeSimpleIdGenerator = new NodeSimpleIdGenerator(this);
	private NodeLabelGenerator nodeLabelGenerator = new NodeLabelGenerator(this);


	public PetriNet() {
		clear();
	}
	

	public Net getRootSubnet() {
		return rootSubnet;
	}
	

	public void setRootSubnet(Net rootSubnet) {
		this.rootSubnet = rootSubnet;
	}
	

	public boolean isCurrentSubnetRoot() {
		return getCurrentSubnet() == getRootSubnet();
	}
	

	public Net getCurrentSubnet() {
		return openedSubnets.peek();
	}
	

	public void clear() {
		rootSubnet = new Net();
		resetView();
	}
	

	public void resetView() {
		openedSubnets.clear();
		openedSubnets.add(rootSubnet);
	}
	

	public void openSubnet(Net subnet) {
		openedSubnets.push(subnet);
	}
	

	public void closeSubnet() {
		if (!isCurrentSubnetRoot()) {
			openedSubnets.pop();
		}
	}
	
	public Collection<Net> getOpenedSubnets() {
		return Collections.unmodifiableCollection(openedSubnets);
	}

	public Marking getInitialMarking() {
		return initialMarking;
	}

	@Deprecated
	public void setInitialMarking(Marking initialMarking) {
		this.initialMarking = initialMarking;
	}

	public NodeSimpleIdGenerator getNodeSimpleIdGenerator() {
		return nodeSimpleIdGenerator;
	}

	public NodeLabelGenerator getNodeLabelGenerator() {
		return nodeLabelGenerator;
	}


}
