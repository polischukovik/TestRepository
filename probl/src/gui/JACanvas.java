package gui;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import geometry.Point;
import graphics.JGDisplay;
import graphics.JGPoint;
import graphics.Map;

@SuppressWarnings("serial")
public class JACanvas extends JPanel {
	final static BasicStroke stroke = new BasicStroke(2.0f);
	private List<JGDisplay> objects = new ArrayList<>();
	private Map map;
	
	private Dimension canvasSize;
	
	public JACanvas(Map m) {
		super();
		this.map = m;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		canvasSize = getSize();
		
		for(JGDisplay o : objects){			
			o.show(g);
		}
	}
	
	public void addObject(JGDisplay obj){
		objects.add(obj);
		this.repaint();
	}
	
	public void addObject(List<JGDisplay> obj){
		objects.addAll(obj);
		this.repaint();
	}
	
	public void clear(){
		objects.clear();
		this.repaint();
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
}
