package uni.miskolc.ips.ilona.measurement.persist.mysql;

import org.springframework.data.repository.CrudRepository;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.PositionEntity;

public interface PositionRepository extends CrudRepository<PositionEntity, String> {
}
