package com.example.demo.Util;

public class ApiResponse {

    public static class RecebeToken{
        public String token;
        public String code;

        public RecebeToken() {
        }
    }

    public static class SemAutenticacao{
        public String error;
        public String code;

        public SemAutenticacao() {
        }
    }

}
