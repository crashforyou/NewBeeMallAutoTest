package com.zjut.doctor.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/couponList
public class PayPage extends BasePage {
    public SelenideElement SaveOrderButton = $("#saveOrder");
    public SelenideElement EditAddressButton = $("a[class*='btn-line-gray']");

    public SelenideElement inputAddress = $("#address");
    public SelenideElement buttonSave = $("#saveButton");
    public ElementsCollection couponList = $$("#my-coupon ul li a");

    public SelenideElement payOrder = $("a[onclick='payOrder()']");

    public SelenideElement AliPay = $("li[class='J_alipay']");
    public SelenideElement WeixinPay = $("#J_weixin");
    public SelenideElement paySuccess = $("a[onclick='payOrderSuccess()']");

    public SelenideElement OrderStatus = $("div[class='order-status']");

    public SelenideElement CancelOrder = $("a[onclick='cancelOrder()']");
    public SelenideElement OkButton = $("button[class*='swal-button--confirm']");
}