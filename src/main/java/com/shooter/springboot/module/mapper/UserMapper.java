package com.shooter.springboot.module.mapper;

import com.shooter.springboot.module.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suhe
 * @since 2022-07-17 04:21:05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
