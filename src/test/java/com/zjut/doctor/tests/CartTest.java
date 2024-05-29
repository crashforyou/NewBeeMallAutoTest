package com.zjut.doctor.tests;

import com.codeborne.selenide.Condition;
import com.zjut.doctor.pages.CartPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;

// page_url = http://localhost:28079/shop-cart
public class CartTest extends BaseTest {
    private CartPage cartPage = new CartPage();

    @BeforeEach
    public void cartSetUp() {
        login(username, password, verifyCode);
        cartPage.openPage("http://localhost:28079/shop-cart");
    }

    @Test
    public void testGoToSettle() {
        cartPage.clickSettle();
        cartPage.verifyUrl("http://localhost:28079/shop-cart/settle");
    }

    @Test
    public void testUpdateItemQuantity() {
        cartPage.updateItemQuantity("223", "2");
        //检查更新后的状态
        $("#goodsCount223").shouldHave(Condition.value("2"));
    }

    @Test
    public void testGoToShop() {
        cartPage.clickShop();
        cartPage.verifyUrl("http://localhost:28079/index");
    }

    @Test
    public void testDeleteItem() {
        cartPage.deleteItem("223");
        cartPage.confirmDelete();
        //判断是否从购物车中删除
        assert (cartPage.isItemDeleted("223"));
    }
}