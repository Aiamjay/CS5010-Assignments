package vehicle;

/**
 * RegularManualTransmission that implements the ManualTransmission interface, and has 5 gear for
 * drivers. Each time, increaseSpeed function would increase the speed with 1.
 */
public class RegularManualTransmission implements ManualTransmission {

  private int currentSpeed;     // speed of the vehicle
  private TransmissionStatus currentStatus;
  private int currentGear;
  private final int maximumSpeed;
  private final int[][] speedRange;
  private static int MAXIMUM_GEAR = 5;

  /**
   * constructor for RegularManualTransmission.
   *
   * @param l1 lowest speed in gear1
   * @param h1 highest speed in gear1
   * @param l2 lowest speed in gear2
   * @param h2 highest speed in gear2
   * @param l3 lowest speed in gear3
   * @param h3 highest speed in gear3
   * @param l4 lowest speed in gear4
   * @param h4 highest speed in gear4
   * @param l5 lowest speed in gear5
   * @param h5 highest speed in gear5
   * @throws IllegalArgumentException arguments for gears are not valid.
   */
  public RegularManualTransmission(int l1, int h1,
                                   int l2, int h2,
                                   int l3, int h3,
                                   int l4, int h4,
                                   int l5, int h5)
          throws IllegalArgumentException {

    if (l1 > h1 | l2 > h2 | l3 > h3 | l4 > h4 | l5 > h5) {
      throw new IllegalArgumentException(
              "For each gear, the lowest speed should be no more that the highest speed!");
    }

    if (l1 >= l2 | l2 >= l3 | l3 >= l4 | l4 >= l5) {
      throw new IllegalArgumentException(
              "For each gear, the lower speed of current gear should be larger than "
                      + "that of the previous ones");
    }
    if (l1 != 0) {
      throw new IllegalArgumentException(
              "The lowest speed for gear1 should be 0!");
    }

    // important overlapping check
    if (l3 <= h1 | l4 <= h2 | l5 <= h3) {
      throw new IllegalArgumentException("Only adjacent gear speed range could have overlap!");
    }

    if (l2 > h1 | l3 > h2 | l4 > h3 | l5 > h4) {
      throw new IllegalArgumentException("The gear ranges should have overlapping.");
    }

    currentSpeed = 0;
    currentStatus = TransmissionStatus.OK;
    currentGear = 0;
    maximumSpeed = h5;
    speedRange = new int[MAXIMUM_GEAR][2];
    speedRange[0][0] = l1;
    speedRange[0][1] = h1;
    speedRange[1][0] = l2;
    speedRange[1][1] = h2;
    speedRange[2][0] = l3;
    speedRange[2][1] = h3;
    speedRange[3][0] = l4;
    speedRange[3][1] = h4;
    speedRange[4][0] = l5;
    speedRange[4][1] = h5;
  }

  /**
   * Different status for the RegularManualTransmission.
   */
  public enum TransmissionStatus {

    // important a object should start with the 0 speed and s status of "OK"
    OK("OK: everything is OK."),
    MAY_INCREASE_GEAR("OK: you may increase the gear."),
    MAY_DECREASE_GEAR("OK: you may decrease the gear."),
    NO_SPEED_INCREASE("Cannot increase speed, increase gear first."),
    NO_SPEED_DECREASE("Cannot decrease speed, decrease gear first."),
    NO_GEAR_INCREASE("Cannot increase gear, increase speed first."),
    NO_GEAR_DECREASE("Cannot decrease gear, decrease speed first."),
    MAXIMUM_SPEED("Cannot increase speed. Reached maximum speed."),
    MINIMUM_SPEED("Cannot decrease speed. Reached minimum speed."),
    MAXIMUM_GEAR("Cannot increase gear. Reached maximum gear."),
    MINIMUM_GEAR("Cannot decrease gear. Reached minimum gear.");

    private String status;

    TransmissionStatus(String status) {
      this.status = status;
    }

    /**
     * return the description of status.
     * @return string description of status.
     */
    public String getStatus() {
      return this.status;
    }
  }

  @Override
  public String getStatus() {
    return currentStatus.getStatus();
  }

  @Override
  public int getSpeed() {
    return currentSpeed;
  }

  @Override
  public int getGear() {
    return currentGear + 1;
  }

  @Override
  public ManualTransmission increaseSpeed() {
    // note increasing the speed by 1
    currentSpeed += 1;
    if (currentSpeed > maximumSpeed) {
      currentSpeed = maximumSpeed;
      currentStatus = TransmissionStatus.MAXIMUM_SPEED;
    } else {
      if (currentSpeed > speedRange[currentGear][1]) {
        // note the current speed is not within the speed range of current range
        currentSpeed -= 1;
        currentStatus = TransmissionStatus.NO_SPEED_INCREASE;
      } else if (currentGear + 1 < MAXIMUM_GEAR && currentSpeed >= speedRange[currentGear + 1][0]) {
        currentStatus = TransmissionStatus.MAY_INCREASE_GEAR;
      } else {
        currentStatus = TransmissionStatus.OK;
      }
    }
    return this;
  }

  @Override
  public ManualTransmission decreaseSpeed() {
    currentSpeed -= 1;
    if (currentSpeed <= 0) {
      currentSpeed = 0;
      currentStatus = TransmissionStatus.MINIMUM_SPEED;
    } else {
      if (currentSpeed < speedRange[currentGear][0]) {
        // note current speed is less than the lowest of the range of current gear
        currentSpeed += 1;
        currentStatus = TransmissionStatus.NO_SPEED_DECREASE;
      } else if (currentGear > 0 && speedRange[currentGear - 1][1] >= currentSpeed) {
        currentStatus = TransmissionStatus.MAY_DECREASE_GEAR;
      } else {
        currentStatus = TransmissionStatus.OK;
      }
    }
    return this;
  }

  @Override
  public ManualTransmission increaseGear() {
    currentGear += 1;
    if (currentGear > MAXIMUM_GEAR - 1) {
      currentGear = MAXIMUM_GEAR - 1;
      currentStatus = TransmissionStatus.MAXIMUM_GEAR;
    } else {
      if (speedRange[currentGear][0] > currentSpeed) {
        // note need to increase the speed first to increase the gear
        currentGear -= 1;
        currentStatus = TransmissionStatus.NO_GEAR_INCREASE;
      } else {
        currentStatus = TransmissionStatus.OK;
      }
    }
    return this;
  }

  @Override
  public ManualTransmission decreaseGear() {
    currentGear -= 1;
    if (currentGear < 0) {
      currentGear = 0;
      // note only when current gear is 1, and still wanna to decrease the gear
      currentStatus = TransmissionStatus.MINIMUM_GEAR;
    } else {
      if (speedRange[currentGear][1] < currentSpeed) {
        currentGear += 1;
        currentStatus = TransmissionStatus.NO_GEAR_DECREASE;
      } else {
        currentStatus = TransmissionStatus.OK;
      }
    }
    return this;
  }
}
