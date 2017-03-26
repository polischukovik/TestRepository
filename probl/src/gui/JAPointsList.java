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

import geometry.GeoPoint;

@SuppressWarnings("serial")
public class JAPointsList extends JPanel{

	private List<GeoPoint> formPointList;
	private JButton showDialogButton;
	private JList<GeoPoint> defaultList;
	
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

	public List<GeoPoint> getSelectedPoints() {
		return defaultList.getSelectedValuesList();
	}

	public List<GeoPoint> getFormPointList() {
		return formPointList;
	}

	public void setListData(List<GeoPoint> list) {
		GeoPoint[] pointArray = new GeoPoint[list.size()];
		formPointList = list;
		defaultList.setListData(list.toArray(pointArray));		
	}
}
