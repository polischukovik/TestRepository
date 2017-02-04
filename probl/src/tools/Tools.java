package tools;

import javax.swing.ImageIcon;

public class Tools {
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	public static ImageIcon createImageIcon(String path, String description) {
	    java.net.URL imgURL = Tools.class.getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't load image: " + path);
	    	return new ImageIcon();
	    }
	}
}
