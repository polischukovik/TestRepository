package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import calculator.App;
import datasource.DataSource;
import datasource.SemiFileDS;
import geometry.Point;
import geometry.Segment;
import graphics.JGDisplay;
import logginig.Logging;
import logic.WaypointFinder;

@SuppressWarnings("serial")
public class MainWindow  extends JFrame {
	final static int windowWidth = 1280;
	final static int windowhHeight = 924;
	
	private JAPointsList pointList;
	private JASegment segmentPanel;
	private JAInteger sections;

	private DataSource ds;
	private JACanvas canvas;
	private Logging log;
	
	private List<JGDisplay> ovfLine;
	private List<JGDisplay> divisionLines;
	private List<JGDisplay> waypoints;
	protected List<JGDisplay> waypointPath;

	public MainWindow(DataSource ds, Logging log, JACanvas canvas) throws HeadlessException {
		super();	
		this.ds = ds;
		this.log = log;
		this.canvas = canvas;
		initUI();	
	}

	public void initUI() {    
		
		this.setLayout(new FlowLayout());
		//this.setPreferredSize(new Dimension(this.getParent().getSize().width*4/5 - 5, this.getParent().getHeight()));
		
        setTitle("Waypoint Calculator");
        setMinimumSize(new Dimension(windowWidth, windowhHeight));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setExtendedState(Frame.MAXIMIZED_BOTH);
        
        JPanel left = new JPanel();	    
        left.setPreferredSize(new Dimension(this.getSize().width*4/5 - 5, this.getHeight()));
        left.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        JPanel placeholderPanel = new JPanel(new BorderLayout());    
        left.add(placeholderPanel);
        Dimension d =  placeholderPanel.getParent().getPreferredSize();
        placeholderPanel.setPreferredSize(new Dimension(d.getSize().width, d.getSize().height -10));
        
        JADisplay display = new JADisplay(canvas);        
        placeholderPanel.add(display);        
        
        this.add(left);
	        
        JPanel right = new JPanel(new FlowLayout());
        right.setPreferredSize(new Dimension(this.getWidth()*1/5 - 5, this.getHeight()));
        right.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                 
        pointList = new JAPointsList();        
        right.add(pointList);
        pointList.setPreferredSize(new Dimension(pointList.getParent().getPreferredSize().width - 10, 350));
        
        segmentPanel = new JASegment("Base segment", pointList);
        right.add(segmentPanel);
        segmentPanel.setPreferredSize(new Dimension(segmentPanel.getParent().getPreferredSize().width - 10, 75));
        
        sections = new JAInteger("Sections");
        right.add(sections);
        sections.setPreferredSize(new Dimension(sections.getParent().getPreferredSize().width - 10, 50));
        
        JPanel placeholderLog = new JPanel(new BorderLayout());    
        right.add(placeholderLog);        
        placeholderLog.setPreferredSize(new Dimension(placeholderLog.getParent().getPreferredSize().width - 10, 200));
        
        placeholderLog.add((JAConsole) log.getConsumer());
        
        JButton flip = new JButton("Switch veiw");  
        right.add(flip);
        
        JButton calculate = new JButton("Go");
        right.add(calculate);
        
        this.add(right);
        this.pack();
        
        calculate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ds.setDevidor(sections.getSections());
				log.info(this.getClass(), "Invoking building waypoints");
				log.info(this.getClass(), ds.toString());
				
				if(ds.isValid()){
					log.info(this.getClass(), "Datasource ready");
					WaypointFinder wpf = new WaypointFinder(ds, log);
					
					if(ovfLine != null){
						canvas.removeObjects(ovfLine);
					}
					ovfLine = Arrays.asList(canvas.createSegment(wpf.ovf, Color.RED));					
					canvas.addObject(ovfLine);
					
					//add division lines
//					if(divisionLines != null){
//						canvas.removeObjects(divisionLines);
//					}
//					divisionLines = canvas.createAllLines(wpf.getDevisionLines(), Color.GREEN);					
//					canvas.addObject(divisionLines);
					
					if(waypointPath != null){
						canvas.removeObjects(waypointPath);
					}
					waypointPath = Arrays.asList(canvas.createWaypointPath(wpf.getWaypoints(), Color.YELLOW));					
					canvas.addObject(waypointPath);
					
					canvas.render();
					
					if(waypoints != null){
						canvas.removeObjects(waypoints);
					}
					waypoints = canvas.createAllPoints(wpf.getWaypoints(), Color.ORANGE);					
					canvas.addObject(waypoints);
					
					canvas.render();
				}else{
					log.info(this.getClass(), "Datasource is not ready");
				}
			}
		});
        
        pointList.getLoadPointsButton().addActionListener(new ActionListener(){  
        	@Override
	    	public void actionPerformed(ActionEvent e){
	    		JFileChooser fc = new JFileChooser(ds.getPath());
    	        int returnVal = fc.showOpenDialog(getParent());

    	        if (returnVal == JFileChooser.APPROVE_OPTION) {
    	        	App.log.info(this.getClass(), "Opening: " + fc.getSelectedFile().getName() + ".");
    	        	List<Point> formPointList;
    	        	try {
    	        		formPointList = SemiFileDS.readFile(fc.getSelectedFile());
					} catch (IOException e1) {
						App.log.info(this.getClass(), e1.getMessage());
						return;
					}    
					ds.setFormPoints(formPointList);					
					pointList.setListData(formPointList);
					
					List<JGDisplay> oldDisplayObject = pointList.getDisplayObjects();
					List<JGDisplay> displaylist = canvas.createAllPoints(ds.getFormPoints(), new Color(0, 255, 0, 127));
					displaylist.add(canvas.createPoligon(ds.getFormPoints(), new Color(50, 30, 210, 32)));
					
					pointList.setDisplayObjects(displaylist);
					
					if(oldDisplayObject != null){
						canvas.removeObjects(oldDisplayObject);	
					}					
					canvas.addObject(displaylist);
					canvas.render();
    	        } else {
    	        	App.log.info(this.getClass(), "Open command cancelled by user." + "\n");
    	        }
		    }
		 });	
        
        segmentPanel.getButtonAdd().addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pointList != null && pointList.getSelectedPoints().size() == 2){
					Segment segment = new Segment(pointList.getSelectedPoints().get(0), pointList.getSelectedPoints().get(1));
					ds.setBase(segment);
					segmentPanel.setSegment(segment);
					
					List<JGDisplay> oldDisplayObject = segmentPanel.getDisplayObjects();
					segmentPanel.setDisplayObjects(Arrays.asList(canvas.createSegment(segment, Color.GREEN)));
					
					if(oldDisplayObject != null){
						canvas.removeObjects(oldDisplayObject);	
					}
					canvas.addObject(segmentPanel.getDisplayObjects());
					canvas.render();
				}							
			}
		});
        
		flip.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				if(placeholderPanel.getComponentCount() == 1 && placeholderLog.getComponentCount() == 1){
					JPanel component = (JPanel) placeholderLog.getComponents()[0];
					JPanel componentOther = (JPanel) placeholderPanel.getComponents()[0];
					placeholderLog.removeAll();
					placeholderLog.add(componentOther);
					componentOther.setPreferredSize(new Dimension(componentOther.getParent().getPreferredSize().width - 10, 200));
					
					placeholderPanel.removeAll();
					placeholderPanel.add(component);
					component.setPreferredSize(new Dimension(component.getParent().getPreferredSize().width, component.getParent().getPreferredSize().height));
				}
				((JButton) e.getSource()).getParent().getParent().revalidate();
				((JButton) e.getSource()).getParent().getParent().repaint();
				canvas.render();
			}
		});
	}

	public Consumer getConsole() {
		return (Consumer)log;
	}
	
}

