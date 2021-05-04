package uk.ac.aber.cs21120.solution;
import uk.ac.aber.cs21120.hospital.JobDisplay;
import uk.ac.aber.cs21120.hospital.RandomPriorityGenerator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * @author brb19
 * This is the main class for the hospital simulations.
 * This class will run create the simulations and run them
 * and collect data for different number of ambulances
 */

public class Main {
    public static void main(String[] args) {
        task3();
        //task4();
        //averageCalculations(10,100);
    }

    /**
     *
     * This method is designed run multiple simulations
     * with the same number on ambulances and then calculate the average times
     * based on the number of ambulances so that the data
     * can be easily collected and analysed
     *
     * @param numAmb number of ambulances to start the simulations with
     * @param numSimulations number of simulations to run
     */
    private static void averageCalculations(int numAmb, int numSimulations){
        Simulator sim = new Simulator(numAmb);

        Map<Integer, Double> ambulanceTotals = new HashMap<>();
        int [] priorities = RandomPriorityGenerator.getPossiblePriorities();
        for (int i = 0; i < priorities.length; i++){
            ambulanceTotals.put(priorities[i],0.0);
        }

        for (int i = 0; i < numSimulations; i++){
            simulate(sim);
            for (int j = 0; j < priorities.length; j++){
                ambulanceTotals.put(j,(ambulanceTotals.get(j) +sim.getAverageJobCompletionTime(j) ) );
            }
        }
        System.out.println("Averages for "+numAmb+" ambulances over "+numSimulations+" simulations: ");
        ambulanceTotals.forEach((prio,average)-> System.out.println(prio+": "+average/numSimulations));

    }



    /**
     * prints the results of a given simulation
     * @param sim a given simulator to print
     */
    private static void printSimulator(Simulator sim){
        System.out.print("Priority 0: ");
        System.out.println(sim.getAverageJobCompletionTime(0));
        System.out.print("Priority 1: ");
        System.out.println(sim.getAverageJobCompletionTime(1));
        System.out.print("Priority 2: ");
        System.out.println(sim.getAverageJobCompletionTime(2));
        System.out.print("Priority 3: ");
        System.out.println(sim.getAverageJobCompletionTime(3));

    }

    /**
     * Simulates a given simulator
     * @param sim a given simulator to simulate
     */
    private static void simulate(Simulator sim){
        Random r = new Random();
        int ticks = 0;
        RandomPriorityGenerator rPriority = new RandomPriorityGenerator();
        JobDisplay jd = new JobDisplay();
        while (ticks < 1000){
            sim.tick();
            int rand = r.nextInt(3)+1;
            if (rand == 1){
                Job j1 = new Job(r.nextInt(10), rPriority.next(), r.nextInt(10)+11);
                sim.add(j1);
            }
            ticks++;
            jd.add(sim);
        }

        while(!sim.allDone()){
            sim.tick();
            ticks++;
            jd.add(sim);
        }
        //jd.show();
    }

    /**
     * My solution for task 3
     */
    private static void task3(){
        int numAmb = 4;
        Simulator sim = new Simulator(numAmb);
        simulate(sim);
        System.out.println("For "+numAmb+" ambulances: ");
        printSimulator(sim);

    }

    /**
     * My solution for task 4
     */
    private static void task4(){
        Random r = new Random();
        int numAmb = r.nextInt(20)+1;
        Simulator sim = new Simulator(numAmb);
        simulate(sim);

        System.out.println("For "+numAmb+" ambulances: ");
        printSimulator(sim);

    }


}
