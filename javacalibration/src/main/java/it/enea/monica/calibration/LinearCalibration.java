package it.enea.monica.calibration;

import android.util.Log;
import it.enea.monica.calibration.exception.AQNotFoundException;
import it.enea.monica.calibration.exception.InvalidInputException;
import it.enea.monica.calibration.exception.MapNotFound;

import java.text.DecimalFormat;
import java.util.HashMap;


/**
 * Created by ozeta on 16/11/2017.
 */

public class LinearCalibration implements ICalibration {

    private Double lastCo = 0d;
    private Double lastNo2 = 0d;
    private Double lastO3 = 0d;


    public abstract class Gas {
        private Double lastRecord = 0d;
        protected HashMap<String, Double> map;
        String S;

        public Double linear(double temp, double we, double ae, double we_zero, double ae_zero, String afe, HashMap calibMap) throws AQNotFoundException {
            map = (HashMap) calibMap.get(afe);
            if (map == null) {
                throw new AQNotFoundException(afe);
            }
            Double s = map.get(S);
            if (s != null) {

                double tmp = (we - ae) - (we_zero - ae_zero);
                tmp = (1 / s) * tmp;
                return tmp;
            } else {
                throw new AQNotFoundException(afe, S);
            }
        }

        private Double preProcessing(Double[] current) {
            return 0d;
        }

        public Double sensitivityTCorrection(Double current, Double temp, String k1, String k2) throws MapNotFound {
            if (map == null) {
                throw new MapNotFound();
            }
            Double k = 0d;
            int tNeg20 = -20;
            int t20 = 20;
            int t40 = 40;

            if (tNeg20 <= temp && temp < t20) {
                k = map.get(k1);
                k *= (t20 - temp);
            } else if (t20 <= temp && temp <= t40) {
                k = map.get(k2);
                k *= (temp - t20);
            }
//            Double off = (k / 100) * current;
//            return current - off;
            double perc = 1 - Math.abs(k/100);
            return current / perc;
        }

        protected Double meanAverage2(Double current) {
            double mean = (current + lastRecord) / 2;
            lastRecord = current;
            return mean;
        }
    }

    protected Gas co, no2, o3;
    private HashMap<String, HashMap<String, String>> calibMap;

    public LinearCalibration(org.json.simple.JSONObject array) {
        calibMap = array;
        co = new Gas() {
            @Override
            public Double linear(double temp, double we, double ae, double we_zero, double ae_zero, String afe, HashMap calibMap) throws AQNotFoundException {
                S = "S1";
                return super.linear(temp, we, ae, we_zero, ae_zero, afe, calibMap);
            }

        };
        no2 = new Gas() {
            @Override
            public Double linear(double temp, double we, double ae, double we_zero, double ae_zero, String afe, HashMap calibMap) throws AQNotFoundException {
                S = "S2";
                return super.linear(temp, we, ae, we_zero, ae_zero, afe, calibMap);
            }
        };
        o3 = new Gas() {
            @Override
            public Double linear(double temp, double we, double ae, double we_zero, double ae_zero, String afe, HashMap calibMap) throws AQNotFoundException {
                S = "S3";
                return super.linear(temp, we, ae, we_zero, ae_zero, afe, calibMap);
            }

        };
        Log.d("", "startup completed");
    }

    /**
     * test the 3 gases
     *
     * @param coInput  [temp, we, ae, we 0, ae 0]
     * @param no2Input [temp, we, ae, we 0, ae 0]
     * @param o3Input  [temp, we, ae, we 0, ae 0]
     * @param afe
     * @return a triple with the 3 estimation
     * @throws InvalidInputException
     * @throws AQNotFoundException
     * @throws MapNotFound
     */
    public Triple test(Double[] coInput, Double[] no2Input, Double[] o3Input, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound {
        return new Triple(
                testCO(coInput, afe),
                testNO2(no2Input, afe),
                testO3(o3Input, afe));
    }

    /**
     * @param input input[0] = temp
     *              input[1] = we co
     *              input[2] = ae co
     *              input[3] = we co zero
     *              input[4] = ae co zero
     * @param afe
     * @return co estimation
     * @throws InvalidInputException
     */
    public Double testCO(Double[] input, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound {
        lastCo = co.linear(input[0], input[1], input[2], input[3], input[4], afe, calibMap);
        lastCo /= 1000; //da ppb a ppm
        lastCo = co.sensitivityTCorrection(lastCo, input[0], "cot1", "cot2");
        lastCo = co.meanAverage2(lastCo);
        if (lastNo2 >= 0) {
            return toPrecision(lastCo, 2);
        } else {
            lastNo2 = 0d;
            return 0d;
        }
    }

    /**
     * r
     *
     * @param input input[0] = temp
     *              input[1] = we co
     *              input[2] = ae co
     *              input[3] = we co zero
     *              input[4] = ae co zero
     * @param afe
     * @return no2 estimation
     * @throws InvalidInputException
     * @throws AQNotFoundException
     * @throws MapNotFound
     */
    public Double testNO2(Double[] input, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound {
        lastNo2 = no2.linear(input[0], input[1], input[2], input[3], input[4], afe, calibMap);
        lastNo2 = no2.sensitivityTCorrection(lastNo2, input[0], "no2t1", "no2t2");
        lastNo2 = no2.meanAverage2(lastNo2);
        if (lastNo2 >= 0) {
            return toPrecision(lastNo2, 2);
        } else {
            lastNo2 = 0d;
            return 0d;
        }
    }

    /**
     * run testNO2 before testO3
     *
     * @param input input[0] = temp
     *              input[1] = we co
     *              input[2] = ae co
     *              input[3] = we co zero
     *              input[4] = ae co zero
     * @param afe
     * @return no2 estimation
     * @throws InvalidInputException
     * @throws AQNotFoundException
     * @throws MapNotFound
     */
    public Double testO3(Double[] input, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound {
        lastO3 = o3.linear(input[0], input[1], input[2], input[3], input[4], afe, calibMap);
        lastO3 = o3.sensitivityTCorrection(lastO3, input[0], "o3t1", "o3t2");
        lastO3 = o3.meanAverage2(lastO3);
        lastO3 -= lastNo2; // NO2 - NO2O3 reading
        if (lastO3 < 0) {
            lastO3 = 0d;
        }
        return toPrecision(lastO3, 2);
    }

    private double toPrecision(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
