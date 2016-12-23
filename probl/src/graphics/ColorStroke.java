package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Stroke;

public class ColorStroke implements Stroke {
	private Stroke stroke;

	public ColorStroke( Stroke stroke, Color color, Graphics g) {
		this.stroke = stroke;
	}

	@Override
	public Shape createStrokedShape(Shape p) {
		return stroke.createStrokedShape(p);
	}
}