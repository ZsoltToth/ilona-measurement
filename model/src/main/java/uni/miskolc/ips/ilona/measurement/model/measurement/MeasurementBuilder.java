package uni.miskolc.ips.ilona.measurement.model.measurement;

import java.util.Date;

import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;

public class MeasurementBuilder {

	private Position position;

	private WiFiRSSI wifiRSSI;
	private Magnetometer magnetometer;
	private BluetoothTags bluetoothTags;
	private GPSCoordinate gpsCoordinates;
	private RFIDTags rfidTags;

	public MeasurementBuilder() {

	}

	public Measurement build() {
		return new Measurement(new Date(), position, wifiRSSI, magnetometer,
				bluetoothTags, gpsCoordinates, rfidTags);
	}

	public void unsetPosition() {
		this.position = null;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setPosition(Zone zone) {
		if (this.position == null) {
			this.position = new Position();
		}
		this.position.setZone(zone);
	}

	public void setPosition(Coordinate coordinate) {
		if (this.position == null) {
			this.position = new Position();
		}
		this.position.setCoordinate(coordinate);
	}

	public void unsetWifiRSSI() {
		this.wifiRSSI = null;
	}

	public void setWifiRSSI(final WiFiRSSI wifiRSSI) {
		this.wifiRSSI = wifiRSSI;
	}

	public void unsetMagnetometer() {
		this.magnetometer = null;
	}

	public void setMagnetometer(Magnetometer magnetometer) {
		this.magnetometer = magnetometer;
	}

	public void unsetbluetoothTags() {
		this.bluetoothTags = null;
	}

	public void setbluetoothTags(BluetoothTags bluetoothTags) {
		this.bluetoothTags = bluetoothTags;
	}

	public void unsetGPSCoordinates() {
		this.gpsCoordinates = null;
	}

	public void setGPSCoordinates(GPSCoordinate gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}

	public void unsetRFIDTags() {
		this.rfidTags = null;
	}

	public void setRFIDTags(RFIDTags rfidTags) {
		this.rfidTags = rfidTags;
	}

}
