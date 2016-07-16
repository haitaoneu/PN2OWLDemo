package org.likefly.owl;

import org.semanticweb.owlapi.model.OWLIndividual;

public class Individual {
	
	private OWLIndividual individual;
	private String id;

	public OWLIndividual getIndividual() {
		return individual;
	}

	public void setIndividual(OWLIndividual individual) {
		this.individual = individual;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
}
