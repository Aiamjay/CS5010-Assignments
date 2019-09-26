public class InvalidCouponException extends Exception {

  enum ErrorMsg {
    INVALID_PERCENT_OFF("Invalid percent off coupon"),
    INVALID_GET_FREE("The amount of free item after bought should be less than the amount of items bought.");

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
