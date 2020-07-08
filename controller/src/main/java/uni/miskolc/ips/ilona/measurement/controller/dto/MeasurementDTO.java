package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class MeasurementDTO {
    protected String id;
    protected XMLGregorianCalendar timestamp;
    protected PositionDTO position;
    protected MeasurementDTO.WifiRSSI wifiRSSI;
    protected MeasurementDTO.Magnetometer magnetometer;
    protected MeasurementDTO.BluetoothTags bluetoothTags;
    protected MeasurementDTO.GpsCoordinates gpsCoordinates;
    protected MeasurementDTO.Rfidtags rfidtags;


    @NoArgsConstructor
    public static class BluetoothTags {
        protected List<String> bluetoothTag;

        public List<String> getBluetoothTag() {
            if (bluetoothTag == null) {
                return new ArrayList<>();
            }
            return bluetoothTag;
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class GpsCoordinates{
        protected double latitude;
        protected double longitude;
        protected double altitude;
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Magnetometer {
        protected double xAxis;
        protected double yAxis;
        protected double zAxis;
        protected double radian;
    }

    @NoArgsConstructor
    public static class Rfidtags {
        protected List<byte[]> rfidTag;

        public List<byte[]> getRfidTag() {
            if (rfidTag == null) {
                rfidTag = new ArrayList<>();
            }
            return rfidTag;
        }
    }

    @NoArgsConstructor
    public static class WifiRSSI {
        protected List<MeasurementDTO.WifiRSSI.Ap> ap;

        public List<Ap> getAp() {
            if (ap == null) {
                ap = new ArrayList<>();
            }
            return ap;
        }

        @Getter @Setter
        @NoArgsConstructor
        public static class Ap {
            protected double value;
            protected String ssid;
        }
    }
}