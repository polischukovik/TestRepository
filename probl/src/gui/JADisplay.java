package gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class JADisplay extends JPanel{
	private JACanvas canvas = new JACanvas();

	public JADisplay() {
		super(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Display"));
		
		JPanel container = new JPanel(new BorderLayout());
		this.add(container);
		container.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				
		container.add(canvas);
	}

	public JACanvas getCanvas() {
		return canvas;
	}
}
