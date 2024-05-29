package com.zjut.doctor.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import com.zjut.doctor.pages.PersonalSideBarPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/index
public class PersonalSideBarTest extends BaseTest {
    private PersonalSideBarPage personalSideBarPage = new PersonalSideBarPage();

    @BeforeEach
    public void personalSideBarSetUp() {
        login(username, password, verifyCode);
        personalSideBarPage.openPage("http://localhost:28079/index");
    }

    @Test
    public void testGoToPersonalInfo() {
        personalSideBarPage.clickDropDown();
        personalSideBarPage.clickPersonal();
        personalSideBarPage.verifyUrl("http://localhost:28079/personal");
    }

    @Test
    public void testGoToOrder() {
        personalSideBarPage.clickDropDown();
        personalSideBarPage.clickOrders();
        personalSideBarPage.verifyUrl("http://localhost:28079/orders");
    }

    @Test
    public void testGoToMyCoupons() {
        personalSideBarPage.clickDropDown();
        personalSideBarPage.clickMyCoupons();
        personalSideBarPage.verifyUrl("http://localhost:28079/myCoupons");
    }

    @Test
    public void testLogout() {
        personalSideBarPage.clickDropDown();
        personalSideBarPage.clickLogout();
        personalSideBarPage.verifyUrl("http://localhost:28079/logout");
    }
}