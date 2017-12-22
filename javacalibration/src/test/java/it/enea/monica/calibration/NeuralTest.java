package it.enea.monica.calibration;


import it.enea.monica.calibration.exception.AQNotFoundException;
import it.enea.monica.calibration.exception.InvalidInputException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NeuralTest {
    NeuralCalibration neuralCalibration;

    private static org.json.simple.JSONObject getJsonObject(String path) {
        Scanner input = null;
        try {
            input = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String json = "";
        for (Scanner it = input; it.hasNext(); ) {
            String s = it.next();
            json = json + s;
        }

        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject jobj = null;
        Object obj = null;
        try {
            obj = parser.parse(json);
            jobj = (org.json.simple.JSONObject) obj;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jobj;
    }

    Double[] a;
    Double[] b;
    ArrayList<Double[]> multi;
    ArrayList<Double[]> mono;

    @Before
    public void beforeTest() {

        Double[] a = {34.67, 259.487, 254.652, 356.996, 373.919, 2965.5680, 257.875};
        Double[] b = {33.87, 253.846, 253.846, 360.220, 369.890, 2249.1570, 243.370};
        Double[] c = {34.67, 255.458, 253.040, 358.608, 371.502, 1693.9200, 232.088};
        Double[] d = {33.07, 257.875, 250.623, 360.220, 375.531, 1693.1140, 231.282};
        Double[] e = {35.47, 258.681, 249.817, 356.996, 375.531, 1693.1140, 229.670};
        Double[] f = {36.27, 252.234, 248.205, 361.026, 372.308, 1149.9630, 215.971};
        Double[] g = {35.47, 260.293, 252.234, 356.190, 377.143, 1151.5750, 218.388};
        Double[] h = {35.47, 258.681, 249.011, 355.385, 372.308, 1079.8540, 215.165};
        Double[] i = {38.77, 257.070, 248.205, 357.802, 370.696, 659.19400, 206.300};
        Double[] l = {30.67, 255.458, 253.040, 361.026, 373.919, 664.83500, 207.106};
        Double[] m = {34.67, 260.293, 250.623, 356.996, 373.919, 623.73600, 207.106};
        Double[] n = {31.47, 253.846, 253.846, 354.579, 369.890, 289.30400, 195.018};
        Double[] o = {33.87, 253.040, 256.264, 357.802, 376.337, 285.27500, 193.407};
        multi = new ArrayList<Double[]>();
        multi.add(a);
        multi.add(b);
        multi.add(c);
        multi.add(d);
        multi.add(e);
        multi.add(f);
        multi.add(g);
        multi.add(h);
        multi.add(i);
        multi.add(l);
        multi.add(m);
        multi.add(n);
        multi.add(o);


    }

    @Before
    public void beforeMono() {
        Double[] a = {34.67, 2965.5680, 257.875};
        Double[] b = {33.87, 2249.1570, 243.370};
        Double[] c = {34.67, 1693.9200, 232.088};
        Double[] d = {33.07, 1693.1140, 231.282};
        Double[] e = {35.47, 1693.1140, 229.670};
        Double[] f = {36.27, 1149.9630, 215.971};
        Double[] g = {35.47, 1151.5750, 218.388};
        Double[] h = {35.47, 1079.8540, 215.165};
        Double[] i = {38.77, 659.19400, 206.300};
        Double[] l = {30.67, 664.83500, 207.106};
        Double[] m = {34.67, 623.73600, 207.106};
        Double[] n = {31.47, 289.30400, 195.018};
        Double[] o = {33.87, 285.27500, 193.407};
        mono = new ArrayList<Double[]>();
        mono.add(a);
        mono.add(b);
        mono.add(c);
        mono.add(d);
        mono.add(e);
        mono.add(f);
        mono.add(g);
        mono.add(h);
        mono.add(i);
        mono.add(l);
        mono.add(m);
        mono.add(n);
        mono.add(o);
    }

    @Test
    public void neuralTestMono() throws InvalidInputException {
        neuralCalibration = CalibrationFactory.getMonoNeuralCalibration(getJsonObject("./res/calib_table/co_weight_cell.json"));

        for (Double[] doub : mono) {
            try {
                System.out.println(neuralCalibration.testCO(doub, "20000033"));
            } catch (InvalidInputException | AQNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("end");

    }
}
