package com.zjut.doctor.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/personal
public class PersonalPage extends BasePage {
    private final String changeInfo = "div[class='grzlbt ml40'] a";
    private final String cancelButton = "button[class$='btn-default']";
    private final String confirmButton = "#saveButton";
    private final String nickName = "html > body > div:nth-of-type(1) > div:nth-of-type(1) > div:nth-of-type(2) > div:nth-of-type(2) > span:nth-of-type(2)";
    private final String introduceSign = "html > body > div:nth-of-type(1) > div:nth-of-type(1) > div:nth-of-type(2) > div:nth-of-type(5) > span:nth-of-type(2)";
    private final String address = "html > body > div:nth-of-type(1) > div:nth-of-type(1) > div:nth-of-type(2) > div:nth-of-type(6) > span:nth-of-type(2)";

    public SelenideElement divModalDialog = $("div[class='modal-dialog']");
    public SelenideElement inputNickName = $("#nickName");
    public SelenideElement inputIntroduceSign = $("#introduceSign");
    public SelenideElement inputAddress = $("#address");

    public void clickChangeInfo() {
        clickElement(changeInfo);
        divModalDialog.shouldBe(Condition.visible);
    }

    public void changeInfo(String nickName, String introduceSign, String address) {
        inputNickName.clear();
        inputNickName.click();
        inputNickName.setValue(nickName);
        inputIntroduceSign.clear();
        inputIntroduceSign.click();
        inputIntroduceSign.setValue(introduceSign);
        inputAddress.clear();
        inputAddress.click();
        inputAddress.setValue(address);
        clickElement(confirmButton);
    }

    public void cancelChangeInfo() {
        clickElement(cancelButton);
    }

    public boolean verifyInfo(String nickName, String introduceSign, String address) {
        //忽略大小写比较
        return $(this.nickName).getText().equalsIgnoreCase(nickName) && $(this.introduceSign).getText().equalsIgnoreCase(introduceSign) && $(this.address).getText().equalsIgnoreCase(address);
    }

    public boolean isModalDialogVisible() {
        return divModalDialog.isDisplayed();
    }
}