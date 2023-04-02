package com.ozeryavuzaslan.webservices.restfulwebservicesProject.controller;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.HelloWorldBeanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

import static com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.Constants.DEFAULT_MSG_DEFINITION;
import static com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.Constants.GOOD_MORNING_DEFINITION;

@RestController
@RequestMapping("/hello-world")
@RequiredArgsConstructor
public class HelloWorldController {
    private final MessageSource messageSource;

    @GetMapping
    //@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping(path = "/bean")
    public ResponseEntity<HelloWorldBeanResponse> helloWorldBean(){
        return ResponseEntity.ok(new HelloWorldBeanResponse());
    }

    @GetMapping(path = "/bean/path-variable/{name}")
    public ResponseEntity<HelloWorldBeanResponse> helloWorldBeanPathVariable(@PathVariable String name){
        return ResponseEntity.ok(new HelloWorldBeanResponse(String.format("Hello World, %s ", name)));
    }

    @GetMapping("/hello-world-internationalized")
    public ResponseEntity<String> helloWorldInternationalized(){
        Locale locale = LocaleContextHolder.getLocale();
        return ResponseEntity.ok(messageSource.getMessage(GOOD_MORNING_DEFINITION, null, DEFAULT_MSG_DEFINITION, locale));
    }
}
