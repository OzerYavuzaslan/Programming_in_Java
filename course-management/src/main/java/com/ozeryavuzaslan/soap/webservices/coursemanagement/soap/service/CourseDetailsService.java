package com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.service;

import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.model.Course;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.DeleteCourseDetailResponse;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.GetAllCourseDetailsResponse;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.GetCourseDetailResponse;

public interface CourseDetailsService {
    GetCourseDetailResponse findById(int id);
    GetAllCourseDetailsResponse findAll();
    DeleteCourseDetailResponse deleteById(int id);
    Course updateById(int id);
    Course insertNewCourse(Course course);
}