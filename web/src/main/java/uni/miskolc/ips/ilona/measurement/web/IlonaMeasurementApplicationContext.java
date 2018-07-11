package uni.miskolc.ips.ilona.measurement.web;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uni.miskolc.ips.ilona.measurement.persist.MeasurementDAO;
import uni.miskolc.ips.ilona.measurement.persist.PositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.ZoneDAO;
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


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "uni.miskolc.ips.ilona.measurement.controller")
public class IlonaMeasurementApplicationContext extends WebMvcConfigurerAdapter {


    /**
     *  TODO
     * @return
     */
    @Bean(name = "zoneDao")
    public ZoneDAO getZoneDAO() throws FileNotFoundException {
        ZoneDAO mySQLZoneDAO= null;
        /*
        new MySQLZoneDAO(
                "mybatis-configuration.xml",
                System.getProperty("database.host"),
                Integer.parseInt(System.getProperty("database.port")),
                System.getProperty("database.db"),
                System.getProperty("database.user"),
                System.getProperty("database.password"));
                */
        return mySQLZoneDAO;
    }

    @Bean(name = "positionDAO")
    public PositionDAO getPositionDAO() throws FileNotFoundException {

        PositionDAO mySQLPositionDAO = null;
        //new MySQLPositionDAO(System.getProperty("${mybatisConfig}"),System.getProperty("${database.host})"), Integer.parseInt(System.getProperty("database.port")),System.getProperty("${database.db}"),System.getProperty("${database.user}"),System.getProperty("${database.password}"), getZoneDAO());
        return mySQLPositionDAO;
    }

    @Bean(name = "measurementDAO")
    public MeasurementDAO getMeasurementDAO() throws FileNotFoundException {

        MeasurementDAO mySQLMeasurementDAO = null;
        //new MySQLMeasurementDAO(System.getProperty("${mybatisConfig}"),System.getProperty("${database.host})"),Integer.parseInt(System.getProperty("database.port")),System.getProperty("${database.db}"),System.getProperty("${database.user}"),System.getProperty("${database.password}"), getPositionDAO());
        return mySQLMeasurementDAO;
    }

    @Bean(name = "zoneManagerService")
    public ZoneManagerServiceImpl zoneManagerService() throws FileNotFoundException {

        ZoneManagerServiceImpl zoneManagerServiceImpl = new ZoneManagerServiceImpl(getZoneDAO());
        return zoneManagerServiceImpl;
    }

    @Bean(name = "measurementManagerService")
    public MeasurementManagerServiceImpl measurementManagerService() throws FileNotFoundException {

        MeasurementManagerServiceImpl measurementManagerServiceImpl = new MeasurementManagerServiceImpl(getMeasurementDAO());
        return measurementManagerServiceImpl;
    }
//
//    @Bean(name = "systemProperties")
//    public PropertiesFactoryBean systemProperties() throws IOException {
//
//        PropertiesFactoryBean factoryBean= new PropertiesFactoryBean();
//        Properties properties= new Properties(factoryBean.getObject());
//        properties.loadFromXML(new FileInputStream(new File("../webapp/WEB-INF/mybatis-configuration.xml")));
//        factoryBean.setProperties(properties);
//        return factoryBean;
//
//    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }


    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");

    }
}
