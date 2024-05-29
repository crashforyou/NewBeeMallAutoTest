package com.zjut.doctor.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/index
public class PersonalSideBarPage extends BasePage {
    private final String personal = "//a[@href='/personal']";
    private final String orders = "//a[@href='/orders']";
    private final String myCoupons = "//a[@href='/myCoupons']";
    private final String logout = "//a[@href='/logout']";
    private final String dropDown="a[class='username']";

    public void clickOption(String option) {
        clickElement(option);
    }

    public void clickDropDown() {
        clickElement(dropDown);
    }
    public void clickPersonal() {
        clickElement(personal);
    }

    public void clickOrders() {
        clickElement(orders);
    }

    public void clickMyCoupons() {
        clickElement(myCoupons);
    }

    public void clickLogout() {
        clickElement(logout);
    }
}