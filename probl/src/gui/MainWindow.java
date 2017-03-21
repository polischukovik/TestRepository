package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import calculator.App;
import datasource.DataSource;
import datasource.SemiFileDS;
import geometry.Point;
import geometry.Polygon;
import geometry.Segment;
import graphics.Map;
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

	private DataSource ds;
	private JACanvas canvas;
	
	private WaypointFinder wpf;	
	
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
        JADisplay display = new JADisplay();    
        
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

				ds.setDevidor(sections.getSections());
				logger.info("Invoking building waypoints");
				logger.info(ds.toString());
				
				if(ds.isValid()){
					logger.info("Datasource ready");
					WaypointFinder oldWpf = wpf; 
					wpf = new WaypointFinder(ds);
					
					if(oldWpf != null){
						canvas.removeElement(oldWpf.ovf, Color.RED);
						canvas.removeElement(oldWpf.getWaypoints(), Color.YELLOW);
						canvas.removeElement(oldWpf.getWaypoints(), Color.ORANGE);
					}
					canvas.createElement(wpf.ovf, Color.RED);
					canvas.createElement(wpf.getWaypoints(), Color.YELLOW);
					canvas.createElement(wpf.getWaypoints(), Color.ORANGE);
					
					canvas.render();
				}else{
					logger .info("Datasource is not ready");
				}
			}
		});
        
        pointList.getLoadPointsButton().addActionListener(new ActionListener(){  
        	@Override
	    	public void actionPerformed(ActionEvent e){
	    		JFileChooser fc = new JFileChooser(new File("."));
    	        int returnVal = fc.showOpenDialog(getParent());

    	        if (returnVal == JFileChooser.APPROVE_OPTION) {
    	        	
    	        	logger.info("Opening: " + fc.getSelectedFile().getName() + ".");
    	        	
    	        	try {
    	        		ds = new SemiFileDS(fc.getSelectedFile());
					} catch (IOException e1) {
						logger.info(e1.getMessage());
						return;
					}   
    	        	Polygon polygon = new Polygon(ds.getFormPoints());
					pointList.setListData(polygon);

					//canvas.setMap(new Map(polygon, canvas.getSize()));
					
//					canvas.clear();
//					List<Point> fieldPoints = ds.getFormPoints();
//					canvas.createAllElements(fieldPoints, new Color(0, 255, 0, 127));
//					canvas.createElement(polygon, new Color(50, 30, 210, 32));
//					canvas.render();
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
					Segment oldBase = ds.getBase();
					
					if(oldBase != null){
						canvas.removeElement(oldBase, Color.GREEN);
					}					
					ds.setBase(segment);
					segmentPanel.setSegment(segment);
					
					canvas.createElement(segment, Color.GREEN);
					canvas.render();
				}							
			}
		});
        
		flip.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				swipeWindows(plcHldrDisp, plcHldrConsole);
				((JButton) e.getSource()).getParent().getParent().revalidate();
				((JButton) e.getSource()).getParent().getParent().repaint();
				canvas.render();
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

