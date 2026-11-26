package org.iesalixar.daw2.MarcosMateoMunoz.accesia.exceptions;

/** Se lanza cuando se intenta guardar un registro con un valor único ya existente. */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
