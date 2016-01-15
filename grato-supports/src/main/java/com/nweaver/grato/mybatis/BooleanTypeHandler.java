package com.nweaver.grato.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class BooleanTypeHandler implements TypeHandler<Boolean> {
	private static final String TRUE = "Y";
	private static final String FALSE = "N";
	
	public Boolean getResult(ResultSet resultSet, String name) throws SQLException {
		return valueOf(resultSet.getString(name));
	}

	public Boolean getResult(ResultSet resultSet, int position) throws SQLException {
		return valueOf(resultSet.getString(position));
	}

	public Boolean getResult(CallableStatement stmt, int position) throws SQLException {
		return valueOf(stmt.getString(position));
	}

	public void setParameter(PreparedStatement stmt, int position, Boolean value, JdbcType jdbcType) throws SQLException {
		stmt.setString(position, value.booleanValue() ? TRUE : FALSE);
	}
	
	private Boolean valueOf(String value) {
		if(TRUE.equalsIgnoreCase(value)) {
			return new Boolean(true);
		} else if(FALSE.equalsIgnoreCase(value) || value == null) {
			return new Boolean(false);
		} else {
			return new Boolean(false);
		}
	}
}