package com.zjut.doctor.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/login.html
public class LoginPage extends BasePage {

    private final String loginNameInput = "#loginName";
    private final String passwordInput = "#password";
    private final String verifyCodeInput = "#verifyCode";
    private final String kaptchaImage = "#kaptcha";
    private final String submitButton = ".submit";

    public void login(String username, String password, String verifyCode) {
        setInputValue(loginNameInput, username);
        setInputValue(passwordInput, password);
        setInputValue(verifyCodeInput, verifyCode);
        clickElement(kaptchaImage);
        clickElement(submitButton);
    }
}