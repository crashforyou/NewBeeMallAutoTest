package com.zjut.doctor.tests;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import com.zjut.doctor.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/login.html
public class LoginTest extends BaseTest {
    private LoginPage loginPage = new LoginPage();

    @Test
    public void testLogin() {
        loginPage.openPage("http://localhost:28079/login.html");
        loginPage.login(username, password, verifyCode); // 处理实际验证码逻辑
        loginPage.verifyUrl("http://localhost:28079/index");
    }
}