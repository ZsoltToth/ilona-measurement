package uni.miskolc.ips.ilona.measurement.model.measurement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MeasurementDistanceCalculatorImpl implements MeasurementDistanceCalculator {

    private static final double UNKNOWN_DISTANCE = -1.0;

    private static final Logger LOG = LogManager.getLogger(MeasurementDistanceCalculatorImpl.class);

    private final WifiRssiDistanceCalculator wifiDistanceCalculator;

    private final double wifiDistanceWeight;

    private final double magnetometerDistanceWeight;

    private final double gpsDistanceWeight;

    private final double bluetoothDistanceWeight;

    private final double rfidDistanceWeight;

    public MeasurementDistanceCalculatorImpl(
            WifiRssiDistanceCalculator wifiDistanceCalculator,
            double wifiDistanceWeight,
            double magnetometerDistanceWeight,
            double gpsDistanceWeight,
            double bluetoothDistanceWeight,
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
        double result;
        double wifiDistance;
        double bluetoothDistance;
        double magnetometerDistance;
        double gpsCoordinateDistance;
        double rfidDistance;

        wifiDistance =
                measA.getWifiRssi() != null && measB.getWifiRssi() != null
                        ? wifiDistanceCalculator.distance(measA.getWifiRssi(), measB.getWifiRssi())
                        : UNKNOWN_DISTANCE;
        bluetoothDistance =
                measA.getBluetoothTags() != null && measB.getBluetoothTags() != null
                        ? measA.getBluetoothTags().distance(measB.getBluetoothTags())
                        : UNKNOWN_DISTANCE;
        magnetometerDistance =
                measA.getMagnetometer() != null && measB.getMagnetometer() != null
                        ? measA.getMagnetometer().distance(measB.getMagnetometer())
                        : UNKNOWN_DISTANCE;
        gpsCoordinateDistance =
                measA.getGpsCoordinates() != null && measB.getGpsCoordinates() != null
                        ? measA.getGpsCoordinates().distance(measB.getGpsCoordinates())
                        : UNKNOWN_DISTANCE;
        rfidDistance =
                measA.getRfidtags() != null && measB.getRfidtags() != null
                        ? measA.getRfidtags().distance(measB.getRfidtags())
                        : UNKNOWN_DISTANCE;

        result = 0.0;
        double denominator = 0.0;

        if (wifiDistance != WifiRssiDistanceCalculator.UNKOWN_DISTANCE) {
            result += wifiDistanceWeight * Math.pow(wifiDistance, 2.0);
            denominator += wifiDistanceWeight;
        }

        if (bluetoothDistance != UNKNOWN_DISTANCE) {
            result += bluetoothDistanceWeight * Math.pow(bluetoothDistance, 2.0);
            denominator += bluetoothDistanceWeight;
        }

        if (magnetometerDistance != Magnetometer.UNKNOW_DISTANCE) {
            result += magnetometerDistanceWeight * Math.pow(magnetometerDistance, 2.0);
            denominator += magnetometerDistanceWeight;
        }

        if (gpsCoordinateDistance != UNKNOWN_DISTANCE) {
            result += gpsDistanceWeight * Math.pow(gpsCoordinateDistance, 2.0);
            denominator += gpsDistanceWeight;
        }

        if (rfidDistance != UNKNOWN_DISTANCE) {
            result += rfidDistanceWeight * Math.pow(rfidDistance, 2.0);
            denominator += rfidDistanceWeight;
        }

        if (denominator == 0.0) {
            return UNKNOWN_DISTANCE;
        }

        result = Math.sqrt(result / denominator);
        LOG.info(
                String.format(
                        "Distance between %s and %s is %f", measA.toString(), measB.toString(), result));
        return result;
    }
}
