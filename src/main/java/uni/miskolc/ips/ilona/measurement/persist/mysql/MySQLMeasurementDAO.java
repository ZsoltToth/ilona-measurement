package uni.miskolc.ips.ilona.measurement.persist.mysql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.measurement.persist.MeasurementDAO;
import uni.miskolc.ips.ilona.measurement.persist.PositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MySQLMeasurementDAO implements MeasurementDAO {
  private final PositionDAO positionDAO;

  @Override
  public final void createMeasurement(final Measurement measurement) throws InsertionException {}

  @Override
  public final Collection<Measurement> readMeasurements() {
    return new ArrayList<>();
  }

  @Override
  public Measurement readMeasurement(UUID uuid) {
    return null;
  }

  @Override
  public final void updateMeasurement(final Measurement measurement)
      throws InsertionException, RecordNotFoundException {}

  @Override
  public final void deleteMeasurement(final Date timestamp) throws RecordNotFoundException {}

  @Override
  public final void deleteMeasurement(final Measurement measurement)
      throws RecordNotFoundException {}
}
