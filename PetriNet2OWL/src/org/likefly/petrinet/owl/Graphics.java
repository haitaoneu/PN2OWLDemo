package org.likefly.petrinet.owl;

import java.util.HashSet;
import java.util.Set;

public class Graphics {
	
	private Set<Position> positions = new HashSet<Position>();;
	private Set<Offset> offsets = new HashSet<Offset>();
	
	public Graphics() {
	}

	public Set<Position> getPositions() {
		return positions;
	}

	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}

	public Set<Offset> getOffsets() {
		return offsets;
	}

	public void setgOffsets(Set<Offset> offsets) {
		this.offsets = offsets;
	}
	
	public void addPosition(Position p) {
		positions.add(p);
	}
	
	public void addOffset(Offset o) {
		offsets.add(o);
	}
}
