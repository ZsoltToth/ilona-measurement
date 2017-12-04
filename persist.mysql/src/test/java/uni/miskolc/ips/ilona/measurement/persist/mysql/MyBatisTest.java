package uni.miskolc.ips.ilona.measurement.persist.mysql;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Ignore;
import org.junit.Test;

import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.mappers.ZoneMapper;

@Ignore
public class MyBatisTest {

	@Test
	public void testIsConfigExsist() {
		File configFile = new File("src/main/resources/mybatis-configuration.xml");
		assertTrue(configFile.exists());
	}
	
	@Ignore
	@Test
	public void testBuildSqlSessionFactory() throws FileNotFoundException{
		File configFile = new File("src/main/resources/mybatis-configuration.xml");
		InputStream inputStream = new FileInputStream(configFile);
		SqlSessionFactory sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream);
//		System.out.println(sqlSessionFactory.toString());
//		System.out.println(String.format("Session object: %s", sqlSessionFactory.openSession()));
		
		
	}
	
	@Ignore
	@Test
	public void testBuildSqlSessionFactoryWithProperties() throws FileNotFoundException{
		File configFile = new File("src/main/resources/mybatis-configuration.xml");
		InputStream inputStream = new FileInputStream(configFile);
		Properties props = new Properties();
		props.put("driver", "com.mysql.jdbc.Driver");
		props.put("url", "jdbc:mysql://localhost:3306/ilona");
		props.put("username", "ilona");
		props.put("password", "ilona");
		
		SqlSessionFactory sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream,props);
//		System.out.println(sqlSessionFactory.toString());
//		System.out.println(String.format("Session object: %s", sqlSessionFactory.openSession()));
		
	}
	
	@Ignore
	@Test
	public void testOpenSessionGetMapper() throws FileNotFoundException{
		File configFile = new File("src/main/resources/mybatis-configuration.xml");
		InputStream inputStream = new FileInputStream(configFile);
		Properties props = new Properties();
		props.put("driver", "com.mysql.jdbc.Driver");
		props.put("url", "jdbc:mysql://localhost:3306/ilona");
		props.put("username", "ilona");
		props.put("password", "ilona");
		
		SqlSessionFactory sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream,props);
		SqlSession session = sqlSessionFactory.openSession();
		ZoneMapper mapper = session.getMapper(ZoneMapper.class);
	}
	
	@Test
	public void testOpenSessionGetMapperListZones() throws FileNotFoundException{
		File configFile = new File("src/main/resources/mybatis-configuration.xml");
		InputStream inputStream = new FileInputStream(configFile);
		Properties props = new Properties();
		props.put("driver", "com.mysql.jdbc.Driver");
		props.put("url", "jdbc:mysql://localhost:3306/mybatisTest");
		props.put("username", "ilona");
		props.put("password", "ilona");
		
		SqlSessionFactory sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream,props);
		SqlSession session = sqlSessionFactory.openSession();
		ZoneMapper mapper = session.getMapper(ZoneMapper.class);
		Collection<Zone> zones = mapper.selectZones();
//		System.out.println(zones.size());
//		for(Zone zone : zones){
//			System.out.println(zone);
//		}
	}
	
	
	@Test
	public void testOpenSessionGetMapperInsertZone() throws FileNotFoundException{
		File configFile = new File("src/main/resources/mybatis-configuration.xml");
		InputStream inputStream = new FileInputStream(configFile);
		Properties props = new Properties();
		props.put("driver", "com.mysql.jdbc.Driver");
		props.put("url", "jdbc:mysql://localhost:3306/mybatisTest");
		props.put("username", "ilona");
		props.put("password", "ilona");
		
		SqlSessionFactory sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream,props);
		SqlSession session = sqlSessionFactory.openSession();
		ZoneMapper mapper = session.getMapper(ZoneMapper.class);
		
		Zone zone = new Zone("Kitchen");
		
//		System.out.println(zone.getId().toString() +"\t"+zone.getId().toString().length());
		
		mapper.insertZone(zone);
		session.commit();
	}
	
	@Ignore
	@Test
	public void testFactoryTypeHandlers() throws FileNotFoundException{
		File configFile = new File("src/main/resources/mybatis-configuration.xml");
		InputStream inputStream = new FileInputStream(configFile);
		Properties props = new Properties();
		props.put("driver", "com.mysql.jdbc.Driver");
		props.put("url", "jdbc:mysql://localhost:3306/ilona");
		props.put("username", "ilona");
		props.put("password", "ilona");
		
		SqlSessionFactory sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream,props);
		Collection<TypeHandler<?>> handlers = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().getTypeHandlers();
		for(TypeHandler<?> handler : handlers){
			System.out.println(handler.toString());
		}
	}

	@Ignore
	@Test
	public void testSessionTypeHandlers() throws FileNotFoundException{
		File configFile = new File("src/main/resources/mybatis-configuration.xml");
		InputStream inputStream = new FileInputStream(configFile);
		Properties props = new Properties();
		props.put("driver", "com.mysql.jdbc.Driver");
		props.put("url", "jdbc:mysql://localhost:3306/ilona");
		props.put("username", "ilona");
		props.put("password", "ilona");
		
		SqlSessionFactory sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream,props);
		Collection<TypeHandler<?>> handlers = sqlSessionFactory.openSession().getConfiguration().getTypeHandlerRegistry().getTypeHandlers();
		for(TypeHandler<?> handler : handlers){
			System.out.println(handler.toString());
		}
	}
	
	
	@Test
	public void testInsertZone() throws FileNotFoundException{
		File configFile = new File("src/main/resources/mybatis-configuration.xml");
		InputStream inputStream = new FileInputStream(configFile);
		Properties props = new Properties();
		props.put("driver", "com.mysql.jdbc.Driver");
		props.put("url", "jdbc:mysql://localhost:3306/mybatisTest");
		props.put("username", "ilona");
		props.put("password", "ilona");
		
		SqlSessionFactory sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream,props);
		SqlSession session = sqlSessionFactory.openSession();
		ZoneMapper mapper = session.getMapper(ZoneMapper.class);
		
		Zone livingRoom = new Zone("Test created Zone");
		mapper.insertZone(livingRoom);
	}
	
	
	
}
