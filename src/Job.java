package uk.ac.aber.cs21120.solution;
import uk.ac.aber.cs21120.hospital.IJob;


public class Job implements IJob, Comparable<IJob> {
    private int id;
    private int priority;
    private int duration;
    private int timeElapsed;
    private int submitTime;
    private boolean submitted;

    public Job(int id, int priority, int duration){
        this.id = id;
        this.priority = priority;
        this.duration = duration;
        timeElapsed = 0;
        submitted = false;

    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void tick() {
        timeElapsed++;
    }

    @Override
    public boolean isDone() {
        return (timeElapsed >= duration);
    }

    @Override
    public int getTimeSinceSubmit(int now) throws RuntimeException {

        if (!submitted){
            throw new RuntimeException("Job not submitted yet.");
        }else{
            return now - submitTime;
        }
    }

    @Override
    public void setSubmitTime(int time) {
        submitted = true;
        this.submitTime = time;
    }

    @Override
    public int compareTo(IJob o) {
        return this.priority - o.getPriority() ;

    }
}
