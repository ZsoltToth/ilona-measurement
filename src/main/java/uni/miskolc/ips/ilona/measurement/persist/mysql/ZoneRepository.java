package uni.miskolc.ips.ilona.measurement.persist.mysql;

import org.springframework.data.repository.CrudRepository;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.ZoneEntity;

public interface ZoneRepository extends CrudRepository<ZoneEntity, String> {
}
