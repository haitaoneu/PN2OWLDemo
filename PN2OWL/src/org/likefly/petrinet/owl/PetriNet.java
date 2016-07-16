package org.likefly.petrinet.owl;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author like
 * PetriNet Class
 */
public class PetriNet {
	
	//Net类包含数据属性id和type
	private String id;
	private String type;
	private Name name;
	private Set<Page> pages = new HashSet<Page>();
	private Set<Place> places = new HashSet<Place>();
	private Set<Transition> transitions = new HashSet<Transition>();
	private Set<Arc> arcs = new HashSet<Arc>();
	
	
	public String getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}

	public Set<Page> getPages() {
		return pages;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}

	public Set<Place> getPlaces() {
		return places;
	}

	public void setPlace(Set<Place> places) {
		this.places = places;
	}

	public Set<Transition> getTransitions() {
		return transitions;
	}

	public void setTransition(Set<Transition> transitions) {
		this.transitions = transitions;
	}

	public Set<Arc> getArcs() {
		return arcs;
	}

	public void setHasArc(Set<Arc> arcs) {
		this.arcs = arcs;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void addPage(Page p) {
		pages.add(p);
	}
	
	public void addArc(Arc a) {
		arcs.add(a);
	}
	
	public void addPlace(Place p) {
		places.add(p);
	}
	
	public void addTransition(Transition t) {
		transitions.add(t);
	}
	
}
