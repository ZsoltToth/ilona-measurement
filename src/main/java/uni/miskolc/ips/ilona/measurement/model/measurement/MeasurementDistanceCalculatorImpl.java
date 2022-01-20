package uni.miskolc.ips.ilona.measurement.model.measurement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MeasurementDistanceCalculatorImpl implements MeasurementDistanceCalculator {

    private static final double UNKNOWN_DISTANCE = -1.0;

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
        double result = 0.0;
        double denominator = 0.0;

        double wifiDistance = wifiDistance(measA.getWifiRssi(), measB.getWifiRssi());

        if (wifiDistance != WifiRssiDistanceCalculator.UNKOWN_DISTANCE) {
            result += wifiDistanceWeight * Math.pow(wifiDistance, 2.0);
            denominator += wifiDistanceWeight;
        }

        double bluetoothDistance = bluetoothDistance(measA.getBluetoothTags(), measB.getBluetoothTags());

        if (bluetoothDistance != UNKNOWN_DISTANCE) {
            result += bluetoothDistanceWeight * Math.pow(bluetoothDistance, 2.0);
            denominator += bluetoothDistanceWeight;
        }

        double magnetometerDistance = magnetometerDistance(measA.getMagnetometer(), measB.getMagnetometer());

        if (magnetometerDistance != Magnetometer.UNKNOWN_DISTANCE) {
            result += magnetometerDistanceWeight * Math.pow(magnetometerDistance, 2.0);
            denominator += magnetometerDistanceWeight;
        }

        double gpsCoordinateDistance = gpsCoordinateDistance(measA.getGpsCoordinates(), measB.getGpsCoordinates());

        if (gpsCoordinateDistance != UNKNOWN_DISTANCE) {
            result += gpsDistanceWeight * Math.pow(gpsCoordinateDistance, 2.0);
            denominator += gpsDistanceWeight;
        }

        double rfidDistance = rfidDistance(measA.getRfidtags(), measB.getRfidtags());

        if (rfidDistance != UNKNOWN_DISTANCE) {
            result += rfidDistanceWeight * Math.pow(rfidDistance, 2.0);
            denominator += rfidDistanceWeight;
        }

        if (denominator == 0.0) {
            return UNKNOWN_DISTANCE;
        }

        result = Math.sqrt(result / denominator);
        log.info(
                String.format(
                        "Distance between %s and %s is %f", measA.toString(), measB.toString(), result));
        return result;
    }

    private double wifiDistance(WifiRssi wifiRssiA, WifiRssi wifiRssiB) {
        if (wifiRssiA != null && wifiRssiB != null) {
            return wifiDistanceCalculator.distance(wifiRssiA, wifiRssiB);
        }

        return UNKNOWN_DISTANCE;
    }

    private double bluetoothDistance(BluetoothTags bluetoothTagsA, BluetoothTags bluetoothTagsB) {
        if (bluetoothTagsA != null && bluetoothTagsB != null) {
            return bluetoothTagsA.distance(bluetoothTagsB);
        }

        return UNKNOWN_DISTANCE;
    }

    private double magnetometerDistance(Magnetometer magnetometerA, Magnetometer magnetometerB) {
        if (magnetometerA != null && magnetometerB != null) {
            return magnetometerA.distance(magnetometerB);
        }

        return UNKNOWN_DISTANCE;
    }

    private double gpsCoordinateDistance(GpsCoordinate gpsCoordinatesA, GpsCoordinate gpsCoordinatesB) {
        if (gpsCoordinatesA != null && gpsCoordinatesB != null) {
            return gpsCoordinatesA.distance(gpsCoordinatesB);
        }

        return UNKNOWN_DISTANCE;
    }

    private double rfidDistance(RfidTags rfidTagsA, RfidTags rfidTagsB) {
        if (rfidTagsA != null && rfidTagsB != null) {
            return rfidTagsA.distance(rfidTagsB);
        }

        return UNKNOWN_DISTANCE;
    }
}
