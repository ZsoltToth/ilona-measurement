package uni.miskolc.ips.ilona.measurement.web;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLMeasurementDAO;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLPositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLZoneDAO;
import uni.miskolc.ips.ilona.measurement.service.impl.MeasurementManagerServiceImpl;
import uni.miskolc.ips.ilona.measurement.service.impl.ZoneManagerServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ApplicationContext {


    /**
     *  TODO
     * @return
     */
    @Bean
    public MySQLZoneDAO getZoneDAO() throws FileNotFoundException {

        MySQLZoneDAO mySQLZoneDAO= new MySQLZoneDAO(System.getProperty("${mybatisConfig}"),System.getProperty("${database.host})"), Integer.parseInt(System.getProperty("${database.port}")),System.getProperty("${database.db}"),System.getProperty("${database.user}"),System.getProperty("${database.password}"));
        return mySQLZoneDAO;
    }

    @Bean
    public MySQLPositionDAO getPositionDAO() throws FileNotFoundException {

        MySQLPositionDAO mySQLPositionDAO = new MySQLPositionDAO(System.getProperty("${mybatisConfig}"),System.getProperty("${database.host})"), Integer.parseInt(System.getProperty("${database.port}")),System.getProperty("${database.db}"),System.getProperty("${database.user}"),System.getProperty("${database.password}"), getZoneDAO());
        return mySQLPositionDAO;
    }

    @Bean
    public MySQLMeasurementDAO getMeasurementDAO() throws FileNotFoundException {

        MySQLMeasurementDAO mySQLMeasurementDAO = new MySQLMeasurementDAO(System.getProperty("${mybatisConfig}"),System.getProperty("${database.host})"), Integer.parseInt(System.getProperty("${database.port}")),System.getProperty("${database.db}"),System.getProperty("${database.user}"),System.getProperty("${database.password}"), getPositionDAO());
        return mySQLMeasurementDAO;
    }

    @Bean
    public ZoneManagerServiceImpl zoneManagerService() throws FileNotFoundException {

        ZoneManagerServiceImpl zoneManagerServiceImpl = new ZoneManagerServiceImpl(getZoneDAO());
        return zoneManagerServiceImpl;
    }

    @Bean
    public MeasurementManagerServiceImpl measurementManagerService() throws FileNotFoundException {

        MeasurementManagerServiceImpl measurementManagerServiceImpl = new MeasurementManagerServiceImpl(getMeasurementDAO());
        return measurementManagerServiceImpl;
    }

    @Bean
    public PropertiesFactoryBean systemProperties() throws IOException {

        PropertiesFactoryBean factoryBean= new PropertiesFactoryBean();
        Properties properties= new Properties(factoryBean.getObject());
        properties.loadFromXML(new FileInputStream(new File("../webapp/WEB-INF/mybatis-configuration.xml")));
        factoryBean.setProperties(properties);
        return factoryBean;

    }

}
