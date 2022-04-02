package us.hypermediocrity.springclean;

import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = { SpringCleanApplication.class }, loader = SpringBootContextLoader.class)
//@ContextConfiguration(classes = { SpringCleanTestConfig.class })
public class CucumberSpringContextConfig {

}
