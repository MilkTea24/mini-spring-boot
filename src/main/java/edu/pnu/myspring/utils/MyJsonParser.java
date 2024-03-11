package edu.pnu.myspring.utils;

import java.util.*;

public class MyJsonParser {
    private String json;
    private int index;

    private final JsonEscapeCharacterHandler jsonEscapeCharacterHandler;

    public MyJsonParser(String json) {
        this.json = json;
        this.index = 0;
        jsonEscapeCharacterHandler = new JsonEscapeCharacterHandler();
    }

    public MyJsonParser() {
        this.index = 0;
        jsonEscapeCharacterHandler = new JsonEscapeCharacterHandler();
    }

    public void setJson(String json) {
        this.index = 0;
        this.json = json;
    }

    public Object parseJsonString() {
        skipWhitespace();
        char currentChar = json.charAt(index);

        if (currentChar == '{') {
            return parseObject();
        } else if (currentChar == '[') {
            return parseArray();
        } else if (currentChar == '"') {
            return parseString();
        } else return parseNumber();
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> object = new HashMap<>();
        index++;
        boolean isFirst = true;

        while (true) {
            skipWhitespace();
            if (json.charAt(index) == '}') {
                index++;
                break;
            }

            if (!isFirst) {
                if (json.charAt(index) == ',') {
                    index++;
                }
            }

            skipWhitespace();
            String key = parseString();
            skipWhitespace();
            index++;
            Object value = parseJsonString();
            object.put(key, value);

            isFirst = false;
        }

        return object;
    }

    private List<Object> parseArray() {
        List<Object> array = new ArrayList<>();
        index++;
        boolean isFirst = true;

        while (true) {
            skipWhitespace();
            if (json.charAt(index) == ']') {
                index++;
                break;
            }

            if (!isFirst) {
                if (json.charAt(index) == ',') {
                    index++;
                }
            }

            skipWhitespace();
            Object value = parseJsonString();
            array.add(value);

            isFirst = false;
        }


        return array;
    }

    private String parseString() {
        StringBuilder sb = new StringBuilder();
        index++;

        while (index < json.length()) {
            char currentChar = json.charAt(index);

            if (currentChar == '"') {
                index++;
                break;
            } else if (currentChar == '\\') {
                sb.append(jsonEscapeCharacterHandler.parseEscapeSequence(json.charAt(index + 1), json, index + 1));
                index = jsonEscapeCharacterHandler.returnIndex(json.charAt(index + 1), index + 1);
            } else {
                index++;
                sb.append(currentChar);
            }
        }

        return sb.toString();
    }

    private Object parseNumber() {
        int startIndex = index;

        while (index < json.length() && isNumberChar(json.charAt(index))) {
            index++;
        }

        String numberStr = json.substring(startIndex, index);
        if (numberStr.contains(".") || numberStr.toLowerCase().contains("e")) {
            return Double.parseDouble(numberStr);
        } else {
            return Integer.parseInt(numberStr);
        }
    }

    private boolean isNumberChar(char c) {
        return Character.isDigit(c) || c == '-' || c == '+' || c == '.' || c == 'e' || c == 'E';
    }

    private void skipWhitespace() {
        while (index < json.length() && Character.isWhitespace(json.charAt(index))) {
            index++;
        }
    }
}

