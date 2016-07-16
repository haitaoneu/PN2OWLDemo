package org.likefly.petrinet.owl;

import java.util.HashSet;
import java.util.Set;

public class Place extends Node {
	
	private Set<PTMarking> initialMarkings = new HashSet<PTMarking>();
	
	public Place() {
		super();
	}

	public Set<PTMarking> getInitialMarkings() {
		return initialMarkings;
	}

	public void setInitialMarking(Set<PTMarking> markings) {
		this.initialMarkings = markings;
	}
	
	public void addInitialMarking(PTMarking marking) {
		initialMarkings.add(marking);
	}
}
