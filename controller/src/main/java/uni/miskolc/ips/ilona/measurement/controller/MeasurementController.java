package uni.miskolc.ips.ilona.measurement.controller;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import uni.miskolc.ips.ilona.measurement.controller.dto.*;
import uni.miskolc.ips.ilona.measurement.model.measurement.*;
import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.service.MeasurementService;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.InconsistentMeasurementException;
import uni.miskolc.ips.ilona.measurement.service.exception.TimeStampNotFoundException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * @author bogdandy, tothzs
 */
@Controller
public class MeasurementController {

    /**
     * Logger.
     */
    private static final Logger LOG = LogManager.getLogger(MeasurementController.class);
    /**
     * Reads data from context.xml automatically.
     */

    private MeasurementService measurementManagerService;

    @Autowired
    public MeasurementController(MeasurementService measurementManagerService) {
        this.measurementManagerService = measurementManagerService;
    }

    @RequestMapping("/")
    public ModelAndView loadHomePage() {
        ModelAndView result = new ModelAndView("index");
        return result;
    }

    /**
     * Lists the available measurements based on the zoneID which is not
     * necessarily required.
     *
     * @param zoneId The zoneID of the list is based on
     * @return Returns the list of results
     */
    @RequestMapping("/resources/listMeasurements")
    @ResponseBody
    public List<MeasurementDTO> listMeasurements(@RequestParam(value = "zoneId", required = false) final UUID zoneId) throws DatatypeConfigurationException {
        List<MeasurementDTO> result = new ArrayList<MeasurementDTO>();
        try {
            Collection<Measurement> measurements = measurementManagerService.readMeasurements();
            for (Measurement measurement : measurements) {
                if (zoneId != null && measurement.getPosition().getZone().getId().equals(zoneId) == false) {
                    continue;
                }
                result.add(assembleMeasurementDTO(measurement));
            }
        } catch (DatabaseUnavailableException e) {
            // TODO Auto-generated catch block
            LOG.info(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }


    /**
     * Calls the measurement manager service to create a measurement.
     * It accepts only post requests.
     *
     * @param measurementRegistrationRequest measurement data
     * @return returns true if the operation is successful.
     */
    @RequestMapping(value = "/recordMeasurement", method = RequestMethod.POST)
    @ResponseBody
    public final boolean recordMeasurement(@RequestBody final MeasurementRegistrationRequest measurementRegistrationRequest) {
        Measurement measurement = new Measurement();
        measurement.setId(UUID.randomUUID());
        measurement.setTimestamp(new Date());
        measurement.setPosition(dispersePositionDTO(measurementRegistrationRequest.getPosition()));
        if (measurementRegistrationRequest.getWifiRSSI() != null) {
            Map<String, Double> wifiRssis = new HashMap<>();
            for (MeasurementRegistrationRequest.WifiRSSI.Ap ap : measurementRegistrationRequest.getWifiRSSI().getAp()) {
                wifiRssis.put(ap.getSsid(), ap.getValue());
            }
            measurement.setWifiRSSI(new WiFiRSSI(wifiRssis));
        }
        if (measurementRegistrationRequest.getMagnetometer() != null) {
            Magnetometer magnetometer = new Magnetometer(
                    measurementRegistrationRequest.getMagnetometer().getXAxis(),
                    measurementRegistrationRequest.getMagnetometer().getYAxis(),
                    measurementRegistrationRequest.getMagnetometer().getZAxis(),
                    measurementRegistrationRequest.getMagnetometer().getRadian());
            measurement.setMagnetometer(magnetometer);
        }
        if (measurementRegistrationRequest.getBluetoothTags() != null) {
            BluetoothTags bluetoothTags = new BluetoothTags(
                    new HashSet<String>(
                            measurementRegistrationRequest.getBluetoothTags().getBluetoothTag()
                    )
            );
            measurement.setBluetoothTags(bluetoothTags);

        }
        if (measurementRegistrationRequest.getGpsCoordinates() != null) {
            GPSCoordinate gpsCoordinate = new GPSCoordinate(
                    measurementRegistrationRequest.getGpsCoordinates().getLatitude(),
                    measurementRegistrationRequest.getGpsCoordinates().getLongitude(),
                    measurementRegistrationRequest.getGpsCoordinates().getAltitude()
            );
            measurement.setGpsCoordinates(gpsCoordinate);
        }
        if (measurementRegistrationRequest.getRfidtags() != null) {
            measurement.setRfidtags(new RFIDTags(new HashSet<byte[]>(measurementRegistrationRequest.getRfidtags().getRfidTag())));
        }
        try {
            this.measurementManagerService.recordMeasurement(measurement);
        } catch (DatabaseUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InconsistentMeasurementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    /**
     * It calls the measurement manager service to delete the measurement with
     * the given timestamp.
     *
     * @param timestamp The timestamp the deletion is based on
     * @return It returns true if the operation was successful. Otherwise it
     * throws exception.
     */
    @RequestMapping("/deleteMeasurement")
    @ResponseBody
    public final boolean deleteMeasurement(@RequestParam("timestamp") final long timestamp) {
        try {
            measurementManagerService.deleteMeasurement(new Date(timestamp));
        } catch (DatabaseUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TimeStampNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    private MeasurementDTO assembleMeasurementDTO(Measurement measurement) throws DatatypeConfigurationException {
        GregorianCalendar calendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        MeasurementDTO dto = new MeasurementDTO();
        dto.setId(measurement.getId().toString());
        calendar.setTime(measurement.getTimestamp());
        dto.setTimestamp(datatypeFactory.newXMLGregorianCalendar(calendar));
        dto.setPosition(assemblePositionDTO(measurement.getPosition()));

        if (measurement.getWifiRSSI() != null) {
            MeasurementDTO.WifiRSSI wifiRSSI = new MeasurementDTO.WifiRSSI();
            List<MeasurementDTO.WifiRSSI.Ap> aps = wifiRSSI.getAp();
            for (Map.Entry<String, Double> wifiRSSIEntry : measurement.getWifiRSSI().getRssiValues().entrySet()) {
                MeasurementDTO.WifiRSSI.Ap ap = new MeasurementDTO.WifiRSSI.Ap();
                ap.setSsid(wifiRSSIEntry.getKey());
                ap.setValue(wifiRSSIEntry.getValue());
                aps.add(ap);
            }

            dto.setWifiRSSI(wifiRSSI);
        }
        if (measurement.getMagnetometer() != null) {
            MeasurementDTO.Magnetometer magnetometer = new MeasurementDTO.Magnetometer();
            magnetometer.setXAxis(measurement.getMagnetometer().getxAxis());
            magnetometer.setYAxis(measurement.getMagnetometer().getyAxis());
            magnetometer.setZAxis(measurement.getMagnetometer().getzAxis());
            magnetometer.setRadian(measurement.getMagnetometer().getRadian());
            dto.setMagnetometer(magnetometer);
        }
        if (measurement.getBluetoothTags() != null) {
            MeasurementDTO.BluetoothTags bluetoothTags = new MeasurementDTO.BluetoothTags();
            bluetoothTags.getBluetoothTag().addAll(measurement.getBluetoothTags().getTags());
            dto.setBluetoothTags(bluetoothTags);
        }
        if (measurement.getGpsCoordinates() != null) {
            MeasurementDTO.GpsCoordinates gpsCoordinates = new MeasurementDTO.GpsCoordinates();
            gpsCoordinates.setLatitude(measurement.getGpsCoordinates().getLatitude());
            gpsCoordinates.setLongitude(measurement.getGpsCoordinates().getLongitude());
            gpsCoordinates.setAltitude(measurement.getGpsCoordinates().getAltitude());
            dto.setGpsCoordinates(gpsCoordinates);
        }
        if (measurement.getRfidtags() != null) {
            MeasurementDTO.Rfidtags rfidtags = new MeasurementDTO.Rfidtags();
            rfidtags.getRfidTag().addAll(measurement.getRfidtags().getTags());
            dto.setRfidtags(rfidtags);
        }

        return dto;
    }

    private PositionDTO assemblePositionDTO(Position position) {
        PositionDTO result = new PositionDTO();
        result.setId(position.getUUID().toString());
        ZoneDTO zone = new ZoneDTO();
        zone.setId(position.getZone().getId().toString());
        zone.setName(position.getZone().getName());
        result.setZone(zone);
        CoordinateDTO coordinate = new CoordinateDTO();
        coordinate.setX(position.getCoordinate().getX());
        coordinate.setY(position.getCoordinate().getY());
        coordinate.setZ(position.getCoordinate().getZ());
        result.setCoordinate(coordinate);
        return result;
    }

    private Position dispersePositionDTO(MeasurementRegistrationRequest.Position dto) {
        Coordinate coordinate = null;
        Zone zone = null;
        if(dto.getCoordinate() != null){
            coordinate = new Coordinate(dto.getCoordinate().getX(), dto.getCoordinate().getY(), dto.getCoordinate().getZ());
        }
        if(dto.getZone() != null) {
            zone = new Zone(dto.getZone().getName());
            zone.setId(UUID.fromString(dto.getZone().getId()));
        }
        return new Position(coordinate,zone);
    }
}
