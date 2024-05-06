package clientSide.main;

import clientSide.entities.Coach;
import clientSide.stubs.ContestantBenchStub;
import clientSide.stubs.GeneralRepositoryStub;
import clientSide.stubs.PlaygroundStub;
import clientSide.stubs.RefereeSiteStub;
import commonInfra.Strategy.StrategyType;
import serverSide.main.SimulParse;

public class ClientGameOfRopeCoach {
    /**
     * Main method.
     * 
     * @param args runtime arguments
     *             args[0] - name of the platform where is located the refereeSite
     *             server
     *             args[1] - port number for listening to service requests of the
     *             refereeSite server
     *             args[2] - name of the platform where is located the playground
     *             server
     *             args[3] - port number for listening to service requests of the
     *             playground server
     *             args[4] - name of the platform where is located the contestants
     *             bench server
     *             args[5] - port number for listening to service requests of the
     *             bench server
     *             args[6] - name of the platform where is located the general
     *             repository server
     *             args[7] - port number for listening to service requests of the general repository server
     * 
     */
    public static void main(String[] args) {
        Coach[] coach = new Coach[SimulParse.COACH];
        RefereeSiteStub refereeSiteStub;
        PlaygroundStub playgroundStub;
        ContestantBenchStub contestantBenchStub;
        GeneralRepositoryStub generalRepositoryStub;

        if (args.length != 8) {
            System.out.println("Invalid number of arguments!");
            System.exit(1);
        }

        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) {
                try {
                    int port = Integer.parseInt(args[i + 1]);
                    if ((port < 4000) || (port > 65536)) {
                        System.out.println("Invalid args[" + i + "] port number!");
                        System.exit(1);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid args[" + i + "] type!");
                    System.exit(1);
                }

            }
        }

        /* problem initialization */

        refereeSiteStub = new RefereeSiteStub(args[0], Integer.parseInt(args[1]));
        playgroundStub = new PlaygroundStub(args[2], Integer.parseInt(args[3]));
        contestantBenchStub = new ContestantBenchStub(args[4], Integer.parseInt(args[5]));
        generalRepositoryStub = new GeneralRepositoryStub(args[6], Integer.parseInt(args[7]));

        for (int i = 0; i < SimulParse.COACH; i++) {
            coach[i] = new Coach(i, contestantBenchStub, playgroundStub, refereeSiteStub, selectStrategy(i));
            coach[i].start();
        }

        for (int i = 0; i < SimulParse.COACH; i++) {
            try {
                coach[i].join();
            } catch (InterruptedException e) {
                System.out.println("Coach thread was interrupted!");
                e.printStackTrace();
                System.exit(1);
            }
        }

        refereeSiteStub.shutdown();
        playgroundStub.shutdown();
        contestantBenchStub.shutdown();
        generalRepositoryStub.shutdown();
    }

    /**
     * Select the strategy for the coach.
     * 
     * @param team team id
     * @return strategy type
     */

    public static StrategyType selectStrategy(int team) {
        switch (((int) Math.random() * 3)) {
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
