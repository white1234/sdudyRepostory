package com.studydemo.demo.threadtest;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.studydemo.demo.model.entity.SysUserInfo;
import com.studydemo.demo.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author teronb
 * @Date 2023/3/7 23:11
 */
@Service
@Slf4j
public class CountDownLatchTest {

    CountDownLatch countDownLatch = new CountDownLatch(8);

    @Resource(name = "asyncThreadPoolExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void testCountDownLatchAndThreadPool() {

        for (int i = 0; i < 9; i++) {
            threadPoolTaskExecutor.execute(() -> {
                try {
                    System.out.println("Thread Name= " + Thread.currentThread().getName());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await(200, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有线程均执行完毕");
        threadPoolTaskExecutor.shutdown();
    }

}
