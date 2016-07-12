package org.likefly.petrinet.owl;

import java.util.HashSet;
import java.util.Set;

/*
 * Arc上一般包含Inscription对象
 */
public class Inscription {
	
	private Set<Graphics> graphics = new HashSet<Graphics>();
	
	public Inscription() {
	}
	
	public void addGraphics(Graphics gd) {
		graphics.add(gd);
	}
	
	public Set<Graphics> getGraphics() {
		return graphics;
	}
	
	public void setGraphics(Set<Graphics> graphics) {
		this.graphics = graphics;
	}
	
}
