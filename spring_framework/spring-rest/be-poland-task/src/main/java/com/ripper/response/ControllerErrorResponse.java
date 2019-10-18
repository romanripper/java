package com.ripper.response;

import java.util.ArrayList;
import java.util.List;

public class ControllerErrorResponse {
	
	private int status;
	private List<String> errorMessages;
	private long timeStamp;

	public ControllerErrorResponse() {

	}

	public ControllerErrorResponse(int status, String error, long timeStamp) {
		this.status = status;
		this.timeStamp = timeStamp;
		errorMessages = new ArrayList<>();
		errorMessages.add(error);
	}

	public ControllerErrorResponse(int status, List<String> errors, long timeStamp) {
		this.status = status;
		this.errorMessages = errors;
		this.timeStamp = timeStamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errors) {
		this.errorMessages = errors;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
