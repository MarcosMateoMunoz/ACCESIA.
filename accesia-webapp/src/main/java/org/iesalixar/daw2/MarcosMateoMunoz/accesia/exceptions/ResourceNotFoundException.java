package org.iesalixar.daw2.MarcosMateoMunoz.accesia.exceptions;

/** Se lanza cuando no existe el registro solicitado. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
