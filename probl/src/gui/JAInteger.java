package gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import probl.App;

public class JAInteger extends JPanel{
	private Integer sections;

	public JAInteger(String name) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(BorderFactory.createTitledBorder(name));
		
		JLabel label1 = new JLabel(String.format("%s:", name));
        
        JTextField sectionsField = new JTextField();
        sectionsField.setText("0");
        sectionsField.setEditable(true);
               
        add(label1);
        add(sectionsField);
        
        sections = Integer.parseInt(sectionsField.getText());
	}

	public Integer getSections() {
		return sections;
	}

}
