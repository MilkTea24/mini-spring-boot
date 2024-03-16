package edu.pnu.cse.validation;

import edu.pnu.cse.annotations.Valid;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

public class ValidationInvocationHandler implements InvocationHandler {

    private final Object target;



    public ValidationInvocationHandler(Object target) {

        this.target = target;

    }



    @Override

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        for (int i = 0; i < args.length; i++) {

            if (method.getParameters()[i].isAnnotationPresent(Valid.class)) {

                Set<String> violations = MyValidator.validate(args[i]);

                if (!violations.isEmpty()) {

                    System.out.println(String.format("%s - Validation errors: %s", args[i], violations));

                    return null;

                }

            }

        }

        return method.invoke(target, args);

    }

}
