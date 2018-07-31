package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.common.persistence.model.User;

import java.util.List;
import java.util.Map;

/**
 * 用户相关业务
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午9:11:57
 */
public interface IUserService {

  List<Map<String,Object>> find(User user);

  User getById(Integer id);
}
