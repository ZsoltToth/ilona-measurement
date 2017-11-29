package uni.miskolc.ips.ilona.measurement.persist.mysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.UUID;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.ZoneDAO;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.InsertionException;
import uni.miskolc.ips.ilona.measurement.persist.exceptions.RecordNotFoundException;
import uni.miskolc.ips.ilona.measurement.persist.mappers.ZoneMapper;

import com.mysql.jdbc.PreparedStatement;

public class MySQLZoneDAO implements ZoneDAO {

	private final Logger LOG = LogManager.getLogger(MySQLPositionDAO.class);

	private SqlSessionFactory sessionFactory;
	
	/**
	 * This constructor is defined for Unit test.
	 * It makes it possible to mock the SqlSessionFactory local field.
	 * 
	 * 
	 * @param sessionFactory
	 */
	protected MySQLZoneDAO(SqlSessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

//	public MySQLZoneDAO(String configFilePath, String propertyFilePath) {
//
//	}

	public MySQLZoneDAO(String configFilePath, String host, int port,
			String database, String user, String password)
			throws FileNotFoundException {
		File configFile = new File(configFilePath);
		if(configFile.exists() == false){
			LOG.fatal(configFile.getAbsolutePath() + " is not found!");
		}
		// new File("src/main/resources/mybatis-configuration.xml");
		InputStream inputStream = new FileInputStream(configFile);
		String urlPattern = "jdbc:mysql://%s:%s/%s";
		String connectionURL = String.format(urlPattern, host,port,database);
		Properties props = new Properties();
		props.put("driver", "com.mysql.jdbc.Driver");
		props.put("url", connectionURL);
		props.put("username", user);
		props.put("password", password);

		this.sessionFactory = (new SqlSessionFactoryBuilder()).build(
				inputStream, props);
	}

	@Override
	public void createZone(Zone zone) throws InsertionException {
		SqlSession session = sessionFactory.openSession();
		try {
			ZoneMapper mapper = session.getMapper(ZoneMapper.class);
			mapper.insertZone(zone);
			session.commit();
		}
		catch(PersistenceException ex){
			throw new InsertionException();
		}
		finally {
			session.close();
		}
	}

	@Override
	public Collection<Zone> readZones() {
		Collection<Zone> result = new ArrayList<Zone>();
		SqlSession session = sessionFactory.openSession();
		try {
			ZoneMapper mapper = session.getMapper(ZoneMapper.class);
			result.addAll(mapper.selectZones());
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public Collection<Zone> readZones(String zoneName) {
		Collection<Zone> result = new ArrayList<Zone>();
		SqlSession session = sessionFactory.openSession();
		try {
			ZoneMapper mapper = session.getMapper(ZoneMapper.class);
			result.addAll(mapper.selectZonesByName(zoneName));
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public Zone readZone(UUID id) throws RecordNotFoundException {
		Zone result = null;
		SqlSession session = sessionFactory.openSession();
		try {
			ZoneMapper mapper = session.getMapper(ZoneMapper.class);
			result = mapper.selectZoneByUUID(id);
		}
		catch(PersistenceException ex){
			throw new RecordNotFoundException();
		}
		finally {
			session.close();
		}
		if (result == null) {
			throw new RecordNotFoundException();
		}
		return result;
	}

	@Override
	public void deleteZone(Zone zone) throws RecordNotFoundException {
		SqlSession session = sessionFactory.openSession();
		try {
			ZoneMapper mapper = session.getMapper(ZoneMapper.class);
			mapper.deleteZone(zone);
			session.commit();
		} finally {
			session.close();
		}
	}

}
