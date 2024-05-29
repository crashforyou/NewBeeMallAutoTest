package com.zjut.doctor.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/index
public class IndexPage extends BasePage {
    public SelenideElement divBanner = $("#banner");
    public SelenideElement nav = $("#nav");
    public SelenideElement divSubBanner = $("#sub_banner");
    public SelenideElement divFlash = $("#flash");
    public SelenideElement divRecommend = $("#recommend");
    public SelenideElement divSiteFooter = $("div[class='site-footer']");

    public ElementsCollection firstLevelLinks = $$("h3 a");
    public ElementsCollection hotGoods = divSubBanner.$$(".hot-image a");
    public ElementsCollection newGoods = divFlash.$$(" li a");
    public ElementsCollection recommendGoods = divRecommend.$$("li a");
    public ElementsCollection thirdLevelLinks = divBanner.$$(".item-list .subitem dd em a");

    public SelenideElement seckKillLink = $x("//a[@href='/seckill']");
    public SelenideElement couponListLink = $x("//a[@href='/couponList']");
    public SelenideElement linkLogo = $("a[class='logo']");

    public SelenideElement inputKeyword = $("#keyword");
    public SelenideElement searchButton = $("div[onclick='search()']");

    public SelenideElement saveAndGoCartButton = null;
    public SelenideElement addToCartButton = null;

    public SelenideElement divSwalModal = $("div[aria-modal='true']");
    public SelenideElement swalCancelButton = $("button[class$='swal-button--cancel']");
    public SelenideElement swalConfirmButton = $("button[class$='swal-button--confirm']");

    public SelenideElement divAriaModal = $("div[aria-modal='true']");
    public SelenideElement buttonCancel = $("button[tabindex='0']");
    public SelenideElement buttonOK = $("button[class*='swal-button--confirm']");

    public SelenideElement cartLogo = $("div[class='shopcart'] a");


    public ElementsCollection getThirdLevelLinks(SelenideElement firstLevelLink) {
        return firstLevelLink.closest(".item").$$("em a").filter(visible);
    }

    public SelenideElement getDivBanner() {
        return divBanner;
    }

    // 搜索商品
    public void search(String keyword) {
        inputKeyword.setValue(keyword);
        searchButton.click();
    }

    //立即选购
    public void saveAndGoCart(String goodsId) {
        saveAndGoCartButton = $(byCssSelector("input[onclick='saveAndGoCart(" + goodsId + ")']"));
        saveAndGoCartButton.click();
    }

    //加入购物车
    public void saveToCart(String goodsId) {
        addToCartButton = $(byCssSelector("input[onclick='saveToCart(" + goodsId + ")']"));
        addToCartButton.click();
    }
}