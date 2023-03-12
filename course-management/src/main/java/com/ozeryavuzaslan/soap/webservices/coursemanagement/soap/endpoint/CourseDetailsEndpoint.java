package com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.endpoint;

import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.*;
import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.service.CourseDetailsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Data
@Endpoint
@RequiredArgsConstructor
public class CourseDetailsEndpoint {
    private final CourseDetailsService courseDetailsService;

    @ResponsePayload
    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "GetCourseDetailRequest")
    public GetCourseDetailResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailRequest request){
        return getCourseDetailsService().findById(request.getId());
    }

    @ResponsePayload
    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "GetAllCourseDetailsRequest")
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request){
        return getCourseDetailsService().findAll();
    }

    @ResponsePayload
    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "DeleteCourseDetailRequest")
    public DeleteCourseDetailResponse processCourseDetailsRequest(@RequestPayload DeleteCourseDetailRequest request){
        return getCourseDetailsService().deleteById(request.getId());
    }
}