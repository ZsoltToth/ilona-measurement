package uni.miskolc.ips.ilona.measurement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uni.miskolc.ips.ilona.measurement.controller.dto.CoordinateDTO;
import uni.miskolc.ips.ilona.measurement.controller.dto.MeasurementDTO;
import uni.miskolc.ips.ilona.measurement.controller.dto.MeasurementRegistrationRequest;
import uni.miskolc.ips.ilona.measurement.controller.dto.PositionDTO;
import uni.miskolc.ips.ilona.measurement.controller.dto.ZoneDTO;
import uni.miskolc.ips.ilona.measurement.model.measurement.BluetoothTags;
import uni.miskolc.ips.ilona.measurement.model.measurement.GPSCoordinate;
import uni.miskolc.ips.ilona.measurement.model.measurement.Magnetometer;
import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.model.measurement.RFIDTags;
import uni.miskolc.ips.ilona.measurement.model.measurement.WiFiRSSI;
import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.service.MeasurementService;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.InconsistentMeasurementException;
import uni.miskolc.ips.ilona.measurement.service.exception.TimeStampNotFoundException;
import uni.miskolc.ips.ilona.measurement.service.exception.ZoneNotFoundException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/measurements")
@RequiredArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementManagerService;

    @GetMapping(value = {"", "/"})
    public List<MeasurementDTO> listMeasurements(@RequestParam(value = "zoneId", required = false) UUID zoneId) {
        try {
            List<MeasurementDTO> result = new ArrayList<>();
            Collection<Measurement> measurements = measurementManagerService.readMeasurements();
            for (Measurement measurement : measurements) {
                if (zoneId != null && !measurement.getPosition().getZone().getId().equals(zoneId)) {
                    continue;
                }
                result.add(assembleMeasurementDTO(measurement));
            }
            return result;
        } catch (DatatypeConfigurationException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
        } catch (DatabaseUnavailableException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        }
    }

    @PostMapping(value = "/")
    public void recordMeasurement(@RequestBody MeasurementRegistrationRequest measurementRegistrationRequest) {
        try {
            Measurement measurement = new Measurement();
            measurement.setId(UUID.randomUUID());
            measurement.setTimestamp(new Date());
            measurement.setPosition(dispersePositionDTO(measurementRegistrationRequest.getPosition()));
            if (measurementRegistrationRequest.getWifiRSSI() != null) {
                Map<String, Double> wifiRssis = new HashMap<>();
                for (MeasurementRegistrationRequest.WifiRSSI.Ap ap :
                        measurementRegistrationRequest.getWifiRSSI().getAp()) {
                    wifiRssis.put(ap.getSsid(), ap.getValue());
                }
                measurement.setWifiRSSI(new WiFiRSSI(wifiRssis));
            }
            if (measurementRegistrationRequest.getMagnetometer() != null) {
                Magnetometer magnetometer = new Magnetometer(
                        measurementRegistrationRequest.getMagnetometer().getXAxis(),
                        measurementRegistrationRequest.getMagnetometer().getYAxis(),
                        measurementRegistrationRequest.getMagnetometer().getZAxis(),
                        measurementRegistrationRequest.getMagnetometer().getRadian()
                );
                measurement.setMagnetometer(magnetometer);
            }
            if (measurementRegistrationRequest.getBluetoothTags() != null) {
                BluetoothTags bluetoothTags = new BluetoothTags(new HashSet<>(
                        measurementRegistrationRequest.getBluetoothTags().getBluetoothTag())
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
                RFIDTags rfidTags = new RFIDTags(
                        new HashSet<>(measurementRegistrationRequest.getRfidtags().getRfidTag())
                );
                measurement.setRfidtags(rfidTags);
            }
            this.measurementManagerService.recordMeasurement(measurement);
        } catch (InconsistentMeasurementException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
        } catch (DatabaseUnavailableException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        }
    }

    @DeleteMapping("/")
    public void deleteMeasurement(@RequestParam("timestamp") long timestamp) {
        try {
            measurementManagerService.deleteMeasurement(new Date(timestamp));
        } catch (TimeStampNotFoundException | ZoneNotFoundException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DatabaseUnavailableException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        }
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
            MeasurementDTO.Rfidtags rfidTags = new MeasurementDTO.Rfidtags();
            rfidTags.getRfidTag().addAll(measurement.getRfidtags().getTags());
            dto.setRfidtags(rfidTags);
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
        if (dto.getCoordinate() != null) {
            coordinate = new Coordinate(
                    dto.getCoordinate().getX(), dto.getCoordinate().getY(), dto.getCoordinate().getZ()
            );
        }
        if (dto.getZone() != null) {
            zone = new Zone(dto.getZone().getName());
            zone.setId(UUID.fromString(dto.getZone().getId()));
        }
        return new Position(coordinate, zone);
    }
}
