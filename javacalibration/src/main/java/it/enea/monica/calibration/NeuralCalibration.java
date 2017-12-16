package it.enea.monica.calibration;

import android.util.Log;

import it.enea.monica.calibration.exception.AQNotFoundException;
import it.enea.monica.calibration.exception.InvalidInputException;
import it.enea.monica.calibration.exception.MapNotFound;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NeuralCalibration implements ICalibration{

    private final JSONObject coCalib;
    private final JSONObject no2Calib;
    private final JSONObject o3Calib;

    public NeuralCalibration(JSONObject co_calib) {
        this.coCalib = co_calib;
        this.no2Calib = null;
        this.o3Calib = null;
    }

    public NeuralCalibration(JSONObject co_calib, JSONObject no2_calib, JSONObject o3_calib) {
        this.coCalib = co_calib;
        this.no2Calib = no2_calib;
        this.o3Calib = o3_calib;
    }

    @Override
    public Triple test(Double[] coInput, Double[] no2Input, Double[] o3Input, String afe) throws InvalidInputException, AQNotFoundException, MapNotFound {
        return new Triple(
                testCO(coInput, afe),
                testNO2(no2Input, afe),
                testO3(o3Input, afe));
    }

    /**
     * @param px1 px[0] = temperatura
     *            px[1] = we co
     *            px[2] = ae co
     *            px[3] = we no2
     *            px[4] = ae no2
     *            px[5] = we o3
     *            px[6] = ae o3
     * @param afe
     * @return
     * @throws InvalidInputException
     */
    public Double testCO(Double[] px1, String afe) throws InvalidInputException, AQNotFoundException {
        if (!setupready) {
            JSONArray map = (JSONArray) coCalib.get(afe);
            if (map == null){
                throw new AQNotFoundException(afe);
            }
            this.setup(map);
        }
        return test(px1);
    }

    public Double testNO2(Double[] px1, String afe) throws InvalidInputException, AQNotFoundException {
        if (this.no2Calib == null) {
            throw new InvalidInputException("no2calib json not found!");
        }
        if (!setupready) {
            JSONArray map = (JSONArray) no2Calib.get(afe);
            if (map == null){
                throw new AQNotFoundException(afe);
            }
            this.setup(map);
        }
        return test(px1);
    }

    public Double testO3(Double[] px1, String afe) throws InvalidInputException, AQNotFoundException {
        if (this.o3Calib == null) {
            throw new InvalidInputException("o3calib json not found!");
        }
        if (!setupready) {
            JSONArray map = (JSONArray) o3Calib.get(afe);
            if (map == null){
                throw new AQNotFoundException(afe);
            }
            this.setup(map);
        }
        return test(px1);
    }

    private Double test(Double[] xx1) {
        try {

            double dxx1[] = new double[xx1.length];
            for (int i = 0; i < xx1.length; i++) {
                dxx1[i] = xx1[i];
            }
            DoubleMatrix x1 = new DoubleMatrix(dxx1);
            DoubleMatrix xp1 = mapminmax_apply(x1, this.x1_step1);
            DoubleMatrix mult1 = IW1_1.mmul(xp1);
            DoubleMatrix sum1 = mult1.add(b1);
            DoubleMatrix a1 = tansig_apply(sum1);
            DoubleMatrix mult2 = LW2_1.mmul(a1);
            DoubleMatrix a2 = mult2.add(b2);
            DoubleMatrix y1 = mapinmax_reverse(a2, y1_step1);
            return y1.get(0);
        } catch (org.jblas.exceptions.SizeException ex) {
            return -5353d;
        }
    }

    private DoubleMatrix mapinmax_reverse(DoubleMatrix y, Nnconstant settings) {
        DoubleMatrix x = y.sub(settings.ymin);
        x = x.div(settings.gain);
        x = x.add(settings.xoffset);
        return x;
    }

    private DoubleMatrix tansig_apply(DoubleMatrix n) {
        return MatrixFunctions.tanh(n);
    }

    private DoubleMatrix mapminmax_apply(DoubleMatrix x, Nnconstant settings) {
        DoubleMatrix y = x.sub(settings.xoffset);
        y = y.mul(settings.gain);
        y = y.add(settings.ymin);
        return y;
    }

    public void setup(JSONArray array) {

        this.x1_step1 = parseStep1((JSONObject) array.get(0));
        this.y1_step1 = parseStep1((JSONObject) array.get(5));

        this.b1 = parseWeight((JSONArray) array.get(1));

        this.b2 = parseWeight((Double) array.get(3));
        this.LW2_1 = parseWeight((JSONArray) array.get(4)).transpose();
        this.IW1_1 = parseWeight((JSONArray) array.get(2));
        setupready = true;
    }

    public class Nnconstant {
        //campi pubblici per rendere più ridurre l'overhead sulla lettura/scrittura dei valori
        public DoubleMatrix xoffset;
        public DoubleMatrix gain;
        public Long ymin;

        @Override
        public String toString() {
            String output =
                    "xoffset:\n" + this.xoffset + "gain:\n" + this.gain
                            + "ymin:\n" + this.ymin;
            return output;
        }
    }

    private Nnconstant x1_step1;
    private Nnconstant y1_step1;

    //Layer1
    private DoubleMatrix b1;
    private DoubleMatrix IW1_1;
    //Layer2
    private DoubleMatrix b2;
    private DoubleMatrix LW2_1;
    private Boolean debug;

    boolean setupready = false;

    @SuppressWarnings("Duplicates")
    private Nnconstant parseStep1(JSONObject jx1_step1) {
        Nnconstant nn1 = new Nnconstant();
        HashMap m = (HashMap) jx1_step1;
        nn1.ymin = (Long) m.get("ymin");
        if (m.get("xoffset") instanceof ArrayList) {
            ArrayList xoffset = ((ArrayList) m.get("xoffset"));
            double tmp[] = new double[xoffset.size()];
            for (int i = 0; i < xoffset.size(); i++) {
                tmp[i] = (Double) xoffset.get(i);
            }
            nn1.xoffset = new DoubleMatrix(tmp);

            ArrayList<Double> gain = ((ArrayList) m.get("gain"));
            int i = 0;
            double[] gg = new double[gain.size()];
            for (double d : gain) {
                gg[i++] = d;
            }
            nn1.gain = new DoubleMatrix(gg);
        } else {
            double d = Double.parseDouble(m.get("xoffset").toString());
            double[][] mm = {{d}};
            nn1.xoffset = new DoubleMatrix(mm);
            d = Double.parseDouble(m.get("gain").toString());

            double[][] cc = {{d}};
            nn1.gain = new DoubleMatrix(cc);
        }
        return nn1;
    }


    @SuppressWarnings("Duplicates")
    private DoubleMatrix parseWeight(Object obj) {
        DoubleMatrix res = null;
        if (obj instanceof JSONArray) {
            double tmp[] = new double[((JSONArray) obj).size()];
            int i = 0;
            if (((JSONArray) obj).get(0) instanceof Double) {

                for (Object o : (JSONArray) obj) {
                    tmp[i++] = (Double) o;
                }
                res = new DoubleMatrix(tmp);

            } else if (((JSONArray) obj).get(0) instanceof JSONArray) {
                int size = ((JSONArray) obj).size();
                double[][] tmpF = new double[size][];
                ArrayList larr0 = (ArrayList) ((JSONArray) obj).get(0);
                ArrayList larr1 = (ArrayList) ((JSONArray) obj).get(1);

                double tmp1[] = new double[larr0.size()];
                double tmp2[] = new double[larr1.size()];
                i = 0;
                for (i = 0; i < larr0.size(); i++) {
                    Object o = larr0.get(i);
                    Object o1 = larr1.get(i);
                    tmp1[i] = (Double) o;
                    tmp2[i] = (Double) o1;
                }
                tmpF[0] = tmp1;
                tmpF[1] = tmp2;
                res = new DoubleMatrix(tmpF);
            }
        } else {
            try {
                res = new DoubleMatrix(new double[][]{{(Double) obj}});
            } catch (Exception ex) {
                Log.e("ZOID", "Double parse error");
            }

        }
        return res;
    }

}
