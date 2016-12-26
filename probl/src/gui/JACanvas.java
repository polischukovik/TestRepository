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
import geometry.Polygon;
import geometry.Segment;
import graphics.Dimention;
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
	
    public void positionMap(Polygon polygon){
 
		Dimention mapOvf = polygon.getOvf();		
    	App.log.info(this.getClass(), String.format("Map OVF is %s", mapOvf));
				
		Point start = null, end = null;
		if(mapOvf.getvOvf().getLength() > mapOvf.gethOvf().getLength()){
			/*
			 * when vertical component is greater
			 */
			double halfVertical = mapOvf.getA().getLatitude() + (mapOvf.getB().getLongitude() - mapOvf.getA().getLongitude())/2;
			double horizontal = mapOvf.gethOvf().getB().getLongitude() - mapOvf.gethOvf().getA().getLongitude();
			
			start = new Point(
					mapOvf.getA().getLatitude(),
					halfVerticalY - mapOvf.gethOvf().getLength()/2);
			end = new Point(
					mapOvf.gethOvf().getB().getLatitude(),
					halfVerticalY + mapOvf.gethOvf().getLength()/2);
		}else if(mapOvf.getvOvf().getLength() < mapOvf.gethOvf().getLength()){
			/*
			 * when horizontal component is greater
			 */
			double halfHorizontalX = mapOvf.gethOvf().getA().getLatitude() + (mapOvf.gethOvf().getB().getLatitude() - mapOvf.gethOvf().getA().getLatitude())/2;
			
			start = new Point(
					halfHorizontalX - mapOvf.getvOvf().getLength()/2,
					mapOvf.getvOvf().getA().getLongitude());
			end = new Point(
					halfHorizontalX + mapOvf.getvOvf().getLength()/2,
					mapOvf.getvOvf().getB().getLongitude());
		}else{
			/*
			 * when ovfH x ovfV  is square
			 */
			start = new Point(
					mapOvf.gethOvf().getA().getLatitude(),
					mapOvf.getvOvf().getA().getLongitude());
			end = new Point(
					mapOvf.gethOvf().getB().getLatitude(),
					mapOvf.getvOvf().getB().getLongitude());
		}
		
		this.setMap(new Map(start, end));;
    }
	
	public int getDisplayX(double x){
		return new Double((x - map.getStart().getLatitude()) * canvasSize.getWidth() / (map.getEnd().getLatitude() - (map.getStart().getLatitude()))).intValue();
	}
	
	public int getDisplayY(double y){
		return new Double(canvasSize.getHeight() - (y - map.getStart().getLongitude()) * canvasSize.getHeight() / (map.getEnd().getLongitude() - (map.getStart().getLongitude()))).intValue();
	}
	
	public double getMapX(int x){
		return ((x - 0)  * (map.getEnd().getLatitude()) - (map.getStart().getLatitude()) ) / (canvasSize.getWidth() - 0);
	}
	
	public double getMapY(int y){
		return ((canvasSize.getHeight() - y - 0)  * (map.getEnd().getLongitude()) - (map.getStart().getLongitude()) ) / (canvasSize.getHeight() - 0);
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	
}
