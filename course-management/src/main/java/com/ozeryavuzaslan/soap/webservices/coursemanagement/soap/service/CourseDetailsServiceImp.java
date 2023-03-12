package com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.service;

import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.model.Course;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.converter.CourseDetailsConverter;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.exception.CourseNotFoundException;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.DeleteCourseDetailResponse;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.GetAllCourseDetailsResponse;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.GetCourseDetailResponse;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.enums.OperationStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class CourseDetailsServiceImp implements CourseDetailsService{
    private final CourseDetailsConverter courseDetailsConverter;
    private static final List<Course> courseList = new ArrayList<>();

    static {
        Course course1 = new Course(1, "Spring", "10 Steps");
        courseList.add(course1);

        Course course2 = new Course(2, "Spring MVC", "10 Examples");
        courseList.add(course2);

        Course course3 = new Course(3, "Spring Boot", "6K Students");
        courseList.add(course3);

        Course course4 = new Course(4, "Maven", "Most popular Maven course");
        courseList.add(course4);
    }

    @Override
    public GetCourseDetailResponse findById(int id) {
        int index = getIndex(id);

        if (index == -1)
            throw new CourseNotFoundException("Invalid Course ID " + id);

        return getCourseDetailsConverter().convert(courseList.get(index));
    }

    @Override
    public GetAllCourseDetailsResponse findAll() {
        return getCourseDetailsConverter().convert(courseList);
    }

    @Override
    public DeleteCourseDetailResponse deleteById(int id) {
        int index = getIndex(id);

        if (index != -1){
            courseList.remove(index);
            return getCourseDetailsConverter().convert(OperationStatus.SUCCESS);
        }

        return getCourseDetailsConverter().convert(OperationStatus.FAILURE);
    }

    @Override
    public Course updateById(int id) {
        return null;
    }

    @Override
    public Course insertNewCourse(Course course) {
        return null;
    }

    private int getIndex(int id){
        for (int i = 0; i < courseList.size(); i++){
            if (courseList.get(i).getId() == id)
                return i;
        }

        return -1;
    }
}
