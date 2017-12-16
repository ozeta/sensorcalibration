package it.enea.monica.calibration;

import it.enea.monica.calibration.exception.AQNotFoundException;
import it.enea.monica.calibration.exception.InvalidInputException;
import it.enea.monica.calibration.exception.MapNotFound;

/**
 * Created by ozeta on 17/11/2017.
 */

public interface ICalibration {
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

    Triple test(Double[] coInput, Double[] no2Input, Double[] o3Input, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound;

    Double testCO(Double[] px1, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound;

    Double testNO2(Double[] px1, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound;

    Double testO3(Double[] px1, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound;
}
