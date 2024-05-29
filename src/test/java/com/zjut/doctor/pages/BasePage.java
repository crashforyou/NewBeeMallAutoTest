package com.zjut.doctor.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.*;

// page_url = http://localhost:28079
public class BasePage {
    public SelenideElement start=$(byText("Visit Site"));
    public void openPage(String url) {
        open(url);
    }

    public void refreshPage() {
        refresh();
    }

    public void clickElement(String selector) {
        if (selector.startsWith("//") || selector.startsWith(".//")) {
            $x(selector).click();
        } else {
            $(selector).click();
        }
    }

    public void setInputValue(String selector, String value) {
        if (selector.startsWith("//") || selector.startsWith(".//")) {
            $x(selector).setValue(value);
        } else {
            $(selector).setValue(value);
        }
    }

    public void verifyUrl(String expectedUrl) {
        webdriver().shouldHave(url(expectedUrl));
    }

    public String decodeUrl(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }
    public String encodeUrl(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, StandardCharsets.UTF_8);
    }
}