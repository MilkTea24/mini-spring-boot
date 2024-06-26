package com.milktea.myspring.boot.web.ioc.classes;

import com.milktea.myspring.annotations.*;

public class ClassList {
    @RestController
    public static class A {
        @Autowired
        public B b;
    }

    @Service
    public static class B {
        @Autowired
        public C c;
    }

    @Repository
    public static class C {}

    public static class D {
        public E e;
        public F f;

        @Autowired
        public D(E e, F f) {
            this.e = e;
            this.f = f;
        }
    }
    public static class E {
        public G g;

        @Autowired
        public E (G g) {
            this.g = g;
        }
    }
    public static class F {
        public int number = 0;
        @PostConstruct
        public void init() {
            System.out.println("F init");
            number = 2;
        }
    }

    public static class G {
        public int number = 0;
        @PreDestroy
        public void destroy() {
            System.out.println("G destroyed");
            number = 2;
        }
    }

    public static class H {
        public I i;
        @Autowired
        public H(I i) {
            this.i = i;
        }
    }
    public static class I {
        public H h;
        @Autowired
        public I(H h) {
            this.h = h;
        }
    }
}
