package vehicle;

public interface ManualTransmission {

  /**
   * get the status of the vehicle.
   *
   * @return current status
   */
  public String getStatus();

  /**
   * get the current speed of the vehicle.
   *
   * @return current speed
   */
  public int getSpeed();

  /**
   * get the current gear of the vehicle.
   *
   * @return current speed
   */
  public int getGear();

  /**
   * increase the speed by a certain amount.
   *
   * @return the resulting transmission object.
   */
  public ManualTransmission increaseSpeed();

  /**
   * decrease the speed by a certain amount.
   *
   * @return the resulting transmission object.
   */
  public ManualTransmission decreaseSpeed();

  /**
   * increase the gear by a certain amount.
   *
   * @return the resulting transmission object.
   */
  public ManualTransmission increaseGear();

  /**
   * decrease the gear by a certain amount.
   *
   * @return the resulting transmission amount.
   */
  public ManualTransmission decreaseGear();
}