package org.springframework.boot.mustache.web;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.mustache.web.MustacheTemplateStandaloneTests.Application;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.samskivert.mustache.Mustache;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest({"spring.main.web_environment=false", "env.foo=Heaven", "foo=World"})
public class MustacheTemplateStandaloneTests {

	@Autowired
	private Mustache.Compiler compiler;

	@Test
	public void directCompilation() throws Exception {
		assertEquals("Hello: World", compiler.compile("Hello: {{world}}").execute(
				Collections.singletonMap("world", "World")));
	}

	@Test
	public void environmentCollectorCompoundKey() throws Exception {
		assertEquals("Hello: Heaven", compiler.compile("Hello: {{env.foo}}").execute(new Object()));
	}

	@Test
	public void environmentCollectorCompoundKeyStandard() throws Exception {
		assertEquals("Hello: Heaven", compiler.standardsMode(true).compile("Hello: {{env.foo}}").execute(new Object()));
	}

	@Test
	public void environmentCollectorSimpleKey() throws Exception {
		assertEquals("Hello: World", compiler.compile("Hello: {{foo}}").execute(new Object()));
	}

	@EnableAutoConfiguration
	@Configuration
	protected static class Application {

	}

}
