package ru.lanit.at;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        plugin = {
                "pretty",
                "ru.lanit.at.utils.allure.CustomAllureCucumber"
//                , "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        features = "classpath:features",
//        tags = "@google or @negative",
        tags = "@allure_exp",
        glue = {"ru.lanit.at.steps", "ru.lanit.at.hooks"}
)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
