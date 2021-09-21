package com.connectedliving.closer.configuration;

public enum CLConfigProperty {

	LIQUIBASE_LOC("liquibaseLocation"),

	DATABASE_WRITE_URL("databaseWriteUrl"), DATABASE_READ_URL("databaseReadUrl"), DATABASE_USER("databaseUser"),
	DATABASE_PASSWORD("databasePassword"),

	MAX_DATABASE_CONNECTIONS("maxConnections", 10),

	SESSION_IDLE_LIMIT("sessionIdleLimit", 30), // Session idle time limit in minutes
	;

	private String value;
	private Object defaultVal;

	CLConfigProperty(String value, Object defValue) {
		this.value = value;
		this.defaultVal = defValue;
	}

	CLConfigProperty(String value) {
		this.value = value;
		this.defaultVal = null;
	}

	public String getValue() {
		return this.value;
	}

	public Object getDefault() {
		return this.defaultVal;
	}

}
