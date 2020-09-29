package com.comp671.testing.project.seleniumdemo;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber.html"},
        glue = "com.comp671.testing.project.seleniumdemo",
        features = "features"
)
public class SeleniumDemoCucumberRunner {}
