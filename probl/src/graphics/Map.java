package graphics;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import geometry.Point;
import geometry.Polygon;
import tools.GoogleTools;

public class Map{
	
	private Point SW, NE;
	private ImageIcon image;

//	/**
//	 * Creates blank map
//	 */
//	public Map() {
//		this.SW = null;
//		this.NE = null;
//		
//		defaultImage = createImageIcon(DEFAULTIMAGEPATH, "Default Image");
//	}
	
	/**
	 * Creates map to display polygon.
	 * Map contains SW and NE points and GoogleMap image
	 * @param polygon
	 */
	public Map(Polygon polygon, Dimension canvasSize) {
		image = GoogleTools.getMapImage(polygon, canvasSize, "/img/blank.png");
		
		Dimention poligonDimention = new Dimention(polygon);
		
		if(image != null) {
//			this.SW = center.moveTo(direction, distance);
//			this.NE = center.moveTo(direction, distance);
			this.SW = poligonDimention.getSquareDiagonal().getA();
			this.NE = poligonDimention.getSquareDiagonal().getB();
		}else{
			this.SW = poligonDimention.getSquareDiagonal().getA();
			this.NE = poligonDimention.getSquareDiagonal().getB();
		}
	}

	public ImageIcon getImage() {
		return image;
	}

	public Point getSW() {
		return SW;
	}

	public void setSW(Point SW) {
		this.SW = SW;
	}

	public Point getNE() {
		return NE;
	}
	
	public void setNE(Point NE) {
		this.NE = NE;
	}
	
//	public int getBoundsZoomLevel(Dimention, mapDim) {
//	    int WORLD_DIM_height = 256, WORLD_DIM_width = 256;
//	    int ZOOM_MAX = 21;
//
//	    var ne = bounds.getNorthEast();
//	    var sw = bounds.getSouthWest();
//
//	    var latFraction = (latRad(ne.lat()) - latRad(sw.lat())) / Math.PI;
//
//	    var lngDiff = ne.lng() - sw.lng();
//	    var lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;
//
//	    var latZoom = zoom(mapDim.height, WORLD_DIM.height, latFraction);
//	    var lngZoom = zoom(mapDim.width, WORLD_DIM.width, lngFraction);
//
//	    return Math.min(latZoom, lngZoom, ZOOM_MAX);
//	}
//	
//	function latRad(lat) {
//        var sin = Math.sin(lat * Math.PI / 180);
//        var radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
//        return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
//    }
//
//    function zoom(mapPx, worldPx, fraction) {
//        return Math.floor(Math.log(mapPx / worldPx / fraction) / Math.LN2);
//    }

}
