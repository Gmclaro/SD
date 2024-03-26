package sharedRegions;

import java.util.Arrays;
import java.util.Comparator;

import commonInfra.MemException;
import commonInfra.MemFIFO;
import entities.*;

public class ContestantBench {

    private final GeneralRepository repo;

    private final Contestant[][] contestants;
    private final MemFIFO<Integer>[] playgroundQueue;

    public ContestantBench(GeneralRepository repo) {
        this.repo = repo;
        this.contestants = new Contestant[2][5];
        playgroundQueue = new MemFIFO[2];
    }

    public synchronized void callContestants(int[] selected, int team) throws MemException {
        // TODO: implement callContestants
        for (int contestantID : selected) {
            playgroundQueue[team].write(contestantID);
        }
        notifyAll();
    }

    public synchronized void followCoachAdvice() {
        // try {
        //     // Wait until there are contestants in the playground queue
        //     while (playgroundQueue[Coach.team].isEmpty()) {
        //         wait();
        //     }

        //     // Remove a contestant from the playground queue
        //     int contestantID = playgroundQueue[Coach.team].read();

        //     // Perform actions based on coach's advice (if needed)
        //     // For example, update contestant's state or perform specific actions

        //     // Notify waiting threads (if any)
        //     notifyAll();
        // } catch (InterruptedException e) {
        //     // Handle InterruptedException if needed
        //     e.printStackTrace();
        // }
    }

    public synchronized void seatDown(int id, int team) {

        contestants[team][id] = (Contestant) Thread.currentThread();
        contestants[team][id].setEntityState(ContestantState.SEAT_AT_THE_BENCH);
        notifyAll();
    }
    

}