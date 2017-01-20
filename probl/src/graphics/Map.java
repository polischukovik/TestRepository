package graphics;

import geometry.Point;

public class Map{
	private Point start, end;

	public Map(Point start, Point end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}
	
	public int getBoundsZoomLevel(Dimention, mapDim) {
	    int WORLD_DIM_height = 256, WORLD_DIM_width = 256;
	    int ZOOM_MAX = 21;

	    var ne = bounds.getNorthEast();
	    var sw = bounds.getSouthWest();

	    var latFraction = (latRad(ne.lat()) - latRad(sw.lat())) / Math.PI;

	    var lngDiff = ne.lng() - sw.lng();
	    var lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;

	    var latZoom = zoom(mapDim.height, WORLD_DIM.height, latFraction);
	    var lngZoom = zoom(mapDim.width, WORLD_DIM.width, lngFraction);

	    return Math.min(latZoom, lngZoom, ZOOM_MAX);
	}
	
	function latRad(lat) {
        var sin = Math.sin(lat * Math.PI / 180);
        var radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
        return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
    }

    function zoom(mapPx, worldPx, fraction) {
        return Math.floor(Math.log(mapPx / worldPx / fraction) / Math.LN2);
    }

}
