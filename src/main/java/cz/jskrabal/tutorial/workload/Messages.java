package cz.jskrabal.tutorial.workload;
/* =========================================================================
 * IBA CZ Confidential
 *
 * (c) Copyright IBA CZ 2016 ALL RIGHTS RESERVED
 * The source code for this program is not published or otherwise
 * divested of its trade secrets.
 *
 * ========================================================================= */

/**
 * @author Jan Škrabal (jan.skrabal@ibacz.eu)
 */
public class Messages {
    public static class Calculate {
    }

    public static class PiApproximation {
        private final double pi;
        private final long duration;

        public PiApproximation(double pi, long duration) {
            this.pi = pi;
            this.duration = duration;
        }

        public double getPi() {
            return pi;
        }

        public long getDuration() {
            return duration;
        }
    }

    public static class Result {
        private final double value;

        public Result(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    public static class Work {
        private final int start;
        private final int nrOfElements;

        public Work(int start, int nrOfElements) {
            this.start = start;
            this.nrOfElements = nrOfElements;
        }

        public int getStart() {
            return start;
        }

        public int getNrOfElements() {
            return nrOfElements;
        }
    }
}
