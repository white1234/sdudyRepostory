package com.studydemo.demo.controller;

import com.studydemo.demo.annotation.OperationLog;
import com.studydemo.demo.em.OperTypeEnum;
import com.studydemo.demo.model.bo.UserDetailBO;
import com.studydemo.demo.model.vo.DeleteUserVO;
import com.studydemo.demo.model.vo.UpdateUserVO;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/29 23:03
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("userController")
public class UserController {

    @Autowired
    private ISysUserService userService;

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/updateUserMessage")
    public BaseResponse<Integer> updateUserMessage(@RequestBody UpdateUserVO updateUserVO) {
        return RespGenerator.success("成功");
    }

    //@PreAuthorize("CD_005")
    @ApiOperation(value = "获取用户列表信息")
    @GetMapping("/getUserList")
    @OperationLog(content = "用户列表接口",action = "获取用户列表信息",opType = OperTypeEnum.QUERY)
    public BaseResponse<List<UserDetailBO>> getUserList() {
        return RespGenerator.success(userService.list());
    }

    @ApiOperation(value = "删除用户信息")
    @PostMapping("/deleteUser")
    public BaseResponse<Integer> deleteUser(@RequestBody DeleteUserVO deleteUserVO) {
        return RespGenerator.success("成功");
    }


    //自定义异常全局处理 测试
   /* @GetMapping("/test1")
    public BaseResponse<String> test1(@RequestParam(value = "username",required = false) String username,
                                      @RequestParam(value = "password",required = false) String password
    ){
        if (username==null){
            throw new BaseException(BaseErrorEnum.USER_NOT_EXISTS);
        }else {
            return RespGenerator.success("调用成功,"+username+","+password);
        }
    }*/
}