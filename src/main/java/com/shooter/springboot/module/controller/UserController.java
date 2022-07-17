package com.shooter.springboot.module.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shooter.springboot.common.model.RestListResult;
import com.shooter.springboot.common.model.RestObjectResult;
import com.shooter.springboot.common.model.RestResult;
import com.shooter.springboot.module.domain.User;
import com.shooter.springboot.module.service.UserService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author suhe
 * @since 2022-07-17 04:21:05
 */
@RestController
@RequestMapping(value = "/users")
@Api(tags = "用户的增删改查")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有的用户
     * api :localhost:8080/users
     */
    @GetMapping
    @Operation(summary = "获取所有用户列表")
    public RestListResult<User> selectAll(){
        val users = userService.list();
        // 返回结果
        return new RestListResult<User>().success(users);
    }

    /**
     * 通过id查找用户
     * api :localhost:8080/users/1
     */
    @GetMapping("/{id}")
    @ApiImplicitParam(paramType = "path",name= "id" ,value = "用户编号",dataType = "int")
    @Operation(summary = "通过id获取用户信息", description="返回用户信息")
    public RestObjectResult<User> selectById(@PathVariable Integer id){
        // 根据id查询用户
        User user =  userService.getById(id);
        // 返回结果
        return new RestObjectResult<User>().success(user);
    }

    /**
     * 通过用户名模糊查询
     * api :localhost:8080/users/selectByName?username=xxx
     */
    @GetMapping("/selectByName")
    @ApiImplicitParam(paramType = "query",name= "username" ,value = "用户名",dataType = "string")
    @Operation(summary = "通过用户名模糊搜索用户信息", description="返回用户信息")
    public RestListResult<User> selectByName(String username){
        val queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.like(User::getName,username);
        val users =  userService.list(queryWrapper);
        // 返回结果
        return new RestListResult<User>().success(users);
    }


    /**
     * 通过用户名、年龄精确查询
     * api :localhost:8080/users/selectByNameAndAge?username=xxx&&age=xxx
     */
    @GetMapping("/selectByNameAndAge")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",name= "username" ,value = "用户名"),
            @ApiImplicitParam(paramType = "query",name= "age" ,value = "年龄",dataType = "int"),
    })
    @Operation(summary = "通过用户名、年龄精确查询用户信息", description="返回用户信息")
    public RestObjectResult<User> selectByNameAndAge(@RequestParam String username, @RequestParam Integer age){
        val queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getName,username);
        queryWrapper.eq(User::getAge,age);
        val user =  userService.getOne(queryWrapper);
        // 返回结果
        return new RestObjectResult<User>().success(user);
    }

    /**
     * 添加用户
     * api :localhost:8080/users
     */
    @PostMapping
    @Operation(summary="新增用户",description = "新增的用户信息")
    public RestResult saveUser(@RequestBody User user){
        userService.save(user);
        // 返回结果
        return RestResult.success();
    }

    /**
     * 更新用户
     * api :localhost:8080/users
     */
    @PutMapping
    @Operation(summary = "更新用户", description="更新用户信息")
    public RestResult updateUser(@RequestBody User user){
        userService.updateById(user);
        // 返回结果
        return RestResult.success();
    }

    /**
     * 通过ID删除用户
     * api :localhost:8080/users/1
     */
    @DeleteMapping
    @Operation(summary = "删除用户", description="删除用户信息")
    public RestResult removeUser(@RequestBody User user){
        userService.removeById(user);
        // 返回结果
        return RestResult.success();
    }
}
