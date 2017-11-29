package uni.miskolc.ips.ilona.measurement.model.position;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoordinateTest {

	@Test
	public void testSetParameters() {
		double x = 0.4, y = 0.7, z = 0.2;
		Coordinate coord = new Coordinate();
		coord.setX(x);
		coord.setY(y);
		coord.setZ(z);
		if ((coord.getX() == x && coord.getY() == y && coord.getZ() == z)) {
			String coordString= coord.toString();
		}
	}
	
	@Test
	public void testDistanceFromItself(){
		Coordinate coord = new Coordinate(0.2, 0.6, 1.2);
		double expected = 0.0;
		assertEquals(expected, coord.distance(coord),1e-10);
	}

}
