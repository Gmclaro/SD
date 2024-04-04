package commonInfra;

import main.SimulParse;
import java.util.HashSet;

/**
 * This class represents the strategy used by the teams to select their players.
 */
public class Strategy {

    /**
     * Functional interface to define the strategy to select the team.
     * 
     * @param contestants The list of contestants to select the team from.
     */
    @FunctionalInterface
    public interface InnerStrategy {
        int[] selectTeam(View[] contestants);
    }

    /**
     * Enum to define the strategy type.
     */
    public enum StrategyType {
        STRONGEST,
        FIFO,
        RANDOM,
    }

    /**
     * The strategy to use.
     */
    private InnerStrategy strategy;

    /**
     * Constructor to create the strategy.
     * 
     * @param type The type of strategy to use.
     */
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

    /**
     * Get the strategy.
     * 
     * @return InnerStrategy The strategy.
     */
    public InnerStrategy getStrategy() {
        return this.strategy;
    }

    /**
     * The strategy to select the strongest players.
     */
    private class StrongestStrategy implements InnerStrategy {
        /**
         * Select the team based on the strongest players.
         * 
         * @param contestants The list of contestants to select the team from.
         * @return int[] The selected team.
         */
        @Override
        public int[] selectTeam(View[] contestants) {
            for (int i = 1; i < contestants.length; ++i) {
                View key = contestants[i];
                int j = i - 1;

                while (j >= 0 && contestants[j].getValue() > key.getValue()) {
                    contestants[j + 1] = contestants[j];
                    j = j - 1;
                }
                contestants[j + 1] = key;
            }

            return new int[] {
                    contestants[contestants.length - 1].getKey(),
                    contestants[contestants.length - 2].getKey(),
                    contestants[contestants.length - 3].getKey()
            };
        }
    }

    /**
     * The strategy to select the players based on the FIFO order.
     */
    private class FifoStrategy implements InnerStrategy {
        /**
         * The FIFO queue to store the contestants and their order to be selected.
         */
        private MemFIFO<View> fifo;

        /**
         * Select the team based on the FIFO order.
         * 
         * @param contestants The list of contestants to select the team from.
         * @return int[] The selected team.
         */
        @Override
        public int[] selectTeam(View[] contestants) {
            int[] selected = new int[3];

            if (fifo == null) {
                try {
                    fifo = new MemFIFO<View>(contestants);
                    for (View c : contestants) {
                        fifo.write(c);
                    }
                } catch (MemException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM; i++) {
                try {
                    View c = fifo.read();
                    selected[i] = c.getKey();
                    fifo.write(c);
                } catch (MemException e) {
                    e.printStackTrace();
                }
            }

            return selected;
        }
    }

    /**
     * The strategy to select the players randomly.
     */
    private class RandomStrategy implements InnerStrategy {
        /**
         * Select the team based on the random selection.
         * 
         * @param contestants The list of contestants to select the team from.
         * @return int[] The selected team.
         */
        @Override
        public int[] selectTeam(View[] contestants) {
            HashSet<Integer> setSelected = new HashSet<Integer>();

            do {
                int index = (int) (Math.random() * contestants.length);
                setSelected.add(contestants[index].getKey());
            } while (setSelected.size() < SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM);

            return setSelected.stream().mapToInt(Integer::intValue).toArray();
        }
    }
}
