import com.sun.istack.internal.Nullable;

public class AmountOffCoupon extends AbstractCoupon {

  private float amountOffPerUnit;

  /**
   * Get a amount-off coupon of a item.
   *
   * @param itemName  the item that coupon can apply to.
   * @param stackable whether this coupon can used with some other coupon of the same kind.
   * @param off       how much money off per item.
   */
  public AmountOffCoupon(String itemName, boolean stackable, float off) {
    super(itemName, stackable);
    this.amountOffPerUnit = off;
  }

  @Override
  public float getDiscountedPrice(int quantity, float oriPrice) {
    return Math.max(0f, oriPrice - amountOffPerUnit) * quantity;
  }

  @Override
  public String getDescription() {
    return String.format("$%.2f off item %s", amountOffPerUnit, itemName);
  }

  @Override
  @Nullable
  public Coupon stack(Coupon other) {
    if (this == other) {
      return this;
    }
    if (other instanceof AbstractCoupon) {
      return ((AbstractCoupon) other).stackToAmountOffCoupon(this);
    }
    return null;
  }

  @Override
  @Nullable
  protected AmountOffCoupon stackToAmountOffCoupon(AmountOffCoupon other) {
    // note only both coupon are stackable and both can applied to the same item.
    if (other.stackable && this.stackable && other.itemName.equals(this.itemName)) {
      return new AmountOffCoupon(this.itemName, true,
              this.amountOffPerUnit + other.amountOffPerUnit);
    }
    return null;
  }
}
