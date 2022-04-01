package us.hypermediocrity.springclean;

import org.springframework.test.context.ContextConfiguration;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = { SpringCleanTestConfig.class })
public class CucumberSpringContextConfig {

}
