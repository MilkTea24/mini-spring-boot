package com.milktea.myspring.boot.web;

import com.milktea.myspring.annotations.Autowired;

public class ClassList {
    static class A {
        @Autowired
        B b;
    }

    static class B {C c;}

    static class C {}

    static class D {
        E e;
        F f;

        @Autowired
        public D(E e, F f) {
            this.e = e;
            this.f = f;
        }
    }
    static class E {
        G g;

        @Autowired
        public E (G g) {
            this.g = g;
        }
    }
    static class F {}

    static class G {}

    static class H {
        I i;
        @Autowired
        public H(I i) {
            this.i = i;
        }
    }
    static class I {
        H h;
        @Autowired
        public I(H h) {
            this.h = h;
        }
    }
}
