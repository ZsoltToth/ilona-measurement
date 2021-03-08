package uni.miskolc.ips.ilona.measurement.persist.mysql;

import org.springframework.data.repository.CrudRepository;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.MeasurementEntity;

import java.util.Date;
import java.util.Optional;

public interface MeasurementRepository extends CrudRepository<MeasurementEntity, String> {
    Optional<MeasurementEntity> findAllByMeasTimestamp(Date timestamp);

    void deleteAllByMeasTimestamp(Date timestamp);
}
