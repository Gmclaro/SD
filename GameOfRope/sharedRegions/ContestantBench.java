package sharedRegions;
import entities.*;

public class ContestantBench {

    private final GeneralRepository repo;
    private final Playground playground;

    private final Contestant[][] contestants;
    private final int[][] playgroundQueue;

    public ContestantBench(GeneralRepository repo, Playground playground) {
        this.repo = repo;
        this.playground = playground;
        this.contestants = new Contestant[2][5];
        playgroundQueue = new int [2][3];
    }

    public synchronized void callContestants(int[] selected, int team){
        playgroundQueue[team] = selected;
        notifyAll();
    }


    public synchronized void followCoachAdvice() {
        int team = ((Contestant) Thread.currentThread()).getTeam();
        int id = ((Contestant) Thread.currentThread()).getID();
        for(int i : playgroundQueue[team]){
            if(i == id){
                playground.addContestant(team);
                break;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ((Contestant) Thread.currentThread()).setEntityState(ContestantState.STAND_IN_POSITION);
        notifyAll();
    }

    public synchronized void seatDown() {
        ((Contestant) Thread.currentThread()).setEntityState(ContestantState.SEAT_AT_THE_BENCH);
        int team = ((Contestant) Thread.currentThread()).getTeam();
        int id = ((Contestant) Thread.currentThread()).getID();
        contestants[team][id] = (Contestant) Thread.currentThread();
        playground.removeContestant(team);
        notifyAll();

    }

    /**
     * Get the contestants in the bench
     * @return Contestant[][]
     */
    public synchronized Contestant[] getBench(int team){
        return contestants[team];
    }

}