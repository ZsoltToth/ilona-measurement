package uni.miskolc.ips.ilona.measurement.persist.mysql;

import org.springframework.data.repository.CrudRepository;
import uni.miskolc.ips.ilona.measurement.persist.mysql.entity.ZoneEntity;

import java.util.Collection;

public interface ZoneRepository extends CrudRepository<ZoneEntity, String> {
    Collection<ZoneEntity> findAllByName(String name);
}
