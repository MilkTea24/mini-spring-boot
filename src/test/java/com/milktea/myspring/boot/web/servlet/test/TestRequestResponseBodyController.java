package com.milktea.myspring.boot.web.servlet.test;

import com.milktea.myspring.annotations.GetMapping;
import com.milktea.myspring.annotations.RequestBody;
import com.milktea.myspring.annotations.RestController;
import edu.pnu.myspring.annotations.PostMapping;

@RestController
public class TestRequestResponseBodyController {
    @PostMapping("/register1")
    public void defaultHandler(@RequestBody UserRequestDto request) {
        System.out.println("defaultHandler invoked");
    }

    @GetMapping("/register2")
    public UserResponseDto responseHandler() {
        return new UserResponseDto(1L, "user1");
    }
}
