package com.luv2code.springdemo.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentRestController {

	private List<Student> theStudents;

	// define @PostConstruct to load the students data - only once
	@PostConstruct
	public void loadData() {

		theStudents = new ArrayList<>();

		theStudents.add(new Student("Sneha", "Gupta"));
		theStudents.add(new Student("Nimmi", "Diwakar"));
		theStudents.add(new Student("Rahul", "Singh"));
	}

	// defien endpoint for "/students" - return list of students
	@GetMapping("/students")
	public List<Student> getStudents() {

		return theStudents;
	}

	// define endpoint for "/students/{studentsId}" - return student at index
	@GetMapping("/students/{studentId}")
	public Student getStudent(@PathVariable int studentId) {

		if (studentId < 0 || studentId >= theStudents.size()) {
			throw new StudentNotfoundException("Student id not found - " + studentId);
		}
		return theStudents.get(studentId);
	}

}
