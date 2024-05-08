package com.milktea.myspring.boot.web.servlet.test;

import com.milktea.myspring.annotations.RequestBody;
import edu.pnu.myspring.annotations.PostMapping;

public class TestRequestBodyController {
    @PostMapping("/register1")
    public void defaultHandler(@RequestBody UserRequestDto request) {
        System.out.println("defaultHandler invoked");
    }
}
