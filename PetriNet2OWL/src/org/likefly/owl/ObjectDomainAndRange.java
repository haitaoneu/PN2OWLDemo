/**
 * 
 */
package org.likefly.owl;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;

/**
 * @author like
 * @ 2013-5-11
 */
public class ObjectDomainAndRange {
	
	private Set<OWLClass> range = new HashSet<OWLClass>();
	private Set<OWLClass> domain = new HashSet<OWLClass>();
	
	/**
	 * 
	 */
	public ObjectDomainAndRange() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public void addRange(OWLClass r) {
		range.add(r);
	}
	public void addDomain(OWLClass d) {
		domain.add(d);
	}

	public Set<OWLClass> getRange() {
		return range;
	}

	public void setRange(Set<OWLClass> range) {
		this.range = range;
	}

	public Set<OWLClass> getDomain() {
		return domain;
	}

	public void setDomain(Set<OWLClass> domain) {
		this.domain = domain;
	}
	
}
