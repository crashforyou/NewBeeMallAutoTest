package com.zjut.doctor.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import com.zjut.doctor.pages.BasePage;
import com.zjut.doctor.pages.LoginPage;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/
public class BaseTest {
    protected final String username = "13866668888";
    protected final String password = "13866668888";
    protected final String verifyCode = "abcd";
    protected final String serverUrl = "http://localhost:28079/";
    private final static BasePage basePage = new BasePage();

    @BeforeAll
    public static void setUp() {
        // 设置 Chrome 的选项以最大化启动
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // 这个参数会使浏览器窗口最大化

        // 将这些选项应用到 Selenide 配置
        Configuration.browserCapabilities = options;
        Configuration.browser = "chrome";
    }

    public static void login(String username, String password, String verifyCode) {
        LoginPage loginPage = open("http://localhost:28079/login", LoginPage.class);
        loginPage.login(username, password, verifyCode);
    }
}