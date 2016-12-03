package uni.miskolc.ips.ilona.measurement.model.measurement;

import java.util.Date;
import java.util.UUID;

import uni.miskolc.ips.ilona.measurement.model.position.Position;

/**
 * It represents a measurement. It is stored in the radio map. A measurement
 * contains a time stamp, a location and the RSS values as (SSID,value) pairs.
 * The measurements are distinguished by their time stamp.
 * 
 * @author zsolt
 *
 */
public class Measurement {

	/**
	 * It represents a universally unique identifier, which identifies the
	 * measurement.
	 */
	private UUID id;

	/**
	 * The class Date represents a specific instant in time, with millisecond
	 * precision.
	 */
	private Date timestamp;

	/**
	 * The Position class.
	 */
	private Position position;

	// Measured Values

	/**
	 * Received signal strength indicator is a measurement of the power present
	 * in a received radio signal.
	 */
	private WiFiRSSI wifiRSSI;

	/**
	 * It represents the measured values of a Magnetometer. The magnetometer
	 * returns values in Cartesian coordinate system.
	 */

	private Magnetometer magnetometer;

	/**
	 * Represents the measurement of the bluetooth sensor and calculates the
	 * distance of two bluetooth sensor measurement.
	 */
	private BluetoothTags bluetoothTags;

	/**
	 * The class responsible for the GPS Coordinate of the measurement.
	 */
	private GPSCoordinate gpsCoordinates;

	/**
	 * The RFIDTags class.
	 */
	private RFIDTags rfidtags;

	/**
	 * Constructor for the Measurement class.
	 */
	public Measurement() {
		super();
	}

	/**
	 * 
	 * @param timestamp
	 *            represents a specific instant in time, with millisecond.
	 * @param position
	 *            based on the coordinate, zone and UUID attributes.
	 * @param wifiRSSI
	 *            represents the received signal strength indicator.
	 * @param magnetometer
	 *            measured values.
	 * @param bluetoothTags
	 *            represents the measurement of the bluetooth sensor.
	 * @param gpsCoordinates
	 *            defines the latitude, longitude and distance.
	 * @param rfidTags
	 *            uses electromagnetic fields to automatically identify and
	 *            track tags attached to objects.
	 */
	protected Measurement(final Date timestamp, final Position position, final WiFiRSSI wifiRSSI,
			final Magnetometer magnetometer, final BluetoothTags bluetoothTags, final GPSCoordinate gpsCoordinates,
			final RFIDTags rfidTags) {
		super();
		this.id = UUID.randomUUID();
		this.timestamp = timestamp;
		this.position = position;
		this.wifiRSSI = wifiRSSI;
		this.magnetometer = magnetometer;
		this.bluetoothTags = bluetoothTags;
		this.gpsCoordinates = gpsCoordinates;
		this.rfidtags = rfidTags;
	}

	/**
	 * RFID tag getter method.
	 * 
	 * @return the RFID tags.
	 */
	public final RFIDTags getRfidtags() {
		return rfidtags;
	}

	/**
	 * RFID tag setter method.
	 * 
	 * @param rfidtags
	 *            sets the value of rfidtags.
	 */
	public final void setRfidtags(final RFIDTags rfidtags) {
		this.rfidtags = rfidtags;
	}

	/**
	 * WiFiRSSI getter method.
	 * 
	 * @return wifiRSSI gets the measured WiFiRSSI values.
	 */
	public final WiFiRSSI getWifiRSSI() {
		return wifiRSSI;
	}

	/**
	 * WiFiRSSI setter method.
	 * 
	 * @param wifiRSSI
	 *            sets the measured WiFIRSSI values.
	 */

	public final void setWifiRSSI(final WiFiRSSI wifiRSSI) {
		this.wifiRSSI = wifiRSSI;
	}

	/**
	 * Magnetometer getter method.
	 * 
	 * @return magnetometer values in Cartesian coordinate system.
	 */
	public final Magnetometer getMagnetometer() {
		return magnetometer;
	}

	/**
	 * Magnetometer setter method.
	 * 
	 * @param magnetometer
	 *            sets magnetometer values.
	 */
	public final void setMagnetometer(final Magnetometer magnetometer) {
		this.magnetometer = magnetometer;
	}

	/**
	 * BluetoothTags getter method.
	 * 
	 * @return bluetoothTags gets the measured and distance data.
	 */
	public final BluetoothTags getBluetoothTags() {
		return bluetoothTags;
	}

	/**
	 * BluetoothTags setter method.
	 * 
	 * @param bluetoothTags
	 *            sets the measured and distance data.
	 */
	public final void setBluetoothTags(final BluetoothTags bluetoothTags) {
		this.bluetoothTags = bluetoothTags;
	}

	/**
	 * GPSCoordinate getter method.
	 * 
	 * @return gpsCoordinates gets the calculated coordinates.
	 */
	public final GPSCoordinate getGpsCoordinates() {
		return gpsCoordinates;
	}

	/**
	 * GpsCoordinates setter method.
	 * 
	 * @param gpsCoordinates
	 *            sets the calculated coordinates.
	 */
	public final void setGpsCoordinates(final GPSCoordinate gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}

	/**
	 * UUID getter method.
	 * 
	 * @return id gets the measurement identifier.
	 */
	public final UUID getId() {
		return id;
	}

	/**
	 * UUID setter method.
	 * 
	 * @param id
	 *            sets the measurement identifier.
	 */
	public final void setId(final UUID id) {
		this.id = id;
	}

	/**
	 * Date getter method.
	 * 
	 * @return timestamp gets the current timestamp measured in milliseconds.
	 */
	public final Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Timestamp setter method.
	 * 
	 * @param timestamp
	 *            sets the current timestamp measured in milliseconds.
	 */
	public final void setTimestamp(final Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Position getter method.
	 * 
	 * @return position gets the position based on the coordinate, zone and UUID
	 *         attributes.
	 */
	public final Position getPosition() {
		return position;
	}

	/**
	 * Position setter method.
	 * 
	 * @param position
	 *            sets the position based on the coordinate, zone and UUID
	 *            attributes.
	 */
	public final void setPosition(final Position position) {
		this.position = position;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (!(obj instanceof Measurement)) {
			return false;
		}
		

		return this.id.equals(((Measurement) obj).id);
	}

	@Override
	public final String toString() {
		return "Measurement [id=" + id + ", timestamp=" + timestamp + ", position=" + position + ", " + wifiRSSI + ", "
				+ magnetometer + ", " + bluetoothTags + ", " + gpsCoordinates + ", " + rfidtags + "]";
	}

}
