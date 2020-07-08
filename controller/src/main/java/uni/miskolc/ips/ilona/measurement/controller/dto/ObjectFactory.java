package uni.miskolc.ips.ilona.measurement.controller.dto;

import lombok.*;

@NoArgsConstructor
public class ObjectFactory {

    /**
     * Create an instance of {@link MeasurementRegistrationRequest }
     *
     */
    public MeasurementRegistrationRequest createMeasurementRegistrationRequest() {
        return new MeasurementRegistrationRequest();
    }

    /**
     * Create an instance of {@link MeasurementRegistrationRequest.WifiRSSI }
     *
     */
    public MeasurementRegistrationRequest.WifiRSSI createMeasurementRegistrationRequestWifiRSSI() {
        return new MeasurementRegistrationRequest.WifiRSSI();
    }

    /**
     * Create an instance of {@link MeasurementDTO }
     *
     */
    public MeasurementDTO createMeasurementDTO() {
        return new MeasurementDTO();
    }

    /**
     * Create an instance of {@link MeasurementDTO.WifiRSSI }
     *
     */
    public MeasurementDTO.WifiRSSI createMeasurementDTOWifiRSSI() {
        return new MeasurementDTO.WifiRSSI();
    }

    /**
     * Create an instance of {@link ZoneDTO }
     *
     */
    public ZoneDTO createZoneDTO() {
        return new ZoneDTO();
    }

    /**
     * Create an instance of {@link CoordinateDTO }
     *
     */
    public CoordinateDTO createCoordinateDTO() {
        return new CoordinateDTO();
    }

    /**
     * Create an instance of {@link PositionDTO }
     *
     */
    public PositionDTO createPositionDTO() {
        return new PositionDTO();
    }

    /**
     * Create an instance of {@link MeasurementRegistrationRequest.Position }
     *
     */
    public MeasurementRegistrationRequest.Position createMeasurementRegistrationRequestPosition() {
        return new MeasurementRegistrationRequest.Position();
    }

    /**
     * Create an instance of {@link MeasurementRegistrationRequest.Magnetometer }
     *
     */
    public MeasurementRegistrationRequest.Magnetometer createMeasurementRegistrationRequestMagnetometer() {
        return new MeasurementRegistrationRequest.Magnetometer();
    }

    /**
     * Create an instance of {@link MeasurementRegistrationRequest.BluetoothTags }
     *
     */
    public MeasurementRegistrationRequest.BluetoothTags createMeasurementRegistrationRequestBluetoothTags() {
        return new MeasurementRegistrationRequest.BluetoothTags();
    }

    /**
     * Create an instance of {@link MeasurementRegistrationRequest.GpsCoordinates }
     *
     */
    public MeasurementRegistrationRequest.GpsCoordinates createMeasurementRegistrationRequestGpsCoordinates() {
        return new MeasurementRegistrationRequest.GpsCoordinates();
    }

    /**
     * Create an instance of {@link MeasurementRegistrationRequest.Rfidtags }
     *
     */
    public MeasurementRegistrationRequest.Rfidtags createMeasurementRegistrationRequestRfidtags() {
        return new MeasurementRegistrationRequest.Rfidtags();
    }

    /**
     * Create an instance of {@link MeasurementRegistrationRequest.WifiRSSI.Ap }
     *
     */
    public MeasurementRegistrationRequest.WifiRSSI.Ap createMeasurementRegistrationRequestWifiRSSIAp() {
        return new MeasurementRegistrationRequest.WifiRSSI.Ap();
    }

    /**
     * Create an instance of {@link MeasurementDTO.Magnetometer }
     *
     */
    public MeasurementDTO.Magnetometer createMeasurementDTOMagnetometer() {
        return new MeasurementDTO.Magnetometer();
    }

    /**
     * Create an instance of {@link MeasurementDTO.BluetoothTags }
     *
     */
    public MeasurementDTO.BluetoothTags createMeasurementDTOBluetoothTags() {
        return new MeasurementDTO.BluetoothTags();
    }

    /**
     * Create an instance of {@link MeasurementDTO.GpsCoordinates }
     *
     */
    public MeasurementDTO.GpsCoordinates createMeasurementDTOGpsCoordinates() {
        return new MeasurementDTO.GpsCoordinates();
    }

    /**
     * Create an instance of {@link MeasurementDTO.Rfidtags }
     *
     */
    public MeasurementDTO.Rfidtags createMeasurementDTORfidtags() {
        return new MeasurementDTO.Rfidtags();
    }

    /**
     * Create an instance of {@link MeasurementDTO.WifiRSSI.Ap }
     *
     */
    public MeasurementDTO.WifiRSSI.Ap createMeasurementDTOWifiRSSIAp() {
        return new MeasurementDTO.WifiRSSI.Ap();
    }


}
