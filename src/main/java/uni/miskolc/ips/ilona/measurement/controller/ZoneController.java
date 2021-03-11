package uni.miskolc.ips.ilona.measurement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uni.miskolc.ips.ilona.measurement.controller.dto.ZoneDto;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.service.ZoneService;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.ZoneNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/zones")
public class ZoneController {
    private final ZoneService zoneManagerService;

    @GetMapping(value = {"", "/"})
    public Collection<ZoneDto> listZones() {
        try {
            Collection<ZoneDto> result = new ArrayList<>();
            for (Zone zone : this.zoneManagerService.getZones()) {
                ZoneDto dto = new ZoneDto();
                dto.setId(zone.getId().toString());
                dto.setName(zone.getName());
                result.add(dto);
            }
            return result;
        } catch (DatabaseUnavailableException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        }
    }

    @PostMapping("/add")
    public void addZone(@RequestParam("name") String name) {
        try {

            Zone zone = new Zone(name);
            zoneManagerService.createZone(zone);
        } catch (DatabaseUnavailableException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable("id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Zone zone = new Zone();
            zone.setId(uuid);
            this.zoneManagerService.deleteZone(zone);
        } catch (DatabaseUnavailableException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        } catch (ZoneNotFoundException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ZoneDto getZone(@PathVariable("id") String id) {
        try {
            Zone zone = zoneManagerService.getZone(UUID.fromString(id));
            return ZoneDto.builder()
                    .id(zone.getId().toString())
                    .name(zone.getName())
                    .build();
        } catch (DatabaseUnavailableException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, e.getMessage());
        } catch (ZoneNotFoundException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
