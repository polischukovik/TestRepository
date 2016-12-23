package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import geometry.Point;
import graphics.JGDisplay;

@SuppressWarnings("serial")
public class JAPointsList extends JPanel{

	private List<Point> formPointList = new ArrayList<>();
	private JButton showDialogButton;
	private JList<Point> defaultList;
	private List<JGDisplay> displayObjects;
	
	public JAPointsList() {
	    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createTitledBorder("Points"));
   
        showDialogButton = new JButton("Open");
	    
	    defaultList = new JList<>();
	    defaultList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    defaultList.setLayoutOrientation(JList.HORIZONTAL_WRAP);  
	    
	    this.add(new JScrollPane(defaultList));
	    this.add(showDialogButton);	   
	}
	
	public JButton getLoadPointsButton() {
		return showDialogButton;
	}

	public List<Point> getSelectedPoints() {
		return defaultList.getSelectedValuesList();
	}

	public List<Point> getFormPointList() {
		return formPointList;
	}

	public void setListData(List<Point> list) {
		Point[] pointArray = new Point[list.size()];
		defaultList.setListData(list.toArray(pointArray));		
	}

	public List<JGDisplay> getDisplayObjects() {
		return displayObjects;
	}

	public void setDisplayObjects(List<JGDisplay> displayObjects) {
		this.displayObjects = displayObjects;
	}	
}
