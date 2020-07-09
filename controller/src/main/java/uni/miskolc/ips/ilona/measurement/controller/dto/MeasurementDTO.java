package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
public class MeasurementDTO {
    private String id;
    private XMLGregorianCalendar timestamp;
    private PositionDTO position;
    private MeasurementDTO.WifiRSSI wifiRSSI;
    private MeasurementDTO.Magnetometer magnetometer;
    private MeasurementDTO.BluetoothTags bluetoothTags;
    private MeasurementDTO.GpsCoordinates gpsCoordinates;
    private MeasurementDTO.Rfidtags rfidtags;

    public MeasurementDTO(String id, XMLGregorianCalendar timestamp, PositionDTO position, WifiRSSI wifiRSSI,
                          Magnetometer magnetometer, BluetoothTags bluetoothTags, GpsCoordinates gpsCoordinates,
                          Rfidtags rfidtags) {
        this.id = id;
        this.timestamp = timestamp;
        this.position = position;
        this.wifiRSSI = wifiRSSI;
        this.magnetometer = magnetometer;
        this.bluetoothTags = bluetoothTags;
        this.gpsCoordinates = gpsCoordinates;
        this.rfidtags = rfidtags;
    }

    @Builder
    @NoArgsConstructor
    public static class BluetoothTags {
        private List<String> bluetoothTag;

        public BluetoothTags(List<String> bluetoothTag) {
            this.bluetoothTag = bluetoothTag;
        }

        public List<String> getBluetoothTag() {
            if (bluetoothTag == null) {
                return new ArrayList<>();
            }
            return bluetoothTag;
        }
    }

    @Builder
    @Getter @Setter
    @NoArgsConstructor
    public static class GpsCoordinates{
        private double latitude;
        private double longitude;
        private double altitude;

        public GpsCoordinates(double latitude, double longitude, double altitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
        }
    }

    @Builder
    @Getter @Setter
    @NoArgsConstructor
    public static class Magnetometer {
        private double xAxis;
        private double yAxis;
        private double zAxis;
        private double radian;

        public Magnetometer(double xAxis, double yAxis, double zAxis, double radian) {
            this.xAxis = xAxis;
            this.yAxis = yAxis;
            this.zAxis = zAxis;
            this.radian = radian;
        }
    }

    @Builder
    @NoArgsConstructor
    public static class Rfidtags {
        private List<byte[]> rfidTag;

        public Rfidtags(List<byte[]> rfidTag) {
            this.rfidTag = rfidTag;
        }

        public List<byte[]> getRfidTag() {
            if (rfidTag == null) {
                rfidTag = new ArrayList<>();
            }
            return rfidTag;
        }
    }

    @Builder
    @NoArgsConstructor
    public static class WifiRSSI {
        private List<MeasurementDTO.WifiRSSI.Ap> ap;

        public WifiRSSI(List<Ap> ap) {
            this.ap = ap;
        }

        public List<Ap> getAp() {
            if (ap == null) {
                ap = new ArrayList<>();
            }
            return ap;
        }

        @Builder
        @Getter @Setter
        @NoArgsConstructor
        public static class Ap {
            private double value;
            private String ssid;

            public Ap(double value, String ssid) {
                this.value = value;
                this.ssid = ssid;
            }
        }
    }
}
