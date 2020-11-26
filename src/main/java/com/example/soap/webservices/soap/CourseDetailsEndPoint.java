package com.example.soap.webservices.soap;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.soap.webservices.CourseDetails;
import com.example.soap.webservices.DeleteCourseDetailsRequest;
import com.example.soap.webservices.DeleteCourseDetailsResponse;
import com.example.soap.webservices.GetAllCourseDetailsRequest;
import com.example.soap.webservices.GetAllCourseDetailsResponse;
import com.example.soap.webservices.GetCourseDetailsRequest;
import com.example.soap.webservices.GetCourseDetailsResponse;
import com.example.soap.webservices.Status;
import com.example.soap.webservices.soap.bean.Course;
import com.example.soap.webservices.soap.exception.CourseNotFoundException;
import com.example.soap.webservices.soap.service.CourseDetailsService;


@Endpoint
public class CourseDetailsEndPoint {
	
	@Autowired
	CourseDetailsService service;
	
	@PayloadRoot(namespace = "http://shalu/soapwebservices", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
		Course course =  service.findById(request.getId().intValue());
		if(course == null){
			throw new CourseNotFoundException("Invalid course id at"+request.getId());
		}
		return mapCourseDetails(course);		
	}
	
	

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();	
		response.setCourseDetails(mapCourse(course));
		return response;
	}
	
	

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(BigInteger.valueOf(course.getId()));
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}
	
	@PayloadRoot(namespace = "http://shalu/soapwebservices", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {
		List<Course> courses =  service.findAll();	
		return mapAllCourseDetails(courses);
	}
	
	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();	
		courses.forEach(crse->{
			CourseDetails courseDetails = mapCourse(crse);
			response.getCourseDetails().add(courseDetails);
		});
		return response;
	}
	
	@PayloadRoot(namespace = "http://shalu/soapwebservices", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {
		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		com.example.soap.webservices.soap.service.CourseDetailsService.Status status = service.deleteById(request.getId().intValue());
		response.setStatus(mapStatus(status));
		return response;
	}



	private Status mapStatus(com.example.soap.webservices.soap.service.CourseDetailsService.Status status) {
		if(com.example.soap.webservices.soap.service.CourseDetailsService.Status.SUCCESS == status) {
			return Status.SUCCESS;
		}
		return Status.FAILURE;
	}

}
