package com.pnu.demo;

import edu.pnu.myspring.annotations.MySpringApplication;
import edu.pnu.myspring.boot.MySpringApplicationRunner;

@MySpringApplication
public class MySpringDemoApplication {
    //현재 빈 가져오는게 안됨

    public static void main(String[] args) {
        MySpringApplicationRunner.run(MySpringDemoApplication.class, args);
    }
}

//POST
///students
//{
//"name": "John Doe",
//"course": "Mathematics"
//}
//END
//GET
///students/1/110
//EXIT

