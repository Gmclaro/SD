package clientSide.main;

import clientSide.entities.Referee;
import clientSide.stubs.*;

public class ClientGameOfRopeReferee {
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
     *             args[4] - name of the platform where is located the contestant
     *             bench server
     *             args[5] - port number for listening to service requests of the
     *             bench server
     *             args[6] - name of the platform where is located the general
     *             repository server
     *             args[7] - port number for listening to service requests of the
     *             general repository server
     * 
     */
    public static void main(String[] args) {
        Referee referee;
        RefereeSiteStub refereeSiteStub;
        PlaygroundStub playgroundStub;
        ContestantBenchStub contestantBenchStub;
        GeneralRepositoryStub generalRepositoryStub;

        if (args.length != 8) {
            System.out.println("Invalid number of arguments!");
            System.exit(1);
        }

        for (int i = 0; i < 8; i++) {
            if (i % 2 != 0) {
                try {
                    int port = Integer.parseInt(args[i]);
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

        referee = new Referee(playgroundStub, refereeSiteStub, contestantBenchStub);

        referee.start();

        try {
            referee.join();
        } catch (InterruptedException e) {
            System.out.println("Referee thread was interrupted!");
            e.printStackTrace();
            System.exit(1);
        }

        // refereeSiteStub.shutdown();
        // playgroundStub.shutdown();
        // contestantBenchStub.shutdown();
        // generalRepositoryStub.shutdown();
    }
}
