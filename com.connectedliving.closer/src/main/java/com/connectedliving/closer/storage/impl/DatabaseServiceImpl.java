package com.connectedliving.closer.storage.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.configuration.CLConfig;
import com.connectedliving.closer.configuration.CLConfigProperty;
import com.connectedliving.closer.storage.DatabaseService;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

public class DatabaseServiceImpl implements DatabaseService {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	BasicDataSource writeAble;
	BasicDataSource readOnly;

	public DatabaseServiceImpl(CLConfig config) throws Exception {
		writeAble = new BasicDataSource();
		Class.forName(JDBC_DRIVER);
		setupWritePool(createUrl(config.getProperty(CLConfigProperty.DATABASE_WRITE_URL)),
				config.getProperty(CLConfigProperty.DATABASE_USER),
				config.getProperty(CLConfigProperty.DATABASE_PASSWORD),
				config.getIntProperty(CLConfigProperty.MAX_DATABASE_CONNECTIONS));
		readOnly = new BasicDataSource();
		String readURL = config.getProperty(CLConfigProperty.DATABASE_READ_URL);
		if (readURL == null || readURL.isEmpty()) {
			readURL = config.getProperty(CLConfigProperty.DATABASE_WRITE_URL);
		}
		setupReadPool(createUrl(readURL), config.getProperty(CLConfigProperty.DATABASE_USER),
				config.getProperty(CLConfigProperty.DATABASE_PASSWORD),
				config.getIntProperty(CLConfigProperty.MAX_DATABASE_CONNECTIONS));
	}

	/**
	 * Create a mysql jdbc url from configured url
	 * 
	 * @param url
	 * @return
	 */
	private String createUrl(String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:mysql://");
		sb.append(url);
		sb.append("/CL");
		return sb.toString();
	}

	/**
	 * Set up the Database write pool
	 * 
	 * @param url  URL of server
	 * @param user username
	 * @param pass database user password
	 * @param max  max number of allowed connections
	 * @throws Exception
	 */
	private void setupWritePool(String url, String user, String pass, int max) throws Exception {
		writeAble.setDriverClassName(JDBC_DRIVER);
		writeAble.setUrl(url);
		writeAble.setUsername(user);
		writeAble.setPassword(pass);
		writeAble.setMaxTotal(max);
	}

	/**
	 * Setup the Database read pool
	 * 
	 * @param url  URL of server
	 * @param user username
	 * @param pass database user password
	 * @param max  max number of allowed connections
	 * @throws Exception
	 */
	private void setupReadPool(String url, String user, String pass, int max) throws Exception {
		readOnly.setDriverClassName(JDBC_DRIVER);
		readOnly.setUrl(url);
		readOnly.setUsername(user);
		readOnly.setPassword(pass);
		readOnly.setMaxTotal(max);
	}

	public Connection getWriteableCon() throws SQLException {
		return writeAble.getConnection();
	}

	public Connection getReadOnlyCon() throws SQLException {
		return readOnly.getConnection();
	}

	@Override
	public void closeAll(PreparedStatement stmt, Connection con) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// quite
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// quite
			}
		}

	}

	@Override
	public void closeAll(ResultSet rs, PreparedStatement stmt, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// quite
			}
		}
		closeAll(stmt, con);

	}

	@Override
	public void updateDatabase() {

		try {
			CLConfig config = Services.getInstance().getService(CLConfig.class);
			Connection connection = getWriteableCon();
			String path = new File(".").getAbsolutePath();
			System.out.println(path);
			Database database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(connection));
			Liquibase liquibase = new liquibase.Liquibase("liquibase.xml",
					new FileSystemResourceAccessor(new File(config.getProperty(CLConfigProperty.LIQUIBASE_LOC))),
					database);
			liquibase.update(new Contexts(), new LabelExpression());
			connection.close();
		} catch (LiquibaseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Unable to update database");
		}

	}

}
