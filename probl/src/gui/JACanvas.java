package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import calculator.App;
import geometry.Line;
import geometry.Point;
import geometry.Segment;
import graphics.JGDisplay;
import graphics.JGLine;
import graphics.JGPoint;
import graphics.JGPoligon;
import graphics.JGSegment;
import graphics.Map;

@SuppressWarnings("serial")
public class JACanvas extends JPanel {
	final static BasicStroke stroke = new BasicStroke(2.0f);
	private List<JGDisplay> objects = new ArrayList<>();
	private Map map;
	
	private Dimension canvasSize;
	
	public JACanvas() {
		super();

		this.setBackground(Color.WHITE);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		canvasSize = getSize();
		
		for(JGDisplay o : objects){			
			o.show(g);
		}
	}
	
	public JGPoint createPoint(Point p, Color color){
		return new JGPoint(p, this, color);
	}
	
	public List<JGDisplay> createAllPoints(Collection<? extends Point> points, Color color){
		List<JGDisplay> result = new ArrayList<>();
		for(Point p : points){
			result.add(createPoint(p, color));
		}
		return result;
	}
	
	public JGDisplay createPoligon(List<Point> p, Color color){
		return new JGPoligon(p, this, color);
	}
	
	public JGDisplay createLine(Line l, Color color){
		return new JGLine(l, this, color);
	}
	
	public List<JGDisplay> createAllLines(Collection<? extends Line> lines, Color color){
		List<JGDisplay> result = new ArrayList<>();
		for(Line l : lines){
			result.add(createLine(l, color));
		}
		return result;
	}
	
	public JGDisplay createSegment(Segment s, Color color){
		return new JGSegment(s, this, color);
	}
	
	public List<JGDisplay> createAllSegments(Collection<? extends Segment> segments, Color color){
		List<JGDisplay> result = new ArrayList<>();
		for(Segment s : segments){
			result.add(createSegment(s, color));
		}
		return result;
	}	

	public JGDisplay createWaypointPath(List<Point> waypoints, Color color) {
		return new JGWaypointPath(waypoints, this, color);
	}
	
	public void addObject(JGDisplay obj){
		objects.add(obj);
	}
	
	public void addObject(List<JGDisplay> obj){
		objects.addAll(obj);
	}

	public void removeObjects(List<JGDisplay> o) {
		objects.removeAll(o);		
	}
	
	public void clear(){
		objects.clear();
	}
	
	public void render(){
		if(this.map == null){
			App.log.info(this.getClass(), "Map is not initialized");
			return;
		}
		this.repaint(); 
	}
	
    public void positionMap(List<Point> formPointList){
		
		Segment hOvf = Line.getHorizontal().getProjection((formPointList));
		Segment vOvf = Line.getVertical().getProjection((formPointList));
		
		Point start = null, end = null;
		if(hOvf.getLength() > vOvf.getLength()){
			/*
			 * when horizontal component is greater
			 */
			double halfVerticalY = vOvf.getA().getY() + (vOvf.getB().getY() - vOvf.getA().getY())/2;
			
			start = new Point(
					hOvf.getA().getX(),
					halfVerticalY - hOvf.getLength()/2);
			end = new Point(
					hOvf.getB().getX(),
					halfVerticalY + vOvf.getLength()/2);
		}else if(hOvf.getLength() < vOvf.getLength()){
			/*
			 * when vertical component is greater
			 */
			double halfHorizontalX = hOvf.getA().getX() + (hOvf.getB().getX() - hOvf.getA().getX())/2;
			
			start = new Point(
					halfHorizontalX - vOvf.getLength()/2,
					vOvf.getA().getY());
			end = new Point(
					halfHorizontalX + vOvf.getLength()/2,
					vOvf.getB().getY());
		}else{
			/*
			 * when ovfH x ovfV  is square
			 */
			start = new Point(
					hOvf.getA().getX(),
					vOvf.getA().getY());
			end = new Point(
					hOvf.getB().getX(),
					vOvf.getB().getY());
		}
		
		this.setMap(new Map(start, end));;
    }
	
	public int getDisplayX(double x){
		return new Double((x - map.getStart().getX()) * canvasSize.getWidth() / (map.getEnd().getX() - (map.getStart().getX()))).intValue();
	}
	
	public int getDisplayY(double y){
		return new Double(canvasSize.getHeight() - (y - map.getStart().getY()) * canvasSize.getHeight() / (map.getEnd().getY() - (map.getStart().getY()))).intValue();
	}
	
	public double getMapX(int x){
		return ((x - 0)  * (map.getEnd().getX()) - (map.getStart().getX()) ) / (canvasSize.getWidth() - 0);
	}
	
	public double getMapY(int y){
		return ((canvasSize.getHeight() - y - 0)  * (map.getEnd().getY()) - (map.getStart().getY()) ) / (canvasSize.getHeight() - 0);
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	
}
