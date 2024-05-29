package com.zjut.doctor.tests;

import com.codeborne.selenide.*;
import com.codeborne.selenide.conditions.Or;
import com.zjut.doctor.pages.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.UnsupportedEncodingException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.back;

// page_url = http://localhost:28079/index
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("主流程测试")
public class MainProcessTest {
    private final CartPage cartPage = new CartPage();
    private final IndexPage indexPage = new IndexPage();
    private final LoginPage loginPage = new LoginPage();
    private final PersonalPage personalPage = new PersonalPage();
    private final PersonalSideBarPage personalSideBarPage = new PersonalSideBarPage();
    private final PayPage payPage = new PayPage();
    private final CouponListPage couponListPage = new CouponListPage();
    private final SeckillPage seckillPage = new SeckillPage();

    private String[] urlParts = null;
    private String goodsId = null;

    @BeforeAll
    public static void setUp() {
        // 设置 Chrome 的选项以最大化启动
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // 这个参数会使浏览器窗口最大化

        // 将这些选项应用到 Selenide 配置
        Configuration.browserCapabilities = options;
        Configuration.browser = "chrome";
    }

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("个人中心测试")
    class PersonalInfoTest {
        @BeforeAll
        public static void personalSetUp() {
            BaseTest.login("13866668888", "13866668888", "abcd");
        }

        @Test
        @Order(1)
        @DisplayName("进入个人中心")
        public void testGoToPersonalInfo() {
            personalSideBarPage.clickDropDown();
            personalSideBarPage.clickPersonal();
            personalSideBarPage.verifyUrl("http://localhost:28079/personal");
        }

        @Test
        @Order(2)
        @DisplayName("修改个人信息")
        public void testChangePersonalInfo() {
            personalPage.clickChangeInfo();
            personalPage.changeInfo("testMe!", "MyTest", "浙江工业大学");
            personalPage.divModalDialog.shouldNotBe(visible);
            personalPage.refreshPage();
            //assert personalPage.verifyInfo("testMe!","MyTest","浙江工业大学");
            assert personalPage.verifyInfo("testMe!", "MyTest", "浙江工业大学");
        }

        @Test
        @Order(3)
        @DisplayName("进入个人订单")
        public void testGoToOrder() {
            personalSideBarPage.clickDropDown();
            personalSideBarPage.clickOrders();
            personalSideBarPage.verifyUrl("http://localhost:28079/orders");
        }

        @Test
        @Order(4)
        @DisplayName("进入我的优惠券")
        public void testGoToMyCoupons() {
            personalSideBarPage.clickDropDown();
            personalSideBarPage.clickMyCoupons();
            personalSideBarPage.verifyUrl("http://localhost:28079/myCoupons");
        }

        @Test
        @Order(5)
        @DisplayName("退出登录")
        public void testLogout() {
            personalSideBarPage.clickDropDown();
            personalSideBarPage.clickLogout();
            personalSideBarPage.verifyUrl("http://localhost:28079/logout");
        }
    }

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("首页完整性和链接跳转测试")
    class IndexTest {
        @Test
        @Order(1)
        @DisplayName("登录")
        public void testLogin() {
            loginPage.openPage("http://localhost:28079/login.html");
            loginPage.login("13866668888", "13866668888", "abcd"); // 处理实际验证码逻辑
            loginPage.verifyUrl("http://localhost:28079/index");
        }

        @Test
        @Order(2)
        @DisplayName("首页完整性测试")
        public void testVisible() {
            indexPage.nav.shouldBe(visible);//验证导航栏可见
            indexPage.divBanner.shouldBe(visible);//验证横幅可见
            indexPage.divSubBanner.shouldBe(visible);//验证热销商品可见
            indexPage.divFlash.shouldBe(visible);//验证新品可见
            indexPage.divRecommend.shouldBe(visible);//验证推荐商品可见
            indexPage.divSiteFooter.shouldBe(visible);//验证页脚可见
        }

        @Test
        @Order(3)
        @DisplayName("搜索功能测试")
        public void testSearch() throws UnsupportedEncodingException {
            String[] keywords = {"耳机", "手机", "电脑", "键盘", "鼠标"};
            for (String keyword : keywords) {
                indexPage.search(keyword);
                indexPage.verifyUrl("http://localhost:28079/search?keyword=" + indexPage.encodeUrl(keyword));
                back();
            }
        }

        @Test
        @Order(4)
        @DisplayName("导航链接测试")
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
        @Order(5)
        @DisplayName("分类目录链接跳转验证")
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
        @Order(6)
        @DisplayName("热销商品链接测试")
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
        @Order(7)
        @DisplayName("新品链接测试")
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
        @Order(8)
        @DisplayName("推荐商品链接测试")
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
        @Order(9)
        @DisplayName("立即选购测试")
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

        @Test
        @Order(10)
        @DisplayName("清空购物车")
        public void testClearCart() {
            indexPage.cartLogo.click();
            String[] goodsInfo = new String[cartPage.goodsDelete.size()];
            indexPage.verifyUrl("http://localhost:28079/shop-cart");
            for (SelenideElement goodsDelete : cartPage.goodsDelete) {
                //从css中提取商品id,css形如a[onclick='deleteItem(1)']
                String goodsId = goodsDelete.getAttribute("onclick").split("\\(")[1].split("\\)")[0];
                //向goodsInfo中添加商品id
                goodsInfo[cartPage.goodsDelete.indexOf(goodsDelete)] = goodsId;
            }
            for (String goodsId : goodsInfo) {
                cartPage.deleteItem(goodsId);
                cartPage.confirmDelete();
                cartPage.isItemDeleted(goodsId);
            }
        }
    }

    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("正常购物流程测试")
    class NormalShoppingTest {
        @BeforeAll
        public static void normalShoppingSetUp() {
            BaseTest.login("13866668888", "13866668888", "abcd");
        }

        @Test
        @Order(1)
        @DisplayName("加入购物车")
        public void testAddGoods() {
            for (SelenideElement recommendGood : indexPage.recommendGoods) {
                recommendGood.hover();
                recommendGood.click();
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
        @Order(2)
        @DisplayName("随机修改商品数量并随机删除商品")
        public void testUpdateAndDeleteGoods() {
            indexPage.cartLogo.click();
            String[] goodsInfo = new String[cartPage.goodsDelete.size()];
            indexPage.verifyUrl("http://localhost:28079/shop-cart");
            for (SelenideElement goodsDelete : cartPage.goodsDelete) {
                //从css中提取商品id,css形如a[onclick='deleteItem(1)']
                String goodsId = goodsDelete.getAttribute("onclick").split("\\(")[1].split("\\)")[0];
                //向goodsInfo中添加商品id
                goodsInfo[cartPage.goodsDelete.indexOf(goodsDelete)] = goodsId;
            }
            //随机修改一般商品的数量，数量为1-5，随机删除一半商品
            for (String goodsId : goodsInfo) {
                if (Math.random() > 0.5) {
                    cartPage.updateItemQuantity(goodsId, String.valueOf((int) (Math.random() * 5) + 1));
                } else {
                    cartPage.deleteItem(goodsId);
                    cartPage.confirmDelete();
                    cartPage.isItemDeleted(goodsId);
                }
            }
        }

        @Test
        @Order(3)
        @DisplayName("领取优惠券")
        public void testGetCoupon() {
            indexPage.openPage("http://localhost:28079/index");
            indexPage.couponListLink.click();
            indexPage.verifyUrl("http://localhost:28079/couponList");
            if (couponListPage.couponList.isEmpty()) {
                System.out.println("No coupon found");
                return;
            }
            for (SelenideElement coupon : couponListPage.couponList) {
                coupon.click();
                couponListPage.couponModal.shouldBe(visible);
                couponListPage.OkButton.click();
                couponListPage.couponModal.shouldNotBe(visible);
            }
        }

        @Test
        @Order(4)
        @DisplayName("结算")
        public void testPay() {
            personalSideBarPage.clickDropDown();
            personalSideBarPage.clickPersonal();
            personalPage.clickChangeInfo();
            personalPage.changeInfo("testMe!", "MyTest", "铜鸟一觉醒来");
            indexPage.cartLogo.click();
            indexPage.verifyUrl("http://localhost:28079/shop-cart");
            cartPage.clickSettle();
            payPage.verifyUrl("http://localhost:28079/shop-cart/settle");
            payPage.EditAddressButton.click();
            payPage.inputAddress.shouldBe(visible).setValue("浙江工业大学留和路288号");
            payPage.buttonSave.click();
            if (!payPage.couponList.isEmpty()) {
                //随机选择一张优惠券
                payPage.couponList.get((int) (Math.random() * payPage.couponList.size())).click();
            }
            payPage.SaveOrderButton.click();
            payPage.payOrder.click();
            payPage.WeixinPay.click();
            payPage.paySuccess.click();
            assert (payPage.OrderStatus.getText().equals("已支付"));
            payPage.CancelOrder.click();
            payPage.OkButton.shouldBe(visible).click();
            assert (payPage.OrderStatus.getText().equals("手动关闭"));
        }
    }

    @Nested
    @Order(4)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("秒杀流程测试")
    class SeckillTest {
        @BeforeAll
        public static void seckillSetUp() {
            BaseTest.login("13866668888", "13866668888", "abcd");
        }

        @Test
        @Order(1)
        @DisplayName("进入秒杀页面")
        public void testGoToSeckill() {
            indexPage.openPage("http://localhost:28079/index");
            indexPage.seckKillLink.click();
            indexPage.verifyUrl("http://localhost:28079/seckill");
        }

        @Test
        @Order(2)
        @DisplayName("秒杀商品")
        public void testSeckill() {
            if (seckillPage.seckillList.isEmpty()) {
                System.out.println("No seckill goods found");
                return;
            }
            //随机秒杀一件商品
            SelenideElement seckillGood = seckillPage.seckillList.get((int) (Math.random() * seckillPage.seckillList.size()));
            seckillGood.click();
            seckillPage.buttonKill.click();
            assert payPage.OrderStatus.getText().equals("已支付");
            payPage.CancelOrder.click();
            payPage.OkButton.shouldBe(visible).click();
            assert payPage.OrderStatus.getText().equals("手动关闭");
        }
    }

}