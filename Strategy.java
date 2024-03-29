package commonInfra;

import entities.Contestant;

public class Strategy {

    @FunctionalInterface
    public interface InnerStrategy {
        int[] selectTeam(Contestant[] contestants);
    }

    public enum StrategyType {
        STRONGEST,
        FIFO,
        RANDOM,
    }

    private InnerStrategy strategy;

    public Strategy(StrategyType type) {
        switch (type) {
            case STRONGEST:
                this.strategy = new StrongestStrategy();
                break;
            case FIFO:
                this.strategy = new FifoStrategy();
                break;
            case RANDOM:
                this.strategy = new RandomStrategy();
                break;

            default:
                this.strategy = new StrongestStrategy();
                break;
        }
    }

    public InnerStrategy getStrategy() {
        return this.strategy;
    }

    private class StrongestStrategy implements InnerStrategy {
        @Override
        public int[] selectTeam(Contestant[] contestants) {
            Contestant[] sortedContestants = contestants.clone();

            for (int i = 1; i < sortedContestants.length; ++i) {
                Contestant key = sortedContestants[i];
                int j = i - 1;

                while (j >= 0 && sortedContestants[j].getStrength() > key.getStrength()) {
                    sortedContestants[j + 1] = sortedContestants[j];
                    j = j - 1;
                }
                sortedContestants[j + 1] = key;
            }

            return new int[] {
                    sortedContestants[sortedContestants.length - 1].getID(),
                    sortedContestants[sortedContestants.length - 2].getID(),
                    sortedContestants[sortedContestants.length - 3].getID()
            };
        }
    }

    private class FifoStrategy implements InnerStrategy {
        MemFIFO<Contestant> fifo;
        @Override
        public int[] selectTeam(Contestant[] contestants) {
            int[] selected = new int[3];

            if (fifo == null) {
                try {
                    fifo = new MemFIFO<Contestant>(contestants);
                } catch (MemException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 3; i++) {
                try {
                    Contestant c = fifo.read();
                    selected[i] = c.getID();
                    fifo.write(c);
                } catch (MemException e) {
                    e.printStackTrace();
                }
            }

            return selected;
        }
    }

    private class RandomStrategy implements InnerStrategy {
        @Override
        public int[] selectTeam(Contestant[] contestants) {
            int[] selected = new int[3];

            for (int i = 0; i < 3; i++) {
                selected[i] = (int) (Math.random() * contestants.length);
            }
        
            return selected;
        }
    }
}
