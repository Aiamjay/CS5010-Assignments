import com.sun.istack.internal.Nullable;

public class BuyGetFreeCoupon extends AbstractCoupon {

  private int amountBuy;
  private int amountFree;

  public BuyGetFreeCoupon(String itemName, boolean stackable, int amountBuy, int amountFree) throws InvalidCouponException {
    super(itemName, stackable);
    this.amountBuy = amountBuy;
    this.amountFree = amountFree;
  }

  @Override
  public String getDescription() {
    return String.format("Buy %d get %d free for item %s", this.amountBuy, this.amountFree, this.itemName);
  }

  @Override
  public float getDiscountedPrice(int quantity, float oriPrice) {

    int temp = quantity / (amountBuy + amountFree);
    int remainder = quantity % (amountBuy + amountFree);
    return oriPrice * temp * amountBuy + (Math.min(amountBuy, remainder) * oriPrice);
  }

  @Override
  @Nullable
  public Coupon stack(Coupon other) {
    if (this == other) {
      return this;
    }
    if (other instanceof AbstractCoupon) {
      return ((AbstractCoupon) other).stackToBuyGetFreeCoupon(this);
    }
    return null;
  }

  @Override
  @Nullable
  protected BuyGetFreeCoupon stackToBuyGetFreeCoupon(BuyGetFreeCoupon other) {
    if (other.stackable && this.stackable && other.itemName.equals(this.itemName)) {
      float diff = ((float) this.amountFree / (float) this.amountBuy)
              - ((float) other.amountFree / (float) other.amountBuy);
      if (Math.abs(diff) < 0.001) {
        return other.amountBuy > this.amountBuy ? this : other;
      } else {
        return diff < 0f ? other : this;
      }
    }
    return null;
  }
}
