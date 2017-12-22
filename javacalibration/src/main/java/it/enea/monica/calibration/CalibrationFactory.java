package it.enea.monica.calibration;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Created by ozeta on 16/11/2017.
 * return the specific calibration object: neural or linear supported.
 * NN can be created with 1 gas (getMonoNeuralCalibration) and all 3 gases (getMultiNeuralCalibration)
 */

public class CalibrationFactory {
    public static NeuralCalibration getMonoNeuralCalibration(JSONObject json_calib) {
        return new NeuralCalibration(json_calib);
    }

    public static NeuralCalibration getMultiNeuralCalibration(JSONObject co_calib, JSONObject no2_calib, JSONObject o3_calib) {
        return new NeuralCalibration(co_calib, no2_calib, o3_calib);
    }

    public static LinearCalibration getLinearCalibration(JSONObject json_calib) {
        return new LinearCalibration(json_calib);
    }
}
