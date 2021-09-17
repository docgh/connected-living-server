package com.connectedliving.closer.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseService {

	/**
	 * Get connection from the write pool
	 * 
	 * @return Database connection
	 * @throws SQLException
	 */
	public Connection getWriteableCon() throws SQLException;

	/**
	 * Get connection from the read pool
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getReadOnlyCon() throws SQLException;

	public void closeAll(PreparedStatement stmt, Connection con);

	public void closeAll(ResultSet rs, PreparedStatement stmt, Connection con);

}
