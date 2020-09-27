package uni.miskolc.ips.ilona.measurement.model.measurement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MeasurementDistanceCalculatorImpl implements
		MeasurementDistanceCalculator {

	private static final double UNKNOWN_DISTANCE = -1.0;
	private static final Logger LOG = LogManager.getLogger(MeasurementDistanceCalculatorImpl.class);
	private WiFiRSSIDistanceCalculator wifiDistanceCalculator;
	private double wifiDistanceWeight;
	private double magnetometerDistanceWeight;
	private double gpsDistanceWeight;
	private double bluetoothDistanceWeight;
	private double rfidDistanceWeight;

	public MeasurementDistanceCalculatorImpl(
			WiFiRSSIDistanceCalculator wifiDistanceCalculator,
			double wifiDistanceWeight, double magnetometerDistanceWeight,
			double gpsDistanceWeight, double bluetoothDistanceWeight,
			double rfidDistanceWeight) {
		super();
		this.wifiDistanceCalculator = wifiDistanceCalculator;
		this.wifiDistanceWeight = wifiDistanceWeight;
		this.magnetometerDistanceWeight = magnetometerDistanceWeight;
		this.gpsDistanceWeight = gpsDistanceWeight;
		this.bluetoothDistanceWeight = bluetoothDistanceWeight;
		this.rfidDistanceWeight = rfidDistanceWeight;
	}

	public double distance(Measurement measA, Measurement measB) {
		double result = -1;
		double wifiDistance, bluetoothDistance, magnetometerDistance, GPSCoordinateDistance, rfidDistance;
		wifiDistance = measA.getWifiRSSI() !=null && measB.getWifiRSSI() != null ? wifiDistanceCalculator.distance(measA.getWifiRSSI(),
				measB.getWifiRSSI()) : UNKNOWN_DISTANCE;
		bluetoothDistance = measA.getBluetoothTags() != null
				&& measB.getBluetoothTags() != null ? measA.getBluetoothTags()
				.distance(measB.getBluetoothTags()) : UNKNOWN_DISTANCE;
		magnetometerDistance = measA.getMagnetometer() != null
				&& measB.getMagnetometer() != null ? measA.getMagnetometer()
				.distance(measB.getMagnetometer()) : UNKNOWN_DISTANCE;
		GPSCoordinateDistance = measA.getGpsCoordinates() != null
				&& measB.getGpsCoordinates() != null ? measA
				.getGpsCoordinates().distance(measB.getGpsCoordinates())
				: UNKNOWN_DISTANCE;
		rfidDistance = measA.getRfidtags() != null
				&& measB.getRfidtags() != null ? measA.getRfidtags().distance(
				measB.getRfidtags()) : UNKNOWN_DISTANCE;

		result = 0.0;
		double denominator = 0.0;

		if (wifiDistance != WiFiRSSIDistanceCalculator.UNKOWN_DISTANCE) {
			result += wifiDistanceWeight * Math.pow(wifiDistance, 2.0);
			denominator += wifiDistanceWeight;
		}

		if (bluetoothDistance != UNKNOWN_DISTANCE) {
			result += bluetoothDistanceWeight
					* Math.pow(bluetoothDistance, 2.0);
			denominator += bluetoothDistanceWeight;
		}

		if (magnetometerDistance != Magnetometer.UNKNOW_DISTANCE) {
			result += magnetometerDistanceWeight
					* Math.pow(magnetometerDistance, 2.0);
			denominator += magnetometerDistanceWeight;
		}

		if (GPSCoordinateDistance != UNKNOWN_DISTANCE) {
			result += gpsDistanceWeight * Math.pow(GPSCoordinateDistance, 2.0);
			denominator += gpsDistanceWeight;
		}

		if(rfidDistance != UNKNOWN_DISTANCE){
			result += rfidDistanceWeight * Math.pow(rfidDistance, 2.0);
			denominator += rfidDistanceWeight;
		}
		
		if (denominator == 0.0) {
			return UNKNOWN_DISTANCE;
		}

	
		result = Math.sqrt(result / denominator);
		LOG.info(String.format("Distance between %s and %s is %f",
				measA.toString(), measB.toString(), result));
		return result;

	}

}
