package uk.ac.aber.cs21120.solution;

import uk.ac.aber.cs21120.hospital.IJob;
import uk.ac.aber.cs21120.hospital.ISimulator;
import uk.ac.aber.cs21120.hospital.RandomPriorityGenerator;

import java.util.*;

public class Simulator implements ISimulator {
    private int availableAmbulances;
    private int timeElapsed;
    private Set<IJob> runningJobs;
    private PriorityQueue<IJob> jobQueue;
    private Iterator<IJob> itr;
    private Map<Integer, Integer> completionTimes;
    private Map<Integer, Integer> jobsCompleted;
    private static int[] priorities = RandomPriorityGenerator.getPossiblePriorities();




    public Simulator(int numOfAmbulances){

        this.availableAmbulances = numOfAmbulances;
        timeElapsed = 0;
        runningJobs = new HashSet<>(numOfAmbulances);
        jobQueue = new PriorityQueue<>();

        completionTimes = new HashMap<>();
        jobsCompleted = new HashMap<>();
        for (int i = 0; i < priorities.length; i++){

            jobsCompleted.put(priorities[i],0);
            completionTimes.put(priorities[i],0);
        }

    }

    @Override
    public void add(IJob j) {

        jobQueue.add(j);
        j.setSubmitTime(timeElapsed);
    }

    @Override
    public void tick() {
        itr = runningJobs.iterator();
        while(itr.hasNext()){

            IJob j = itr.next();

            j.tick();

            if (j.isDone()){
                runningJobs.remove(j);

                //I have to set the iterator to the changed version of runningJobs
                itr= runningJobs.iterator();

                //now that a job is complete an ambulance has become available
                availableAmbulances++;

                completionTimes.put(j.getPriority(),(completionTimes.get(j.getPriority()) +j.getTimeSinceSubmit(timeElapsed) ) );
                jobsCompleted.put(j.getPriority(),jobsCompleted.get(j.getPriority()) + 1);

            }
        }

        //adding waiting jobs
        while( (availableAmbulances > 0) && (!jobQueue.isEmpty()) ){

            runningJobs.add(jobQueue.poll());
            availableAmbulances--;
        }
        timeElapsed++;
    }

    @Override
    public int getTime() {
        return timeElapsed;
    }

    @Override
    public boolean allDone() {
        return ( jobQueue.isEmpty() && runningJobs.isEmpty() );
    }

    @Override
    public Set<Integer> getRunningJobs() {
        Set<Integer> s = new HashSet<>();
        itr = runningJobs.iterator();
        while(itr.hasNext()){
            s.add(itr.next().getID());
        }

        return s;
    }

    @Override
    public double getAverageJobCompletionTime(int priority) {
        Double time = Double.valueOf(completionTimes.get(priority));
        Double jobs = Double.valueOf(jobsCompleted.get(priority));
        return time/jobs;
    }
}
