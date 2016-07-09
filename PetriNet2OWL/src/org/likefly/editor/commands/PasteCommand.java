package org.likefly.editor.commands;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

import org.likefly.petrinet.Element;
import org.likefly.petrinet.Net;
import org.likefly.petrinet.PetriNet;


public class PasteCommand implements Command {
	
	private Net subnet;
	private Set<Element> elements;

	public PasteCommand(Set<Element> elements, Net currentSubnet, PetriNet petriNet) {
		this.subnet = currentSubnet;
		this.elements = elements;
		petriNet.getNodeLabelGenerator().setLabelsToPastedContent(elements);

		Point translation = calculateTranslatioToCenter(elements, currentSubnet);
		for (Element element : elements) {
			element.moveBy(translation.x, translation.y);
		}
	}

	public void execute() {
		subnet.addAll(elements);
	}


	
	private Point calculateTranslatioToCenter(Set<Element> elements, Net currentSubnet) {
		Point viewTranslation = currentSubnet.getViewTranslation();
		Net tempSubnet = new Net();
		tempSubnet.addAll(elements);
		Rectangle bounds = tempSubnet.getBounds();

		Point result = new Point();
		result.translate(Math.round(-(float)bounds.getCenterX()), Math.round(-(float)bounds.getCenterY()));
		result.translate(-viewTranslation.x, -viewTranslation.y);
		return result;
	}

}
