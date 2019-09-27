import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests for AmountOffCoupon
 */
public class AmountOffCouponTest {

  /**
   * Tests for amount off coupon.
   */
  @Test
  public void testsForAmountOffCoupon() throws InvalidCouponException {
    // note different item coupon cannot stack.
    AmountOffCoupon coupon1 = new AmountOffCoupon("basketball", true, 2f);
    AmountOffCoupon coupon2 = new AmountOffCoupon("pumpkin", true, 8f);
    assertNull(coupon1.stack(coupon2));

    // note if one coupon is not stackable, then a null will be returned.
    coupon1 = new AmountOffCoupon("basketball", true, 2f);
    coupon2 = new AmountOffCoupon("basketball", false, 8f);
    assertNull(coupon1.stack(coupon2));

    // note if both can be applied to same item and both are stackable.
    coupon1 = new AmountOffCoupon("basketball", true, 2f);
    coupon2 = new AmountOffCoupon("basketball", true, 8f);
    Coupon coupon = coupon1.stack(coupon2);
    assertNotNull(coupon);
    assertEquals(5.0f, coupon.getDiscountedPrice(1, 15f), 0.001);

    // note the amount of money an coupon can take off should no more than the price of the product.
    coupon1 = new AmountOffCoupon("basketball", true, 2f);
    assertEquals(0f, coupon1.getDiscountedPrice(1, 2f), 0.001);

    // note test coupon get description
    coupon1 = new AmountOffCoupon("basketball", false, 2f);
    assertEquals("$2.00 off item basketball", coupon1.getDescription());

    // note test coupon stack with different coupon
    try {
      PercentOffCoupon coupon3 = new PercentOffCoupon("basketball", true, 0.1f);
      assertNull(coupon1.stack(coupon3));
    } catch (InvalidCouponException e) {
      // avoid such exception to occur
    }

    // note test coupon stack with different coupon
    BuyGetFreeCoupon coupon4 = new BuyGetFreeCoupon("basketball", true, 3, 1);
    assertNull(coupon1.stack(coupon4));

    // note a stack stack with itself will return itself.
    assertEquals(coupon, coupon.stack(coupon));

    // note negative parameter
    try {
      coupon = new AmountOffCoupon("basketball", true, -10f);
      assertNull(coupon);
    } catch (InvalidCouponException e) {
      System.out.println(e);
    }
  }
}
