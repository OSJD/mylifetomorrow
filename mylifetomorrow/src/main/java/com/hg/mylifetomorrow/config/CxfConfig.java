package com.hg.mylifetomorrow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:docusign/docusign.services.xml"})
public class CxfConfig {

}
