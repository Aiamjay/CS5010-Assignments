import com.sun.istack.internal.Nullable;

public abstract class AbstractCoupon implements Coupon {

  protected boolean stackable;
  protected String itemName;

  protected AbstractCoupon(String itemName, boolean stackable) {
    this.itemName = itemName;
    this.stackable = stackable;
  }

  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public float getDiscountedPrice(int quantity, float oriPrice) {
    return 0;
  }

  @Override
  @Nullable
  public Coupon stack(Coupon other) {
    return null;
  }

  /**
   * Try to stack with a amount-off coupon. May return null, if one of two is in-stackable.
   *
   * @param other other amount-off coupon
   * @return null or a amount-off coupon.
   */
  @Nullable
  protected AmountOffCoupon stackToAmountOffCoupon(AmountOffCoupon other) {
    return null;
  }

  /**
   * Try to stack with a buy-get-free coupon. May return null, if one of two is in-stackable.
   *
   * @param other other buy-get-free coupon
   * @return null or a buy-get-free coupon.
   */
  @Nullable
  protected BuyGetFreeCoupon stackToBuyGetFreeCoupon(BuyGetFreeCoupon other) {
    return null;
  }

  /**
   * Try to stack with a percent-off coupon. May return null, if one of two is in-stackable.
   *
   * @param other other percent-off coupon
   * @return null or a percent-off coupon.
   */
  @Nullable
  protected PercentOffCoupon stackToPercentOffCoupon(PercentOffCoupon other) {
    return null;
  }

  @Override
  public boolean isStackable() {
    return stackable;
  }
}
