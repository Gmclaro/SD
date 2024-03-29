package sharedRegions;

import entities.*;

public class ContestantBench {

    private final GeneralRepository repo;
    // private final Playground playground;

    // TODO: Instead of using Contestant reference, use the content of each one, and
    // only change it not the "person" itself
    private final Contestant[][] contestants;
    private final int[][] playgroundQueue;

    public ContestantBench(GeneralRepository repo) {
        this.repo = repo;
        // this.playground = playground;
        this.contestants = new Contestant[2][5];
        playgroundQueue = new int[2][3];
    }

    public synchronized void callContestants(int[] selected, int team) {
        playgroundQueue[team] = selected;
        notifyAll();
    }

    // private boolean hasId(int id){

    // }

    public synchronized void followCoachAdvice() {
        int team = ((Contestant) Thread.currentThread()).getTeam();
        int id = ((Contestant) Thread.currentThread()).getID();

        for (int i : playgroundQueue[team]) {
            if (i == id) {
                ((Contestant) Thread.currentThread()).setEntityState(ContestantState.STAND_IN_POSITION);
                //playground.addContestant(team);
                break;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        notifyAll();
    }

    public synchronized void seatDown() {
        ((Contestant) Thread.currentThread()).setEntityState(ContestantState.SEAT_AT_THE_BENCH);
        int team = ((Contestant) Thread.currentThread()).getTeam();
        int id = ((Contestant) Thread.currentThread()).getID();
        contestants[team][id] = (Contestant) Thread.currentThread();
        //playground.removeContestant(team);
        notifyAll();

    }

    /**
     * Get the contestants in the bench
     * 
     * @return Contestant[][]
     */
    public synchronized Contestant[] getBench(int team) {
        return contestants[team].clone();
    }

    /**
     * The referee declares the match winner
     */
    public synchronized void declareMatchWinner() {
        // TODO : implement declareMatchWinner -> informar ao contestants que ja acabou

    }

}