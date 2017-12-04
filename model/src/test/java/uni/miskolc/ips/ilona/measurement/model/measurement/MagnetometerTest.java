package uni.miskolc.ips.ilona.measurement.model.measurement;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class MagnetometerTest {

	@Test
	public void testdistanceFromItself() {
		Magnetometer magneto = new Magnetometer();
		magneto.setRadian(20.0);
		magneto.setxAxis(1.0);
		magneto.setyAxis(1.0);
		magneto.setzAxis(1.0);
		double expected = 0.0;
		double actual = magneto.distance(magneto);
		assertEquals(expected, actual, 1e-10);

	}

	@Test
	public void testdistanceReflexivity() {
		Magnetometer magneto1 = new Magnetometer(1, 1, 1, 20);
		Magnetometer magneto2 = new Magnetometer(3, 11, 23, 0.7);
		assertEquals(magneto1.distance(magneto2),
				magneto2.distance(magneto1), 1e-10);
	}
	
	@Test
	public void testdistanceFromNullVector() {
		Magnetometer magneto1 = new Magnetometer(1, 0, 0, 0.7);
		Magnetometer magneto2 = new Magnetometer(0, 0, 0, 0.8);
		double expected = Magnetometer.UNKNOW_DISTANCE;
		double actual = magneto1.distance(magneto2);
		assertEquals(expected, actual, 1e-10);

	}
	
	@Test
	public void testdistance90() {
		Magnetometer magneto1 = new Magnetometer(1, 0, 0, 0.7);
		Magnetometer magneto2 = new Magnetometer(0, 1, 0, 0.7);
		double expected = Math.toRadians(90) / Math.PI;
		double actual = magneto1.distance(magneto2);
		assertEquals(expected, actual, 1e-10);

	}

	@Test
	public void testdistance180() {
		Magnetometer magneto1 = new Magnetometer(0.3, 0, 0, 0.7);
		Magnetometer magneto2 = new Magnetometer(-0.5, 0, 0, 0.7);
		double expected = 1.0;
		double actual = magneto1.distance(magneto2);
		assertEquals(expected, actual, 1e-10);

	}
	
	@Test
	public void testNullVectors(){
		Magnetometer magneto1 = new Magnetometer(0, 0, 0, 0);
		Magnetometer magneto2 = new Magnetometer(0, 0, 0, 0);
		assertEquals(Magnetometer.UNKNOW_DISTANCE, magneto1.distance(magneto2),1e-10);
	}
	
	@Test
	public void testOutOfIntervalCosine(){
		Magnetometer magneto1 = new Magnetometer(-1, -1, -1, 0);
		Magnetometer magneto2 = new Magnetometer(1, 1, 1, 0);
		double expected = 1.0;
		assertEquals(expected, magneto1.distance(magneto2),1e-10);
	}

}
