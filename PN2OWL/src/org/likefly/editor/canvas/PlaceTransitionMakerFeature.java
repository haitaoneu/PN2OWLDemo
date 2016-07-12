package org.likefly.editor.canvas;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import org.likefly.editor.PNEditor;
import org.likefly.editor.commands.AddPlaceCommand;
import org.likefly.editor.commands.AddTransitionCommand;
import org.likefly.util.CollectionTools;


public class PlaceTransitionMakerFeature implements Feature {

	public PlaceTransitionMakerFeature(Canvas canvas) {
	}
	
	public void mousePressed(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		int mouseButton = event.getButton();
		
		if (mouseButton == MouseEvent.BUTTON1) {
			if (PNEditor.getRoot().getClickedElement() == null) {
				if (PNEditor.getRoot().isSelectedTool_Place()) {
					PNEditor.getRoot().getSelection().clear();
					PNEditor.getRoot().excuteCommand(new AddPlaceCommand(PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet(), x, y, PNEditor.getRoot().getDocument().petriNet));
					PNEditor.getRoot().setClickedElement(CollectionTools.getLastElement(PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getElements()));
				}
				else if (PNEditor.getRoot().isSelectedTool_Transition()) {
					PNEditor.getRoot().getSelection().clear();
					PNEditor.getRoot().excuteCommand(new AddTransitionCommand(PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet(), x, y, PNEditor.getRoot().getDocument().petriNet));
					PNEditor.getRoot().setClickedElement(CollectionTools.getLastElement(PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getElements()));
				}
			}
		}
	
	}
	
	public void drawForeground(Graphics g) {}
	public void drawBackground(Graphics g) {}
	public void mouseDragged(int x, int y) {}
	public void mouseReleased(int x, int y) {}
	public void setHoverEffects(int x, int y) {}
	public void setCursor(int x, int y) {}
	public void drawMainLayer(Graphics g) {}
	public void mouseMoved(int x, int y) {}
}
