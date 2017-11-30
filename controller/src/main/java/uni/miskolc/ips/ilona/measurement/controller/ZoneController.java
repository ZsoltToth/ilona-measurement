package uni.miskolc.ips.ilona.measurement.controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import uni.miskolc.ips.ilona.measurement.controller.dto.ZoneDTO;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.service.ZoneService;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.ZoneNotFoundException;
/**
 * 
 * @author bogdandy
 *
 */
@Controller
public class ZoneController {
/**
 * 
 */

	private ZoneService zoneManagerService;

	@Autowired
	public ZoneController(ZoneService zoneManagerService) {
		this.zoneManagerService = zoneManagerService;
	}

	/**
 * 
 * @return Returns the list of zones.
 */
	@RequestMapping(value = { "/listZones", "/resource/zones" })
	public @ResponseBody
	final Collection<ZoneDTO> listZones() {
		Collection<ZoneDTO> result = new ArrayList<>();
		try {
			for(Zone zone : this.zoneManagerService.getZones()){
				ZoneDTO dto = new ZoneDTO();
				dto.setId(zone.getId().toString());
				dto.setName(zone.getName());
				result.add(dto);
			}
		} catch (DatabaseUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
/**
 * Adds a new zone to the list of zones.
 * @param name The name of the new zone
 * @return Returns true if successful, or an exception if not.
 */
	@RequestMapping("/addZone")
	public @ResponseBody boolean addZone(@RequestParam("name")final String name) {
		//TODO return with void
		Zone zone = new Zone(name);

		try {
			this.zoneManagerService.createZone(zone);
		} catch (DatabaseUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZoneNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
/**
 * Deletes a zone based on the zone ID.
 * @param id The ID of the zone that needs to be deleted
 * @return Returns true if successful, or exception if not.
 */
	@RequestMapping("/deleteZone")
	@ResponseBody
	public final boolean deleteZone(@RequestParam("id") final String id) { //TODO throw exception boolean -> void
		UUID uuid = UUID.fromString(id);

		Zone zone = new Zone();
		zone.setId(uuid);

		try {
			this.zoneManagerService.deleteZone(zone);
		} catch (DatabaseUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZoneNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}


	/**
	 * Retrieves a zone based on the zone ID.
	 * @param id The ID of the zone that needs to be retrieved
	 * @return Returns the Zone if successful.
	 */

	@RequestMapping(value = "/zones/{id}", method = RequestMethod.GET)
	public @ResponseBody
	final ZoneDTO getZone(@PathVariable("id") final String id) { //TODO throw excepton
		UUID uuid = UUID.fromString(id);
		ZoneDTO result = new ZoneDTO();
		try {
			Zone zone = zoneManagerService.getZone(uuid) != null? zoneManagerService.getZone(uuid) : Zone.UNKNOWN_POSITION;
			result.setId(zone.getId().toString());
			result.setName(zone.getName());
		} catch (DatabaseUnavailableException e) {
			e.printStackTrace();
		} catch (ZoneNotFoundException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
 * Loads  zonemanagement.jps page.
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

}
