package com.studydemo.demo;


import com.studydemo.demo.threadtest.CountDownLatchTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Resource
    private CountDownLatchTest countDownLatchTest;


    @Test
    void latchTest() throws Exception {
        countDownLatchTest.testCountDownLatchAndThreadPool();
    }

    @Test
    void transmittableThreadLocalTest() {

    }

}
