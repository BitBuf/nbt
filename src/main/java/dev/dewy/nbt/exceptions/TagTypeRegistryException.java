package dev.dewy.nbt.exceptions;

public class TagTypeRegistryException extends Exception {
    public TagTypeRegistryException(String message) {
        super(message);
    }

    public TagTypeRegistryException(String message, Throwable cause) {
        super(message, cause);
    }
}
