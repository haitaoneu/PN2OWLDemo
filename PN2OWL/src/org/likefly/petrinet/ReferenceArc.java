package org.likefly.petrinet;

import java.awt.Graphics;
import java.awt.Point;

import org.likefly.util.GraphicsTools;


public class ReferenceArc extends ArcEdge {
	

	private static final long serialVersionUID = -3910305735629949051L;

	public ReferenceArc(PlaceNode placeNode, Net subnet) {
		super(placeNode, subnet, true); 
	}
	
	public Net getSubnet() {
		return (Net)getTransitionNode();
	}
	
	public ReferencePlace getReferencePlace() {
		for (Element element : getSubnet().getElements()) {
			if (element instanceof ReferencePlace) {
				ReferencePlace referencePlace = (ReferencePlace)element;
				if (referencePlace.getConnectedPlaceNode() == getPlaceNode()) {
					return referencePlace;
				}
			}
		}
		throw new RuntimeException("ReferenceArc: missing ReferencePlace");
	}
	
	@Override
	public void draw(Graphics g, DrawingOptions drawingOptions) {

		ReferencePlace referencePlace = getReferencePlace();
		if (referencePlace.getConnectedTransitionNodes().size() == 1) {
			g.setColor(color);
			GraphicsTools.setDashedStroke(g);
			drawSegmentedLine(g);
			
			for (Arc arc : referencePlace.getConnectedArcs()) { //TODO: also referenceArcs
				setPlaceToTransition(arc.isPlaceToTransition());
				Point arrowTip = computeArrowTipPoint();
				drawArrow(g, arrowTip);
			}
			GraphicsTools.setDefaultStroke(g);
		}
		else if (referencePlace.getConnectedTransitionNodes().isEmpty()) {
			GraphicsTools.setDottedStroke(g);
			drawSegmentedLine(g);
			GraphicsTools.setDefaultStroke(g);
		}
		else {
			GraphicsTools.setDashedStroke(g);
			drawSegmentedLine(g);
			GraphicsTools.setDefaultStroke(g);
		}
	}
	
}
