package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
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
import geometry.Polygon;
import geometry.Segment;
import graphics.CanvasObject;
import graphics.Map;
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

	public MainWindow(DataSource ds, Logging log, JACanvas canvas) throws HeadlessException {
		super();	
		this.ds = ds;
		this.log = log;
		this.canvas = canvas;
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
        JADisplay display = new JADisplay(canvas);    
        
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(300, 300));
        rightPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        generalPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    
        plcHldrDisp.add(display);        
        plcHldrConsole.add((JAConsole) log.getConsumer());
        
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
				log.info(this.getClass(), "Invoking building waypoints");
				log.info(this.getClass(), ds.toString());
				
				if(ds.isValid()){
					log.info(this.getClass(), "Datasource ready");
					WaypointFinder wpf = new WaypointFinder(ds, log);
					
					if(ovfLine != null){
						canvas.removeObjects(ovfLine);
					}
					ovfLine = Arrays.asList(canvas.createElement(wpf.ovf, Color.RED));					
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
					waypointPath = Arrays.asList(canvas.createElement(wpf.getWaypoints(), Color.YELLOW));					
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
    	        	
    	        	try {
    	        		ds.setFormPoints(SemiFileDS.readFile(fc.getSelectedFile()));
					} catch (IOException e1) {
						App.log.info(this.getClass(), e1.getMessage());
						return;
					}   
    	        	Polygon polygon = new Polygon(ds.getFormPoints());
					pointList.setListData(polygon);

					canvas.setMap(new Map(polygon, canvas.getSize()));
					
					List<CanvasObject> oldDisplayObject = pointList.getDisplayObjects();
					List<CanvasObject> displaylist = canvas.createAllPoints(ds.getFormPoints(), new Color(0, 255, 0, 127));
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
					
					List<CanvasObject> oldDisplayObject = segmentPanel.getDisplayObjects();
					segmentPanel.setDisplayObjects(Arrays.asList(canvas.createElement(segment, Color.GREEN)));
					
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
				swipeWindows(plcHldrDisp, plcHldrConsole);
				((JButton) e.getSource()).getParent().getParent().revalidate();
				((JButton) e.getSource()).getParent().getParent().repaint();
				canvas.render();
			}
		});
	}

	public Consumer getConsole() {
		return (Consumer)log;
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

