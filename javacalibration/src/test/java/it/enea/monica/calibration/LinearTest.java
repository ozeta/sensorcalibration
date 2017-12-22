package it.enea.monica.calibration;

import it.enea.monica.calibration.exception.AQNotFoundException;
import it.enea.monica.calibration.exception.InvalidInputException;
import it.enea.monica.calibration.exception.MapNotFound;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LinearTest {
    private ArrayList<Double[]> coList;
    private ArrayList<Double[]> no2List;
    private ArrayList<Double[]> o3List;
    private ArrayList<Double[]> test;
    private LinearCalibration linear;

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

    @Before
    public void setup() {
        coList = new ArrayList<Double[]>();
        no2List = new ArrayList<Double[]>();
        o3List = new ArrayList<Double[]>();
        test = new ArrayList<Double[]>();

        buildArrays();
        linear = CalibrationFactory.getLinearCalibration(getJsonObject("./res/calib_table/linear_calibration.json"));

    }

    private void buildArrays() {

        coList.add(new Double[]{15.8d, 377.956d, 283.423d, 306d, 281d});
        coList.add(new Double[]{15.5d, 401.634d, 255.287d, 306d, 281d});
        coList.add(new Double[]{15.3d, 388.697d, 265.799d, 306d, 281d});
        coList.add(new Double[]{15.1d, 407.044d, 271.303d, 306d, 281d});
        coList.add(new Double[]{15.4d, 454.189d, 288.438d, 306d, 281d});
        coList.add(new Double[]{15.4d, 478.954d, 307.662d, 306d, 281d});
        coList.add(new Double[]{15.3d, 613.473d, 307.843d, 306d, 281d});
        coList.add(new Double[]{15.2d, 745.266d, 301.375d, 306d, 281d});
        coList.add(new Double[]{15d, 1261.85d, 296.178d, 306d, 281d});
        coList.add(new Double[]{14.9d, 1490.35d, 300.215d, 306d, 281d});
        coList.add(new Double[]{14.9d, 2165.07d, 318.354d, 306d, 281d});
        coList.add(new Double[]{14.9d, 2782.798d, 336.142d, 306d, 281d});
        coList.add(new Double[]{14.9d, 2701.711d, 340.789d, 306d, 281d});
        coList.add(new Double[]{15d, 2123.026d, 323.205d, 306d, 281d});
        coList.add(new Double[]{15d, 2027.618d, 316.737d, 306d, 281d});
        coList.add(new Double[]{15d, 1688.975d, 298.6d, 306d, 281d});
        coList.add(new Double[]{15d, 1476.19d, 290.055d, 306d, 281d});
        coList.add(new Double[]{14.9d, 1370.271d, 272.267d, 306d, 281d});
        coList.add(new Double[]{14.9d, 1393.719d, 281.969d, 306d, 281d});
        coList.add(new Double[]{14.8d, 1878.037d, 285.204d, 306d, 281d});
        coList.add(new Double[]{14.8d, 1714.812d, 276.799d, 306d, 281d});


        no2List.add(new Double[]{15.8d, 292.31d, 301.198d, 290d, 315d});
        no2List.add(new Double[]{15.5d, 295.715d, 299.757d, 290d, 315d});
        no2List.add(new Double[]{15.3d, 298.949d, 303.8d, 290d, 315d});
        no2List.add(new Double[]{15.1d, 297.158d, 304.43d, 290d, 315d});
        no2List.add(new Double[]{15.4d, 303.8d, 302.992d, 290d, 315d});
        no2List.add(new Double[]{15.4d, 297.158d, 303.622d, 290d, 315d});
        no2List.add(new Double[]{15.3d, 297.332d, 298.949d, 290d, 315d});
        no2List.add(new Double[]{15.2d, 296.523d, 303.8d, 290d, 315d});
        no2List.add(new Double[]{15d, 296.178d, 301.022d, 290d, 315d});
        no2List.add(new Double[]{14.9d, 294.563d, 299.407d, 290d, 315d});
        no2List.add(new Double[]{14.9d, 306.226d, 300.566d, 290d, 315d});
        no2List.add(new Double[]{14.9d, 298.14d, 303.8d, 290d, 315d});
        no2List.add(new Double[]{14.9d, 297.158d, 300.39d, 290d, 315d});
        no2List.add(new Double[]{15d, 234.265d, 300.566d, 290d, 315d});
        no2List.add(new Double[]{15d, 297.332d, 302.183d, 290d, 315d});
        no2List.add(new Double[]{15d, 295.37d, 300.215d, 290d, 315d});
        no2List.add(new Double[]{15d, 297.332d, 299.757d, 290d, 315d});
        no2List.add(new Double[]{14.9d, 305.417d, 298.14d, 290d, 315d});
        no2List.add(new Double[]{14.9d, 300.566d, 304.609d, 290d, 315d});
        no2List.add(new Double[]{14.8d, 299.757d, 302.183d, 290d, 315d});
        no2List.add(new Double[]{14.8d, 302.637d, 299.407d, 290d, 315d});

        o3List.add(new Double[]{15.8d, 433.707d, 391.692d, 419d, 402d});
        o3List.add(new Double[]{15.5d, 429.933d, 392.74d, 419d, 402d});
        o3List.add(new Double[]{15.3d, 429.933d, 363.632d, 419d, 402d});
        o3List.add(new Double[]{15.1d, 432.091d, 401.388d, 419d, 402d});
        o3List.add(new Double[]{15.4d, 435.593d, 392.74d, 419d, 402d});
        o3List.add(new Double[]{15.4d, 433.707d, 393.308d, 419d, 402d});
        o3List.add(new Double[]{15.3d, 433.167d, 393.549d, 419d, 402d});
        o3List.add(new Double[]{15.2d, 442.061d, 392.74d, 419d, 402d});
        o3List.add(new Double[]{15d, 427.787d, 398.72d, 419d, 402d});
        o3List.add(new Double[]{14.9d, 432.631d, 390.646d, 419d, 402d});
        o3List.add(new Double[]{14.9d, 429.933d, 387.08d, 419d, 402d});
        o3List.add(new Double[]{14.9d, 431.55d, 393.549d, 419d, 402d});
        o3List.add(new Double[]{14.9d, 428.859d, 389.268d, 419d, 402d});
        o3List.add(new Double[]{15d, 428.316d, 389.506d, 419d, 402d});
        o3List.add(new Double[]{15d, 434.784d, 352.313d, 419d, 402d});
        o3List.add(new Double[]{15d, 426.979d, 382.571d, 419d, 402d});
        o3List.add(new Double[]{15d, 447.721d, 384.655d, 419d, 402d});
        o3List.add(new Double[]{14.9d, 456.615d, 401.634d, 419d, 402d});
        o3List.add(new Double[]{14.9d, 440.444d, 387.08d, 419d, 402d});
        o3List.add(new Double[]{14.8d, 449.338d, 392.74d, 419d, 402d});
        o3List.add(new Double[]{14.8d, 442.32d, 387.416d, 419d, 402d});

        test.add(new Double[]{19.29999924d, 332.592d, 252.533d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 332.592d, 252.533d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 329.324d, 253.35d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 327.494d, 257.287d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 327.494d, 257.287d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 326.678d, 256.471d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 326.678d, 256.471d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 353.015d, 256.617d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 892.19d, 271.322d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 892.19d, 271.322d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1467.128d, 278.513d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1647.852d, 283.576d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1647.852d, 283.576d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1667.136d, 285.043d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1667.136d, 285.043d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1635.298d, 279.329d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1575.962d, 280.309d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1575.962d, 280.309d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1478.747d, 280.309d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1478.747d, 280.309d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1352.022d, 275.247d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1218.139d, 271.982d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1218.139d, 271.982d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1186.301d, 277.696d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1186.301d, 277.696d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1207.527d, 276.88d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1226.303d, 289.942d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1226.303d, 289.942d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1254.091d, 290.112d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1254.091d, 290.112d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1318.551d, 300.554d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1270.429d, 304d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1270.429d, 304d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1180.587d, 305.452d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1180.587d, 305.452d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 1102.141d, 308.901d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 995.274d, 303.82d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 995.274d, 303.82d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 912.822d, 297.289d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 912.822d, 297.289d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 851.344d, 299.098d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 790.368d, 296.472d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 790.368d, 296.472d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 731.591d, 290.758d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 731.591d, 290.758d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 686.691d, 284.227d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 649.139d, 287.493d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 649.139d, 287.493d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 631.995d, 285.86d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 631.995d, 285.86d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 607.504d, 284.227d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 573.217d, 283.411d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 573.217d, 283.411d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 542.196d, 280.145d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 542.196d, 280.145d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 529.473d, 277.858d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 525.052d, 279.329d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 525.052d, 279.329d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 509.866d, 282.759d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 492.711d, 286.844d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 492.711d, 286.844d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 473.104d, 286.027d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 473.104d, 286.027d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 445.866d, 277.696d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 445.866d, 277.696d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 427.09d, 274.431d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 437.702d, 276.064d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 437.702d, 276.064d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 414.844d, 270.349d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 414.844d, 270.349d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 409.946d, 272.798d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 400.15d, 271.982d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 400.15d, 271.982d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 383.242d, 272.139d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 383.242d, 272.139d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 376.706d, 275.407d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 372.394d, 274.431d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 372.394d, 274.431d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 365.863d, 272.798d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 365.863d, 272.798d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 358.516d, 268.716d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 364.452d, 272.139d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 364.452d, 272.139d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 358.516d, 271.165d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 358.516d, 271.165d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 356.666d, 268.561d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 356.283d, 268.055d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 356.283d, 268.055d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 357.917d, 270.505d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 357.917d, 270.505d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 360.965d, 273.614d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 360.965d, 279.329d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 360.965d, 279.329d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 364.452d, 280.309d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 324.229d, 275.247d, 338.6d, 242.2d});
        test.add(new Double[]{19.29999924d, 324.229d, 275.247d, 338.6d, 242.2d});

    }

    @Test
    public void testLinearWithAFE20000033() {
        try {
            for (int i = 0; i < coList.size(); i++) {
                Double[] co = coList.get(i);
                Double[] no2 = no2List.get(i);
                Double[] o3 = o3List.get(i);
                ICalibration.Triple a = linear.test(co, no2, o3, "20000033");
                System.out.println(a);
            }
        } catch (AQNotFoundException | InvalidInputException | MapNotFound e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLinearCOTESTWithAFE20000033() {
        try {
            for (int i = 0; i < test.size(); i++) {
                Double[] co = test.get(i);
                double coresult = linear.testCO(co, "20000033");
                System.out.println(coresult);
            }
        } catch (AQNotFoundException | InvalidInputException | MapNotFound e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLinearCOWithAFE20000033() {
        try {
            for (int i = 0; i < coList.size(); i++) {
                Double[] co = coList.get(i);
                double coresult = linear.testCO(co, "20000033");
                System.out.println(coresult);
            }
        } catch (AQNotFoundException | InvalidInputException | MapNotFound e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLinearNO2WithAFE20000033() {
        try {
            for (int i = 0; i < no2List.size(); i++) {
                Double[] no2 = no2List.get(i);
                double no2result = linear.testNO2(no2, "20000033");
                System.out.println(no2result);
            }
        } catch (AQNotFoundException | InvalidInputException | MapNotFound e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLinearO3WithAFE20000033() {
        try {
            for (int i = 0; i < o3List.size(); i++) {
                Double[] no2 = no2List.get(i);
                Double[] o3 = o3List.get(i);
                double no2result = linear.testNO2(no2, "20000033");
                double o3result = linear.testO3(o3, "20000033");
                System.out.println(o3result);
            }
        } catch (AQNotFoundException | InvalidInputException | MapNotFound e) {
            e.printStackTrace();
        }

    }

    /**
     * CO
     */
    @Test
    public void testAFE20000033coSensibilityTCorrectionLow() {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Double> map = (HashMap<String, Double>) getJsonObject("./res/calib_table/linear_calibration.json").get("20000033");
            linear.co.map = map;
            Double testValue = 100d;
            Double result = linear.co.sensitivityTCorrection(testValue, -20d, "cot1", "cot2");
            assertTrue(result > testValue);
            System.out.println("sensitivity test CO with low temperature: " + result);
        } catch (MapNotFound mapNotFound) {
            mapNotFound.printStackTrace();
        }
    }

    /**
     * CO
     */
    @Test
    public void testAFE20000033coSensibilityTCorrectionLow1() {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Double> map = (HashMap<String, Double>) getJsonObject("./res/calib_table/linear_calibration.json").get("20000033");
            linear.co.map = map;
            Double testValue = 5d;
            Double result = linear.co.sensitivityTCorrection(testValue, 0d, "cot1", "cot2");
            System.out.println("sensitivity test CO with low temperature: " + result);
        } catch (MapNotFound mapNotFound) {
            mapNotFound.printStackTrace();
        }
    }

    @Test
    public void testAFE20000033coSensibilityTCorrectionHigh() {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Double> map = (HashMap<String, Double>) getJsonObject("./res/calib_table/linear_calibration.json").get("20000033");
            linear.co.map = map;
            Double testValue = 115d;

            Double result = linear.co.sensitivityTCorrection(testValue, 40d, "cot1", "cot2");
            assertTrue(result < testValue);

            System.out.println("sensitivity test CO with high temperature: " + result);
        } catch (MapNotFound mapNotFound) {
            mapNotFound.printStackTrace();
        }
    }

    /***
     *
     * NO2
     *
     *
     */

    @Test
    public void testAFE20000033no2SensibilityTCorrectionLow() {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Double> map = (HashMap<String, Double>) getJsonObject("./res/calib_table/linear_calibration.json").get("20000033");
            linear.no2.map = map;
            Double testValue = 100d;
            Double result = linear.no2.sensitivityTCorrection(testValue, -20d, "no2t1", "no2t2");
            assertTrue(result > testValue);
            System.out.println("sensitivity test NO2 with low temperature: " + result);
        } catch (MapNotFound mapNotFound) {
            mapNotFound.printStackTrace();
        }
    }

    @Test
    public void testAFE20000033no2SensibilityTCorrectionHigh() {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Double> map = (HashMap<String, Double>) getJsonObject("./res/calib_table/linear_calibration.json").get("20000033");
            linear.no2.map = map;
            Double testValue = 115d;

            Double result = linear.no2.sensitivityTCorrection(testValue, 40d, "no2t1", "no2t2");
            assertTrue(result < testValue);

            System.out.println("sensitivity test NO2 with high temperature: " + result);
        } catch (MapNotFound mapNotFound) {
            mapNotFound.printStackTrace();
        }
    }

    /***
     *
     * O3
     *
     *
     */
    @Test
    public void testAFE20000033O3SensibilityTCorrectionLow() {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Double> map = (HashMap<String, Double>) getJsonObject("./res/calib_table/linear_calibration.json").get("20000033");
            linear.o3.map = map;
            Double testValue = 100d;
            Double result = linear.o3.sensitivityTCorrection(testValue, -20d, "o3t1", "o3t2");
            System.out.println("sensitivity test O3 with low temperature: " + result);
            assertTrue(result > testValue);
        } catch (MapNotFound mapNotFound) {
            mapNotFound.printStackTrace();
        }
    }

    @Test
    public void testAFE20000033O3SensibilityTCorrectionHigh() {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Double> map = (HashMap<String, Double>) getJsonObject("./res/calib_table/linear_calibration.json").get("20000033");
            linear.o3.map = map;
            Double testValue = 115d;

            Double result = linear.o3.sensitivityTCorrection(testValue, 40d, "o3t1", "o3t2");
            System.out.println("sensitivity test O3 with high temperature: " + result);
            assertTrue(result > testValue);

        } catch (MapNotFound mapNotFound) {
            mapNotFound.printStackTrace();
        }
    }

    /***
     *
     *
     *AVG
     *
     */
    @Test
    public void testMean2AverageCO() {
        System.out.println("CO AVG");
        testMean2Average(linear.co);
    }

    @Test
    public void testMean2AverageNO2() {
        System.out.println("NO2 AVG");
        testMean2Average(linear.co);
    }

    @Test
    public void testMean2AverageO3() {
        System.out.println("O3 AVG");
        testMean2Average(linear.co);
    }

    public void testMean2Average(LinearCalibration.Gas gas) {
        Double result = gas.meanAverage2(5d);
        assertTrue(result == 2.5d);
        System.out.println(result);
        result = gas.meanAverage2(5d);
        assertTrue(result == 5d);
        System.out.println(result);
        result = gas.meanAverage2(10d);
        assertTrue(result == 7.5);
        System.out.println(result);
        result = gas.meanAverage2(10d);
        assertTrue(result == 10d);
        System.out.println(result);
    }
}
