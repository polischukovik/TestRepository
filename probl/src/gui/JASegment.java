package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import geometry.Segment;
import graphics.CanvasObject;

@SuppressWarnings("serial")
public class JASegment extends JPanel{
	private Segment segment;
	private JButton buttonAdd;
	private JTextField segmentField;

	public JASegment(String name, JAPointsList pointList){
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));    
        setBorder(BorderFactory.createTitledBorder(name));
        setPreferredSize(new Dimension (300, 75));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));        
        panel.setMaximumSize(new Dimension(300, 25));       
        panel.setMinimumSize(new Dimension(300, 25));
        JLabel label = new JLabel("Segment:");
        segmentField = new JTextField();
        segmentField.setEditable(false);
        
        panel.add(label);
        panel.add(segmentField);
        buttonAdd = new JButton("Add Segment");
        
        this.add(buttonAdd);
        this.add(panel);
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
		segmentField.setText(segment.toString());	
	}

	public JButton getButtonAdd() {
		return buttonAdd;
	}
}
