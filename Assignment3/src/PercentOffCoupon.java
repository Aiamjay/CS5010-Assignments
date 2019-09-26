import org.omg.CORBA.DynAnyPackage.Invalid;

public class PercentOffCoupon extends AbstractCoupon {

  private float percentOff;

  public PercentOffCoupon(String itemName, boolean stackable, float percent) throws InvalidCouponException {
    super(itemName, stackable);
    if (percent > 1.0f) {
      throw new InvalidCouponException(InvalidCouponException.ErrorMsg.INVALID_PERCENT_OFF.getErrorMsg());
    }
    this.percentOff = percent;
  }

  @Override
  public String getDescription() {
    return String.format("%.1f%%off item %s", percentOff * 100, itemName);
  }

  @Override
  public float getDiscountedPrice(int quantity, float oriPrice) {
    return (1f - percentOff) * oriPrice;
  }

  @Override
  public Coupon stack(Coupon other) {
    if (this == other) {
      return this;
    }
    if (other instanceof AbstractCoupon) {
      return ((AbstractCoupon) other).stackToPercentOffCoupon(this);
    }
    return null;
  }

  @Override
  protected PercentOffCoupon stackToPercentOffCoupon(PercentOffCoupon other) {
    if (other.stackable && other.itemName.equals(this.itemName) && this.stackable) {
      try {
        return new PercentOffCoupon(this.itemName, true,
                Math.min(1.0f, this.percentOff + other.percentOff));
      } catch (InvalidCouponException e) {
        // note there cannot be a exception, since we specifically avoid such exception.
      }
    }
    return null;
  }
}
