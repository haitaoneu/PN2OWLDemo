package org.likefly.petrinet;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;



public class Marking {

	protected Map<Place, Integer> map = new ConcurrentHashMap<Place, Integer>();
	private PetriNet petriNet;


	public Marking(Marking marking) {

		try {
			this.map = new ConcurrentHashMap<Place, Integer>(marking.map);
		} finally {
		}
		this.petriNet = marking.petriNet;
	}


	public Marking(PetriNet petriNet) {
		this.petriNet = petriNet;
	}


    public PetriNet getPetriNet() {
        return petriNet;
    }

	public int getTokens(PlaceNode placeNode) {
		Place place = placeNode.getPlace();
		if (place == null) { 
			return 0;
		}
		Marking marking = this;

        if (marking.map.get(place) == null) {
			return 0;
		}
		
		return marking.map.get(place);
	}



	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Marking other = (Marking) obj;
		if (this.petriNet != other.petriNet && (this.petriNet == null || !this.petriNet.equals(other.petriNet))) {
			return false;
		}
		if (this.map == other.map) {
			return true;
		}
		Set<Place> places = new HashSet<Place>(); // because map is sparse
		places.addAll(this.map.keySet());
		places.addAll(other.map.keySet());
		for (Place place : places) {
			if (this.getTokens(place) != other.getTokens(place)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + (this.petriNet != null ? this.petriNet.hashCode() : 0);
		for (Place place : this.map.keySet()) { 
			hash = 97 * hash + this.getTokens(place);
		}
		return hash;
	}
}
