package gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
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
