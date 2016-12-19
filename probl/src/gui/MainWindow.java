package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class MainWindow  extends JFrame {
	final static int windowWidth = 1024;
	final static int windowhHeight = 768;
	private JAConsole log;
	private JAPointsList pointList;
	private JASegment segmentPanel;
	private JAInteger sections;

	public MainWindow() throws HeadlessException {
		super();
		initUI();
	}

	public void initUI() {    
		Container main = this.getContentPane();
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
        setTitle("Waypoint Calculator");
        setSize(windowWidth, windowhHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel left = new JPanel();	    
        left.setPreferredSize(new Dimension(this.getWidth()*4/5 - 5, this.getHeight()));
        left.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        JPanel placeholderPanel = new JPanel(new BorderLayout());    
        left.add(placeholderPanel, c);      
        
        JTextField tf = new JTextField("asdasd");        
        placeholderPanel.add(tf);
        
        this.add(left,c);
	        
        JPanel right = new JPanel(new FlowLayout());
        right.setPreferredSize(new Dimension(this.getWidth()*1/5 - 5, this.getHeight()));
        right.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                 
        pointList = new JAPointsList();        
        right.add(pointList, c);
        pointList.setPreferredSize(new Dimension(pointList.getParent().getPreferredSize().width - 10, 350));
        
        segmentPanel = new JASegment("Base segment", pointList.getSelectedPoints());
        right.add(segmentPanel, c);
        segmentPanel.setPreferredSize(new Dimension(segmentPanel.getParent().getPreferredSize().width - 10, 75));
        
        sections = new JAInteger("Sections");
        right.add(sections, c);
        sections.setPreferredSize(new Dimension(sections.getParent().getPreferredSize().width - 10, 50));
        
        JPanel placeholderLog = new JPanel(new BorderLayout());    
        right.add(placeholderLog, c);        
        placeholderLog.setPreferredSize(new Dimension(placeholderLog.getParent().getPreferredSize().width - 10, 200));
        
        log = new JAConsole();
        placeholderLog.add(log);
        
        JButton flip = new JButton("Switch veiw");
        flip.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(placeholderPanel.getComponentCount() == 1 && placeholderLog.getComponentCount() == 1){
					JPanel component;
					component = (JPanel) placeholderLog.getComponents()[0];
					placeholderLog.removeAll();
					placeholderLog.add(placeholderPanel.getComponents()[0]);
					placeholderPanel.removeAll();
					placeholderPanel.add(component);
				}
				
				((JButton) e.getSource()).getParent().repaint();
			}
		});
        
        right.add(flip, c);
        
        this.add(right,c);
        
        this.pack();
	}

	public Consumer getConsole() {
		return (Consumer)log;
	}
	
	 public File getSelectedSourceFile() {
		return pointList.getSelectedSourceFile();
	}
	
}

