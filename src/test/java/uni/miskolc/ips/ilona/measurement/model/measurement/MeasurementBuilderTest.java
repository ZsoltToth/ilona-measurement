package uni.miskolc.ips.ilona.measurement.model.measurement;

import static org.junit.Assert.*;

import org.junit.Test;

import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;


public class MeasurementBuilderTest {

	@Test
	public void testSetUnset() {
		MeasurementBuilder measb = new MeasurementBuilder();
		measb.setPosition(new Coordinate());
		measb.unsetPosition();
		measb.setPosition(Zone.UNKNOWN_POSITION);
		measb.unsetPosition();
		measb.setPosition(new Position());
		measb.unsetPosition();
		
		measb.setbluetoothTags(new BluetoothTags());
		measb.setGPSCoordinates(new GPSCoordinate());
		measb.setMagnetometer(new Magnetometer());
		measb.setRFIDTags(new RFIDTags());
		measb.setWifiRSSI(new WiFiRSSI());
		
		measb.unsetbluetoothTags();
		measb.unsetGPSCoordinates();
		measb.unsetMagnetometer();
		measb.unsetRFIDTags();
		measb.unsetWifiRSSI();

	}

}
