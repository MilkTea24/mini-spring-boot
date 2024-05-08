package com.milktea.myspring.boot.web.utils.converters;

public enum ContentType {
    FORM_URLENCODED("application/x-www-form-urlencoded"),
    JSON("application/json"),
    PLAIN_TEST("text/plain"),
    FORM_DATA("application/form-data");

    ContentType(String fullName) {
        this.fullName = fullName;
    }

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public static ContentType fromString(String fullName) {
        for (ContentType type : ContentType.values()) {
            if (type.getFullName().equalsIgnoreCase(fullName)) {
                return type;
            }
        }
        throw new RuntimeException("해당하는 타입을 찾을 수 없습니다.");
    }
}
