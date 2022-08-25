package com.revature.models;

import java.util.Objects;

public class Connectivity {

	private String url;
	private String database;
	private String username;
	private String password;
	
	public Connectivity(String url, String database, String username, String password) {
		super();
		this.url = url;
		this.database = database;
		this.username = username;
		this.password = password;
	}
	
	public Connectivity() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(database, password, url, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connectivity other = (Connectivity) obj;
		return Objects.equals(database, other.database) && Objects.equals(password, other.password)
				&& Objects.equals(url, other.url) && Objects.equals(username, other.username);
	}

	
	@Override
	public String toString() {
		return "Connectivity [url=" + url + ", database=" + database + ", username=" + username + ", password="
				+ password + "]";
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}
