package com.stylefeng.guns.rest.modular.example;

import com.alibaba.fastjson.JSON;
import com.md.member.service.IMemberService;
import com.stylefeng.guns.rest.common.SimpleObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 常规控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@Controller
@RequestMapping("/hello")
public class ExampleController {

    @Resource
    IMemberService memberService;

    @RequestMapping("")
    public ResponseEntity hello(@RequestBody SimpleObject simpleObject) {
        System.out.println(simpleObject.getUser());
        return ResponseEntity.ok("请求成功!");
    }

    @RequestMapping("list")
    public ResponseEntity list() {
        List<Map<String,Object>> members = memberService.find(null);

        return ResponseEntity.ok(JSON.toJSONString(members));
    }
}
