package uni.miskolc.ips.ilona.measurement.controller;

import java.util.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
import uni.miskolc.ips.ilona.measurement.service.exception.ZoneNotFoundException;

/** @author bogdandy, tothzs */
@RequiredArgsConstructor
@Controller
public class MeasurementController {

  /** Logger. */
  private static final Logger LOG = LogManager.getLogger(MeasurementController.class);
  /** Reads data from context.xml automatically. */
  private final MeasurementService measurementManagerService;

  @RequestMapping("/")
  public ModelAndView loadHomePage() {
    return new ModelAndView("index");
  }

  /**
   * Lists the available measurements based on the zoneID which is not necessarily required.
   *
   * @param zoneId The zoneID of the list is based on
   * @return Returns the list of results
   */
  @RequestMapping("/resources/listMeasurements")
  @ResponseBody
  public List<MeasurementDTO> listMeasurements(
      @RequestParam(value = "zoneId", required = false) final UUID zoneId)
      throws DatatypeConfigurationException, DatabaseUnavailableException {
    List<MeasurementDTO> result = new ArrayList<>();
    Collection<Measurement> measurements = measurementManagerService.readMeasurements();
    for (Measurement measurement : measurements) {
      if (zoneId != null && !measurement.getPosition().getZone().getId().equals(zoneId)) {
        continue;
      }
      result.add(assembleMeasurementDTO(measurement));
    }
    return result;
  }

  /**
   * Calls the measurement manager service to create a measurement. It accepts only post requests.
   *
   * @param measurementRegistrationRequest measurement data
   */
  @RequestMapping(value = "/recordMeasurement", method = RequestMethod.POST)
  @ResponseBody
  public void recordMeasurement(
      @RequestBody final MeasurementRegistrationRequest measurementRegistrationRequest)
      throws InconsistentMeasurementException, DatabaseUnavailableException {
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
      Magnetometer magnetometer =
          new Magnetometer(
              measurementRegistrationRequest.getMagnetometer().getXAxis(),
              measurementRegistrationRequest.getMagnetometer().getYAxis(),
              measurementRegistrationRequest.getMagnetometer().getZAxis(),
              measurementRegistrationRequest.getMagnetometer().getRadian());
      measurement.setMagnetometer(magnetometer);
    }
    if (measurementRegistrationRequest.getBluetoothTags() != null) {
      BluetoothTags bluetoothTags =
          new BluetoothTags(
              new HashSet<String>(
                  measurementRegistrationRequest.getBluetoothTags().getBluetoothTag()));
      measurement.setBluetoothTags(bluetoothTags);
    }
    if (measurementRegistrationRequest.getGpsCoordinates() != null) {
      GPSCoordinate gpsCoordinate =
          new GPSCoordinate(
              measurementRegistrationRequest.getGpsCoordinates().getLatitude(),
              measurementRegistrationRequest.getGpsCoordinates().getLongitude(),
              measurementRegistrationRequest.getGpsCoordinates().getAltitude());
      measurement.setGpsCoordinates(gpsCoordinate);
    }
    if (measurementRegistrationRequest.getRfidtags() != null) {
      RFIDTags rfidTags =
          new RFIDTags(
              new HashSet<byte[]>(measurementRegistrationRequest.getRfidtags().getRfidTag()));
      measurement.setRfidtags(rfidTags);
    }
    this.measurementManagerService.recordMeasurement(measurement);
  }

  /**
   * It calls the measurement manager service to delete the measurement with the given timestamp.
   *
   * @param timestamp The timestamp the deletion is based on
   * @return It returns true if the operation was successful. Otherwise it throws exception.
   */
  @RequestMapping("/deleteMeasurement")
  @ResponseBody
  public void deleteMeasurement(@RequestParam("timestamp") final long timestamp)
      throws TimeStampNotFoundException, DatabaseUnavailableException, ZoneNotFoundException {
    measurementManagerService.deleteMeasurement(new Date(timestamp));
  }

  private MeasurementDTO assembleMeasurementDTO(Measurement measurement)
      throws DatatypeConfigurationException {
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
      for (Map.Entry<String, Double> wifiRSSIEntry :
          measurement.getWifiRSSI().getRssiValues().entrySet()) {
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
    if (dto.getCoordinate() != null) {
      coordinate =
          new Coordinate(
              dto.getCoordinate().getX(), dto.getCoordinate().getY(), dto.getCoordinate().getZ());
    }
    if (dto.getZone() != null) {
      zone = new Zone(dto.getZone().getName());
      zone.setId(UUID.fromString(dto.getZone().getId()));
    }
    return new Position(coordinate, zone);
  }

  @ResponseStatus(
      value = HttpStatus.UNSUPPORTED_MEDIA_TYPE,
      reason = "Measurement was Inconsistent.")
  @ExceptionHandler(InconsistentMeasurementException.class)
  public void inconsistentMeasurementExceptionHandler(Exception ex) {
    LOG.info(ex.getMessage());
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(TimeStampNotFoundException.class)
  public void timeStampNotFoundExceptionHandler(Exception ex) {
    LOG.info(ex.getMessage());
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(ZoneNotFoundException.class)
  public void zoneNotFoundExceptionHandler(Exception ex) {
    LOG.info(ex.getMessage());
  }
}
