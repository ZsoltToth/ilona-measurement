package uni.miskolc.ips.ilona.measurement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uni.miskolc.ips.ilona.measurement.controller.dto.ZoneDTO;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.service.ZoneService;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.ZoneNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/** @author bogdandy, tothzs */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/zones")
public class ZoneController {

  private final ZoneService zoneManagerService;

  /** @return Returns the list of zones. */
  @GetMapping(value = {"", "/"})
  public @ResponseBody final Collection<ZoneDTO> listZones() throws DatabaseUnavailableException {
    Collection<ZoneDTO> result = new ArrayList<>();
    for (Zone zone : this.zoneManagerService.getZones()) {
      ZoneDTO dto = new ZoneDTO();
      dto.setId(zone.getId().toString());
      dto.setName(zone.getName());
      result.add(dto);
    }
    return result;
  }

  /**
   * Adds a new zone to the list of zones.
   *
   * @param name The name of the new zone
   */
  @PostMapping("/add")
  @ResponseBody
  public void addZone(@RequestParam("name") final String name) throws DatabaseUnavailableException {
    Zone zone = new Zone(name);
    zoneManagerService.createZone(zone);
  }

  /**
   * Deletes a zone based on the zone ID.
   *
   * @param id The ID of the zone that needs to be deleted
   */
  @DeleteMapping("/{id}")
  @ResponseBody
  public void deleteZone(@PathVariable("id") final String id)
          throws ZoneNotFoundException, DatabaseUnavailableException {
    UUID uuid = UUID.fromString(id);
    Zone zone = new Zone();
    zone.setId(uuid);
    this.zoneManagerService.deleteZone(zone);
  }

  /**
   * Retrieves a zone based on the zone ID.
   *
   * @param id The ID of the zone that needs to be retrieved
   * @return Returns the Zone if successful.
   */
  @GetMapping(value = "/{id}")
  public @ResponseBody final ZoneDTO getZone(@PathVariable("id") final String id)
      throws ZoneNotFoundException, DatabaseUnavailableException {
    Zone zone = zoneManagerService.getZone(UUID.fromString(id));
    return ZoneDTO.builder()
            .id(zone.getId().toString())
            .name(zone.getName())
            .build();
  }

  @ResponseStatus(value = HttpStatus.CONFLICT)
  @ExceptionHandler(ZoneNotFoundException.class)
  public String zoneNotFoundExceptionHandler(Exception ex) {
    return ex.getMessage();
  }
}
