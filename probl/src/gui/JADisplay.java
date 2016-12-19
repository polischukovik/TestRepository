package gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class JADisplay extends JPanel{

	public JADisplay() {
		super();
		JButton btn = new JButton("Button 1");
		this.add(btn);
		setBorder(BorderFactory.createTitledBorder("Display"));
	}

}
