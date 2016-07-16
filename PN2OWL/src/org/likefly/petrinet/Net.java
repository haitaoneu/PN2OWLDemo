package org.likefly.petrinet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Net extends TransitionNode {
	

	private static final long serialVersionUID = 3443677197891839874L;
	private List<Element> elements = new LinkedList<Element>();
	private Point viewTranslation = new Point(0, 0);
	
	public void writeToFile(File file) throws FileNotFoundException, IOException {
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(this);
		fileOut.close();
	}
	
	@SuppressWarnings("unchecked")
	public static Net readFromFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		Net storedSubnet = null;
		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		storedSubnet = (Net)objIn.readObject();
		fileIn.close();
		return storedSubnet;
	}
	
	public Point getViewTranslation() {
		return new Point(viewTranslation);
	}
	
	public void setViewTranslation(Point viewTranslation) {
		this.viewTranslation = new Point(viewTranslation);
	}

	public void setViewTranslationToCenter() {
		int centerX = Math.round((float)getBounds().getCenterX());
		int centerY = Math.round((float)getBounds().getCenterY());
		Point center = new Point(-centerX, -centerY);
		setViewTranslation(center);
	}

	public void setViewTranslationToCenterRecursively() {
		setViewTranslationToCenter();
		for (Net subnet : getSubnetsRecursively()) {
			subnet.setViewTranslationToCenter();
		}
	}
	
	public List<Element> getElements() {
		return Collections.unmodifiableList(elements);
	}
	
	public List<Element> getElementsCopy() {
		return new LinkedList<Element>(elements);
	}
	
	public void setElements(List<Element> elements) {
		this.elements = elements;
		for (Element element : elements) {
			element.setParentSubnet(this);
		}
	}
	
	public Element getElementByXY(int x, int y) {
		for (int i = elements.size() - 1; i >= 0; i--) { // Check elements from front to back.
			Element element = (Element)elements.get(i);
			if (element.containsPoint(x,y)) {
				return element;
			}
		}
		return null;
	}
	
	public Set<Net> getSubnets() {
		Set<Net> subnets = new HashSet<Net>();
		for (Element element : getElements()) {
			if (element instanceof Net) {
				subnets.add((Net)element);
			}
		}
		return subnets;
	}
	
	public void addElement(Element element) {
		element.setParentSubnet(this);
		if (element instanceof ReferenceArc || element instanceof ArcEdge) {
			elements.add(0, element); //background
		}
		else {
			elements.add(element);
		}
	}
	
	public void removeElement(Element element) {
		elements.remove(element);
	}
	
	public void removeElements() {
		elements.clear();
	}

	public void addAll(Set<Element> elements) {
		for (Element element : elements) {
			addElement(element);
		}
	}

	public void removeAll(Set<Element> elements) {
		for (Element element : elements) {
			removeElement(element);
		}
	}
	
	public Set<ArcEdge> getArcEdges() {
		Set<ArcEdge> arcs = new HashSet<ArcEdge>();
		for (Element element : elements) {
			if (element instanceof ArcEdge) {
				ArcEdge arc = (ArcEdge)element;
				arcs.add(arc);
			}
		}
		return arcs;
	}

	public Set<Arc> getArcs() {
		Set<Arc> arcs = new HashSet<Arc>();
		for (Element element : elements) {
			if (element instanceof Arc) {
				Arc arc = (Arc)element;
				arcs.add(arc);
			}
		}
		return arcs;
	}

	public Set<ReferenceArc> getReferenceArcs() {
		Set<ReferenceArc> referenceArcs = new HashSet<ReferenceArc>();
		for (Element element : elements) {
			if (element instanceof ReferenceArc) {
				ReferenceArc referenceArc = (ReferenceArc)element;
				referenceArcs.add(referenceArc);
			}
		}
		return referenceArcs;
	}

	public ArcEdge getArcEdge(PlaceNode placeNode, TransitionNode transitionNode, boolean placeToTransition) {
		for (Element element : getElements()) {
			if (element instanceof ArcEdge) {
				ArcEdge arcEdge = (ArcEdge)element;
				if (arcEdge.getPlaceNode() == placeNode && arcEdge.getTransitionNode() == transitionNode && arcEdge.isPlaceToTransition() == placeToTransition) {
					return arcEdge;
				}
			}
		}
		return null;
	}
	
	public Set<Transition> getTransitions() {
		Set<Transition> transitions = new HashSet<Transition>();
		for (Element element : getElements()) {
			if (element instanceof Transition) {
				transitions.add((Transition)element);
			}
		}
		return transitions;
	}
	
	public Set<Place> getPlaces() {
		Set<Place> places = new HashSet<Place>();
		for (Element element : getElements()) {
			if (element instanceof Place) {
				places.add((Place)element);
			}
		}
		return places;
	}

	public Set<Node> getNodes() {
		Set<Node> nodes = new HashSet<Node>();
		for (Element element : getElements()) {
			if (element instanceof Node) {
				nodes.add((Node)element);
			}
		}
		return nodes;
	}

	public Set<Node> getNodesRecursively() {
		Set<Node> nodes = new HashSet<Node>();
		for (Element element : elements) {
			if (element instanceof Net) {
				Net subnet = (Net)element;
				nodes.addAll(subnet.getNodesRecursively());
			}
			if (element instanceof Node) { // including subnets
				Node node = (Node)element;
				nodes.add(node);
			}
		}
		return nodes;
	}

	public Set<Place> getPlacesRecursively() {
		Set<Place> places = new HashSet<Place>();
		for (Element element : elements) {
			if (element instanceof Net) {
				Net subnet = (Net)element;
				places.addAll(subnet.getPlacesRecursively());
			}
			else if (element instanceof Place) {
				Place place = (Place)element;
				places.add(place);
			}
		}
		return places;
	}

	public Set<ReferencePlace> getReferencePlaces() {
		Set<ReferencePlace> referencePlaces = new HashSet<ReferencePlace>();
		for (Element element : elements) {
			if (element instanceof ReferencePlace) {
				ReferencePlace referencePlace = (ReferencePlace)element;
				referencePlaces.add(referencePlace);
			}
		}
		return referencePlaces;
	}

	public Set<ReferencePlace> getReferencePlacesRecursively() {
		Set<ReferencePlace> referencePlaces = new HashSet<ReferencePlace>();
		for (Element element : elements) {
			if (element instanceof Net) {
				Net subnet = (Net)element;
				referencePlaces.addAll(subnet.getReferencePlacesRecursively());
			}
			else if (element instanceof ReferencePlace) {
				ReferencePlace referencePlace = (ReferencePlace)element;
				referencePlaces.add(referencePlace);
			}
		}
		return referencePlaces;
	}
	
	public Set<Transition> getTransitionsRecursively() {
		Set<Transition> transitions = new HashSet<Transition>();
		for (Element element : elements) {
			if (element instanceof Net) {
				Net subnet = (Net)element;
				transitions.addAll(subnet.getTransitionsRecursively());
			}
			else if (element instanceof Transition) {
				Transition transition = (Transition)element;
				transitions.add(transition);
			}
		}
		return transitions;
	}

	public Set<Net> getSubnetsRecursively() {
		Set<Net> subnets = new HashSet<Net>();
		for (Element element : elements) {
			if (element instanceof Net) {
				Net subnet = (Net)element;
				subnets.add(subnet);
				subnets.addAll(subnet.getSubnetsRecursively());
			}
		}
		return subnets;
	}

	@Override
	public void draw(Graphics g, DrawingOptions drawingOptions) {
		g.setColor(Color.white);
		g.fillRect(getStart().x, getStart().y, getWidth(), getHeight());
		g.setColor(color);
		g.drawRect(getStart().x, getStart().y, getWidth()-1, getHeight()-1);
		int rectanglesGap = 5;
		g.drawRect(getStart().x + rectanglesGap, getStart().y + rectanglesGap, getWidth()-1 - 2*rectanglesGap, getHeight()-1 - 2*rectanglesGap);
		drawLabel(g);
		
		System.out.println(getStart().x);
		
	}
	
	public Rectangle getBounds() {
		Rectangle bounds = null;
        
        for (Element element : elements) {
            if (bounds == null) {
                bounds = new Rectangle(element.getStart().x, element.getStart().y, element.getWidth(), getHeight());
            }
			bounds.add(element.getStart().x, element.getStart().y);
			bounds.add(element.getEnd().x, element.getEnd().y);
			bounds.add(element.getStart().x, element.getEnd().y);
			bounds.add(element.getEnd().x, element.getStart().y);
			if (element instanceof Edge) {
				Edge edge = (Edge)element;
				for (Point breakPoint : edge.getBreakPoints()) {
					bounds.add(breakPoint);
				}
			}
		}
        if (bounds == null) {
            bounds = new Rectangle();
        }
		bounds.width++;
		bounds.height++;
		return bounds;
	}
	
	public Rectangle getBoundsRecursively() {
		return getBoundsRecursively(this);
	}
	
	private Rectangle getBoundsRecursively(Net subnet) {
		Rectangle bounds = subnet.getBounds();
		for (Element element : subnet.elements) {
			if (element instanceof Net) {
				Net subsubnet = (Net)element;
				Rectangle subsubnetBounds = getBoundsRecursively(subsubnet);
				subsubnetBounds.translate(subsubnet.getCenter().x, subsubnet.getCenter().y);
				bounds = bounds.createUnion(subsubnetBounds).getBounds();
			}
		}
		return bounds;
	}
	
	public Node getNodeByLabel(String label) {
		for (Element element : elements) {
			if (element instanceof Node) {
				Node node = (Node)element;
				if (label.equals(node.getLabel())) {
					return node;
				}
			}
		}
		return null;
	}

	public Node getNodeById(String id) {
		for (Element element : elements) {
			if (element instanceof Node) {
				Node node = (Node)element;
				if (id.equals(node.getId())) {
					return node;
				}
			}
		}
		return null;
	}


//	public BufferedImage getPreview(Marking marking) {
//		CachedGraphics2D cachedGraphics = new CachedGraphics2D();
//		cachedGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		DrawingOptions drawingOptions = new DrawingOptions();
//		drawingOptions.setMarking(marking);
//		//marking.getLock().readLock().lock();
//		try {
//			for (Element element : getElements()) {
//				element.draw(cachedGraphics, drawingOptions);
//			}
//		} finally {
//			//marking.getLock().readLock().unlock();
//		}
//		Rectangle bounds = cachedGraphics.getIntegerBounds();
//		int width = bounds.width;
//		int height = bounds.height;
//		width = Math.max(width, 1);
//		height = Math.max(height, 1);
//		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D imageGraphics = (Graphics2D)bufferedImage.getGraphics();
//		imageGraphics.fillRect(0, 0, width, height); // paint the background white
//		imageGraphics.translate(- bounds.x, - bounds.y);
//		cachedGraphics.applyToGraphics(imageGraphics);
//		return bufferedImage;
//	}
    
    @Override
	public Net getClone() {
		Net subnet = (Net)super.getClone();
        subnet.viewTranslation = this.viewTranslation.getLocation();

        subnet.elements = new LinkedList<Element>();
        for (Element element : this.getElements()) {
			subnet.addElement(element.getClone());
		}
		return subnet;
    }
}
