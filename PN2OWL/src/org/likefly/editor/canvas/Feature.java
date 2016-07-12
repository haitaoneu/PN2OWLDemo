package org.likefly.editor.canvas;

import java.awt.Graphics;

import java.awt.event.MouseEvent;


public interface Feature {
	public void drawForeground(Graphics g);
	public void drawMainLayer(Graphics g);
	public void drawBackground(Graphics g);
	public void mousePressed(MouseEvent event);
	public void mouseDragged(int x, int y);
	public void mouseReleased(int x, int y);
	public void mouseMoved(int x, int y);
	public void setHoverEffects(int x, int y);
	public void setCursor(int x, int y);
}
