package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class MeasurementRegistrationRequest {
    protected MeasurementRegistrationRequest.Position position;
    protected MeasurementRegistrationRequest.WifiRSSI wifiRSSI;
    protected MeasurementRegistrationRequest.Magnetometer magnetometer;
    protected MeasurementRegistrationRequest.BluetoothTags bluetoothTags;
    protected MeasurementRegistrationRequest.GpsCoordinates gpsCoordinates;
    protected MeasurementRegistrationRequest.Rfidtags rfidtags;

    @Getter
    @NoArgsConstructor
    public static class BluetoothTags {
        protected List<String> bluetoothTag;
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

    @Getter @Setter
    @NoArgsConstructor
    public static class Position {
        protected ZoneDTO zone;
        protected CoordinateDTO coordinate;
    }

    @Getter
    @NoArgsConstructor
    public static class Rfidtags {
        protected List<byte[]> rfidTag;
    }

   @Getter
    @NoArgsConstructor
    public static class WifiRSSI {
       protected List<MeasurementRegistrationRequest.WifiRSSI.Ap> ap;


       @Getter
       @Setter
       @NoArgsConstructor
       public static class Ap {
           protected double value;
           protected String ssid;
       }
   }
}
