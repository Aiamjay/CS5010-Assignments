import com.sun.istack.internal.Nullable;

public interface Coupon {

  /**
   * Get the description of the coupon.
   *
   * @return string description
   */
  String getDescription();

  /**
   * Get the discounted price given the origin price and the quantity purchased.
   *
   * @param quantity how many unit of product purchased
   * @param oriPrice original price of the product
   * @return discounted price of the item purchased
   */
  float getDiscountedPrice(int quantity, float oriPrice);

  /**
   * If you have two coupon, try to get stacked coupon when providing two coupons. Note: if the
   * coupon provided is not the same type with current coupon, null will be returned.
   *
   * @param other other coupon.
   * @return the stacked coupon if possible.
   */
  @Nullable
  Coupon stack(Coupon other);

  /**
   * Whether this coupon is stackable.
   *
   * @return stackable
   */
  boolean isStackable();
}
