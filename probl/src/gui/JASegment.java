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
import graphics.JGDisplay;

@SuppressWarnings("serial")
public class JASegment extends JPanel{
	private Segment segment;
	private JButton buttonAdd;
	private JTextField segmentField;
	private List<JGDisplay> displayObjects;

	public JASegment(String name, JAPointsList pointList){
	    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));	    
        setBorder(BorderFactory.createTitledBorder(name));
        
        JLabel label = new JLabel("Segment:");
        segmentField = new JTextField();
        segmentField.setEditable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));        
        panel.add(label);
        panel.add(segmentField);
        panel.setPreferredSize(new Dimension(getPreferredSize().getSize().width - 10, 10));
        
        buttonAdd = new JButton("Add Segment");
        
        this.add(buttonAdd);
        
        buttonAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					
			}
		});

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

	public List<JGDisplay> getDisplayObjects() {		
		return displayObjects;
	}

	public void setDisplayObjects(List<JGDisplay> displayObjects) {		
		this.displayObjects = displayObjects;
	}
	

}
