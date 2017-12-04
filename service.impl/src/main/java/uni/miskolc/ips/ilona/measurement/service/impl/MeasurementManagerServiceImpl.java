package uni.miskolc.ips.ilona.measurement.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.MeasurementDAO;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.service.MeasurementService;

/**
 * The MeasurementManagerServiceImpl class is used to Manage Measurements.
 * 
 * @author tamas13
 *
 */
public class MeasurementManagerServiceImpl implements MeasurementService {
	/**
	 * MeasurementDAO provides an abstract interface to database of
	 * Measurements.
	 */
	private MeasurementDAO measurementDAO;

	/**
	 * The constructor of MeasurementManagerServiceImpl with the measurementDAO
	 * parameter.
	 * 
	 * @param measurementDAO
	 *            provides an abstract interface to database of Measurements.
	 */
	public MeasurementManagerServiceImpl(final MeasurementDAO measurementDAO) {
		super();
		this.measurementDAO = measurementDAO;
	}

	/**
	 * The recordMeasurement method insert the given measurement into the
	 * database.
	 * 
	 * @param measurement
	 *            is the given measurement which should be recorded.
	 */
	public final void recordMeasurement(final Measurement measurement) {
		try {
			this.measurementDAO.createMeasurement(measurement);
		} catch (InsertionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The readMeasurements method get the Measurements from the database.
	 * 
	 * @return with the collection of the Measurement read from the database.
	 */
	public final Collection<Measurement> readMeasurements() {
		return this.measurementDAO.readMeasurements();
	}

	/**
	 * The readMeasurements method get the Measurements from the database, which
	 * fulfills the criterion that the Measurement had been taken in the given
	 * zone.
	 * 
	 * @param zone
	 *            is the selection criterion.
	 * @return with the collection of Measurements that fulfills the criterion
	 */
	public final Collection<Measurement> readMeasurements(final Zone zone) {
		Collection<Measurement> wholelist = this.measurementDAO
				.readMeasurements();

		Collection<Measurement> resultlist = new ArrayList<Measurement>();

		for (Measurement m : wholelist) {
			if (m.getPosition().getZone().equals(zone)) {
				resultlist.add(m);
			}
		}

		return resultlist;
	}

	/**
	 * The readMeasurements method get the Measurements from the database, which
	 * fulfills the criterion that the Measurement had been taken in the given
	 * position.
	 * 
	 * @param position
	 *            is the selection criterion.
	 * @return with the collection of Measurements that fulfills the criterion
	 */
	public final Collection<Measurement> readMeasurements(
			final Position position) {
		Collection<Measurement> wholelist = this.measurementDAO
				.readMeasurements();

		Collection<Measurement> resultlist = new ArrayList<Measurement>();

		for (Measurement m : wholelist) {
			if (m.getPosition().equals(position)) {
				resultlist.add(m);
			}
		}
		return resultlist;
	}
	/**
	 * The deleteMeasurement method delete the Measurements from the database, which
	 * fulfills the criterion that the Measurement had been taken at the time stamp.
	 * @param timestamp is the date which Measurement need to be deleted.
	 */
	public final void deleteMeasurement(final Date timestamp) {
		try {
			this.measurementDAO.deleteMeasurement(timestamp);
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
