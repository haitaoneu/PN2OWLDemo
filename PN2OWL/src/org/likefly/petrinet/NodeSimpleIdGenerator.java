package org.likefly.petrinet;

import java.util.Set;


public class NodeSimpleIdGenerator {
	
	private int nextUniqueId = 1; 
	private PetriNet petriNet;

	public NodeSimpleIdGenerator(PetriNet petriNet) {
		this.petriNet = petriNet;
	}

	public void setUniqueId(Node node) {
		String id;
		id = Integer.toString(nextUniqueId++);
		node.setId(id);
	}

	public void fixFutureUniqueIds() {
		int maxId = 0;
		Set<Node> allNodes = petriNet.getRootSubnet().getNodesRecursively();
		allNodes.add(petriNet.getRootSubnet());
		for (Node node : allNodes) {
			try {
				int id = Integer.parseInt(node.getId());
				if (id > maxId) {
					maxId = id;
				}
			}
			catch (NumberFormatException ex) {
				//do nothing
			}
		}
		nextUniqueId = maxId + 1;
	}

	public void ensureNumberIds() {
		for (Node node : petriNet.getRootSubnet().getNodesRecursively()) {
			try {
				Integer.parseInt(node.getId());
			}
			catch (NumberFormatException ex) {
				setUniqueId(node);
			}
		}
	}
}
