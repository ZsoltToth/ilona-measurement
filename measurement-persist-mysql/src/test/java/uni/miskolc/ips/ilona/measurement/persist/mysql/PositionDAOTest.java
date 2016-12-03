package uni.miskolc.ips.ilona.measurement.persist.mysql;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uni.miskolc.ips.ilona.measurement.persist.PositionDAO;
import uni.miskolc.ips.ilona.measurement.persist.mappers.PositionMapper;

@Ignore
public class PositionDAOTest {
	
	private PositionDAO dao;

	@Before
	public void setUp() throws Exception {
		PositionMapper mapper = EasyMock.createMock(PositionMapper.class);
		SqlSession session =  EasyMock.createMock(SqlSession.class);
		SqlSessionFactory sessionFactory = EasyMock.createMock(SqlSessionFactory.class);
		
		
		
		EasyMock.expect(sessionFactory.openSession()).andReturn(session);
		EasyMock.expect(session.getMapper(PositionMapper.class)).andReturn(mapper);
		session.close();
		EasyMock.replay(mapper,session,sessionFactory);
		this.dao = new MySQLPositionDAO(sessionFactory);
	}

	@Test
	public void testMySQLPositionDAO() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreatePosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadPositions() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdatePosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeletePosition() {
		fail("Not yet implemented");
	}

}
