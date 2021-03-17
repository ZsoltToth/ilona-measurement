package uni.miskolc.ips.ilona.measurement.model.measurement;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uni.miskolc.ips.ilona.measurement.model.position.Position;

import java.util.Date;
import java.util.UUID;

/**
 * It represents a measurement. It is stored in the radio map. A measurement contains a time stamp,
 * a location and the RSS values as (SSID,value) pairs. The measurements are distinguished by their
 * time stamp.
 *
 * @author zsolt
 */
@ToString
@EqualsAndHashCode
public class Measurement {

    private UUID id;

    private Date timestamp;

    private Position position;

    private WifiRssi wifiRssi;

    private Magnetometer magnetometer;

    private BluetoothTags bluetoothTags;

    private GpsCoordinate gpsCoordinates;

    private RfidTags rfidtags;

    public Measurement() {
        super();
    }

    protected Measurement(
            final Date timestamp,
            final Position position,
            final WifiRssi wifiRssi,
            final Magnetometer magnetometer,
            final BluetoothTags bluetoothTags,
            final GpsCoordinate gpsCoordinates,
            final RfidTags rfidTags) {
        super();
        this.id = UUID.randomUUID();
        this.timestamp = timestamp;
        this.position = position;
        this.wifiRssi = wifiRssi;
        this.magnetometer = magnetometer;
        this.bluetoothTags = bluetoothTags;
        this.gpsCoordinates = gpsCoordinates;
        this.rfidtags = rfidTags;
    }

    public final RfidTags getRfidtags() {
        return rfidtags;
    }

    public final void setRfidtags(final RfidTags rfidtags) {
        this.rfidtags = rfidtags;
    }

    public final WifiRssi getWifiRssi() {
        return wifiRssi;
    }

    public final void setWifiRssi(final WifiRssi wifiRssi) {
        this.wifiRssi = wifiRssi;
    }

    public final Magnetometer getMagnetometer() {
        return magnetometer;
    }

    public final void setMagnetometer(final Magnetometer magnetometer) {
        this.magnetometer = magnetometer;
    }

    public final BluetoothTags getBluetoothTags() {
        return bluetoothTags;
    }

    public final void setBluetoothTags(final BluetoothTags bluetoothTags) {
        this.bluetoothTags = bluetoothTags;
    }

    public final GpsCoordinate getGpsCoordinates() {
        return gpsCoordinates;
    }

    public final void setGpsCoordinates(final GpsCoordinate gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }

    public final UUID getId() {
        return id;
    }

    public final void setId(final UUID id) {
        this.id = id;
    }

    public final Date getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public final Position getPosition() {
        return position;
    }

    public final void setPosition(final Position position) {
        this.position = position;
    }
}
