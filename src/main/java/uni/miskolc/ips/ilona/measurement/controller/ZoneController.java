package uni.miskolc.ips.ilona.measurement.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.miskolc.ips.ilona.measurement.controller.dto.ZoneDTO;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.service.ZoneService;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.ZoneNotFoundException;

/** @author bogdandy, tothzs */
@RequiredArgsConstructor
@Controller
public class ZoneController {
  /** */
  private final ZoneService zoneManagerService;

  /** @return Returns the list of zones. */
  @RequestMapping(value = {"/listZones", "/resource/zones"})
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
  @RequestMapping("/addZone")
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
  @RequestMapping("/deleteZone")
  @ResponseBody
  public void deleteZone(@RequestParam("id") final String id)
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
  @RequestMapping(value = "/zones/{id}", method = RequestMethod.GET)
  public @ResponseBody final ZoneDTO getZone(@PathVariable("id") final String id)
      throws ZoneNotFoundException, DatabaseUnavailableException {
    UUID uuid = UUID.fromString(id);
    ZoneDTO result = new ZoneDTO();

    Zone zone;
    zone = zoneManagerService.getZone(uuid);

    result.setId(zone.getId().toString());
    result.setName(zone.getName());

    return result;
  }

  /**
   * Loads zonemanagement.jps page.
   *
   * @return Returns the results of the getZones() method
   */
  @RequestMapping("/zoneManagement")
  public final ModelAndView zoneManagementPage() {
    ModelAndView result = new ModelAndView("zoneManagement");
    try {
      result.addObject("zones", zoneManagerService.getZones());
    } catch (DatabaseUnavailableException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

  @ResponseStatus(value = HttpStatus.CONFLICT)
  @ExceptionHandler(ZoneNotFoundException.class)
  public String zoneNotFoundExceptionHandler(Exception ex) {
    return ex.getMessage();
  }
}
