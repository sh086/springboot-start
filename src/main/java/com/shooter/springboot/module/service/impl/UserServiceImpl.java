package com.shooter.springboot.module.service.impl;

import com.shooter.springboot.module.domain.User;
import com.shooter.springboot.module.mapper.UserMapper;
import com.shooter.springboot.module.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suhe
 * @since 2022-07-17 04:21:05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
