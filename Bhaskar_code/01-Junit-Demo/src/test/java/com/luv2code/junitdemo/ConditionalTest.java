package com.luv2code.junitdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class ConditionalTest {

	@Disabled
	@Test
	public void diabled() {

	}

	@Test
	@EnabledOnOs(OS.MAC)
	public void macOnly() {

	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	public void windowOnly() {

	}

	@Test
	@EnabledOnOs(OS.LINUX)
	public void linuxOnly() {

	}

	@Test
	@EnabledOnOs({ OS.MAC, OS.WINDOWS })
	public void macAndWinodwOnly() {

	}

	@Test
	@EnabledIfEnvironmentVariable(named = "bhaskar", matches = "bhaskar")
	public void environmnetVaribale() {

	}

	@Test
	@EnabledIfSystemProperty(named = "system", matches = "system")
	public void systemProperties() {

	}
}
