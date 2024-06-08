package com.chang.omg.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.client.MockRestServiceServer;

import com.chang.omg.global.config.PropertyConfig;
import com.chang.omg.global.config.property.NexonCommonProperties;

@Import({RestTemplateTestConfig.class, PropertyConfig.class})
public abstract class IntegrationApiTest {

    @Autowired
    protected NexonCommonProperties nexonCommonProperties;

    @Autowired
    protected MockRestServiceServer mockRestServiceServer;
}
