import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Double;

import javax.swing.JPanel;

public class DemoPanel extends JPanel {
	String name = "";

	public DemoPanel(String name) {
		super();		
		this.name = name;
	}

	public DemoPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public DemoPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public DemoPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		float dash1[] = {5.0f};
		BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,10.0f, dash1, 0.0f);
		g2.setStroke(dashed);
		
		Double border = new RoundRectangle2D.Double(3, 3, getWidth()-5, getHeight()-5,20,20);
		g2.draw(border);
		g2.drawString(name, getWidth()/2, getHeight()/2);

	}
	
	

}
