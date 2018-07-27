package uni.miskolc.ips.ilona.measurement.controller;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.miskolc.ips.ilona.measurement.controller.dto.ZoneDTO;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.service.ZoneService;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.ZoneNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * @author bogdandy
 */
@Controller
public class ZoneController {
    /**
     *
     */

    private static final Logger LOG = org.apache.logging.log4j.LogManager.getLogger(ZoneController.class);

    private ZoneService zoneManagerService;

    @Autowired
    public ZoneController(ZoneService zoneManagerService) {
        this.zoneManagerService = zoneManagerService;
    }

    /**
     * @return Returns the list of zones.
     */
    @RequestMapping(value = {"/listZones", "/resource/zones"})
    public @ResponseBody
    final Collection<ZoneDTO> listZones() throws DatabaseUnavailableException {
        Collection<ZoneDTO> result = new ArrayList<>();
        for (Zone zone : this.zoneManagerService.getZones()) {
            ZoneDTO dto = new ZoneDTO();
            dto.setId(zone.getId().toString());
            dto.setName(zone.getName());
            result.add(dto);
        }
        LOG.info("Zones listed from ZoneController");
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
        LOG.info("Zone created: " + name);
    }

    /**
     * Deletes a zone based on the zone ID.
     *
     * @param id The ID of the zone that needs to be deleted
     */
    @RequestMapping("/deleteZone")
    @ResponseBody
    public void deleteZone(@RequestParam("id") final String id) throws ZoneNotFoundException, DatabaseUnavailableException {
        UUID uuid = UUID.fromString(id);
        Zone zone = new Zone();
        zone.setId(uuid);
        this.zoneManagerService.deleteZone(zone);
        LOG.info("Zone Deleted:" + id);
    }


    /**
     * Retrieves a zone based on the zone ID.
     *
     * @param id The ID of the zone that needs to be retrieved
     * @return Returns the Zone if successful.
     */

    @RequestMapping(value = "/zones/{id}", method = RequestMethod.GET)
    public @ResponseBody
    final ZoneDTO getZone(@PathVariable("id") final String id) throws ZoneNotFoundException, DatabaseUnavailableException {
        UUID uuid = UUID.fromString(id);
        ZoneDTO result = new ZoneDTO();

        Zone zone;
        zone = zoneManagerService.getZone(uuid);

        result.setId(zone.getId().toString());
        result.setName(zone.getName());
        LOG.info("Zone requested:" + id);
        return result;
    }

    /**
     * Loads  zonemanagement.jps page.
     *
     * @return Returns the results of the getZones() method
     */
    @RequestMapping("/zoneManagement")
    public final ModelAndView zoneManagementPage() throws DatabaseUnavailableException {
        ModelAndView result = new ModelAndView("zoneManagement");
        result.addObject("zones", zoneManagerService.getZones());

        LOG.info("ZoneManagement page returned");
        return result;
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ZoneNotFoundException.class)
    public String zoneNotFoundExceptionHandler(Exception ex) {
        LOG.error("No zone found");
        return "No Zone found";
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(DatabaseUnavailableException.class)
    public String databaseUnavaiableExceptionHandler(Exception ex) {
        LOG.error("Database unavailable");
        return "Database unavailable for the moment";
    }
}
