package us.hypermediocrity.springclean;

import org.junit.jupiter.api.Disabled;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import io.cucumber.core.options.Constants;

@Disabled
@Suite
@SelectClasspathResource("feature")
@ConfigurationParameter(key = Constants.SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
@ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "not(@Ignore)")
public class CucumberTests {

}
