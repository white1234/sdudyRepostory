package com.studydemo.demo.controller;

import com.github.pagehelper.PageInfo;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.model.entity.JobAndTriggerDto;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.service.QuartzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *@Author: CJ
 *@Date: 2021-11-2 11:41
 */
@Api(tags = "quartz接口")
@Slf4j
@RestController
@RequestMapping(path = "/quartz")
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    /**
     * 新增定时任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     * @param tName 触发器名称
     * @param tGroup 触发器组
     * @param cron cron表达式
     * @return BaseResponse
     */
    @ApiOperation(value = "新增定时任务")
    @PostMapping(path = "/addjob")
    @ResponseBody
    public BaseResponse addjob(String jName, String jGroup, String tName, String tGroup, String cron) {
        quartzService.addjob(jName, jGroup, tName, tGroup, cron);
        return RespGenerator.success("添加任务成功");
    }

    /**
     * 暂停任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     * @return BaseResponse
     */
    @ApiOperation("暂停任务")
    @PostMapping(path = "/pausejob")
    @ResponseBody
    public BaseResponse pausejob(String jName, String jGroup) {
        try {
            quartzService.pausejob(jName, jGroup);
            return RespGenerator.success("暂停任务成功");
        } catch (SchedulerException e) {
            throw new BaseException("500","暂停任务失败");
        }
    }

    /**
     * 恢复任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     * @return BaseResponse
     */
    @ApiOperation("恢复任务")
    @PostMapping(path = "/resumejob")
    @ResponseBody
    public BaseResponse resumejob(String jName, String jGroup) {
        try {
            quartzService.resumejob(jName, jGroup);
            return  RespGenerator.success("恢复任务成功");
        } catch (SchedulerException e) {
            throw new BaseException("500","恢复任务失败");
        }
    }

    /**
     * 重启任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     * @param cron cron表达式
     * @return BaseResponse
     */
    @ApiOperation("重启任务")
    @PostMapping(path = "/reschedulejob")
    @ResponseBody
    public BaseResponse rescheduleJob(String jName, String jGroup, String cron) {
        try {
            quartzService.rescheduleJob(jName, jGroup, cron);
            return RespGenerator.success("重启任务成功");
        } catch (SchedulerException e) {
            throw new BaseException("500","重启任务失败");
        }
    }

    /**
     * 删除任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     * @return ResultMap
     */
    @ApiOperation("删除任务")
    @PostMapping(path = "/deletejob")
    public BaseResponse deletejob(String jName, String jGroup) {
        try {
            quartzService.deletejob(jName, jGroup);
            return RespGenerator.success("删除任务成功");
        } catch (SchedulerException e) {
            throw new BaseException("500","删除任务失败");
        }
    }

    /**
     * 查询任务
     *
     * @param pageNum 页码
     * @param pageSize 每页显示多少条数据
     * @return Map
     */
    @ApiOperation("查询任务")
    @GetMapping(path = "/queryjob")
    public BaseResponse queryjob(Integer pageNum, Integer pageSize) {
        PageInfo<JobAndTriggerDto> pageInfo = quartzService.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(pageInfo.getTotal())) {
            map.put("JobAndTrigger", pageInfo);
            map.put("number", pageInfo.getTotal());
            return RespGenerator.success(map);
        }
        return RespGenerator.fail("查询任务成功失败，没有数据");
    }
}
