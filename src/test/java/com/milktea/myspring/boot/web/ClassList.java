package com.milktea.myspring.boot.web;

import com.milktea.myspring.annotations.Autowired;
import com.milktea.myspring.annotations.Repository;
import com.milktea.myspring.annotations.RestController;
import com.milktea.myspring.annotations.Service;

public class ClassList {
    @RestController
    static class A {
        @Autowired
        B b;
    }

    @Service
    static class B {
        @Autowired
        C c;
    }

    @Repository
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
