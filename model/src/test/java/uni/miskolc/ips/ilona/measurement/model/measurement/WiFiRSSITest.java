package uni.miskolc.ips.ilona.measurement.model.measurement;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import uni.miskolc.ips.ilona.measurement.model.measurement.wifi.VectorIntersectionWiFiRSSIDistance;
import uni.miskolc.ips.ilona.measurement.model.measurement.wifi.VectorUnionWiFiRSSIDistance;

public class WiFiRSSITest {

	@Test
	public void testZeroFromItselfIntersection() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -1.3);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -3.3);
		wifi.setRSSI("Four", -2.3);
		VectorIntersectionWiFiRSSIDistance wifiinter = new VectorIntersectionWiFiRSSIDistance();
		double expected = 0.0;
		double actual = wifiinter.distance(wifi, wifi);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testReflexivityIntersection() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -1.3);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -3.3);
		wifi.setRSSI("Four", -2.3);
		WiFiRSSI wifi2 = new WiFiRSSI();
		wifi2.setRSSI("One", -1.3);
		wifi2.setRSSI("Five", -1.4);
		wifi2.setRSSI("Six", -3.3);
		wifi2.setRSSI("Four", -2.3);
		VectorIntersectionWiFiRSSIDistance wifiinter = new VectorIntersectionWiFiRSSIDistance();
		assertEquals(wifiinter.distance(wifi, wifi2),
				wifiinter.distance(wifi2, wifi), 1e-10);
	}

	@Test
	public void testOneEmptyIntersection() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -1.3);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -3.3);
		wifi.setRSSI("Four", -2.3);
		WiFiRSSI wifi2 = new WiFiRSSI();
		VectorIntersectionWiFiRSSIDistance wifiinter = new VectorIntersectionWiFiRSSIDistance();
		double expected = WiFiRSSIDistanceCalculator.UNKOWN_DISTANCE;
		double actual = wifiinter.distance(wifi, wifi2);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testTwoEmptyIntersection() {
		WiFiRSSI wifi = new WiFiRSSI();
		WiFiRSSI wifi2 = new WiFiRSSI();
		VectorIntersectionWiFiRSSIDistance wifiinter = new VectorIntersectionWiFiRSSIDistance();
		double expected = WiFiRSSIDistanceCalculator.UNKOWN_DISTANCE;
		double actual = wifiinter.distance(wifi, wifi2);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testDifferentIntersection() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -1.3);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -8.3);
		wifi.setRSSI("Four", -2.3);
		WiFiRSSI wifi2 = new WiFiRSSI();
		wifi2.setRSSI("Eight", -2.3);
		wifi2.setRSSI("Five", -1.4);
		wifi2.setRSSI("Seven", -3.3);
		wifi2.setRSSI("Six", -2.6);
		VectorIntersectionWiFiRSSIDistance wifiinter = new VectorIntersectionWiFiRSSIDistance();
		double expected = -1.0;
		double actual = wifiinter.distance(wifi, wifi2);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testDistanceIntersection() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -12.4);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -3.3);
		wifi.setRSSI("Four", -2.3);
		WiFiRSSI wifi2 = new WiFiRSSI();
		wifi2.setRSSI("One", -1.4);
		wifi2.setRSSI("Five", -1.4);
		wifi2.setRSSI("Two", -333.3);
		wifi2.setRSSI("Four", -29.3);
		wifi2.setRSSI("Three", -31.3);
		VectorIntersectionWiFiRSSIDistance wifiinter = new VectorIntersectionWiFiRSSIDistance();
		double expected = 0.4734;
		double actual = wifiinter.distance(wifi, wifi2);
		assertEquals(expected, actual, 1e-4);
	}

	@Test
	public void testDistance1Intersection() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -12.4);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -3.3);
		wifi.setRSSI("Four", -2.3);
		WiFiRSSI wifi2 = new WiFiRSSI();
		wifi2.setRSSI("One", -1.4);
		wifi2.setRSSI("Five", -1.4);
		VectorIntersectionWiFiRSSIDistance wifiinter = new VectorIntersectionWiFiRSSIDistance();
		double expected = 1.0;
		double actual = wifiinter.distance(wifi, wifi2);
		assertEquals(expected, actual, 1e-4);
	}

	@Test
	public void testZeroFromItselfUnion() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -1.3);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -3.3);
		wifi.setRSSI("Four", -2.3);
		WiFiRSSIDistanceCalculator wifiunion = new VectorUnionWiFiRSSIDistance(
				0);
		double expected = 0.0;
		double actual = wifiunion.distance(wifi, wifi);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testReflexivityUnion() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -1.3);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -3.3);
		wifi.setRSSI("Four", -2.3);
		WiFiRSSI wifi2 = new WiFiRSSI();
		wifi2.setRSSI("One", -1.3);
		wifi2.setRSSI("Five", -1.4);
		wifi2.setRSSI("Six", -3.3);
		wifi2.setRSSI("Four", -2.3);
		WiFiRSSIDistanceCalculator wifiunion = new VectorUnionWiFiRSSIDistance(
				0);
		assertEquals(wifiunion.distance(wifi, wifi2),
				wifiunion.distance(wifi2, wifi), 1e-10);
	}

	@Test
	public void testOneEmptyUnion() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -1.3);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -3.3);
		wifi.setRSSI("Four", -2.3);
		WiFiRSSI wifi2 = new WiFiRSSI();
		WiFiRSSIDistanceCalculator wifiunion = new VectorUnionWiFiRSSIDistance(
				0);
		double expected = 1;
		double actual = wifiunion.distance(wifi, wifi2);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testTwoEmptyUnion() {
		WiFiRSSI wifi = new WiFiRSSI();
		WiFiRSSI wifi2 = new WiFiRSSI();
		WiFiRSSIDistanceCalculator wifiunion = new VectorUnionWiFiRSSIDistance(
				0);
		double expected = WiFiRSSIDistanceCalculator.UNKOWN_DISTANCE;
		double actual = wifiunion.distance(wifi, wifi2);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testDifferentUnion() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -1.3);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -8.3);
		wifi.setRSSI("Four", -2.3);
		WiFiRSSI wifi2 = new WiFiRSSI();
		wifi2.setRSSI("Eight", -2.3);
		wifi2.setRSSI("Five", -4.4);
		wifi2.setRSSI("Seven", -3.3);
		wifi2.setRSSI("Six", -8.6);
		WiFiRSSIDistanceCalculator wifiunion = new VectorUnionWiFiRSSIDistance(
				0);
		double expected = 13.686855007634149;
		double actual = wifiunion.distance(wifi, wifi2);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testDistanceUnion() {
		WiFiRSSI wifi = new WiFiRSSI();
		wifi.setRSSI("One", -12.4);
		wifi.setRSSI("Two", -1.4);
		wifi.setRSSI("Three", -3.3);
		wifi.setRSSI("Four", -2.3);
		WiFiRSSI wifi2 = new WiFiRSSI();
		wifi2.setRSSI("One", -1.4);
		wifi2.setRSSI("Five", -1.4);
		wifi2.setRSSI("Two", -3.3);
		wifi2.setRSSI("Four", -2.9);
		wifi2.setRSSI("Three", -1.3);
		WiFiRSSIDistanceCalculator wifiunion = new VectorUnionWiFiRSSIDistance(
				0);
		double expected = 11.442464769445436;
		double actual = wifiunion.distance(wifi, wifi2);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testRemoveSSID() {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("One", -50.3);
		map.put("Two", -70.6);
		WiFiRSSI wifi = new WiFiRSSI(map);
		wifi.removeSSID("Two");
		assertEquals(1, wifi.getRssiValues().size(), 1e-10);

	}

}
