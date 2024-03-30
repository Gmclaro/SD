package commonInfra;

import main.SimulParse;
import java.util.HashSet;

public class Strategy {

    @FunctionalInterface
    public interface InnerStrategy {
        int[] selectTeam(View[] contestants);
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

    // TODO: remove this method
    private void test(View[] contestants){
        for (View c : contestants) {
            System.out.println("EXISTE? " + !(c == null));
            System.out.println("HEY: (" + c.getKey() + ", " + c.getValue() + ")");
        }
    }

    private class StrongestStrategy implements InnerStrategy {
        @Override
        public int[] selectTeam(View[] contestants) {
            //test(contestants); // TODO: remove this line

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

    private class FifoStrategy implements InnerStrategy {
        private MemFIFO<View> fifo;

        @Override
        public int[] selectTeam(View[] contestants) {
            //test(contestants); // TODO: remove this line
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

    private class RandomStrategy implements InnerStrategy {
        @Override
        public int[] selectTeam(View[] contestants) {
            //test(contestants); // TODO: remove this line
            HashSet<Integer> setSelected = new HashSet<Integer>();

            do {
                int index = (int) (Math.random() * contestants.length);
                setSelected.add(contestants[index].getKey());
            } while (setSelected.size() < SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM);

            return setSelected.stream().mapToInt(Integer::intValue).toArray();
        }
    }
}
