package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.common.persistence.model.Dept;

/**
 * 部门服务
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
public interface IDeptService {

    /**
     * 删除部门
     *
     * @author stylefeng
     * @Date 2017/7/11 22:30
     */
   void deleteDept(Integer deptId);

   /**
    * 设置dept的pids字段
    * @param dept
    */
   void deptSetPids(Dept dept);
}
