package edu.pnu.cse.validation;

import edu.pnu.cse.annotations.Email;
import edu.pnu.cse.annotations.NotNull;
import edu.pnu.cse.annotations.Size;
import edu.pnu.cse.annotations.Valid;
import edu.pnu.cse.entities.GraduateStudent;
import edu.pnu.cse.entities.Student;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Pattern;

public class MyValidator {
    static int getAnnotation = 0;
    static int getSuperclass = 0;

    static int toString = 0;

    public static Set<String> validate(Object arg) throws IllegalAccessException {
        Set<String> violation = new HashSet<>();
        Class<?> type = null;
        Field[] graduateFields = GraduateStudent.class.getDeclaredFields();
        Field[] fields = Student.class.getDeclaredFields();

        if (arg instanceof GraduateStudent) {
            type = GraduateStudent.class;
            System.arraycopy(graduateFields, 0, fields, 0, graduateFields.length);
        }
        else {
            type = Student.class;
        }

        String name = type.getSimpleName();


        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            field.setAccessible(true);
            String value = (String)field.get(arg);

            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Email.class) {
                    Email emailAnnotation = (Email) annotation;
                    if (!validateEmail(value))
                        violation.add(emailAnnotation.message());
                }
                if (annotation.annotationType() == Size.class) {
                    Size sizeAnnotation = (Size) annotation;
                    if (!validateNull(value) && !validateSize(value, sizeAnnotation.min(), sizeAnnotation.max())) violation.add(sizeAnnotation.message());
                }
                if (annotation.annotationType() == NotNull.class) {
                    NotNull notNullAnnotation = (NotNull) annotation;
                    if (validateNull(value)) violation.add(notNullAnnotation.message());
                }
            }
        }

        return violation;
    }

    private static  boolean validateNull(String value) {
        return Objects.isNull(value);
    }

    private static boolean validateEmail(String email) {
        return Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", email);
    }

    private static boolean validateSize(String value, int min, int max) {
        if (Objects.isNull(value)) return false;
        return (value.length() >= min && value.length() <= max);
    }
}


