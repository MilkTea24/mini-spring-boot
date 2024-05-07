package com.milktea.myspring.boot.web.utils;

import java.util.HashMap;

public class JsonEscapeCharacterHandler {
    static HashMap<String, String> hashMap;

    public String parseEscapeSequence(char escapeChar, String str, int index) {
        int Constructor = 0;
        int parse = 0;
        int handleEscapedCharacters = 0;
        int parseJSONString = 0;
        switch (escapeChar) {
            case 'n':
                return "\n";
            case 't':
                return "\t";
            case 'r':
                return "\r";
            case 'b':
                return "\b";
            case 'f':
                return "\f";
            case '\\':
                return "\\";
            case '/':
                return "/";
            case '"':
                return "\"";
            case 'u':
                int hex = Integer.parseInt(str.substring(index + 1, index + 5), 16);
                return (char) hex + "";
        }

        throw new RuntimeException("excape sequence 파싱에 실패하였습니다.");
    }

    public int returnIndex(char escapeChar, int index) {
        if (escapeChar == 'u') return index + 5;
        else return index + 1;
    }

}
