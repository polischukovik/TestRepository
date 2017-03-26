package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import datasource.DataSource;
import datasource.SemiFileDS;
import geometry.Displayable;
import geometry.Path;
import geometry.GeoPoint;
import geometry.Polygon;
import geometry.Segment;
import logginig.Logger;
import logic.WaypointFinder;

@SuppressWarnings("serial")
public class MainWindow  extends JFrame {
	protected static Logger logger = Logger.getLogger(MainWindow.class);
	
	final static int windowWidth = 1280;
	final static int windowhHeight = 924;
	
	private JAPointsList pointList;
	private JASegment segmentPanel;
	private JAInteger sections;
	private JADisplay display;	

	private WaypointFinder wpf;
	
	public static final String GROUP_FIELD = "field";
	public static final String GROUP_WP = "waypoints";
	public static final String GROUP_SEGMENT = "segment";
	
	public MainWindow() throws HeadlessException {
		super();	
		initUI();	
	}

	public void initUI() {    
        setTitle("Waypoint Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel generalPanel = new JPanel(new BorderLayout());	    
        JPanel rightPanel = new JPanel();
        
        JPanel plcHldrDisp = new JPanel(new BorderLayout());    
        JPanel plcHldrConsole = new JPanel(new BorderLayout());    
        
        pointList = new JAPointsList();  
        segmentPanel = new JASegment("Base segment", pointList);
        sections = new JAInteger("Sections");
        display = new JADisplay();    
        
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(300, 300));
        rightPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        generalPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    
        plcHldrDisp.add(display);        
        plcHldrConsole.add(new JAConsole());

        
        rightPanel.add(pointList);
        rightPanel.add(segmentPanel);
        rightPanel.add(sections);
        rightPanel.add(plcHldrConsole);        
        generalPanel.add(plcHldrDisp);
        
        JButton flip = new JButton("Switch veiw");  
        rightPanel.add(flip);
        
        JButton calculate = new JButton("Go");
        rightPanel.add(calculate);

        this.add(generalPanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        
        calculate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DataSource ds = new DataSource(pointList.getFormPointList(), segmentPanel.getSegment(), sections.getSections());
				logger.info("Invoking building waypoints"); 
				logger.info(ds.toString());
				logger.info(ds.toString());
			
				logger.info("Datasource ready"); 
				wpf = new WaypointFinder(ds);
				
				display.clearDisplayObject(GROUP_WP);

				display.addDisplayObject(GROUP_WP, wpf.ovf, Color.RED);
				display.addDisplayObject(GROUP_WP, wpf.getDevisionPoints(), Color.PINK);
				display.addDisplayObject(GROUP_WP, wpf.getDevisionLines(), Color.MAGENTA);
				
				display.addDisplayObject(GROUP_WP, new Path(wpf.getWaypoints()), Color.YELLOW);
				display.addDisplayObject(GROUP_WP, new Path(wpf.getWaypoints()), Color.ORANGE);
				
				display.render();
			}
		});
        
        pointList.getLoadPointsButton().addActionListener(new ActionListener(){  
        	@Override
	    	public void actionPerformed(ActionEvent e){
	    		JFileChooser fc = new JFileChooser(new File("."));
    	        int returnVal = fc.showOpenDialog(getParent());

    	        if (returnVal == JFileChooser.APPROVE_OPTION) {
    	        	
    	        	logger.info("Opening: " + fc.getSelectedFile().getName() + ".");
    	        	
    	        	Polygon polygon;
    	        	try {
    	        		polygon = new Polygon(SemiFileDS.readFile(fc.getSelectedFile()));
					} catch (IOException e1) {
						logger.info(e1.getMessage());
						return;
					}       	        	
					pointList.setListData(polygon);
					//create map area relative to polygon
					display.setMapForArea(polygon.getDimention());
					
					segmentPanel.setSegment(null);
					sections.setSections(0);
					
					display.getCanvas().clear();
					
					display.addDisplayObject(GROUP_FIELD, (ArrayList<GeoPoint>) polygon, new Color(0, 255, 0, 127));
					display.addDisplayObject(GROUP_FIELD, (Displayable) polygon, new Color(50, 30, 210, 32));
					
					display.getCanvas().render();
    	        } else {
    	        	logger.info("Open command cancelled by user." + "\n");
    	        }
		    }
		 });	
        
        segmentPanel.getButtonAdd().addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pointList != null && pointList.getSelectedPoints().size() == 2){
					Segment segment = new Segment(pointList.getSelectedPoints().get(0), pointList.getSelectedPoints().get(1));					
					segmentPanel.setSegment(segment);
					
					display.clearDisplayObject(GROUP_SEGMENT);
					display.addDisplayObject(GROUP_SEGMENT, segment, Color.GREEN);
					display.getCanvas().render();
				}							
			}
		});
        
		flip.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				swipeWindows(plcHldrDisp, plcHldrConsole);
				((JButton) e.getSource()).getParent().getParent().revalidate();
				((JButton) e.getSource()).getParent().getParent().repaint();
				display.getCanvas().render();
			}
		});
	}

	private void swipeWindows(JPanel plcHldrDisp, JPanel plcHldrConsole) {
		if(plcHldrDisp.getComponentCount() == 1 && plcHldrConsole.getComponentCount() == 1){
			JPanel component = (JPanel) plcHldrConsole.getComponents()[0];
			JPanel componentOther = (JPanel) plcHldrDisp.getComponents()[0];
			plcHldrConsole.removeAll();
			plcHldrConsole.add(componentOther);
			componentOther.setPreferredSize(new Dimension(componentOther.getParent().getPreferredSize().width - 10, 200));
			
			plcHldrDisp.removeAll();
			plcHldrDisp.add(component);
			component.setPreferredSize(new Dimension(component.getParent().getPreferredSize().width, component.getParent().getPreferredSize().height));
		}
	}
}

