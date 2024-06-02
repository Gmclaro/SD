package clientSide.main;

import clientSide.entities.Coach;
import commonInfra.Strategy.StrategyType;
import serverSide.main.SimulParse;
import interfaces.*;
import java.rmi.*;
import java.rmi.registry.*;
/**
 * Client side of Coach
 * Impletation of a client-server model of type 2 (server replication).
 * Communication is based on Java RMI.
 */
public class ClientGameOfRopeCoach {

    /**
     * Select the strategy for the coach.
     * 
     * @param team team id
     * @return strategy type
     */

    public static StrategyType selectStrategy(int team) {
        switch (((int) (Math.random() * 3))) {
            case 0:
                System.out.println("Team " + team + "STRONGEST");
                return StrategyType.STRONGEST;
            case 1:
                System.out.println("Team " + team + "FIFO");
                return StrategyType.FIFO;
            case 2:
                System.out.println("Team " + team + "RANDOM");
                return StrategyType.RANDOM;

            default:
                System.out.println("Team " + team + "RANDOM");
                return StrategyType.RANDOM;
        }
    }
}
