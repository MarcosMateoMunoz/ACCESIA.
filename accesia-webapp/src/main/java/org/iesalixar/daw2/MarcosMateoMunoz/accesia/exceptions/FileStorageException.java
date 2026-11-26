package org.iesalixar.daw2.MarcosMateoMunoz.accesia.exceptions;

/** Se lanza cuando falla el guardado o borrado de una imagen en disco. */
public class FileStorageException extends RuntimeException {

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
