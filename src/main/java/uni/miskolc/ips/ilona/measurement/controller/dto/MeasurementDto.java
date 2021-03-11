package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDto {
    private String id;

    private XMLGregorianCalendar timestamp;

    private PositionDto position;

    private MeasurementDto.WifiRssi wifiRssi;

    private MeasurementDto.Magnetometer magnetometer;

    private MeasurementDto.BluetoothTags bluetoothTags;

    private MeasurementDto.GpsCoordinates gpsCoordinates;

    private MeasurementDto.Rfidtags rfidtags;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BluetoothTags {
        private List<String> bluetoothTag;

        public List<String> getBluetoothTag() {
            if (bluetoothTag == null) {
                return new ArrayList<>();
            }
            return bluetoothTag;
        }
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rfidtags {
        private List<byte[]> rfidTag;

        public List<byte[]> getRfidTag() {
            if (rfidTag == null) {
                rfidTag = new ArrayList<>();
            }
            return rfidTag;
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WifiRssi {
        private List<MeasurementDto.WifiRssi.Ap> ap;

        public List<Ap> getAp() {
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
