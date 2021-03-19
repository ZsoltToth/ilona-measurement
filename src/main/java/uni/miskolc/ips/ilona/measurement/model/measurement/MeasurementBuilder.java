package uni.miskolc.ips.ilona.measurement.model.measurement;

import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;

import java.util.Date;

public class MeasurementBuilder {

    private Position position;

    private WifiRssi wifiRssi;

    private Magnetometer magnetometer;

    private BluetoothTags bluetoothTags;

    private GpsCoordinate gpsCoordinates;

    private RfidTags rfidTags;

    public MeasurementBuilder() {
    }

    public Measurement build() {
        return new Measurement(
                new Date(), position, wifiRssi, magnetometer, bluetoothTags, gpsCoordinates, rfidTags);
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

    public void unsetWifiRssi() {
        this.wifiRssi = null;
    }

    public void setWifiRssi(final WifiRssi wifiRssi) {
        this.wifiRssi = wifiRssi;
    }

    public void unsetMagnetometer() {
        this.magnetometer = null;
    }

    public void setMagnetometer(Magnetometer magnetometer) {
        this.magnetometer = magnetometer;
    }

    public void unsetBluetoothTags() {
        this.bluetoothTags = null;
    }

    public void setBluetoothTags(BluetoothTags bluetoothTags) {
        this.bluetoothTags = bluetoothTags;
    }

    public void unsetGpsCoordinates() {
        this.gpsCoordinates = null;
    }

    public void setGpsCoordinates(GpsCoordinate gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }

    public void unsetRfidTags() {
        this.rfidTags = null;
    }

    public void setRfidTags(RfidTags rfidTags) {
        this.rfidTags = rfidTags;
    }
}
