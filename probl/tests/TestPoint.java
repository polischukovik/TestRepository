import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import geometry.Point;


public class TestPoint {
	
   @Test
   public void constructor() {
      double lat = 50.388171;
      double lon = 30.494762;
      
      Point t = new Point(lat + 0.0000001, lon + 0.0000001);
      
      assertEquals(lat, t.getLatitude(), 0.0000001);
      assertEquals(lon, t.getLongitude(), 0.0000001);
   }
}