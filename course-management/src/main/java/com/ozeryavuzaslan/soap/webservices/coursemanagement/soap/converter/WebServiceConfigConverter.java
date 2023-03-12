package com.ozeryavuzaslan.soap.webservices.coursemanagement.soap.converter;

import org.springframework.stereotype.Component;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;

@Component
public class WebServiceConfigConverter {
    public DefaultWsdl11Definition convert(XsdSchema coursesSchema, String portTypeName, String targetNamespace, String locationUri){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();

        definition.setPortTypeName(portTypeName);
        definition.setTargetNamespace(targetNamespace);
        definition.setLocationUri(locationUri);
        definition.setSchema(coursesSchema);

        return definition;
    }
}
