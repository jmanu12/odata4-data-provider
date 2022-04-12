package com.salesforce.edc.odata401.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ProviderController {
    
    @RequestMapping("/")
    public String helloWorld() {
        return "Hello World";
    }
    
}
