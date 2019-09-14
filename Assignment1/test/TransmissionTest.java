import org.junit.Before;
import org.junit.Test;
import vehicle.ManualTransmission;
import vehicle.RegularManualTransmission;
import vehicle.RegularManualTransmission.TransmissionStatus;

import static org.junit.Assert.assertEquals;

public class TransmissionTest {

    private ManualTransmission transmission;

    /**
     * This test is used to test the invalid arguments of ManualTransmission Constructor
     */
    @Test
    public void invalidArgumentsTest() {
        // parameters for five gears
        int[] gear1 = new int[]{0, 1};
        int[] gear2 = new int[]{1, 2};
        int[] gear3 = new int[]{2, 3};
        int[] gear4 = new int[]{3, 4};
        int[] gear5 = new int[]{4, 5};

        // note test1 lx should be no more than hx
        gear2[1] = 0;
        try {
            transmission = new RegularManualTransmission(
                    gear1[0], gear1[1],
                    gear2[0], gear2[1],
                    gear3[0], gear3[1],
                    gear4[0], gear4[1],
                    gear5[0], gear5[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("exception: lx should be no more than hx");
        }
        gear2[1] = 2;

        // note the lower speed for gear 1 should be strictly lesser than that of gear 2, and so on
        gear2[0] = 0;
        try {
            transmission = new RegularManualTransmission(
                    gear1[0], gear1[1],
                    gear2[0], gear2[1],
                    gear3[0], gear3[1],
                    gear4[0], gear4[1],
                    gear5[0], gear5[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("exception: the lower speed for gear 1 should be strictly lesser than that of gear 2, and so on");
        }
        gear2[0] = 1;

        // note only adjacent-gear ranges may overlap
        gear3[0] = 1;
        try {
            transmission = new RegularManualTransmission(
                    gear1[0], gear1[1],
                    gear2[0], gear2[1],
                    gear3[0], gear3[1],
                    gear4[0], gear4[1],
                    gear5[0], gear5[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("exception: only adjacent-gear ranges may overlap");
        }
        gear3[0] = 2;

        // note there cannot be non-overlapping
        gear5[0] = 5;
        try {
            transmission = new RegularManualTransmission(
                    gear1[0], gear1[1],
                    gear2[0], gear2[1],
                    gear3[0], gear3[1],
                    gear4[0], gear4[1],
                    gear5[0], gear5[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("exception: there cannot be non-overlapping");
        }
        gear5[0] = 4;
    }

    /**
     * this is to test the valid arguments of ManualTransmission Constructor
     */
    @Test
    public void validArgumentsTest() {
        // parameters for five gears
        int[] gear1 = new int[]{0, 1};
        int[] gear2 = new int[]{1, 2};
        int[] gear3 = new int[]{2, 3};
        int[] gear4 = new int[]{3, 4};
        int[] gear5 = new int[]{4, 5};

        transmission = new RegularManualTransmission(
                gear1[0], gear1[1],
                gear2[0], gear2[1],
                gear3[0], gear3[1],
                gear4[0], gear4[1],
                gear5[0], gear5[1]);
    }

    /**
     * Test the implementation of increaseSpeed
     * <p>
     * when increase 1 successfully, two status
     * 1. OK
     * 2. May Increase gear
     * <p>
     * failed to increase 1,
     * 1. reach the maximum speed
     * 2. reach out the speed range of current gear
     */
    @Test
    public void testIncreaseSpeed() {
        // parameters for five gears
        int[] gear1 = new int[]{0, 5};
        int[] gear2 = new int[]{5, 10};
        int[] gear3 = new int[]{10, 15};
        int[] gear4 = new int[]{15, 20};
        int[] gear5 = new int[]{20, 22};

        transmission = new RegularManualTransmission(
                gear1[0], gear1[1],
                gear2[0], gear2[1],
                gear3[0], gear3[1],
                gear4[0], gear4[1],
                gear5[0], gear5[1]);
        transmission.increaseSpeed();
        assertEquals(1, transmission.getSpeed());
        // OK
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        assertEquals(5, transmission.getSpeed());
        // OK, May increase gear
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseSpeed();
        assertEquals(5, transmission.getSpeed());
        // Failed, no speed increase, reach out the speed range of current gear
        assertEquals(TransmissionStatus.NO_SPEED_INCREASE.getStatus(), transmission.getStatus());

        transmission.increaseGear();
        transmission.increaseSpeed(); //6
        transmission.increaseSpeed(); //7
        transmission.increaseSpeed(); //8
        transmission.increaseSpeed(); //9
        transmission.increaseSpeed(); //10
        assertEquals(10, transmission.getSpeed());

        transmission.increaseSpeed();
        assertEquals(10, transmission.getSpeed());
        assertEquals(TransmissionStatus.NO_SPEED_INCREASE.getStatus(), transmission.getStatus());

        transmission.increaseGear();
        transmission.increaseSpeed(); //11
        transmission.increaseSpeed(); //12
        transmission.increaseSpeed(); //13
        transmission.increaseSpeed(); //14
        transmission.increaseSpeed(); //15
        assertEquals(15, transmission.getSpeed());

        transmission.increaseSpeed();
        assertEquals(15, transmission.getSpeed());
        assertEquals(TransmissionStatus.NO_SPEED_INCREASE.getStatus(), transmission.getStatus());

        transmission.increaseGear();
        transmission.increaseSpeed(); //16
        transmission.increaseSpeed(); //17
        transmission.increaseSpeed(); //18
        transmission.increaseSpeed(); //19
        transmission.increaseSpeed(); //20
        assertEquals(20, transmission.getSpeed());

        transmission.increaseSpeed();
        assertEquals(20, transmission.getSpeed());
        assertEquals(TransmissionStatus.NO_SPEED_INCREASE.getStatus(), transmission.getStatus());

        transmission.increaseGear();
        transmission.increaseSpeed(); //21
        transmission.increaseSpeed(); //22
        assertEquals(22, transmission.getSpeed());

        transmission.increaseSpeed();
        assertEquals(22, transmission.getSpeed());
        // Failed Maximum speed
        assertEquals(TransmissionStatus.MAXIMUM_SPEED.getStatus(), transmission.getStatus());
    }

    /**
     * when decrease 1 failed, two status
     * 1. minimal speed
     * 2. need to decrease gear first
     * when increase 1 successfully, two status
     * 1. OK
     * 2. May decrease the gear
     **/
    @Test
    public void testDecreaseSpeed() {
        // parameters for five gears
        int[] gear1 = new int[]{0, 5};
        int[] gear2 = new int[]{5, 10};
        int[] gear3 = new int[]{10, 15};
        int[] gear4 = new int[]{15, 20};
        int[] gear5 = new int[]{20, 22};

        transmission = new RegularManualTransmission(
                gear1[0], gear1[1],
                gear2[0], gear2[1],
                gear3[0], gear3[1],
                gear4[0], gear4[1],
                gear5[0], gear5[1]);

        // Failed minimum speed
        transmission.decreaseSpeed();
        assertEquals(TransmissionStatus.MINIMUM_SPEED.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); //1
        // OK
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); //2
        transmission.increaseSpeed(); //3
        transmission.increaseSpeed(); //4

        transmission.increaseSpeed(); //5
        // OK May decrease gear
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());
        transmission.increaseGear();
        transmission.decreaseSpeed();
        // Failed no speed decrease, need to decrease gear first
        assertEquals(TransmissionStatus.NO_SPEED_DECREASE.getStatus(), transmission.getStatus());


    }

    /**
     * when increase gear by 1 successfully, one status,
     * 1. OK
     * when failed to increase gear by 1 , two status,
     * 1. need to increase speed
     * 2. maximum gear
     */
    @Test
    public void testIncreaseGear() {

        // parameters for five gears
        int[] gear1 = new int[]{0, 3};
        int[] gear2 = new int[]{2, 5};
        int[] gear3 = new int[]{4, 7};
        int[] gear4 = new int[]{6, 9};
        int[] gear5 = new int[]{8, 11};

        transmission = new RegularManualTransmission(
                gear1[0], gear1[1],
                gear2[0], gear2[1],
                gear3[0], gear3[1],
                gear4[0], gear4[1],
                gear5[0], gear5[1]);

        transmission.increaseSpeed(); // 1
        transmission.increaseGear();
        // failed 1. need to increase speed
        assertEquals(TransmissionStatus.NO_GEAR_INCREASE.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // 2
        transmission.increaseGear();
        // OK
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // 3
        transmission.increaseSpeed(); // 4
        transmission.increaseSpeed(); // 5
        transmission.increaseGear();
        transmission.increaseSpeed(); // 6
        transmission.increaseSpeed(); // 7
        transmission.increaseGear();
        transmission.increaseSpeed(); // 8
        transmission.increaseSpeed(); // 9
        transmission.increaseGear();
        assertEquals(5, transmission.getGear());
        // failed to increase gear, maximum gear
        transmission.increaseGear();
        assertEquals(TransmissionStatus.MAXIMUM_GEAR.getStatus(), transmission.getStatus());
    }

    /**
     * note when decrease gear by 1 successfully, one status,
     * 1. OK
     * note when failed to decrease gear by 1 , two status,
     * 1. need to decrease speed
     * 2. minimum gear
     */
    @Test
    public void testDecreaseGear() {
        int[] gear1 = new int[]{0, 3};
        int[] gear2 = new int[]{2, 5};
        int[] gear3 = new int[]{4, 7};
        int[] gear4 = new int[]{6, 9};
        int[] gear5 = new int[]{8, 11};

        transmission = new RegularManualTransmission(
                gear1[0], gear1[1],
                gear2[0], gear2[1],
                gear3[0], gear3[1],
                gear4[0], gear4[1],
                gear5[0], gear5[1]);
        // failed to decrease gear, minimum gear
        transmission.decreaseGear();
        assertEquals(TransmissionStatus.MINIMUM_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // 1
        transmission.increaseSpeed(); // 2
        transmission.increaseGear();
        transmission.decreaseGear();
        // OK
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());
        transmission.increaseGear();
        transmission.increaseSpeed(); // 3
        transmission.increaseSpeed(); // 4
        // failed to decrease gear, need to decrease speed first
        transmission.decreaseGear();
        assertEquals(TransmissionStatus.NO_GEAR_DECREASE.getStatus(), transmission.getStatus());
    }

    /**
     * Test the API getSpeed()
     */
    @Test
    public void testGetSpeed() {
        int[] gear1 = new int[]{0, 3};
        int[] gear2 = new int[]{2, 5};
        int[] gear3 = new int[]{4, 7};
        int[] gear4 = new int[]{6, 9};
        int[] gear5 = new int[]{8, 11};

        transmission = new RegularManualTransmission(
                gear1[0], gear1[1],
                gear2[0], gear2[1],
                gear3[0], gear3[1],
                gear4[0], gear4[1],
                gear5[0], gear5[1]);

        transmission.increaseSpeed(); // 1
        assertEquals(1, transmission.getSpeed());

        transmission.increaseSpeed(); // 2
        assertEquals(2, transmission.getSpeed());

        transmission.increaseSpeed(); // 3
        assertEquals(3, transmission.getSpeed());

        transmission.increaseSpeed(); // 3
        assertEquals(3, transmission.getSpeed());

        transmission.increaseGear();
        transmission.increaseSpeed(); // 4
        assertEquals(4, transmission.getSpeed());

        transmission.increaseSpeed(); // 5
        assertEquals(5, transmission.getSpeed());

        transmission.increaseSpeed(); // 5
        assertEquals(5, transmission.getSpeed());

        transmission.increaseGear();
        transmission.increaseSpeed(); // 6
        assertEquals(6, transmission.getSpeed());

        transmission.increaseSpeed(); // 7
        assertEquals(7, transmission.getSpeed());

        transmission.increaseSpeed(); // 7
        assertEquals(7, transmission.getSpeed());

        transmission.increaseGear();
        transmission.increaseSpeed(); // 8
        assertEquals(8, transmission.getSpeed());

        transmission.increaseSpeed(); // 9
        assertEquals(9, transmission.getSpeed());

        transmission.increaseSpeed(); // 9
        assertEquals(9, transmission.getSpeed());

        transmission.increaseGear();
        transmission.increaseSpeed(); // 10
        assertEquals(10, transmission.getSpeed());

        transmission.increaseSpeed(); // 11
        assertEquals(11, transmission.getSpeed());

        transmission.increaseSpeed(); // 11
        assertEquals(11, transmission.getSpeed());

    }

    /**
     * Test the api getGear() for RegularManualTransmission
     */
    @Test
    public void testGetGear() {
        // parameters for five gears
        int[] gear1 = new int[]{0, 5};
        int[] gear2 = new int[]{5, 10};
        int[] gear3 = new int[]{10, 15};
        int[] gear4 = new int[]{15, 20};
        int[] gear5 = new int[]{20, 22};

        transmission = new RegularManualTransmission(
                gear1[0], gear1[1],
                gear2[0], gear2[1],
                gear3[0], gear3[1],
                gear4[0], gear4[1],
                gear5[0], gear5[1]);

        transmission.decreaseGear();
        assertEquals(1, transmission.getGear());

        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed(); // 5
        transmission.increaseGear();
        assertEquals(2, transmission.getGear());

        transmission.increaseSpeed(); // 6
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed(); // 10
        transmission.increaseGear();
        assertEquals(3, transmission.getGear());

        transmission.increaseSpeed(); // 6
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed(); // 10
        transmission.increaseGear();
        assertEquals(4, transmission.getGear());

        transmission.increaseSpeed(); // 6
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed();
        transmission.increaseSpeed(); // 10
        transmission.increaseGear();
        assertEquals(5, transmission.getGear());

        transmission.increaseGear();
        assertEquals(5, transmission.getGear());
    }

    /**
     * Test the api getStatus() for RegularManualTransmission
     */
    @Test
    public void testGetStatus() {
        // parameters for five gears
        int[] gear1 = new int[]{0, 3};
        int[] gear2 = new int[]{2, 5};
        int[] gear3 = new int[]{4, 7};
        int[] gear4 = new int[]{6, 9};
        int[] gear5 = new int[]{8, 11};

        transmission = new RegularManualTransmission(
                gear1[0], gear1[1],
                gear2[0], gear2[1],
                gear3[0], gear3[1],
                gear4[0], gear4[1],
                gear5[0], gear5[1]);

        // note test the OK status of regular manual transmission
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());
        assertEquals(0, transmission.getSpeed());
        assertEquals(1, transmission.getGear());

        // note test status: MINIMUM_SPEED "Cannot decrease speed. Reached minimum speed."
        transmission.decreaseSpeed();
        assertEquals(TransmissionStatus.MINIMUM_SPEED.getStatus(), transmission.getStatus());
        assertEquals(0, transmission.getSpeed());

        // note test status: MINIMUM_GEAR "“Cannot decrease gear. Reached minimum gear."
        transmission.decreaseGear();
        assertEquals(TransmissionStatus.MINIMUM_GEAR.getStatus(), transmission.getStatus());
        assertEquals(1, transmission.getGear());

        // note test status: MAY_DECREASE_GEAR "OK: you may increase the gear"
        transmission.increaseSpeed();  //1
        transmission.increaseSpeed();  //2
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        // note test status: NO_SPEED_INCREASE "Cannot increase speed, increase gear first."
        transmission.increaseSpeed();  //3
        transmission.increaseSpeed();
        assertEquals(TransmissionStatus.NO_SPEED_INCREASE.getStatus(), transmission.getStatus());
        assertEquals(3, transmission.getSpeed());

        // note test the status of OK, "OK: everything is fine"
        transmission.increaseGear();  // 2
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        // note test status:MAY_DECREASE_GEAR "OK: you may decrease the gear."
        transmission.decreaseSpeed(); // 2
        assertEquals(TransmissionStatus.MAY_DECREASE_GEAR.getStatus(), transmission.getStatus());

        // note test status: NO_SPEED_DECREASE "Cannot decrease speed, decrease gear first."
        transmission.decreaseSpeed();
        assertEquals(TransmissionStatus.NO_SPEED_DECREASE.getStatus(), transmission.getStatus());
        assertEquals(2, transmission.getSpeed());

        // note test status: OK
        transmission.decreaseGear(); // speed = 2, gear = 1
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 3, gear = 1
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseGear(); // speed = 3, gear = 2
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 4, gear = 2
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 5, gear = 2
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 5, gear = 2
        assertEquals(TransmissionStatus.NO_SPEED_INCREASE.getStatus(), transmission.getStatus());

        transmission.increaseGear(); // speed = 5, gear = 3
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 6, gear = 3
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 7, gear = 3
        assertEquals(7, transmission.getSpeed());
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 7, gear = 3
        assertEquals(7, transmission.getSpeed());
        assertEquals(TransmissionStatus.NO_SPEED_INCREASE.getStatus(), transmission.getStatus());

        transmission.increaseGear(); // speed = 7, gear = 4
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 8, gear = 4
        assertEquals(8, transmission.getSpeed());
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 9, gear = 4
        assertEquals(9, transmission.getSpeed());
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 9, gear = 4
        assertEquals(9, transmission.getSpeed());
        assertEquals(TransmissionStatus.NO_SPEED_INCREASE.getStatus(), transmission.getStatus());

        transmission.increaseGear(); // speed = 9, gear = 5
        assertEquals(5, transmission.getGear());
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 10, gear = 5
        assertEquals(10, transmission.getSpeed());
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.increaseSpeed(); // speed = 11, gear = 5
        assertEquals(11, transmission.getSpeed());
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        // note test status: MAXIMUM_SPEED "reaches the maximum speed"
        transmission.increaseSpeed(); // speed = 11, gear = 5
        assertEquals(11, transmission.getSpeed());
        assertEquals(TransmissionStatus.MAXIMUM_SPEED.getStatus(), transmission.getStatus());

        // note test status: MAXIMUM_GEAR "reaches the maximum gear"
        transmission.increaseGear(); // speed = 11, gear = 5
        assertEquals(5, transmission.getGear());
        assertEquals(TransmissionStatus.MAXIMUM_GEAR.getStatus(), transmission.getStatus());

        // note test status: OK "everything is ok."
        transmission.decreaseSpeed(); // speed = 10. gear = 5
        assertEquals(10, transmission.getSpeed());
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        // note test status: NO_GEAR_DECREASE "Cannot decrease gear, decrease speed first"
        transmission.decreaseGear(); // speed = 10, gear = 5
        assertEquals(5, transmission.getGear());
        assertEquals(TransmissionStatus.NO_GEAR_DECREASE.getStatus(), transmission.getStatus());

        transmission.decreaseSpeed(); // speed = 9, gear = 5
        assertEquals(9, transmission.getSpeed());
        assertEquals(TransmissionStatus.MAY_DECREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.decreaseSpeed(); // speed = 8, gear = 5
        assertEquals(8, transmission.getSpeed());
        assertEquals(TransmissionStatus.MAY_DECREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.decreaseSpeed(); // speed = 8, gear = 5
        assertEquals(8, transmission.getSpeed());
        assertEquals(TransmissionStatus.NO_SPEED_DECREASE.getStatus(), transmission.getStatus());

        transmission.decreaseGear(); // speed = 8, gear = 4
        assertEquals(4, transmission.getGear());
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.decreaseSpeed(); // speed = 7, gear = 4
        assertEquals(7, transmission.getSpeed());
        assertEquals(TransmissionStatus.MAY_DECREASE_GEAR.getStatus(), transmission.getStatus());

        // note test status: NO_GEAR_INCREASE "Cannot increase gear, increase speed first."
        transmission.increaseGear(); //speed = 7, gear = 4
        assertEquals(4, transmission.getGear());
        assertEquals(TransmissionStatus.NO_GEAR_INCREASE.getStatus(), transmission.getStatus());


        // parameters for five gears
        gear1 = new int[]{0, 5};
        gear2 = new int[]{5, 10};
        gear3 = new int[]{10, 15};
        gear4 = new int[]{15, 20};
        gear5 = new int[]{20, 22};

        transmission = new RegularManualTransmission(
                gear1[0], gear1[1],
                gear2[0], gear2[1],
                gear3[0], gear3[1],
                gear4[0], gear4[1],
                gear5[0], gear5[1]);

        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());
        assertEquals(0, transmission.getSpeed());
        assertEquals(1, transmission.getGear());

        transmission.decreaseSpeed();
        assertEquals(TransmissionStatus.MINIMUM_SPEED.getStatus(), transmission.getStatus());
        assertEquals(0, transmission.getSpeed());

        transmission.decreaseGear();
        assertEquals(TransmissionStatus.MINIMUM_GEAR.getStatus(), transmission.getStatus());
        assertEquals(1, transmission.getGear());

        transmission.increaseSpeed();  //1
        transmission.increaseSpeed();  //2
        transmission.increaseSpeed();  //3
        transmission.increaseSpeed();  //4
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.increaseSpeed();  //5
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseSpeed();  //5
        assertEquals(TransmissionStatus.NO_SPEED_INCREASE.getStatus(), transmission.getStatus());

        transmission.increaseGear(); // gear = 2
        transmission.increaseSpeed();  //6
        transmission.increaseSpeed();  //7
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.increaseSpeed();  //8
        transmission.increaseSpeed();  //9
        transmission.increaseSpeed();  //10
        assertEquals(TransmissionStatus.MAY_INCREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.increaseSpeed();  //10
        assertEquals(TransmissionStatus.NO_SPEED_INCREASE.getStatus(), transmission.getStatus());

        transmission.decreaseSpeed(); // speed = 9, gear = 2
        assertEquals(9, transmission.getSpeed());

        transmission.decreaseGear(); // speed = 9, gear = 2
        assertEquals(2, transmission.getGear());
        assertEquals(TransmissionStatus.NO_GEAR_DECREASE.getStatus(), transmission.getStatus());

        transmission.decreaseSpeed();  //8
        transmission.decreaseSpeed();  //7
        transmission.decreaseSpeed();  //6
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.decreaseSpeed();
        assertEquals(TransmissionStatus.MAY_DECREASE_GEAR.getStatus(), transmission.getStatus());

        transmission.decreaseSpeed();
        assertEquals(TransmissionStatus.NO_SPEED_DECREASE.getStatus(), transmission.getStatus());

        transmission.decreaseGear();
        assertEquals(TransmissionStatus.OK.getStatus(), transmission.getStatus());

        transmission.decreaseSpeed(); //4
        transmission.increaseGear();
        assertEquals(TransmissionStatus.NO_GEAR_INCREASE.getStatus(), transmission.getStatus());
    }
}
