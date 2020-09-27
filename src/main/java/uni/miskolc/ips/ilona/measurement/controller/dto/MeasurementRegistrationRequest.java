package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementRegistrationRequest {
    private MeasurementRegistrationRequest.Position position;
    private MeasurementRegistrationRequest.WifiRSSI wifiRSSI;
    private MeasurementRegistrationRequest.Magnetometer magnetometer;
    private MeasurementRegistrationRequest.BluetoothTags bluetoothTags;
    private MeasurementRegistrationRequest.GpsCoordinates gpsCoordinates;
    private MeasurementRegistrationRequest.Rfidtags rfidtags;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BluetoothTags {
        private List<String> bluetoothTag;
    }

    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GpsCoordinates{
        private double latitude;
        private double longitude;
        private double altitude;
    }

    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Magnetometer {
        private double xAxis;
        private double yAxis;
        private double zAxis;
        private double radian;
    }

    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private ZoneDTO zone;
        private CoordinateDTO coordinate;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rfidtags {
        private List<byte[]> rfidTag;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WifiRSSI {
        private List<MeasurementRegistrationRequest.WifiRSSI.Ap> ap;

        public List<MeasurementRegistrationRequest.WifiRSSI.Ap> getAp() {
            if (ap == null) {
               ap = new ArrayList<>();
            }
            return ap;
        }

        @Builder
        @Getter @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Ap {
           private double value;
           private String ssid;
        }
    }
}
