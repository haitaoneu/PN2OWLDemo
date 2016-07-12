/**
 * @author like
 * 2013-5-13最后更新
 * 这个类是又Petri网系统想OWL本体转换最核心的类。实现的功能包括了根据提供的Net系统构建OWL本体Model
 * 以及Individual，各个函数非必要与外界的通信一律设成私有函数。调用的主方法是buildNetSystem。
 * 传入一个包含一个或者多个的网系统。
 * 得到本体。
 */
package org.likefly.owl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.likefly.petrinet.owl.Arc;
import org.likefly.petrinet.owl.Graphics;
import org.likefly.petrinet.owl.Inscription;
import org.likefly.petrinet.owl.Name;
import org.likefly.petrinet.owl.Offset;
import org.likefly.petrinet.owl.PTMarking;
import org.likefly.petrinet.owl.Page;
import org.likefly.petrinet.owl.PetriNet;
import org.likefly.petrinet.owl.Place;
import org.likefly.petrinet.owl.Position;
import org.likefly.petrinet.owl.RefPlace;
import org.likefly.petrinet.owl.RefTransition;
import org.likefly.petrinet.owl.Transition;
import org.likefly.util.PNMLParserException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;


/*
 * OWL本体构建器，根据网系统的所有对象构建OWL本体系统
 * 在类的基础上添加属性和约数，并实例化主要的对象
 */
@SuppressWarnings("unused")
public class OWLGenerator {

	private OWLOntologyManager manager = null;
	private IRI ontologyIRI = null;
	private OWLOntology ont = null;
	private OWLDataFactory factory = null;
	private Set<OWLAxiom> owlAxioms = new HashSet<OWLAxiom>();
	private ObjectIdGenerator oig = new ObjectIdGenerator();
	private Set<Individual> store = new HashSet<Individual>();
	
	private OWLClass netClass;
	private OWLClass placeClass;
	private OWLClass pageClass;
	private OWLClass arcClass;
	private OWLClass transitionClass;
	private OWLClass nameClass;
	private OWLClass initialMarkingClass;
	private OWLClass graphicsClass;
	private OWLClass transitionRefClass;
	private OWLClass placeRefClass;
	private OWLClass inscriptionClass;
	private OWLClass positionClass;
	private OWLClass offsetClass;
	private OWLClass nodeClass;

	//数据类型：
	//目前所涉及的P/T网之中只需要int和string两种对象就已经足够了
	private OWLDatatype intDatatype;
	private OWLDatatype stringDatatype;
	
	private ObjectDomainAndRange haveName = new ObjectDomainAndRange();
	private ObjectDomainAndRange haveGraphics = new ObjectDomainAndRange();
	private ObjectDomainAndRange havePage = new ObjectDomainAndRange();
	private ObjectDomainAndRange havePlace = new ObjectDomainAndRange();
	private ObjectDomainAndRange haveTransition = new ObjectDomainAndRange();
	private ObjectDomainAndRange havePlaceRef = new ObjectDomainAndRange();
	private ObjectDomainAndRange haveArc = new ObjectDomainAndRange();
	private ObjectDomainAndRange haveTransitionRef = new ObjectDomainAndRange();
	private ObjectDomainAndRange havePosition = new ObjectDomainAndRange();
	private ObjectDomainAndRange haveOffset = new ObjectDomainAndRange();
	private ObjectDomainAndRange haveInscription = new ObjectDomainAndRange();
	private ObjectDomainAndRange haveInitialMarking = new ObjectDomainAndRange();

	
	private DataDomainAndRange haveId = new DataDomainAndRange();
	private DataDomainAndRange haveX = new DataDomainAndRange();
	private DataDomainAndRange haveY = new DataDomainAndRange();
	
	private OWLDataProperty hasId;
	private OWLDataProperty hasX;
	private OWLDataProperty hasY;
	
	private OWLObjectProperty hasName;
	private OWLObjectProperty hasGraphics;
	private OWLObjectProperty hasPage;
	private OWLObjectProperty hasPlace;
	private OWLObjectProperty hasTransition;
	private OWLObjectProperty hasInitialMarking;
	private OWLObjectProperty hasInscription;
	private OWLObjectProperty hasOffset;
	private OWLObjectProperty hasPosition;
	private OWLObjectProperty hasArc;
	private OWLObjectProperty hasPlaceRef;
	private OWLObjectProperty hasTransitionRef;


	private boolean thereHasId = false;
	private boolean thereHasX = false;
	private boolean thereHasY = false;
	
	
	private boolean thereHasName = false;
	private boolean thereHasGraphics = false;
	private boolean thereHasPage = false;
	private boolean thereHasPlace = false;
	private boolean thereHasTransition = false;
	private boolean thereHasInitialMarking = false;
	private boolean thereHasPlaceRef = false;
	private boolean thereHasTransitionRef = false;
	private boolean thereHasOffset = false;
	private boolean thereHasPosition = false;
	private boolean thereHasArc = false;
	private boolean thereHasInscription = false;

	
	public OWLGenerator(String uri) throws OWLOntologyCreationException {
		manager = OWLManager.createOWLOntologyManager();
		ontologyIRI = IRI.create(uri);
		ont = manager.createOntology(ontologyIRI);
		factory = manager.getOWLDataFactory();
	}
	
	/*
	 * OWL本体里面类在赋值之前并无区别
	 */
	private OWLClass getClass(String name) {
		return factory.getOWLClass(
				IRI.create(ontologyIRI + "#" + name));
	}
	
	/**
	 * 创建本体必要的元素，之后再填充
	 * @throws OWLOntologyCreationException
	 */
	private void createOWLOntology() throws OWLOntologyCreationException {


		netClass = getClass("PetriNet");
		placeClass = getClass("Place");
		transitionClass = getClass("Transition");
		arcClass = getClass("Arc");
		placeRefClass = getClass("RefPlace");
		transitionRefClass = getClass("RefTransition");
		inscriptionClass = getClass("Inscription");
		nameClass = getClass("Name");
		offsetClass = getClass("Offset");
		positionClass = getClass("Position");
		pageClass = getClass("Page");
		graphicsClass = getClass("Graphics");
		initialMarkingClass = getClass("PTMarking");
		nodeClass = getClass("Node");

		intDatatype = factory.getIntegerOWLDatatype();
		stringDatatype = factory.getOWLDatatype(
				OWL2Datatype.XSD_STRING.getIRI());
		
		hasId = getDataProperty("hasId");
		hasX = getDataProperty("hasX");
		hasY = getDataProperty("hasY");
	
		haveId.setRange(stringDatatype);
		haveX.setRange(intDatatype);
		haveY.setRange(intDatatype);
		
		hasName = getObjectProperty("hasName");
		haveName.addRange(nameClass);
		hasGraphics = getObjectProperty("hasGraphics");
		haveGraphics.addRange(graphicsClass);
		hasPage = getObjectProperty("hasPage");
		havePage.addRange(pageClass);
		hasPlace = getObjectProperty("hasPlace");
		havePlace.addRange(placeClass);
		hasArc = getObjectProperty("hasArc");
		haveArc.addRange(arcClass);
		hasTransition = getObjectProperty("hasTransition");
		haveTransition.addRange(transitionClass);
		hasPosition = getObjectProperty("hasPosition");
		havePosition.addRange(positionClass);
		hasOffset = getObjectProperty("hasOffset");
		haveOffset.addRange(offsetClass);
		hasInscription = getObjectProperty("hasInscription");
		haveInscription.addRange(inscriptionClass);
		hasInitialMarking = getObjectProperty("hasInitialMarking");
		haveInitialMarking.addRange(initialMarkingClass);
		
	}
	
	private void endOWLGenerate() {
		
		if (thereHasId == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(haveId.getDomain());
			addDataPropertyDomainAndRange(hasId, d, haveId.getRange());
		}
		
		if (thereHasX == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(haveX.getDomain());
			addDataPropertyDomainAndRange(hasX, d, haveX.getRange());
		}
		
		if (thereHasY == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(haveY.getDomain());
			addDataPropertyDomainAndRange(hasY, d, haveY.getRange());
		}
		
		if (thereHasName == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(haveName.getDomain());
			OWLObjectUnionOf r = factory.getOWLObjectUnionOf(haveName.getRange());
			addObjectPropertyDomainAndRange(hasName, d, r);
		}
		
		if (thereHasGraphics == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(haveGraphics.getDomain());
			OWLObjectUnionOf r = factory.getOWLObjectUnionOf(haveGraphics.getRange());
			addObjectPropertyDomainAndRange(hasGraphics, d, r);
		}
		
		if (thereHasPage == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(havePage.getDomain());
			OWLObjectUnionOf r = factory.getOWLObjectUnionOf(havePage.getRange());
			addObjectPropertyDomainAndRange(hasPage, d, r);
		}
		
		if (thereHasPlace == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(havePlace.getDomain());
			OWLObjectUnionOf r = factory.getOWLObjectUnionOf(havePlace.getRange());
			addObjectPropertyDomainAndRange(hasPlace, d, r);
		}
		
		if (thereHasArc == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(haveArc.getDomain());
			OWLObjectUnionOf r = factory.getOWLObjectUnionOf(haveArc.getRange());
			addObjectPropertyDomainAndRange(hasArc, d, r);
		}
		
		if (thereHasTransition == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(haveTransition.getDomain());
			OWLObjectUnionOf r = factory.getOWLObjectUnionOf(haveTransition.getRange());
			addObjectPropertyDomainAndRange(hasTransition, d, r);
		}
		
		if (thereHasPosition == true) {
			OWLObjectUnionOf d = 
				factory.getOWLObjectUnionOf(havePosition.getDomain());
			OWLObjectUnionOf r = 
				factory.getOWLObjectUnionOf(havePosition.getRange());
			addObjectPropertyDomainAndRange(hasPosition, d, r);
		}
		
		if (thereHasOffset == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(haveOffset.getDomain());
			OWLObjectUnionOf r = factory.getOWLObjectUnionOf(haveOffset.getRange());
			addObjectPropertyDomainAndRange(hasOffset, d, r);
		}
		
		if (thereHasPlaceRef == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(havePlaceRef.getDomain());
			OWLObjectUnionOf r = factory.getOWLObjectUnionOf(havePlaceRef.getRange());
			addObjectPropertyDomainAndRange(hasPlaceRef, d, r);
		}
		
		if (thereHasTransitionRef == true) {
			OWLObjectUnionOf d = factory.getOWLObjectUnionOf(haveTransitionRef.getDomain());
			OWLObjectUnionOf r = factory.getOWLObjectUnionOf(haveTransitionRef.getRange());
			addObjectPropertyDomainAndRange(hasTransitionRef, d, r);
		}
		
		if (thereHasInscription == true) {
			OWLObjectUnionOf d = 
				factory.getOWLObjectUnionOf(haveInscription.getDomain());
			OWLObjectUnionOf r = 
				factory.getOWLObjectUnionOf(haveInscription.getRange());
			addObjectPropertyDomainAndRange(hasInscription, d, r);
		}
	}
	
	/*
	 * 如果有page对象在的话，需要对其进行处理得到本体模型
	 */
	private void dealPage(Page p, OWLIndividual pa) {
		
		addIndividualType(pageClass, pa);
		
		if (p.getId() != null) {

			thereHasId = true;
			haveId.addDomain(pageClass);
			owlAxioms.add(
					factory.getOWLDataPropertyAssertionAxiom(
					hasId, pa, p.getId()));
		}
		
		if (!p.getPlaces().isEmpty()) {
			
			Set<Place> pPlace = p.getPlaces();
			thereHasPlace = true;
			havePlace.addDomain(pageClass);
			Set<OWLIndividual> pag = new HashSet<OWLIndividual>();
			for (Place pl : pPlace) {
				OWLIndividual x = getNamedIndividual("Place");
        		owlAxioms.add(
    					factory.getOWLObjectPropertyAssertionAxiom(
    					hasPlace, pa, x));
        		pag.add(x);
				dealPlace(pl, x);
			}
			if (pag.size() > 1) {
        		owlAxioms.add(factory.getOWLDifferentIndividualsAxiom(pag));
        	}
			
		}

		if (!p.getTransitions().isEmpty()) {
			
			Set<Transition> tr = p.getTransitions();
			thereHasTransition = true;
			haveTransition.addDomain(pageClass);
			Set<OWLIndividual> pag = new HashSet<OWLIndividual>();
        	
        	for (Transition t : tr) {
      
            	OWLIndividual x = getNamedIndividual("Transition");
        		owlAxioms.add(
    					factory.getOWLObjectPropertyAssertionAxiom(
    					hasTransition, pa, x));
        		pag.add(x);
        		dealTransition(t, x);
        	}
        	if (pag.size() > 1) {
        		owlAxioms.add(factory.getOWLDifferentIndividualsAxiom(pag));
        	}
		}
		
		if (!p.getArcs().isEmpty()) {

			Set<Arc> pr = p.getArcs();
			thereHasArc = true;
			haveArc.addDomain(pageClass);
			Set<OWLIndividual> pag = new HashSet<OWLIndividual>();
			for (Arc a : pr) {
				OWLIndividual x = getNamedIndividual("Arc");
        		owlAxioms.add(
    					factory.getOWLObjectPropertyAssertionAxiom(
    					hasArc, pa, x));
        		pag.add(x);
				dealArc(a, x);
			}
			if (pag.size() > 1) {
        		owlAxioms.add(factory.getOWLDifferentIndividualsAxiom(pag));
        	}
		}
		
		Set<RefTransition> tref = p.getRefTransitions();
		
		if (!tref.isEmpty()) {
			
			thereHasTransitionRef = true;
			haveTransitionRef.addDomain(pageClass);
			
			for (RefTransition tre : tref) {
				dealTransitionRef(tre);
			}
		}
		
		Set<RefPlace> pref = p.getRefPlaces();
		
		if (!pref.isEmpty()) {

			thereHasPlaceRef = true;
			havePlaceRef.addDomain(pageClass);
			
			for (RefPlace pre : pref) {
				dealPlaceRef(pre);
			}
		}
		
		Set<Graphics> gdp = p.getGraphics();
		
		if (!gdp.isEmpty()) {
			
			thereHasGraphics = true;
			haveGraphics.addDomain(placeClass);
			
			for (Graphics gd : gdp) {
				dealGraphic(gd);
			}
		}
		
		OWLDataExactCardinality hasIdRestriction = factory
				.getOWLDataExactCardinality(1, hasId);
		manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(
				pageClass, hasIdRestriction));
	}
	
	
	//Graphics对象
	private void dealGraphic(Graphics gd) {

		Set<Position> p = gd.getPositions();
		
		if (!p.isEmpty()) {
			
			thereHasPosition = true;
			havePosition.addDomain(graphicsClass);
			
			for (Position po : p) {
				dealPosition(po);
			}
		}
		
		Set<Offset> o = gd.getOffsets();
		
		if (!o.isEmpty()) {
			
			thereHasOffset = true;
			haveOffset.addDomain(graphicsClass);
			
			for (Offset of : o) {
				dealOffset(of);
			}
		}
	}
	
	private void dealOffset(Offset of) {
	
		thereHasX = true;
		haveX.addDomain(offsetClass);
		thereHasY = true;
		haveY.addDomain(offsetClass);
		
		OWLDataExactCardinality hasXRestriction = factory
			.getOWLDataExactCardinality(1, hasX);
		OWLDataExactCardinality hasYRestriction = factory
			.getOWLDataExactCardinality(1, hasY);
		OWLObjectIntersectionOf intersection = factory
			.getOWLObjectIntersectionOf(
				hasXRestriction, hasYRestriction);
	
	    manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(
	    		offsetClass, intersection));
	}
	

	private void dealPosition(Position po) {
		// TODO Auto-generated method stub
		if (po == null)return;
		thereHasX = true;
		haveX.addDomain(positionClass);
		thereHasY = true;
		haveY.addDomain(positionClass);
		
		OWLDataExactCardinality hasXRestriction = factory
			.getOWLDataExactCardinality(1, hasX);
		OWLDataExactCardinality hasYRestriction = factory
			.getOWLDataExactCardinality(1, hasY);
   
		OWLObjectIntersectionOf intersection = factory
			.getOWLObjectIntersectionOf(
			hasXRestriction, hasYRestriction);

		manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(
    		positionClass, intersection));
	}
	
	
	private void dealPlaceRef(RefPlace pre) {
		// TODO Auto-generated method stub
		if (pre.getId() != null) {
			thereHasId = true;
			haveId.addDomain(placeRefClass);
		}
		
		if (pre.getIDRef() != null) {
			
			OWLObjectProperty placeReferenceHasIDRef = 
				getObjectProperty("placeReferenceHasIDRef");
			addObjectPropertyDomainAndRange(
					placeReferenceHasIDRef, placeRefClass, placeClass);
		}
	}
	
	private void dealTransitionRef(RefTransition tre) {
		
		if (tre.getId() != null) {

			thereHasId = true;
			haveId.addDomain(transitionRefClass);
		}
		
		if (tre.getIDRef() != null) {
		
			OWLObjectProperty TransitionRefHasIDRef = 
				getObjectProperty("TransitionRefHasIDRef");
			addObjectPropertyDomainAndRange(
					TransitionRefHasIDRef, transitionRefClass,
					transitionClass);
		}
	}
	
	private void dealArc(Arc a, OWLIndividual arcIn) {
		
        addIndividualType(arcClass, arcIn);
		
		if (a.getId() != null) {
			thereHasId = true;
			haveId.addDomain(arcClass);
			owlAxioms.add(
					factory.getOWLDataPropertyAssertionAxiom(
					hasId, arcIn, a.getId()));
		}
		

		OWLObjectProperty hasSource = getObjectProperty("hasSource");
		OWLObjectProperty hasTarget = getObjectProperty("hasTarget");
		//lujian
//		addObjectPropertyDomainAndRange(hasSource, arcClass, factory.getOWLObjectUnionOf(
//				placeClass, transitionClass));
		//addObjectPropertyRange(hasSource, factory.getOWLObjectUnionOf(
		//		placeClass, transitionClass));
		//lujian
//		addObjectPropertyDomainAndRange(hasTarget, arcClass, 
//				factory.getOWLObjectUnionOf(
//				placeClass, transitionClass));

		//Arc class
		OWLClassExpression arcSourcePlace 
				= factory.getOWLObjectAllValuesFrom(hasSource, placeClass);
		
		//注意这个地方如果是加一个约束：hasTarget具有某个值(hasValue)的话，值应该是实例。
		//而如果是allValuesFrom的话应该是一个类，这一点很重要。记住: 值是实例。
		OWLClassExpression arcTargetTransition
				= factory.getOWLObjectAllValuesFrom(
				hasTarget, transitionClass);
		OWLClassExpression arcFirst = factory.
				getOWLObjectIntersectionOf(
				arcSourcePlace, arcTargetTransition);
		OWLClassExpression arcSourceTransition
				= factory.getOWLObjectAllValuesFrom(
				hasSource, transitionClass);
		OWLClassExpression arcTargetPlace 
				= factory.getOWLObjectAllValuesFrom(
				hasTarget, placeClass);
		OWLClassExpression arcSecond = factory.
				getOWLObjectIntersectionOf(
				arcSourceTransition, arcTargetPlace);
		//lujian
//		OWLClassExpression arcR = factory.getOWLObjectUnionOf(
//				arcFirst, arcSecond);
//	    OWLAxiom arcDefinition = factory.getOWLEquivalentClassesAxiom(
//	    		arcClass, arcR);
	        
//	    manager.addAxiom(ont, arcDefinition);
	    ///~
	    
	    OWLDataExactCardinality hasIdRestriction = factory
			.getOWLDataExactCardinality(1, hasId);
	    OWLObjectExactCardinality hasSourceRestriction = factory
			.getOWLObjectExactCardinality(1, hasSource);
	    OWLObjectExactCardinality hasTargetRestriction = factory
			.getOWLObjectExactCardinality(1, hasTarget);
	    
	    OWLObjectIntersectionOf intersection = factory
			.getOWLObjectIntersectionOf(
				hasIdRestriction, hasTargetRestriction, hasSourceRestriction);

	    manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(
	    		arcClass, intersection));
	    
	    OWLIndividual source = null;
		OWLIndividual target = null;
		
		for (Individual is : store) {
			
			if (a.getTargetId().equals(is.getId())) {
				target = is.getIndividual();
				owlAxioms.add(
						factory.getOWLObjectPropertyAssertionAxiom(
						hasTarget, arcIn, target));
			}
			if (a.getSourceId().equals(is.getId())) {
				source = is.getIndividual();
				owlAxioms.add(
						factory.getOWLObjectPropertyAssertionAxiom(
						hasSource, arcIn, source));
			}
		}
		
		Set<Inscription> ip = a.getInscription();
		
		if (!ip.isEmpty()) {
			thereHasInscription = true;
			haveInscription.addDomain(arcClass);
			for (Inscription nd : ip) {
				dealInscription(nd);
			}
		}
		
		Set<Graphics> gdp = a.getGraphics();
		
		if (!gdp.isEmpty()) {
			
			thereHasGraphics = true;
			haveGraphics.addDomain(arcClass);
			
			for (Graphics nd : gdp) {
				dealGraphic(nd);
			}
		}
	}
	
	private void dealInscription(Inscription nd) {
		// TODO Auto-generated method stub		
		Set<Graphics> gdp = nd.getGraphics();
		
		if (!gdp.isEmpty()) {
			thereHasGraphics = true;
			haveGraphics.addDomain(inscriptionClass);
			for (Graphics n : gdp) {
				dealGraphic(n);
			}
		}
		OWLDataProperty hasInscriptionText = 
			getDataProperty("hasIncriptionText");
		addDataPropertyDomain(hasInscriptionText, inscriptionClass);
		addDataPropertyRange(hasInscriptionText, intDatatype);
	}

	private void dealPlace(Place p, OWLIndividual pla) {
		
        addIndividualType(placeClass, pla);
        
		if (p.getId() != null) {
			thereHasId = true;
			haveId.addDomain(placeClass);
			owlAxioms.add(
					factory.getOWLDataPropertyAssertionAxiom(
					hasId, pla, p.getId()));
			Individual temp = new Individual();
			temp.setId(p.getId());
			temp.setIndividual(pla);
			store.add(temp);
		}
		
		if (p.getName() != null) {
			
			thereHasName = true;
			haveName.addDomain(placeClass);
			//OWLIndividual name = getNamedIndividual("Name");
			dealName(p.getName());
		}
		
		if (!p.getInitialMarkings().isEmpty()) {
			Set<PTMarking> imk = p.getInitialMarkings();
			thereHasInitialMarking = true;
			haveInitialMarking.addDomain(pageClass);
			
			for (PTMarking i : imk) 
				dealInitialMarking(i);
		}
		
		Set<Graphics> gdp = p.getGraphics();
		
		if (!gdp.isEmpty()) {
			
			thereHasGraphics = true;
			haveGraphics.addDomain(pageClass);
			
			for (Graphics gd : gdp) {
				dealGraphic(gd);
			}
		}
		
		OWLDataExactCardinality hasIdRestriction = factory
			.getOWLDataExactCardinality(1, hasId);
		manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(
				placeClass, hasIdRestriction));
	}
	
	private void dealInitialMarking(PTMarking marking) {
		if (marking == null) {
			return;
		}
		OWLDataProperty hasMarkingText = getDataProperty("hasMarkingText");
		addDataPropertyDomain(hasMarkingText, initialMarkingClass);
		addDataPropertyRange(hasMarkingText, intDatatype);
	}
	


	private void dealTransition(Transition p, OWLIndividual transition) {
		
		addIndividualType(transitionClass, transition);
        
		if (p.getId() != null) {

			thereHasId = true;
			haveId.addDomain(transitionClass);
			owlAxioms.add(
					factory.getOWLDataPropertyAssertionAxiom(
					hasId, transition, p.getId()));
			Individual temp = new Individual();
			temp.setId(p.getId());
			temp.setIndividual(transition);
			store.add(temp);
		}
		
		if (p.getName() != null) {
			
			thereHasName = true;
			haveName.addDomain(transitionClass);
			//OWLIndividual name = getNamedIndividual("Name");
			dealName(p.getName());
		}
		
		Set<Graphics> gdp = p.getGraphics();
		
		if (!gdp.isEmpty()) {
			
			thereHasGraphics = true;
			haveGraphics.addDomain(pageClass);
			
			for (Graphics gd : gdp) {
				dealGraphic(gd);
			}
		}
		
		OWLDataExactCardinality hasIdRestriction = factory
				.getOWLDataExactCardinality(1, hasId);
		manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(
				transitionClass, hasIdRestriction));
	}
	
	private void dealName(Name n) {
		
		if (!n.getGraphics().isEmpty()) {
			
			Set<Graphics> gdp = n.getGraphics();
			thereHasGraphics = true;
			haveGraphics.addDomain(nameClass);
			
			for (Graphics g : gdp) {
        		dealGraphic(g);
        	}
		}
		
		OWLDataProperty hasNameText = getDataProperty("hasNameText");
		addDataPropertyDomain(hasNameText, nameClass);
		addDataPropertyRange(hasNameText, stringDatatype);
	}

	
	private void dealNet(PetriNet n, OWLIndividual net) {
		
		addIndividualType(netClass, net);
		
		if (n.getId() != null) {
			
			thereHasId = true;
			haveId.addDomain(netClass);
			owlAxioms.add(
					factory.getOWLDataPropertyAssertionAxiom(
					hasId, net, n.getId()));
		}
		//hasType是只有net才有的属性，所以其值域和定义域都是唯一的。	
		//OWLDataProperty hasType = factory.getOWLDataProperty(
		//		IRI.create(ontologyIRI + "#hasType"));
		OWLDataProperty hasType = getDataProperty("hasType");
		addDataPropertyDomain(hasType, netClass);
		addDataPropertyRange(hasType, stringDatatype);
		
		if (n.getType() != null) {
			owlAxioms.add(
					factory.getOWLDataPropertyAssertionAxiom(
					hasType, net, n.getType()));
		}
		
		Set<Page> pa = n.getPages();
		
        if (!pa.isEmpty()) {
        	
        	thereHasPage = true;
        	havePage.addDomain(netClass);
        	Set<OWLIndividual> pag = new HashSet<OWLIndividual>();
        	
        	for (Page p : pa) {
        		OWLIndividual x = getNamedIndividual("Page");
        		owlAxioms.add(
    					factory.getOWLObjectPropertyAssertionAxiom(
    					hasPage, net, x));
        		pag.add(x);
        		dealPage(p, x);
        	}
        	//if the number of pages is bigger than 1,
        	//All of these pages should be defined to AllDifferent
        	if (pa.size() > 1) {
        		owlAxioms.add(factory.getOWLDifferentIndividualsAxiom(pag));
        	}
        }
          
        if (!n.getPlaces().isEmpty()) {
        	
        	Set<Place> pl = n.getPlaces();
        	thereHasPlace = true;
        	havePlace.addDomain(netClass);
        	Set<OWLIndividual> pag = new HashSet<OWLIndividual>();
        	
        	for (Place p : pl) {
        		
        		OWLIndividual x = getNamedIndividual("Place");
        		owlAxioms.add(
    					factory.getOWLObjectPropertyAssertionAxiom(
    					hasPlace, net, x));
        		pag.add(x);
        		dealPlace(p, x);
        	}
        	if (pag.size() > 1) {
        		owlAxioms.add(factory.getOWLDifferentIndividualsAxiom(pag));
        	}
        }
       
        if (!n.getTransitions().isEmpty()) {
        	Set<Transition> tr = n.getTransitions();
        	thereHasTransition = true;
        	haveTransition.addDomain(netClass);
        	Set<OWLIndividual> pag = new HashSet<OWLIndividual>();
        	
        	for (Transition t : tr) {
      
            	OWLIndividual x = getNamedIndividual("Transition");
        		owlAxioms.add(
    					factory.getOWLObjectPropertyAssertionAxiom(
    					hasTransition, net, x));
        		pag.add(x);
        		dealTransition(t, x);
        	}
        	if (pag.size() > 1) {
        		owlAxioms.add(factory.getOWLDifferentIndividualsAxiom(pag));
        	}
        }
        
        if (!n.getArcs().isEmpty()) {
        	
        	Set<Arc> ar = n.getArcs();
        	thereHasArc = true;
        	haveArc.addDomain(netClass);
        	Set<OWLIndividual> pag = new HashSet<OWLIndividual>();
        	
        	for (Arc a : ar) {
        		OWLIndividual x = getNamedIndividual("Arc");
        		owlAxioms.add(
    					factory.getOWLObjectPropertyAssertionAxiom(
    					hasArc, net, x));
        		pag.add(x);
        		dealArc(a, x);
        	}
        	if (pag.size() > 1) {
        		owlAxioms.add(factory.getOWLDifferentIndividualsAxiom(pag));
        	}
        }
        
        if (n.getName() != null) {
        	thereHasName = true;
        	haveName.addDomain(netClass);
        	//OWLIndividual name = getNamedIndividual("Name");
        	dealName(n.getName());
        }
        
        OWLDataExactCardinality hasIdRestriction = factory
        		.getOWLDataExactCardinality(1, hasId);
        OWLDataExactCardinality hasTypeRestriction = factory
				.getOWLDataExactCardinality(1, hasType);
        OWLObjectIntersectionOf intersection = factory
        		.getOWLObjectIntersectionOf(
        		hasIdRestriction, hasTypeRestriction);

        manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(
        		netClass, intersection));
	}
	/**
	 * 构建本体模型
	 * @param aFile
	 * @throws PNMLParserException 
	 * @throws OWLOntologyCreationException 
	 */
	public void generateOWL(Set<PetriNet> nets, File toFile, int type) 
		throws PNMLParserException, OWLOntologyCreationException {
		
		if (nets == null) {
			throw new PNMLParserException("Petri Net is Empty!");
		}
		
		createOWLOntology();

		for (PetriNet n : nets) {
			
			OWLIndividual net = getNamedIndividual("PetriNet");
			dealNet(n, net);
		}
		
		// erase the objectUnionOf label
//		endOWLGenerate();
        manager.addAxioms(ont, owlAxioms);
        
        try {
        	
        	// Save to RDF/XML
            //manager.saveOntology(ont, new RDFXMLOntologyFormat(),
            //        IRI.create("file:/tmp/philo.xml"));
        	//manager.saveOntology(ont, System.out);
        	
        	if (type == 1) {
        		manager.saveOntology(ont, new FileOutputStream(toFile));
        	}
        	else if (type == 2) {
        		manager.saveOntology(ont, new OWLXMLOntologyFormat(), 
        				new FileOutputStream(toFile));
        	}
        	else if (type == 3) {
        		manager.saveOntology(ont, new TurtleOntologyFormat(), 
        				new FileOutputStream(toFile));
        	}
        	else {
        		manager.saveOntology(ont, new ManchesterOWLSyntaxOntologyFormat(),
        				new FileOutputStream(toFile));
        	}
        	

		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error: Failed to output ontology!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private OWLDataProperty getDataProperty(String name) {
		return factory.getOWLDataProperty(
				IRI.create(ontologyIRI + "#" + name));
	}
	
	private void addDataPropertyDomain(
			OWLDataProperty dataProperty, OWLClassExpression cla) {
		owlAxioms.add(factory.getOWLDataPropertyDomainAxiom(
			dataProperty, cla));
	}
	
	private void addDataPropertyRange(
			OWLDataProperty dataProperty, OWLDataRange r) {
		owlAxioms.add(factory.getOWLDataPropertyRangeAxiom(
			dataProperty, r));
	}
	
	//create a NamedIndividual object by name
	private OWLIndividual getNamedIndividual(String name) {
		return factory.getOWLNamedIndividual(
    			IRI.create(ontologyIRI + "#" + oig.generatorOfId(name)));
	}

	private OWLObjectProperty getObjectProperty(String name) {
		return factory.getOWLObjectProperty(
				IRI.create(ontologyIRI + "#" + name));
	}
	
	private void addDataPropertyDomainAndRange(
			OWLDataProperty dataProperty, 
			OWLClassExpression domain, 
			OWLDataRange range) {
		owlAxioms.add(factory.getOWLDataPropertyDomainAxiom(
				dataProperty, domain));
		owlAxioms.add(factory.getOWLDataPropertyRangeAxiom(
				dataProperty, range));
	}
	
	
	private void addObjectPropertyDomainAndRange(
			OWLObjectProperty objectProperty, 
			OWLClassExpression domain, 
			OWLClassExpression range) {
		owlAxioms.add(factory.getOWLObjectPropertyDomainAxiom(
			objectProperty, domain));
		owlAxioms.add(factory.getOWLObjectPropertyRangeAxiom(
				objectProperty, range));
	}
	
	private void addIndividualType(OWLClassExpression a, 
			OWLIndividual in) {
		OWLClassAssertionAxiom classAssertionAx = factory.getOWLClassAssertionAxiom(
                a, in);
        // Add the axiom directly using the addAxiom convenience method on
        // OWLOntologyManager
        manager.addAxiom(ont, classAssertionAx);
	}
}
