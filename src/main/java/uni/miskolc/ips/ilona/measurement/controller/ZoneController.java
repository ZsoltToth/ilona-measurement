package uni.miskolc.ips.ilona.measurement.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import uni.miskolc.ips.ilona.measurement.controller.dto.ZoneDTO;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.service.ZoneService;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.ZoneNotFoundException;

@RequiredArgsConstructor
@Controller
public class ZoneController {
    private static final Logger LOG = LogManager.getLogger(ZoneController.class);
    private final ZoneService zoneManagerService;

    @GetMapping(value = {"/listZones", "/resource/zones"})
    @ResponseBody
    public Collection<ZoneDTO> listZones() {
        try {
            Collection<ZoneDTO> result = new ArrayList<>();
            for (Zone zone : this.zoneManagerService.getZones()) {
                ZoneDTO dto = new ZoneDTO();
                dto.setId(zone.getId().toString());
                dto.setName(zone.getName());
                result.add(dto);
            }
            return result;
        } catch (DatabaseUnavailableException e) {
            LOG.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        }
    }

    @PostMapping("/addZone")
    @ResponseBody
    public void addZone(@RequestParam("name") String name) {
        try {
            Zone zone = new Zone(name);
            zoneManagerService.createZone(zone);
        } catch (DatabaseUnavailableException e) {
            LOG.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        }
    }

    @DeleteMapping("/deleteZone")
    @ResponseBody
    public void deleteZone(@RequestParam("id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Zone zone = new Zone();
            zone.setId(uuid);
            this.zoneManagerService.deleteZone(zone);
        } catch (DatabaseUnavailableException e) {
            LOG.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        } catch (ZoneNotFoundException e) {
            LOG.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping(value = "/zones/{id}")
    @ResponseBody
    public ZoneDTO getZone(@PathVariable("id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            ZoneDTO result = new ZoneDTO();

            Zone zone;
            zone = zoneManagerService.getZone(uuid);

            result.setId(zone.getId().toString());
            result.setName(zone.getName());
            return result;
        } catch (DatabaseUnavailableException e) {
            LOG.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        } catch (ZoneNotFoundException e) {
            LOG.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/zoneManagement")
    public ModelAndView zoneManagementPage() {
        ModelAndView result = new ModelAndView("zoneManagement");
        try {
            result.addObject("zones", zoneManagerService.getZones());
        } catch (DatabaseUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
