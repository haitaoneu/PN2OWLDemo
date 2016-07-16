package org.likefly.util;


public class Point {

	private java.awt.Point point;

	public Point(int x, int y) {
		this.point = new java.awt.Point(x, y);
	}

	public Point(java.awt.Point point) {
		this.point = point;
	}

	public Point() {
		this(0, 0);
	}
	
	public int getX() {
		return point.x;
	}

	public int getY() {
		return point.y;
	}

	public double distance(Point point) {
		return this.point.distance(point.point);
	}

	public Point getTranslated(int dx, int dy) {
		return new Point(getX() + dx, getY() + dy);
	}

	public Point getTranslated(Point point) {
		return new Point(getX() + point.getX(), getY() + point.getY());
	}

	public Point getNegative() {
		return new Point(- getX(), - getY());
	}

	public java.awt.Point getPoint() {
		return new java.awt.Point(point);
	}

	@Override
	public String toString() {
		return "[" + point.x + ", " + point.y + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Point other = (Point) obj;
		if (this.point != other.point && (this.point == null || !this.point.equals(other.point))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 11 * hash + (this.point != null ? this.point.hashCode() : 0);
		return hash;
	}

}
