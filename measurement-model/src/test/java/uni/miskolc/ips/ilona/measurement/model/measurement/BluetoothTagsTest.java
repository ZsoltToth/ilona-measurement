package uni.miskolc.ips.ilona.measurement.model.measurement;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class BluetoothTagsTest {

	@Test
	public void testDistanceFromItselfIsZero() {
		Set<String> data = new HashSet<String>(Arrays.asList(new String[] {
				"001060AA36F8", "001060AA36F4", "001060AA36F2" }));
		BluetoothTags bluetooth = new BluetoothTags();
		bluetooth.setTags(data);
		bluetooth.addTag("001060AA3611");
		bluetooth.removeTag("001060AA3611");
		double expected = 0.0;
		double actual = bluetooth.distance(bluetooth);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testDistanceReflexivity() {
		Set<String> data1 = new HashSet<String>(Arrays.asList(new String[] {
				"001060AA36F8", "001060AA36F4", "001060AA36F2"  }));
		BluetoothTags bluetooth1 = new BluetoothTags(data1);
		Set<String> data2 = new HashSet<String>(Arrays.asList(new String[] {
				"001060AA36A8", "001060AA36D4", "001060AA36F2"  }));
		BluetoothTags bluetooth2 = new BluetoothTags(data2);
//		assertTrue(bluetooth1.distance(bluetooth2) == bluetooth2.distance(bluetooth1));
		assertEquals(bluetooth1.distance(bluetooth2), bluetooth2.distance(bluetooth1), 1e-10);
		
	}

	@Test
	public void testDistanceOneEmptySet() {
		Set<String> data1 = new HashSet<String>(Arrays.asList(new String[] {}));
		BluetoothTags bluetooth1 = new BluetoothTags(data1);
		Set<String> data2 = new HashSet<String>(Arrays.asList(new String[] {
				"001060AA36F8", "001060AA36F4", "001060AA36F2" }));
		BluetoothTags bluetooth2 = new BluetoothTags(data2);
		double expected = 1;
		double actual = bluetooth1.distance(bluetooth2);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testDistanceAllEmptySet() {
		Set<String> data1 = new HashSet<String>(Arrays.asList(new String[] {}));
		BluetoothTags bluetooth1 = new BluetoothTags(data1);
		BluetoothTags bluetooth2 = new BluetoothTags(data1);
		double expected = 0;
		double actual = bluetooth1.distance(bluetooth2);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testDistanceDifferent() {
		Set<String> data1 = new HashSet<String>(Arrays.asList(new String[] {
				"011060AA36F8", "011060AA36F4", "011060AA36F2" }));
		BluetoothTags bluetooth1 = new BluetoothTags(data1);
		Set<String> data2 = new HashSet<String>(Arrays.asList(new String[] {
				"001060AA36F8", "001060AA36F4", "001060AA36F2" }));
		BluetoothTags bluetooth2 = new BluetoothTags(data2);
		double expected = 1;
		double actual = bluetooth1.distance(bluetooth2);
		assertEquals(expected, actual, 1e-10);
	}

	@Test
	public void testDistance() {
		Set<String> data1 = new HashSet<String>(
				Arrays.asList(new String[] { "001060AA36F8", "001060AA36F4",
						"001060AA36F2", "011060AA36C8" }));
		BluetoothTags bluetooth1 = new BluetoothTags(data1);
		Set<String> data2 = new HashSet<String>(
				Arrays.asList(new String[] { "001060AA36F8", "001060AA36D4",
						"001060AA36F2", "011060AA36C8" }));
		BluetoothTags bluetooth2 = new BluetoothTags(data2);
		double expected = 0.4;
		double actual = bluetooth1.distance(bluetooth2);
		assertEquals(expected, actual, 1e-10);

	}
}
