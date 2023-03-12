package com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto;

import com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.dto.enums.OperationStatus;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "status"
})
@XmlRootElement(name = "DeleteCourseDetailResponse")
public class DeleteCourseDetailResponse {
    protected OperationStatus status;
}
