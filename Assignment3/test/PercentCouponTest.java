import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class PercentCouponTest {

  @Test(expected = InvalidCouponException.class)
  public void testPercentCoupon() throws InvalidCouponException {
    PercentOffCoupon coupon = new PercentOffCoupon("pumpkin", true, 0.1f);
    // note percent-off, normal test
    coupon = new PercentOffCoupon("pumpkin", true, 0.2f);
    assertEquals(8.0f, coupon.getDiscountedPrice(1, 10f), 0.001);
    assertEquals("20.0%off item pumpkin", coupon.getDescription());
    // note a stack cannot stack with iteself
    assertEquals(coupon, coupon.stack(coupon));

    // note minimal price test
    coupon = new PercentOffCoupon("basketball", true, 1f);
    assertEquals(0f, coupon.getDiscountedPrice(1, 10f), 0.001);

    // note coupon stack, if one coupon is no stackable
    PercentOffCoupon coupon1 = new PercentOffCoupon("pumpkin", true, 0.8f);
    PercentOffCoupon coupon2 = new PercentOffCoupon("pumpkin", false, 0.1f);
    assertNull(coupon1.stack(coupon2));

    // note coupon stack, if there are specified to different product.
    coupon1 = new PercentOffCoupon("basketball", true, 0.1f);
    coupon2 = new PercentOffCoupon("pumpkin", true, 0.2f);
    assertNull(coupon1.stack(coupon2));

    // note coupon stack, if the total percent exceed 100%
    coupon1 = new PercentOffCoupon("basketball", true, 0.9f);
    coupon2 = new PercentOffCoupon("basketball", true, 0.3f);
    assertEquals(0f,
            (coupon1.stack(coupon2)).getDiscountedPrice(1, 10f),
            0.001);
    // note if coupon percent-off is 100%, then it is not stackable.
    assertFalse((coupon1.stack(coupon2)).isStackable());

    // note different coupon cannot stack
    AmountOffCoupon coupon3 = new AmountOffCoupon("basketball", true, 2f);
    coupon = new PercentOffCoupon("basketball", true, 0.2f);
    assertNull(coupon.stack(coupon3));

    // note percent-off cannot exceed 100% percent, this can throw an exception.
    try {
      coupon = new PercentOffCoupon("basketball", true, 1.1f);
    } catch (InvalidCouponException e) {
      System.out.println(e);
    }
  }

}
