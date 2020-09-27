package uni.miskolc.ips.ilona.measurement.model.measurement;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;

import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;



public class MeasurementTest {

//	@Test
//	public void testDistanceFromItself() {
//		MeasurementBuilder measbuilder = new MeasurementBuilder();
//		BluetoothTags bluetooth = new BluetoothTags(new HashSet<String>(Arrays.asList(new String[] {
//				"001060AA36F8", "001060AA36F4", "001060AA36F2"  })));
//		Magnetometer magneto = new Magnetometer(12, 32, 23, 0.5);
//		RFIDTags rfid = new RFIDTags(new HashSet<byte[]>());
//		rfid.addTag(new byte[]{ (byte)12,(byte)46});
//		WiFiRSSI wifi = new WiFiRSSI();
//		wifi.setRSSI("egy", -0.4);
//		wifi.setRSSI("Ketto", -1.2);
//		wifi.setRSSI("Harom", -3.2);
//		measbuilder.setbluetoothTags(bluetooth);
//		measbuilder.setMagnetometer(magneto);
//		measbuilder.setRFIDTags(rfid);
//		measbuilder.setWifiRSSI(wifi);
//		measbuilder.setPosition((Position) null); 
//		Measurement meas = measbuilder.build();
//		MeasurementDistanceCalculator measdistance = new MeasurementDistanceCalculatorImpl(new VectorIntersectionWiFiRSSIDistance(), 0.4, 0.3, 0.3, 0.3, 0.5);
//		double expected = 0.0;
//		double actual = measdistance.distance(meas, meas);
//		assertEquals(expected, actual, 1e-10);
//	}
	
//	@Test
//	public void testDistanceReflexivity() {
//		MeasurementBuilder measbuilder = new MeasurementBuilder();
//		GPSCoordinate coord = new GPSCoordinate(20,54, 61200);
//		BluetoothTags bluetooth = new BluetoothTags(new HashSet<String>(Arrays.asList(new String[] {
//				"001060AA36F8", "001060AA36F4", "001060AA36F2"  })));
//		Magnetometer magneto = new Magnetometer(12, 32, 23, 0.5);
//		RFIDTags rfid = new RFIDTags(new HashSet<byte[]>());
//		rfid.addTag(new byte[]{ (byte)12,(byte)46});
//		WiFiRSSI wifi = new WiFiRSSI();
//		wifi.setRSSI("egy", -0.4);
//		wifi.setRSSI("Ketto", -1.2);
//		wifi.setRSSI("Harom", -3.2);
//		measbuilder.setbluetoothTags(bluetooth);
//		measbuilder.setGPSCoordinates(coord);
//		measbuilder.setMagnetometer(magneto);
//		measbuilder.setRFIDTags(rfid);
//		measbuilder.setWifiRSSI(wifi);
//		measbuilder.setPosition((Position) null); 
//		Measurement meas = measbuilder.build();
//		MeasurementBuilder measbuilder2 = new MeasurementBuilder();
//		GPSCoordinate coord2 = new GPSCoordinate(20,20, 61201);
//		BluetoothTags bluetooth2 = new BluetoothTags(new HashSet<String>(Arrays.asList(new String[] {
//				"001060AA31F8", "001010AA36F4", "001060AA36F2"  })));
//		Magnetometer magneto2 = new Magnetometer(12, 32, 23, 0.6);
//		RFIDTags rfid2 = new RFIDTags(new HashSet<byte[]>());
//		rfid.addTag(new byte[]{ (byte)11,(byte)6});
//		WiFiRSSI wifi2 = new WiFiRSSI();
//		wifi.setRSSI("egy", -0.4);
//		wifi.setRSSI("Negy", -1.2);
//		wifi.setRSSI("Harom", -5.2);
//		measbuilder2.setbluetoothTags(bluetooth2);
//		measbuilder2.setGPSCoordinates(coord2);
//		measbuilder2.setMagnetometer(magneto2);
//		measbuilder2.setRFIDTags(rfid2);
//		measbuilder2.setWifiRSSI(wifi2);
//		measbuilder2.setPosition(new Position(new Zone("eeee"))); 
//		Measurement measB = measbuilder2.build();
//		MeasurementDistanceCalculator measdistance = new MeasurementDistanceCalculatorImpl(new VectorIntersectionWiFiRSSIDistance(), 0.4, 0.3, 0.3, 0.3, 0.5);
//		assertEquals(measdistance.distance(meas, measB), measdistance.distance(measB, meas), 1e-10);
//	}
	
//	@Test
//	public void testDistanceOfNull(){
//		MeasurementBuilder measb = new MeasurementBuilder();
//		Measurement meas = measb.build();
//		MeasurementDistanceCalculator measdistance = new MeasurementDistanceCalculatorImpl(new VectorIntersectionWiFiRSSIDistance(), 0.4, 0.3, 0.3, 0.3, 0.5);
//		System.out.println();
//		assertEquals(-1.0, measdistance.distance(meas, meas), 1e-10);
//	}
	
	@Test 
	public void testSetUnset(){
		Measurement meas = new Measurement();
		meas.setBluetoothTags(new BluetoothTags());
		meas.setGpsCoordinates(new GPSCoordinate());
		meas.setId(UUID.randomUUID());
		meas.setMagnetometer(new Magnetometer());
		meas.setPosition(new Position());
		meas.setRfidtags(new RFIDTags());
		meas.setTimestamp(new Timestamp((new Date()).getTime()));
		meas.setWifiRSSI(new WiFiRSSI());
		
		meas.equals(meas);
		meas.equals(new Position());
		meas.getTimestamp();
		meas.getPosition();
		meas.getId();
	}



}
