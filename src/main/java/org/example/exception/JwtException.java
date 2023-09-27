package org.example.exception;

public class JwtException extends IllegalArgumentException {
    public JwtException(String s) {
        super(s);
    }
}