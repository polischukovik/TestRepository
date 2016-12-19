package gui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import geometry.Point;
import geometry.Segment;

public class JASegment extends JPanel{
	private Segment segment;

	public JASegment(String name, List<Point> selectedPoints){
	    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));	    
        setBorder(BorderFactory.createTitledBorder(name));
        
        JButton buttonAdd = new JButton("Add Segment"); 
                
        JLabel label = new JLabel("Segment:");
        JTextField segmentField = new JTextField();
        segmentField.setEditable(true);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));        
        panel.add(label);
        panel.add(segmentField);
        panel.setPreferredSize(new Dimension(getPreferredSize().getSize().width - 10, 10));
        
        this.add(buttonAdd);
        this.add(panel);
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

}
