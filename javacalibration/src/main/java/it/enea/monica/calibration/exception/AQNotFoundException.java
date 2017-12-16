package it.enea.monica.calibration.exception;

/**
 * Created by ozeta on 06/12/2017.
 */

public class AQNotFoundException extends Exception {
    public AQNotFoundException(String afe) {
        super("AQ afe not found in calibration file. please add " + afe);
    }

    public AQNotFoundException(String afe, String s) {
        super("S not found for afe: " + afe + " S: " + s);
    }
}
