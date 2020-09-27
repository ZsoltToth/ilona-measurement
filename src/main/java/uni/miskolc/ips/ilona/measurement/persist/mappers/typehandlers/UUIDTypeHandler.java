package uni.miskolc.ips.ilona.measurement.persist.mappers.typehandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
/**
 * 
 * @author bogdandy
 *
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(UUID.class)
public class UUIDTypeHandler implements TypeHandler<UUID> {
/**
 * 
 */
	@Override
	public final UUID getResult(final ResultSet rs, final String colName) throws SQLException {
		return UUID.fromString(rs.getString(colName));
	}
/**
 * 
 */
	@Override
	public final UUID getResult(final ResultSet rs, final int colIndex) throws SQLException {
		return UUID.fromString(rs.getString(colIndex));
	}
/**
 * 
 */
	@Override
	public final UUID getResult(final CallableStatement cs, final int colIndex) throws SQLException {
		return UUID.fromString(cs.getString(colIndex));
	}
/**
 * 
 */
	@Override
	public final void setParameter(final PreparedStatement pstmt, final int index, final UUID uuid, final JdbcType jdbcType) throws SQLException {
		pstmt.setObject(index, uuid.toString(), jdbcType.TYPE_CODE);
		pstmt.setString(index, uuid.toString());
	}

}
