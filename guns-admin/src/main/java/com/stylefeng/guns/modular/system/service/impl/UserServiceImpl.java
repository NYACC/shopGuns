package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    UserMapper userMapper;

    @Override
    public List<Map<String, Object>> find(User user) {
        Wrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("name",user.getName());
        return userMapper.selectMaps(wrapper);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.selectById(id);
    }
}
