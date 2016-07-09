/**
 * 
 */
package org.likefly.owl;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDatatype;

/**
 * @author like
 *
 */
public class DataDomainAndRange {
	
	private OWLDatatype range;
	private Set<OWLClass> domain = new HashSet<OWLClass>();
	
	
	public OWLDatatype getRange() {
		return range;
	}
	public void setRange(OWLDatatype range) {
		this.range = range;
	}
	public Set<OWLClass> getDomain() {
		return domain;
	}
	public void setDomain(Set<OWLClass> domain) {
		this.domain = domain;
	}
	
	public void addDomain(OWLClass d) {
		domain.add(d);
	}
	
}
