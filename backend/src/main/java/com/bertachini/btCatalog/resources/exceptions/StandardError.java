package com.bertachini.btCatalog.resources.exceptions;

import java.time.Instant;
import java.util.Objects;

public class StandardError {
	
	private Instant timestamp;
	private Integer status;
	private String error;
	private String messege;
	private String path;
	
	public StandardError() {
		
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessege() {
		return messege;
	}

	public void setMessege(String messege) {
		this.messege = messege;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int hashCode() {
		return Objects.hash(error, messege, path, status, timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardError other = (StandardError) obj;
		return Objects.equals(error, other.error) && Objects.equals(messege, other.messege)
				&& Objects.equals(path, other.path) && Objects.equals(status, other.status)
				&& Objects.equals(timestamp, other.timestamp);
	}

	
	
}
