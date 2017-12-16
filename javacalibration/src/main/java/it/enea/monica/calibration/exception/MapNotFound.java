package it.enea.monica.calibration.exception;

/**
 * Created by ozeta on 06/12/2017.
 */

public class MapNotFound extends Exception {
    public MapNotFound() {
        super("parameter hashmap for specified AQ was not initialized");
    }

}
