package com.example.soap.webservices.soap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.soap.webservices.soap.bean.Course;

@Component
public class CourseDetailsService {
	
	public enum Status{
		SUCCESS,FAILURE
	}
	
	private static List<Course> courses = new ArrayList<Course>();
	
	static {
		courses.add(new Course(1, "Spring", "10 Steps"));
		courses.add(new Course(2, "Spring MVC", "10 Examples"));
		courses.add(new Course(3, "Spring Boot", "6K Students"));
		courses.add(new Course(4, "Maven", "Most popular maven course on internet!"));
	}
	
	
	public List<Course> findAll() {
		return courses;
	}
	
	public Status deleteById(int id) {
		for (Course course : courses) {
			if (course.getId()== id) {
				return Status.SUCCESS;
			}
		}		
		return Status.FAILURE;
	}

	public Course findById(int id) {
		return courses.stream().filter(course-> course.getId()==id).findFirst().orElse(null);
	
	}
	

}
