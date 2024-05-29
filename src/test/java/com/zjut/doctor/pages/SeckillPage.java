package com.zjut.doctor.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = http://localhost:28079/seckill
public class SeckillPage extends BasePage {
    public ElementsCollection seckillList = $$("#seckill-list .item_card_frame");
    public SelenideElement buttonKill = $("#killBtn");
}