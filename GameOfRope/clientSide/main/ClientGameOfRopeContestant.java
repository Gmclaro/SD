package clientSide.main;

import clientSide.entities.Contestant;
import clientSide.stubs.ContestantBenchStub;
import clientSide.stubs.GeneralRepositoryStub;
import clientSide.stubs.PlaygroundStub;
import serverSide.main.SimulParse;

public class ClientGameOfRopeContestant {
    /**
     * Main method.
     * 
     * @param args runtime arguments
     *             args[0] - name of the platform where is located the playground
     *             server
     *             args[1] - port number for listening to service requests of the
     *             playground server
     *             args[2] - name of the platform where is located the constant
     *             bench server
     *             args[3] - port number for listening to service requests of the
     *             bench server
     *             args[4] - name of the platform where is located the general
     *             repository server
     *             args[5] - port number for listening to service requests of the
     * 
     */
    public static void main(String[] args) {
        Contestant[][] contestants = new Contestant[SimulParse.COACH][SimulParse.CONTESTANT_PER_TEAM];
        PlaygroundStub playgroundStub;
        ContestantBenchStub contestantBenchStub;
        GeneralRepositoryStub generalRepositoryStub;

        if (args.length != 6) {
            System.out.println("Invalid number of arguments!");
            System.exit(1);
        }

        for (int i = 0; i < 6; i++) {
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

        playgroundStub = new PlaygroundStub(args[0], Integer.parseInt(args[1]));
        contestantBenchStub = new ContestantBenchStub(args[2], Integer.parseInt(args[3]));
        generalRepositoryStub = new GeneralRepositoryStub(args[4], Integer.parseInt(args[5]));

        int strength[][] = new int[SimulParse.COACH][SimulParse.CONTESTANT_PER_TEAM];
        for (int i = 0; i < SimulParse.COACH; i++) {
            for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
                strength[i][j] = (int) (Math.random() * 5) + 6;
            }
        }

        generalRepositoryStub.initSimul(strength);

        for (int i = 0; i < SimulParse.COACH; i++) {
            for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
                contestants[i][j] = new Contestant(i, j, strength[i][j], playgroundStub, contestantBenchStub);
                contestants[i][j].start();
            }
        }

        for (int i = 0; i < SimulParse.COACH; i++) {
            for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
                try {
                    contestants[i][j].join();
                } catch (InterruptedException e) {
                    System.out.println("Coach thread was interrupted!");
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }

        playgroundStub.shutdown();
        contestantBenchStub.shutdown();
        generalRepositoryStub.shutdown();
    }
}
