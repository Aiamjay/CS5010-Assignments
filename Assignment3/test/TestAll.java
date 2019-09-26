import org.junit.Test;

public class TestAll {
  @Test
  public void testAll() throws InvalidCouponException {
    AmountOffCouponTest test1 = new AmountOffCouponTest();
    PercentCouponTest test2 = new PercentCouponTest();
    BuyGetFreeCouponTest test3 = new BuyGetFreeCouponTest();

    test1.testsForAmountOffCoupon();
    test2.testPercentCoupon();
    test3.testBuyGetCoupon();
  }
}
