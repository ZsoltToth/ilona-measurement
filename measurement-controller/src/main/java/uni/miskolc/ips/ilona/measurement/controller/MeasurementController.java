package uni.miskolc.ips.ilona.measurement.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.service.MeasurementService;
import uni.miskolc.ips.ilona.measurement.service.exception.DatabaseUnavailableException;
import uni.miskolc.ips.ilona.measurement.service.exception.InconsistentMeasurementException;
import uni.miskolc.ips.ilona.measurement.service.exception.TimeStampNotFoundException;

/**
 * 
 * @author bogdandy
 *
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
	@Autowired
	private MeasurementService measurementManagerService;

	/**
	 * Lists the available measurements based on the zoneID which is not
	 * necessarily required.
	 * 
	 * @param zoneId
	 *            The zoneID of the list is based on
	 * @return Returns the list of results
	 */
	@RequestMapping("/resources/listMeasurements")
	@ResponseBody
	public List<Measurement> listMeasurements(@RequestParam(value = "zoneId", required = false) final UUID zoneId) {
		List<Measurement> result = new ArrayList<Measurement>();
		try {
			result = new ArrayList<Measurement>(measurementManagerService.readMeasurements());
		} catch (DatabaseUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * It loads the measurement.jsp view.
	 * 
	 * @return return loads the measurement.jsp page
	 */
	@RequestMapping("/measurementManagement")
	public final ModelAndView loadMeasurementManagementPage() {
		ModelAndView result = new ModelAndView("measurements");
		Set<String> aps = new HashSet<String>();
		Collection<Measurement> measurements = null;
		try {
			measurements = measurementManagerService.readMeasurements();
		} catch (DatabaseUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// for(Measurement meas : measurements){
		// aps.addAll(meas.getValues().keySet());
		// }
		result.addObject("accessPoints", new ArrayList<String>(aps));
		result.addObject("measurements", measurements);
		return result;
	}

	/**
	 * Calls the measurement manager service to create a measurement.
	 * It accepts only post requests.
	 * 
	 * @param meas measurement data
	 * @return returns true if the operation is successful.
	 */
	@RequestMapping(value = "/recordMeasurement", method = RequestMethod.POST)
	@ResponseBody
	public final boolean recordMeasurement(@RequestBody final Measurement meas) {
		LOG.info(meas);

		try {
			this.measurementManagerService.recordMeasurement(meas);
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
	 * 
	 * @param timestamp
	 *            The timestamp the deletion is based on
	 * @return It returns true if the operation was successful. Otherwise it
	 *         throws exception.
	 */
	@RequestMapping("/deleteMeasurement")
	@ResponseBody
	public final boolean deleteMeasurement(@RequestParam("timestamp") final long timestamp) {
		// System.out.println(timestamp);
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

	// @RequestMapping("/getLocation")
	// @ResponseBody
	// public Zone getLocation(
	// @ModelAttribute("values") RSSMapWrapper rssMapWrapper) {
	// Zone result = null;
	// Map<String, Double> rss = rssMapWrapper.getValues();
	// System.out.println("getLocation");
	// System.out.println("Received Values");
	// if (rss != null) {
	// for (String key : rss.keySet()) {
	// System.out.println(key + ": " + rss.get(key));
	// }
	// } else {
	// System.out.println("Parameter is null!");
	// }
	// System.out.println("Positioning is Done!");
	// result = positioningService.determinePosition(rss);
	// if (result == null) {
	// result = new Zone("Unknown Position");
	// }
	// return result;
	// }
/**
 * 
 * @author bogdandy
 *
 */
	public static class RSSMapWrapper {
/**
 * 
 */
		private Map<String, Double> values;
/**
 * 
 * @return Returns the values of the getValues method
 */
		public final Map<String, Double> getValues() {
			return values;
		}
/**
 * 
 * @param values Sets the values the values with the setValues method
 */
		public final void setValues(final Map<String, Double> values) {
			this.values = values;
		}
	}
}
