package com.example.demo;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getImage(HttpServletRequest request, Model model) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, Object> attributes = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            attributes.put(parameterName, request.getParameter(parameterName));
        }
        model.addAllAttributes(attributes);
        return (String) attributes.get("template");
    }

    @RequestMapping(value = "/take-screen", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void takeScreenshot() throws IOException {
        long start = System.currentTimeMillis();
        System.setProperty("webdriver.chrome.driver", "/home/dev/Downloads/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=540,280");
        options.addArguments("--hide-scrollbars");
        options.setHeadless(true);
        WebDriver driver = new ChromeDriver(options);
        driver.get("http://127.0.0.1:8080?template=html&name=qwe&profile=ttt.abc");
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("screenshot.png"));
        System.out.println("request took: + " + (System.currentTimeMillis() - start));
    }
}
