package it.enea.monica.calibration;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Created by ozeta on 16/11/2017.
 */

public class CalibrationFactory {
    public static NeuralCalibration getNeuralCalibration(JSONObject json_calib){
        return new NeuralCalibration(json_calib);
    }
    public static NeuralCalibration getNeuralCalibration(JSONObject co_calib, JSONObject no2_calib, JSONObject o3_calib ){
        return new NeuralCalibration(co_calib, no2_calib, o3_calib);
    }
    public static LinearCalibration getLinearCalibration(JSONObject json_calib){
        return new LinearCalibration(json_calib);
    }
}
