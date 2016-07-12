/**
 * Copyright (C) 2013
 * @author like
 */
package org.likefly.petrinet.owl;

import java.util.HashSet;
import java.util.Set;


/*
 * Name被当成了一个对象处理，因为Name下面还可能包括Graphic等
 */
public class Name {
	
	private String text;
	private Set<Graphics> graphics = new HashSet<Graphics>();
	
	public Name() {
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public Set<Graphics> getGraphics() {
		return graphics;
	}

	public void setGraphics(Set<Graphics> graphics) {
		this.graphics = graphics;
	}

	public String getText() {
		return text;
	}
	
	public void addGraphics(Graphics gd) {
		graphics.add(gd);
	}
}
