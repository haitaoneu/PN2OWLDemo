package org.likefly.petrinet.owl;

import java.util.HashSet;
import java.util.Set;

/*
 * Arc类：表示节点之间的有向弧
 */
public class Arc {
	
	private String id;
	private String targetId;
	private String sourceId;
	private Set<Graphics> graphics;
	private Set<Inscription> inscription;

	
	public Arc() {
		graphics = new HashSet<Graphics>();
		inscription = new HashSet<Inscription>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	public void addGraphics(Graphics gd) {
		graphics.add(gd);
	}
	
	public void addInscription(Inscription isp) {
		inscription.add(isp);
	}

	public Set<Graphics> getGraphics() {
		return graphics;
	}

	public void setGraphics(Set<Graphics> gd) {
		this.graphics = gd;
	}

	public Set<Inscription> getInscription() {
		return inscription;
	}

	public void setInscription(Set<Inscription> aHasIsp) {
		this.inscription = aHasIsp;
	}
	
}
