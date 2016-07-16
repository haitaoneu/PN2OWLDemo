package org.likefly.editor.canvas;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import org.likefly.editor.PNEditor;
import org.likefly.editor.commands.AddArcCommand;
import org.likefly.editor.commands.AddReferenceArcCommand;
import org.likefly.petrinet.Arc;
import org.likefly.petrinet.ArcEdge;
import org.likefly.petrinet.Element;
import org.likefly.petrinet.Net;
import org.likefly.petrinet.Node;
import org.likefly.petrinet.PlaceNode;
import org.likefly.petrinet.ReferenceArc;
import org.likefly.petrinet.Transition;
import org.likefly.petrinet.TransitionNode;
import org.likefly.util.CollectionTools;


class ArcFeature implements Feature {
	

	ArcFeature(Canvas canvas) {
	}
	
	private Element sourceElement = null;
	private Arc connectingArc = null;
	private List<Element> backgroundElements = new ArrayList<Element>();
	private boolean started = false;
	private Net currentSubnet;
	
	@SuppressWarnings("deprecation")
	public void mousePressed(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		int mouseButton = event.getButton();
		
		if (mouseButton == MouseEvent.BUTTON1 &&
			PNEditor.getRoot().isSelectedTool_Arc() &&
			PNEditor.getRoot().getClickedElement() != null &&
			PNEditor.getRoot().getClickedElement() instanceof Node &&
			!started
		) {
			sourceElement = PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getElementByXY(x, y);
			connectingArc = new Arc((Node)sourceElement);
			backgroundElements.add(connectingArc);
			started = true;
			currentSubnet = PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet();
		}
	}

	@SuppressWarnings("deprecation")
	public void mouseDragged(int x, int y) {
		

		if (PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet() != currentSubnet) {
			cancelDragging();
		}
		
		Element targetElement = PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getElementByXY(x, y);

		if (started) {
			if (targetElement != null && (
				sourceElement instanceof PlaceNode && targetElement instanceof TransitionNode ||
				sourceElement instanceof TransitionNode && targetElement instanceof PlaceNode
			)) {
				connectingArc.setEnd(targetElement.getCenter().x, targetElement.getCenter().y);
				connectingArc.setDestination((Node)targetElement);
			}
			else {
				connectingArc.setEnd(x, y);
				connectingArc.setSource(null);
				connectingArc.setDestination(null);
			}
			PNEditor.getRoot().repaintCanvas();
		}
	}
	
	public void mouseMoved(int x, int y) {
		mouseDragged(x, y);
	}
	
	public void mouseReleased(int x, int y) {
		if (PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet() != currentSubnet) {
			cancelDragging();
		}
		Element targetElement = PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getElementByXY(x, y);

		if (started) {
			connectingArc.setEnd(x, y);
			if (sourceElement != targetElement) {
				if (targetElement != null) {
					if (sourceElement instanceof PlaceNode && targetElement instanceof TransitionNode ||
						sourceElement instanceof TransitionNode && targetElement instanceof PlaceNode
					) {
						boolean placeToTransition = sourceElement instanceof PlaceNode && targetElement instanceof TransitionNode;
						PlaceNode placeNode;
						TransitionNode transitionNode;
						if (placeToTransition) {
							placeNode = (PlaceNode)sourceElement;
							transitionNode = (TransitionNode)targetElement;
						}
						else {
							transitionNode = (TransitionNode)sourceElement;
							placeNode = (PlaceNode)targetElement;
						}
						
						ArcEdge arcEdge = PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getArcEdge(placeNode, transitionNode, placeToTransition);
						ArcEdge counterArcEdge = PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getArcEdge(placeNode, transitionNode, !placeToTransition);
						if (arcEdge == null) {
							if (transitionNode instanceof Transition) {
								PNEditor.getRoot().excuteCommand(new AddArcCommand(placeNode, (Transition)transitionNode, placeToTransition));
							}
							else if (transitionNode instanceof Net) {
								PNEditor.getRoot().excuteCommand(new AddReferenceArcCommand(placeNode, (Net)transitionNode, PNEditor.getRoot().getDocument().petriNet));
							}
							else {
								throw new RuntimeException("transitionNode not instanceof Transition neither Net");
							}
							
							PNEditor.getRoot().setClickedElement(CollectionTools.getFirstElement(PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getElements()));
						}
						else if (!(arcEdge instanceof ReferenceArc)) {
							PNEditor.getRoot().setClickedElement(arcEdge);
						}
					}
				}
				cancelDragging();
			}
		}
	}

	public void setHoverEffects(int x, int y) {
	}

	public void drawBackground(Graphics g) {
		for (Element element : backgroundElements) {
			element.draw(g, null);
		}
	}
	
	public void drawForeground(Graphics g) {}
	public void setCursor(int x, int y) {}
	public void drawMainLayer(Graphics g) {}

	private void cancelDragging() {
		sourceElement = null;
		backgroundElements.remove(connectingArc);
		started = false;
		PNEditor.getRoot().repaintCanvas();
	}
}
