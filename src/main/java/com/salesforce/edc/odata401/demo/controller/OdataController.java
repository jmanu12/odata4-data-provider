package com.salesforce.edc.odata401.demo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.salesforce.edc.odata401.demo.domain.Storage;
import com.salesforce.edc.odata401.demo.service.DemoEntityCollectionProcessor;
import com.salesforce.edc.odata401.demo.service.DemoEntityProcessor;
import com.salesforce.edc.odata401.demo.service.DemoPrimitiveProcessor;

import org.apache.olingo.commons.api.edm.provider.CsdlEdmProvider;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Juan Fajardo
 * @version 1.0-SNAPSHOT
 */
@RestController
@RequestMapping("/")
public class OdataController {

    @Value("${server.servlet.context-path}")
    private String URI;  

    @Autowired
	CsdlEdmProvider edmProvider;

	@Autowired
	EntityCollectionProcessor processor;

	@Autowired
	Storage storage;

    @RequestMapping(value = "*")
	public void process(HttpServletRequest request, HttpServletResponse response) {
		
		
		
		OData odata = OData.newInstance();
		ServiceMetadata edm = odata.createServiceMetadata(edmProvider,
				new ArrayList<>());
		ODataHttpHandler handler = odata.createHandler(edm);
		handler.register(new DemoEntityCollectionProcessor(storage));
		handler.register(new DemoEntityProcessor(storage));
		handler.register(new DemoPrimitiveProcessor(storage));
		handler.process(new HttpServletRequestWrapper(request) {
			// Spring MVC matches the whole path as the servlet path
			// Olingo wants just the prefix, ie upto /OData/V1.0, so that it
			// can parse the rest of it as an OData path. So we need to override
			// getServletPath()
			@Override
			public String getServletPath() {
				return URI;
			}
		}, response);
	}

    
}
