package com.studydemo.demo.threadtest;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.studydemo.demo.model.UserContext;
import com.studydemo.demo.model.bo.UserTestBo;
import com.studydemo.demo.model.entity.SysUserInfo;
import com.studydemo.demo.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Description 模拟大批量数据插入数据库，使用多线程自增ID及插入操作
 * @Author teronb
 * @Date 2023/3/8 8:27
 */
@Service("transmittableThreadLocalTest")
@Slf4j
public class TransmittableThreadLocalTest {
    //用户编号创建器
    private static final AtomicInteger creator = new AtomicInteger(0);
    CountDownLatch countDownLatch = new CountDownLatch(10);

    //备选生日
    private static final LocalDate[] birthdays = {LocalDate.of(1988, 9, 11),
            LocalDate.of(1989, 11, 10),
            LocalDate.of(1990, 3, 7),
            LocalDate.of(1995, 7, 26),
            LocalDate.of(2000, 10, 1)
    };
    private static final int birthdayLength = birthdays.length;

    @Resource(name = "asyncThreadPoolExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    @Qualifier("sysUserService")
    private ISysUserService service;

    public void testTransmittableThreadLocal() {

        Executor ttlExecutor = TtlExecutors.getTtlExecutor(threadPoolTaskExecutor);

        for (int i = 0; i < 12; i++) {
            ttlExecutor.execute(() -> {
                UserContext.set(initUser(Thread.currentThread().getName()));
                service.testTransmittableThreadLocal();
            });
        }
        threadPoolTaskExecutor.shutdown();
    }

    public void testThreadPoolAdd() {
        final int numThreads = threadPoolTaskExecutor.getCorePoolSize();
        final int groupLength = 10000 / numThreads;

        List<UserTestBo> userTestBoList = new ArrayList<>(10000);

        for (int i = 0; i < 10; i++) {
            threadPoolTaskExecutor.execute(() -> {
                        try {
                            while (creator.intValue() < 10000) {
                                userTestBoList.add(initUser(Thread.currentThread().getName()));
                            }
                        } finally {
                            countDownLatch.countDown();
                            log.info("countDownLatch当前count值：" + countDownLatch.getCount());
                        }
                    }
            );
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<List<UserTestBo>> split = ListUtil.split(userTestBoList, 1000);
        log.info("=========>创建10000个用户完成");
        log.info("当前列表长度:" + userTestBoList.size());
        for (List<UserTestBo> list : split) {
            threadPoolTaskExecutor.execute(() -> {
                service.testThreadPoolAdd(list);
            });
        }
        threadPoolTaskExecutor.shutdown();
    }

    /**
     * 初始化用户信息(模拟请求)
     *
     * @param name -- 用户名
     * @return -- 用户信息
     */
    private static UserTestBo initUser(String name) {
        UserTestBo user = new UserTestBo();
        user.setId(creator.getAndIncrement());
        user.setUsername(name);
        user.setBirthday(birthdays[user.getId() % birthdayLength]);
        System.out.println("Thread Name= " + Thread.currentThread().getName() + ":创建对象,ID:" + creator.intValue());
        return user;
    }


}
