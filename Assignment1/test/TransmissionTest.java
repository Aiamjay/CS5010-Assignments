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
     * This test is to test the status of transmission
     */
    @Test
    public void statusTest() {
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

    }
}
