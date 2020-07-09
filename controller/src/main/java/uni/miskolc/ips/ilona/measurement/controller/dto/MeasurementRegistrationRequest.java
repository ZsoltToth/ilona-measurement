package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
public class MeasurementRegistrationRequest {
    private MeasurementRegistrationRequest.Position position;
    private MeasurementRegistrationRequest.WifiRSSI wifiRSSI;
    private MeasurementRegistrationRequest.Magnetometer magnetometer;
    private MeasurementRegistrationRequest.BluetoothTags bluetoothTags;
    private MeasurementRegistrationRequest.GpsCoordinates gpsCoordinates;
    private MeasurementRegistrationRequest.Rfidtags rfidtags;

    public MeasurementRegistrationRequest(Position position, WifiRSSI wifiRSSI, Magnetometer magnetometer,
                                          BluetoothTags bluetoothTags, GpsCoordinates gpsCoordinates,
                                          Rfidtags rfidtags) {
        this.position = position;
        this.wifiRSSI = wifiRSSI;
        this.magnetometer = magnetometer;
        this.bluetoothTags = bluetoothTags;
        this.gpsCoordinates = gpsCoordinates;
        this.rfidtags = rfidtags;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    public static class BluetoothTags {
        private List<String> bluetoothTag;

        public BluetoothTags(List<String> bluetoothTag) {
            this.bluetoothTag = bluetoothTag;
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
    @Getter @Setter
    @NoArgsConstructor
    public static class Position {
        private ZoneDTO zone;
        private CoordinateDTO coordinate;

        public Position(ZoneDTO zone, CoordinateDTO coordinate) {
            this.zone = zone;
            this.coordinate = coordinate;
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    public static class Rfidtags {
        private List<byte[]> rfidTag;

        public Rfidtags(List<byte[]> rfidTag) {
            this.rfidTag = rfidTag;
        }
    }

    @Builder
    @NoArgsConstructor
    public static class WifiRSSI {
        private List<MeasurementRegistrationRequest.WifiRSSI.Ap> ap;

        public WifiRSSI(List<Ap> ap) {
            this.ap = ap;
        }

        public List<MeasurementRegistrationRequest.WifiRSSI.Ap> getAp() {
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
