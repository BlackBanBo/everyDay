package day.crease.day.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
@Api(tags = "测试地图")
public class IndexController {

    @ApiOperation("首页进入")
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        return "arcgis";
    }


}
