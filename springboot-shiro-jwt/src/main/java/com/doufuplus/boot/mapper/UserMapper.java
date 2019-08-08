package com.doufuplus.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doufuplus.boot.entity.User;
import org.apache.ibatis.annotations.Param;


/**
 * UserMapper
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/7/27
 */
public interface UserMapper extends BaseMapper<User> {

    User findByAccount(@Param("account") String account);
}
