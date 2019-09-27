public class InvalidCouponException extends Exception {

  enum ErrorMsg {
    INVALID_AMOUNT_OFF_PARAMETER("The amount of money off could not be negative."),
    INVALID_PERCENT_OFF("Invalid percent off coupon");

    private String msg;

    ErrorMsg(String msg) {
      this.msg = msg;
    }

    public String getErrorMsg() {
      return this.msg;
    }
  }

  public InvalidCouponException(String msg) {
    super(msg);
  }
}
