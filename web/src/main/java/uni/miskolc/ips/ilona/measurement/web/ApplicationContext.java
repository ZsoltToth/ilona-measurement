package uni.miskolc.ips.ilona.measurement.web;

import org.springframework.context.annotation.Bean;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLPositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLZoneDAO;

import java.io.FileNotFoundException;

public class ApplicationContext {


    /**
     *  TODO
     * @return
     */
    @Bean
    public MySQLZoneDAO zoneDAO() throws FileNotFoundException {

        ;
        MySQLZoneDAO mySQLZoneDAO= new MySQLZoneDAO(System.getProperty("${mybatisConfig}"),);
        return mySQLZoneDAO;
    }

    @Bean
    public MySQLPositionDAO positionDAO() {

    }
}
