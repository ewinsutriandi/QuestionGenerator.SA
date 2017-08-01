package com.alza.quiz.qfactory.geom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Cube implements Shapes3D{
	private double edgeLength;
	public Cube() {
		
	}
	
	public Cube(double edgeLength) {
		super();
		this.edgeLength = edgeLength;
	}
	@Override
	public int getVerticesCount() {
		return 12;

	}
	@Override
	public double getSurfaceArea() {
		List<Shapes2D> fcs = getFaces();
		double surfaceArea = 0.0;
		for (Shapes2D shapes2d : fcs) {
			surfaceArea = surfaceArea + shapes2d.getArea(); 
		}
		return surfaceArea;
	}

	@Override
	public double getVolume() {
		return edgeLength * edgeLength * edgeLength;
	}

	@Override
	public List<Shapes2D> getFaces() {
		List<Shapes2D> fcs = new ArrayList<Shapes2D>();
		for (int i = 0; i < 6; i++) {
			Square shp = new Square(edgeLength);
			fcs.add(shp);
		}
		return fcs;
	}

	@Override
	public double get2DOccupiedLength(double projRatio, double projAngle) {
		double addLength = projRatio * edgeLength * Math.cos(projAngle);
		return addLength+edgeLength;
	}

	@Override
	public double get2DOccupiedHeight(double projRatio, double projAngle) {
		double addHeight = projRatio * edgeLength * Math.sin(projAngle);
		return addHeight+edgeLength;
	}

	@Override
	public String getName() {
		return "cube";
	}

	@Override
	public Shapes3D createExample() {
		double el = ThreadLocalRandom.current().nextInt(5, 26);
		return new Cube(el);
	}

	@Override
	public List<Path> getPaths(int canvasWidth, int canvasHeight, int margin, double projRatio, double projAngle) {
		if (margin >= canvasWidth/2 || margin >= canvasHeight/2){
			margin = 0;
		}
		List<Path> l = new ArrayList<Path>();
		int scale=0;
		double maxHeightRatio = ((double) (canvasHeight-margin*2)) / ((double) get2DOccupiedHeight(projRatio, projAngle));
		double maxLengthRatio = ((double) (canvasWidth-margin*2)) / ((double) get2DOccupiedLength(projRatio, projAngle));
		if (maxHeightRatio>maxLengthRatio){
			scale = (int) maxLengthRatio;
		} else {
			scale = (int) maxHeightRatio;
		}
		int canvasEdgeLength = (int) (scale * edgeLength);
		int pxOl = (int) (scale * get2DOccupiedLength(projRatio, projAngle));
		int pxOh = (int) (scale * get2DOccupiedHeight(projRatio, projAngle));
		int x1,x2,x3,x4,x5,x6,x7,x8;
		int y1,y2,y3,y4,y5,y6,y7,y8;
		System.out.println("scale "+scale+" pxol "+pxOl+" pxoh "+pxOh+" pxCvOL "+canvasEdgeLength);
		x1 = (canvasWidth-pxOl)/2;
		x2 = x1 + canvasEdgeLength;
		x3 = x2;
		x4 = x1;
		x5 = x1 + (int)(projRatio * canvasEdgeLength * Math.cos(projAngle));
		x6 = x5 + canvasEdgeLength;
		x7 = x6;
		x8 = x5;
		y5 = (canvasHeight-pxOh)/2;
		y6 = y5;
		y7 = y6 + canvasEdgeLength;
		y8 = y7;
		y1 = y5 + (int) (projRatio * canvasEdgeLength * Math.sin(projAngle));
		y2 = y1;
		y3 = y2 + canvasEdgeLength;
		y4 = y3;
		l.add(Path.createLinePath(new Point(x1, y1), new Point(x2, y2)));
		l.add(Path.createLinePath(new Point(x2, y2), new Point(x3, y3)));
		l.add(Path.createLinePath(new Point(x3, y3), new Point(x4, y4)));
		l.add(Path.createLinePath(new Point(x4, y4), new Point(x1, y1)));
		
		l.add(Path.createLinePath(new Point(x1, y1), new Point(x5, y5)));
		l.add(Path.createLinePath(new Point(x2, y2), new Point(x6, y6)));
		l.add(Path.createLinePath(new Point(x3, y3), new Point(x7, y7)));
		l.add(Path.createLinePathDotted(new Point(x4, y4), new Point(x8, y8)));
		
		l.add(Path.createLinePath(new Point(x5, y5), new Point(x6, y6)));
		l.add(Path.createLinePath(new Point(x6, y6), new Point(x7, y7)));
		l.add(Path.createLinePathDotted(new Point(x7, y7), new Point(x8, y8)));
		l.add(Path.createLinePathDotted(new Point(x8, y8), new Point(x5, y5)));
		System.out.println(x1+" "+x2+" "+x3+" "+x4);
		return l;
	}

	@Override
	public List<Path> getPaths(int width, int height) {
		double defRatio = 0.3;
		double defAngle = Math.PI/6;
		int margin;
		if (width >= height) {
			margin = height / 5;
		} else margin = width / 5;
		return getPaths(width, height, margin, defRatio, defAngle);
	}
}
