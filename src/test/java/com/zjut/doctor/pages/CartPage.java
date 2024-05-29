package com.zjut.doctor.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/shop-cart
public class CartPage extends BasePage {
    private final String submitButton = "input[name='settle']";
    private final String link = "html > body > div:nth-of-type(1) > div:nth-of-type(3) > div:nth-of-type(2) > div:nth-of-type(1) > ul > li:nth-of-type(1) > a";
    private final String confirmButton = "button[class*='swal-button--confirm']";
    public ElementsCollection goodsCount = $$(".goods_count");
    public ElementsCollection goodsDelete = $$("a").filterBy(text("×"));
    public SelenideElement inputSettleButton = $("input[name='settle']");


    public void clickSettle() {
        clickElement(submitButton);
    }

    public void updateItemQuantity(String goodsId, String quantity) {
        //$("#goodsCount" + goodsId).setValue(quantity);
        SelenideElement quantityInput = $("#goodsCount" + goodsId);

        // 等待元素可见和可编辑
        quantityInput.shouldBe(Condition.visible).shouldBe(Condition.enabled);

        // 清空并设置新值
        quantityInput.clear();
        quantityInput.setValue(quantity);

        // 手动触发blur事件
        quantityInput.sendKeys(Keys.TAB);
    }

    public void clickShop() {
        clickElement(link);
    }

    public void deleteItem(String goodsId) {
        clickElement("a[onclick='deleteItem(" + goodsId + ")']");
    }

    //模拟确认删除弹框
    public void confirmDelete() {
        clickElement(confirmButton);
    }

    //判断购物车项是否被删除
    public boolean isItemDeleted(String goodsId) {
        //根据xPath查找a[onclick='deleteItem(goodsId)']元素不存在
        return !$(byXpath("//a[onclick='deleteItem(" + goodsId + ")']")).exists();
    }
}