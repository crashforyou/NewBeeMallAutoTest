package com.zjut.doctor.tests;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import com.zjut.doctor.pages.PersonalPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/personal
public class PersonalTest extends BaseTest {
    private PersonalPage personalPage = new PersonalPage();

    @BeforeEach
    public void personalSetUp() {
        login(username, password, verifyCode);
        personalPage.openPage("http://localhost:28079/personal");
    }

    @Test
    public void testClickPersonalInfo() {
        personalPage.clickChangeInfo();
        assert personalPage.isModalDialogVisible();
    }

    @Test
    public void testChangePersonalInfo() {
        personalPage.clickChangeInfo();
        personalPage.changeInfo("testMe!", "MyTest", "浙江工业大学");
        personalPage.divModalDialog.shouldNotBe(visible);
        personalPage.refreshPage();
        //assert personalPage.verifyInfo("testMe!","MyTest","浙江工业大学");
        assert personalPage.verifyInfo("testMe!", "MyTest", "浙江工业大学");
    }

    @Test
    public void testCancel(){
        personalPage.clickChangeInfo();
        personalPage.cancelChangeInfo();
        personalPage.divModalDialog.shouldNotBe(visible);
    }
}