package com.zjut.doctor.tests;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebDriverRunner;
import com.zjut.doctor.pages.IndexPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.junit.jupiter.api.Assertions.assertTrue;

// page_url = http://localhost:28079/index
public class IndexTest extends BaseTest {
    private IndexPage indexPage = new IndexPage();
    String[] urlParts = null;
    String goodsId = null;

    @BeforeEach
    public void indexSetUp() {
        login(username, password, verifyCode);
        indexPage.openPage("http://localhost:28079/index");
    }

    @Test
    public void testVisible() {
        indexPage.nav.shouldBe(visible);//验证导航栏可见
        indexPage.divBanner.shouldBe(visible);//验证横幅可见
        indexPage.divSubBanner.shouldBe(visible);//验证热销商品可见
        indexPage.divFlash.shouldBe(visible);//验证新品可见
        indexPage.divRecommend.shouldBe(visible);//验证推荐商品可见
        indexPage.divSiteFooter.shouldBe(visible);//验证页脚可见
    }

    @Test
    public void testSortLists() {
        // 遍历所有一级分类链接
        for (SelenideElement firstLevelLink : indexPage.firstLevelLinks) {
            firstLevelLink.hover(); // Hover over the first level link

            // 获取显示出来的三级链接
            ElementsCollection thirdLevelLinks = indexPage.getThirdLevelLinks(firstLevelLink);
            if (thirdLevelLinks.isEmpty()) {
                System.out.println("No third level category links found under " + firstLevelLink.getText());
                System.out.println(firstLevelLink.getAttribute("href"));
                continue;
            }

            // 遍历三级链接并点击验证
            for (SelenideElement thirdLevelLink : thirdLevelLinks) {
                String href = thirdLevelLink.getAttribute("href");
                thirdLevelLink.click();
                indexPage.verifyUrl(href);
                back();
                firstLevelLink.hover();
            }
        }
    }

    @Test
    public void testHotGoods() {
        if (indexPage.hotGoods.isEmpty()) {
            System.out.println("No hot goods found");
            return;
        }
        //遍历热销商品
        for (SelenideElement hotGood : indexPage.hotGoods) {
            hotGood.hover();
            hotGood.click();
            back();
        }
    }

    @Test
    public void testNewGoods() {
        if (indexPage.newGoods.isEmpty()) {
            System.out.println("No new goods found");
            return;
        }
        //遍历新品
        for (SelenideElement newGood : indexPage.newGoods) {
            newGood.hover();
            newGood.click();
            back();
        }
    }

    @Test
    public void testRecommendGoods() {
        if (indexPage.recommendGoods.isEmpty()) {
            System.out.println("No recommend goods found");
            return;
        }
        //遍历推荐商品
        for (SelenideElement recommendGood : indexPage.recommendGoods) {
            recommendGood.hover();
            recommendGood.click();
            back();
        }
    }

    @Test
    public void testNavLink() {
        //点击秒杀链接
        indexPage.seckKillLink.click();
        indexPage.verifyUrl("http://localhost:28079/seckill");
        back();
        //点击优惠券链接
        indexPage.couponListLink.click();
        indexPage.verifyUrl("http://localhost:28079/couponList");
        back();
        //点击logo链接
        indexPage.linkLogo.click();
        indexPage.verifyUrl("http://localhost:28079/index");
    }

    @Test
    public void testSearch() throws UnsupportedEncodingException {
        String[] keywords = {"耳机", "手机", "电脑", "键盘", "鼠标"};
        for (String keyword : keywords) {
            indexPage.search(keyword);
            indexPage.verifyUrl("http://localhost:28079/search?keyword=" + indexPage.encodeUrl(keyword));
            back();
        }
    }

    @Test
    public void testAddGoods() {
        for (SelenideElement recommendGood : indexPage.recommendGoods) {
            recommendGood.hover();
            recommendGood.click();
            //System.out.println(WebDriverRunner.url());
            //获取url中最后一个/后的数字作为商品id
            urlParts = WebDriverRunner.url().split("/");
            goodsId = urlParts[urlParts.length - 1];
            indexPage.saveToCart(goodsId);
            indexPage.divAriaModal.shouldBe(visible);
            indexPage.buttonOK.click();
            back();
        }
    }

    @Test
    public void testSaveAndGoCart() {
        for (SelenideElement recommendGood : indexPage.recommendGoods) {
            recommendGood.hover();
            recommendGood.click();
            //获取url中最后一个/后的数字作为商品id
            urlParts = WebDriverRunner.url().split("/");
            goodsId = urlParts[urlParts.length - 1];
            indexPage.saveAndGoCart(goodsId);
            indexPage.divSwalModal.shouldBe(visible);
            indexPage.swalCancelButton.click();
            indexPage.divSwalModal.shouldNotBe(visible);
            indexPage.saveAndGoCart(goodsId);
            indexPage.divSwalModal.shouldBe(visible);
            indexPage.swalConfirmButton.click();
            indexPage.verifyUrl("http://localhost:28079/shop-cart");
            back();
            back();
        }
    }
}