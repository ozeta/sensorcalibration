package it.enea.monica.calibration.exception;

public class InvalidInputException extends Exception {
    public InvalidInputException(String empty_input) {
        super(empty_input);
    }
}
