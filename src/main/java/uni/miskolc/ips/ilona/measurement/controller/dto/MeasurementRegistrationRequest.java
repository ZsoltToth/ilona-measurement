package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementRegistrationRequest {
    private MeasurementRegistrationRequest.Position position;

    private MeasurementRegistrationRequest.WifiRssi wifiRssi;

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
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GpsCoordinates {
        private double latitude;

        private double longitude;

        private double altitude;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Magnetometer {
        private double xAxis;

        private double yAxis;

        private double zAxis;

        private double radian;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private ZoneDto zone;

        private CoordinateDto coordinate;
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
    public static class WifiRssi {
        private List<MeasurementRegistrationRequest.WifiRssi.Ap> ap;

        public List<MeasurementRegistrationRequest.WifiRssi.Ap> getAp() {
            if (ap == null) {
                ap = new ArrayList<>();
            }
            return ap;
        }

        @Builder
        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Ap {
            private double value;

            private String ssid;
        }
    }
}
