package gui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import geometry.Point;

@SuppressWarnings("serial")
public class JAPointsList extends JPanel{

	private List<Point> formPointList;
	private JButton showDialogButton;
	private JList<Point> defaultList;
	
	public JAPointsList() {
	    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createTitledBorder("Points"));
        
        showDialogButton = new JButton("Open");
	    
	    defaultList = new JList<>();
	    defaultList.setPreferredSize(new Dimension (300, 400));
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
		formPointList = list;
		defaultList.setListData(list.toArray(pointArray));		
	}
}
