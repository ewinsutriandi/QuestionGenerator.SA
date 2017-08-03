package com.alza.quiz.qfactory.geom;

public class Point3D {
	public int x,y,z;

	public Point3D(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * 
	 * @param ratio
	 * @param angle in radian
	 * @return point projected in 2d space using cabinet projection (60 deg)
	 */
	public Point2D project2D() {
		double angle = Math.PI/3;
		double newX = this.x + (0.5 * this.z * Math.cos(angle));
		double newY = this.y + (0.5 * this.z * Math.sin(angle));
		return new Point2D((int)newX, (int)newY);
	}

}
