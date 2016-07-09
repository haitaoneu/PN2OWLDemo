package org.likefly.petrinet.owl;

import java.util.HashSet;
import java.util.Set;


public class Node {
	
	protected String id;
	protected Name name;
	protected Set<Graphics> graphics = new HashSet<Graphics>();;
	
	public Node() {
	}


	public void addGraphics(Graphics gd) {
		graphics.add(gd);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Set<Graphics> getGraphics() {
		return graphics;
	}

	public void setGraphics(Set<Graphics> graphics) {
		this.graphics = graphics;
	}
	
}
