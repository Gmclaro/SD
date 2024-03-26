package commonInfra;

import entities.Contestant;

public class Strategy {
    @FunctionalInterface
    private interface InnerStrategy {
        int[] selectTeam(Contestant[] contestants);
    }


    public enum StrategyType {
        STRONGEST,
        FIFO,
        RANDOM,
    }

    private Contestant[] contestants;
    private InnerStrategy strategy;

    Strategy(Contestant[] contestants, StrategyType type) {
        this.contestants = contestants;

        switch (type) {
            case STRONGEST:
                this.strategy = Strategy::strongestStrategy;
                break;
            case FIFO:

                break;
            case RANDOM:

                break;

            default:
                break;
        }

    }

    public Contestant[] getContestants() {
        return contestants;
    }

    public void setContestants(Contestant[] contestants) {
        this.contestants = contestants;
    }

    //TODO: implement strongestStrategy
    private static int[] strongestStrategy(Contestant[] contestants){
        return null;
    }

    //TODO: implement fifoStrategy
    private static int[] fifoStrategy(Contestant[] contestants){
        return null;
    }

    //TODO: implement randomStrategy
    private static int[] randomStrategy(Contestant[] contestants){
        return null;
    }

}
