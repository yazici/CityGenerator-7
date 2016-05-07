package materials;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import area_constructors.BasicShapeConstructor;
import test.TestGUIManager;

public class MaterialPoint extends Material {

	private double radius;

	public MaterialPoint(Color c, double rad) {
		super(c);
		radius = rad;
	}

	public void renderFill(TestGUIManager gui, Area area) {
		gui.addShape(area,color);
		renderPoints(gui,BasicShapeConstructor.getAreaEdgePoints(area,getSeperation(radius)));
	}
	
	private void renderPoints(TestGUIManager gui, List<Point2D> points){
		Area area = BasicShapeConstructor.combineAreasParallel(getAreas(points));
		gui.addShape(area,color);
	}
	
	private ArrayList<Area> getAreas(List<Point2D> points){
		ArrayList<Area> areas = new ArrayList<Area>();
		Iterator<Point2D> iterator = points.iterator();
		while(iterator.hasNext()){
			Point2D currPoint = iterator.next();
			areas.add(new Area(new Ellipse2D.Double(currPoint.getX()-radius,currPoint.getY()-radius,2*radius,2*radius)));
		}
		return areas;
	}
	
	static private double getSeperation(double rad){
		return Math.sqrt(Math.pow(rad, 2)/2);
	}
	
	
	static private List<Point2D> getFillPoints(Area area, double separation){
		List<Point2D> internalPoints = BasicShapeConstructor.getPointsInArea(area,separation);
		internalPoints.addAll(BasicShapeConstructor.getAreaEdgePoints(area,separation));
		
		return internalPoints;
	}

	public ArrayList<Point2D> getFillPoints(Area area){
		return new ArrayList(getFillPoints(area,radius));
	}
}