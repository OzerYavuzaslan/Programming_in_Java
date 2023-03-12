package com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.converter;

import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.model.Course;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.CourseDetails;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.DeleteCourseDetailResponse;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.GetAllCourseDetailsResponse;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.GetCourseDetailResponse;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.enums.OperationStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseDetailsConverter {
    public GetCourseDetailResponse convert(Course course){
        GetCourseDetailResponse response = new GetCourseDetailResponse();
        response.setCourseDetails(mapCourse(course));

        return response;
    }

    public GetAllCourseDetailsResponse convert(List<Course> courseList){
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();

        for (Course course : courseList){
            CourseDetails courseDetails = mapCourse(course);
            response.getCourseDetails().add(courseDetails);
        }

        return response;
    }

    public DeleteCourseDetailResponse convert(OperationStatus status){
        DeleteCourseDetailResponse response = new DeleteCourseDetailResponse();
        response.setStatus(status);

        return response;
    }

    public CourseDetails mapCourse(Course course){
        CourseDetails courseDetails = new CourseDetails();

        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        courseDetails.setDescription(course.getDescription());

        return courseDetails;
    }
}
