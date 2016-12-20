package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import datasource.SemiFileDS;
import geometry.Point;
import probl.App;

@SuppressWarnings("serial")
public class JAPointsList extends JPanel{

	private List<Point> formPointList = new ArrayList<>();
	private JButton showDialogButton;
	private JList<Point> defaultList;
	
	public JAPointsList(File defaultFile) {
	    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createTitledBorder("Points"));
   
        showDialogButton = new JButton("Open");
	    
	    defaultList = new JList<>();
	    defaultList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    defaultList.setLayoutOrientation(JList.HORIZONTAL_WRAP);  
	    
	    this.add(new JScrollPane(defaultList));
	    this.add(showDialogButton);
	    
	    showDialogButton.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		if (e.getSource() == showDialogButton) {
	    	    	JFileChooser fc = new JFileChooser(defaultFile);
	    	        int returnVal = fc.showOpenDialog(getParent());
	
	    	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	        	App.log.info(this.getClass(), "Opening: " + fc.getSelectedFile().getName() + ".");
	    	        	try {
							formPointList = SemiFileDS.readFile(fc.getSelectedFile());
							Point[] pointArray = new Point[formPointList.size()];
							defaultList.setListData(formPointList.toArray(pointArray));
						} catch (IOException e1) {
							App.log.info(this.getClass(), e1.getMessage());							
						}     	            
	    	        } else {
	    	        	App.log.info(this.getClass(), "Open command cancelled by user." + "\n");
	    	        }
	    	  }
	    }
	 });	    
	}

	public List<Point> getSelectedPoints() {
		return defaultList.getSelectedValuesList();
	}

	public List<Point> getFormPointList() {
		return formPointList;
	}	
	
	
}
