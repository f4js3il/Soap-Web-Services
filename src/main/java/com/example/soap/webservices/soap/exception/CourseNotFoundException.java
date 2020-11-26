package com.example.soap.webservices.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://shalu/soapwebservices}001_COURSE_NOTFOUND")
public class CourseNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7720672562047998566L;

	public CourseNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
