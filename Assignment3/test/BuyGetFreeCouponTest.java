import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BuyGetFreeCouponTest {

  @Test
  public void testBuyGetCoupon() throws InvalidCouponException {
    // note test the buy-get-free coupon
    BuyGetFreeCoupon coupon = new BuyGetFreeCoupon("basketball", true,
            10, 1);
    assertEquals(9f, coupon.getDiscountedPrice(9, 1f), 0.01f);
    assertEquals(10f, coupon.getDiscountedPrice(11, 1f), 0.01f);
    assertEquals(20f, coupon.getDiscountedPrice(21, 1f), 0.01f);
    assertEquals(20f, coupon.getDiscountedPrice(22, 1f), 0.01f);
    assertEquals(22f, coupon.getDiscountedPrice(24, 1f), 0.01f);

    // note test get description
    assertEquals("Buy 10 get 1 free for item basketball", coupon.getDescription());

    coupon = new BuyGetFreeCoupon("basketball", true,
            1, 100);
    assertEquals(1f, coupon.getDiscountedPrice(2, 1f), 0.01f);
    assertEquals(1f, coupon.getDiscountedPrice(101, 1f), 0.01f);
    assertEquals(2f, coupon.getDiscountedPrice(102, 1f), 0.01f);

    // note a stack cannot stack with iteself
    assertEquals(coupon, coupon.stack(coupon));

    // note coupon stack, if one coupon is no stackable
    BuyGetFreeCoupon coupon1 = new BuyGetFreeCoupon("pumpkin", true, 10, 1);
    BuyGetFreeCoupon coupon2 = new BuyGetFreeCoupon("pumpkin", false, 10, 1);
    assertNull(coupon1.stack(coupon2));

    // note coupon stack, if there are specified to different product.
    coupon1 = new BuyGetFreeCoupon("basketball", true, 10, 1);
    coupon2 = new BuyGetFreeCoupon("pumpkin", true, 10, 1);
    assertNull(coupon1.stack(coupon2));

    // note coupon stack, the stack result is the larger amount free / amount buy
    coupon1 = new BuyGetFreeCoupon("basketball", true, 10, 1);
    coupon2 = new BuyGetFreeCoupon("basketball", true, 10, 2);
    assertEquals(coupon2, coupon1.stack(coupon2));

    // note coupon stack, the stack result is smaller amount buy
    coupon1 = new BuyGetFreeCoupon("basketball", true, 10, 1);
    coupon2 = new BuyGetFreeCoupon("basketball", true, 20, 2);
    assertEquals(coupon1, coupon1.stack(coupon2));

    // note different coupon cannot stack
    PercentOffCoupon coupon3 = new PercentOffCoupon("basketball", true, 0.1f);
    coupon = new BuyGetFreeCoupon("basketball", true, 10, 1);
    assertNull(coupon.stack(coupon3));

    // note different coupon cannot stack
    AmountOffCoupon coupon4 = new AmountOffCoupon("basketball", true, 10f);
    coupon = new BuyGetFreeCoupon("basketball", true, 10, 1);
    assertNull(coupon.stack(coupon4));
  }
}
