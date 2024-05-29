package com.zjut.doctor.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/couponList
public class CouponListPage extends BasePage {
    public ElementsCollection couponList = $$(".quan-item .quan-d-item");
    public SelenideElement couponModal = $("div[aria-modal='true']");
    public SelenideElement OkButton = $("button");

}