package vehicle;

public interface ManualTransmission {

    /**
     * @return get the status of the vehicle
     */
    public String getStatus();

    /**
     * @return get the current speed of the vehicle
     */
    public int getSpeed();

    /**
     * @return get the current gear of the vehicle
     */
    public int getGear();

    /**
     * increase the speed by a certain amount
     *
     * @return the resulting transmission object
     */
    public ManualTransmission increaseSpeed();

    /**
     * decrease the speed by a certain amount
     *
     * @return the resulting transmission object
     */
    public ManualTransmission decreaseSpeed();

    /**
     * increase the gear by a certain amount
     *
     * @return the resulting transmission object
     */
    public ManualTransmission increaseGear();

    /**
     * decrease the gear by a certain amount
     *
     * @return the resulting transmission amount
     */
    public ManualTransmission decreaseGear();
}