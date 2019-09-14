package vehicle;

public class RegularManualTransmission implements ManualTransmission {

    private int mCurrentSpeed;     // speed of the vehicle
    private TransmissionStatus mCurrentStatus;
    private int mCurrentGear;
    private final int mMaximumSpeed;
    private final int[][] mSpeedRange;
    private static int MAXIMUM_GEAR = 5;

    /**
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
                    "For each gear, the lower speed of the current gear should be large the that of the previous ones");
        }
        if (l1 != 0) {
            throw new IllegalArgumentException(
                    "The lowest speed for gear1 should be 0!");
        }

        // important overlapping check
        if (l3 <= h1 | l4 <= h2 | l5 <= h3) {
            throw new IllegalArgumentException(
                    "Only adjacent gear speed range could have overlap!");
        }

        if (l2 > h1 | l3 > h2 | l4 > h3 | l5 > h4) {
            throw new IllegalArgumentException(
                    "The gear ranges should have overlapping.");
        }

        mCurrentSpeed = 0;
        mCurrentStatus = TransmissionStatus.OK;
        mCurrentGear = 0;
        mMaximumSpeed = h5;
        mSpeedRange = new int[MAXIMUM_GEAR][2];
        mSpeedRange[0][0] = l1;
        mSpeedRange[0][1] = h1;
        mSpeedRange[1][0] = l2;
        mSpeedRange[1][1] = h2;
        mSpeedRange[2][0] = l3;
        mSpeedRange[2][1] = h3;
        mSpeedRange[3][0] = l4;
        mSpeedRange[3][1] = h4;
        mSpeedRange[4][0] = l5;
        mSpeedRange[4][1] = h5;
    }

    /**
     * Status of the vehicle
     */
    public enum TransmissionStatus {

        // important a object should start with the 0 speed and s status of "OK"
        OK("OK: everything is OK."),
        MAY_INCREASE_GEAR("the speed has increased successfully, but it's now within the range of next gear"),
        MAY_DECREASE_GEAR("the speed has decreased successfully, but it's now within the range of previous gear"),
        NO_SPEED_INCREASE("Cannot increase the speed, increase gear first. the intended speed is too high for the current gear"),
        NO_SPEED_DECREASE("the speed cannot be decreased if the gear decreased first"),
        NO_GEAR_INCREASE("the gear cannot be increased unless the speed is increased first. the current speed is too low for the next gear."),
        NO_GEAR_DECREASE("the gear cannot be decreased unless the speed is decreased first, the current speed is too high for the previous gear"),
        MAXIMUM_SPEED("reaches the maximum speed"),
        MINIMUM_SPEED("Reached the minimum speed"),
        MAXIMUM_GEAR("Reaches the maximum gear"),
        MINIMUM_GEAR("Reaches the minimum gear");

        private String status;

        TransmissionStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }

    @Override
    public String getStatus() {
        return mCurrentStatus.getStatus();
    }

    @Override
    public int getSpeed() {
        return mCurrentSpeed;
    }

    @Override
    public int getGear() {
        return mCurrentGear + 1;
    }

    @Override
    public ManualTransmission increaseSpeed() {
        // note increasing the speed by 1
        mCurrentSpeed += 1;
        if (mCurrentSpeed > mMaximumSpeed) {
            mCurrentSpeed = mMaximumSpeed;
            mCurrentStatus = TransmissionStatus.MAXIMUM_SPEED;
        } else {
            if (mCurrentSpeed > mSpeedRange[mCurrentGear][1]) {
                // note the current speed is not within the speed range of current range
                mCurrentSpeed -= 1;
                mCurrentStatus = TransmissionStatus.NO_SPEED_INCREASE;
            } else if (mCurrentGear + 1 < MAXIMUM_GEAR && mCurrentSpeed >= mSpeedRange[mCurrentGear + 1][0]) {
                mCurrentStatus = TransmissionStatus.MAY_INCREASE_GEAR;
            } else {
                mCurrentStatus = TransmissionStatus.OK;
            }
        }
        return this;
    }

    @Override
    public ManualTransmission decreaseSpeed() {
        mCurrentSpeed -= 1;
        if (mCurrentSpeed <= 0) {
            mCurrentSpeed = 0;
            mCurrentStatus = TransmissionStatus.MINIMUM_SPEED;
        } else {
            if (mCurrentSpeed < mSpeedRange[mCurrentGear][0]) {
                // note current speed is less than the lowest of the range of current gear
                mCurrentSpeed += 1;
                mCurrentStatus = TransmissionStatus.NO_SPEED_DECREASE;
            } else if (mCurrentGear > 0 && mSpeedRange[mCurrentGear - 1][1] >= mCurrentSpeed) {
                mCurrentStatus = TransmissionStatus.MAY_DECREASE_GEAR;
            } else {
                mCurrentStatus = TransmissionStatus.OK;
            }
        }
        return this;
    }

    @Override
    public ManualTransmission increaseGear() {
        mCurrentGear += 1;
        if (mCurrentGear > MAXIMUM_GEAR - 1) {
            mCurrentGear = MAXIMUM_GEAR - 1;
            mCurrentStatus = TransmissionStatus.MAXIMUM_GEAR;
        } else {
            if (mSpeedRange[mCurrentGear][0] > mCurrentSpeed) {
                // note need to increase the speed first to increase the gear
                mCurrentGear -= 1;
                mCurrentStatus = TransmissionStatus.NO_GEAR_INCREASE;
            } else {
                mCurrentStatus = TransmissionStatus.OK;
            }
        }
        return this;
    }

    @Override
    public ManualTransmission decreaseGear() {
        mCurrentGear -= 1;
        if (mCurrentGear < 0) {
            mCurrentGear = 0;
            // note only when current gear is 1, and still wanna to decrease the gear
            mCurrentStatus = TransmissionStatus.MINIMUM_GEAR;
        } else {
            if (mSpeedRange[mCurrentGear][1] < mCurrentSpeed) {
                mCurrentGear += 1;
                mCurrentStatus = TransmissionStatus.NO_GEAR_DECREASE;
            } else {
                mCurrentStatus = TransmissionStatus.OK;
            }
        }
        return this;
    }
}
