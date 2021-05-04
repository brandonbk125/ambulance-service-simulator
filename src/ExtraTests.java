package uk.ac.aber.cs21120.solution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ExtraTests {
    /**
     * This class contains extra tests for the Job and Simulator classes.
     * @author brb19
     *
     */
    @Test
    public void testAllDone(){
        Simulator sim = new Simulator(1);
        Assertions.assertEquals(true, sim.allDone());

    }

    @Test
    public void testGetAverageCompletionTime(){
        //new simulator
        Simulator sim  = new Simulator(1);

        //create two jobs with duration 5 and priority 0
        Job j1 = new Job(1, 0, 5);
        Job j2 = new Job(1, 0, 5);

        //add jobs to the simulator
        sim.add(j1);
        sim.add(j2);

        for (int i = 0; i < 11; i++){
            //tick the simulator 10 times
            sim.tick();
        }

        //average completion time should be 7.5 for this
        Assertions.assertEquals(7.5, sim.getAverageJobCompletionTime(0));

    }
}
