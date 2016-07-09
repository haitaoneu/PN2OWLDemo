/**
 * 
 */
package org.likefly.petrinet.owl;

import java.util.HashSet;
import java.util.Set;

/**
 * @author like
 * Page被当成了一个对象，考虑其结构化的作用，事实上Page本身并不是Petri网的一部分
 */
public class Page {
	
	private String id;
	private Set<Place> places = new HashSet<Place>();
	private Set<Transition> transitions = new HashSet<Transition>();
	private Set<Arc> arcs = new HashSet<Arc>();
	private Set<RefTransition> refTransitions = new HashSet<RefTransition>();
	private Set<RefPlace> refPlaces = new HashSet<RefPlace>();
	private Set<Graphics> graphics = new HashSet<Graphics>();

	
	public Page() {
	}
	
	
	public void addPlace(Place p) {
		places.add(p);
	}
	
	public void addTransition(Transition t) {
		transitions.add(t);
	}
	
	public void addRefTransition(RefTransition tr) {
		refTransitions.add(tr);
	}
	
	public void addRefPlace(RefPlace pr) {
		refPlaces.add(pr);
	}
	
	public void addArc(Arc a) {
		arcs.add(a);
	}
	
	public void addGraphics(Graphics gd) {
		graphics.add(gd);
	}

	public Set<Place> getPlaces() {
		return places;
	}

	public void setPlaces(Set<Place> places) {
		this.places = places;
	}

	public Set<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(Set<Transition> transitions) {
		this.transitions = transitions;
	}

	public Set<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(Set<Arc> arcs) {
		this.arcs = arcs;
	}

	public Set<RefTransition> getRefTransitions() {
		return refTransitions;
	}

	public void setRefTransitions(Set<RefTransition> refTransitions) {
		this.refTransitions = refTransitions;
	}

	public Set<RefPlace> getRefPlaces() {
		return refPlaces;
	}

	public void setRefPlaces(Set<RefPlace> refPlaces) {
		this.refPlaces = refPlaces;
	}

	public Set<Graphics> getGraphics() {
		return graphics;
	}

	public void setGraphics(Set<Graphics> graphics) {
		this.graphics = graphics;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String Id) {
		id = Id;
	}
}
