package gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import graphics.JGDisplay;

@SuppressWarnings("serial")
public class JACanvas extends JPanel {
	final static BasicStroke stroke = new BasicStroke(2.0f);
	private List<JGDisplay> objects = new ArrayList<>();
	
	public JACanvas() {
		super();
	}
	@Override
	public void paint(Graphics g) {
		for(JGDisplay o : objects){
			o.show(g);
		}
	}
	
	public void addObject(JGDisplay obj){
		objects.add(obj);
	}
}
