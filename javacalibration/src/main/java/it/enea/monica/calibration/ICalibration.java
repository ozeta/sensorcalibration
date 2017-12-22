package it.enea.monica.calibration;

import it.enea.monica.calibration.exception.AQNotFoundException;
import it.enea.monica.calibration.exception.InvalidInputException;
import it.enea.monica.calibration.exception.MapNotFound;

/**
 * Created by ozeta on 17/11/2017.
 *
 * Standard interface for sensor calibrator
 */

public interface ICalibration {

    /**
     * Builtin class for Triple retrival
     */
    public class Triple {
        Double co;
        Double no2;
        Double o3;

        public Double getCo() {
            return co;
        }

        public Double getNo2() {
            return no2;
        }

        public Double getO3() {
            return o3;
        }

        public Triple(Double co, Double no2, Double o3) {
            this.co = co;
            this.no2 = no2;
            this.o3 = o3;
        }

        @Override
        public String toString() {
            String res = String.format("|% 5.2f||% 5.2f||% 5.2f|", this.co, this.no2, this.o3);
            return res;
        }
    }

    /**
     * each array contains the following fields: [temperature, we electrode, ae electrode , we zero electrode, ae zero electrode]
     * @param coInput
     * @param no2Input
     * @param o3Input
     * @param afe
     * @return the Triple containg the 3 estimations.
     * @throws InvalidInputException
     * @throws AQNotFoundException if afe string is not found into configuration json file
     * @throws MapNotFound
     */
    Triple test(Double[] coInput, Double[] no2Input, Double[] o3Input, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound;

    Double testCO(Double[] px1, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound;

    Double testNO2(Double[] px1, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound;

    Double testO3(Double[] px1, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound;
}
