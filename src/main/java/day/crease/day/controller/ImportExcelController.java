package day.crease.day.controller;

import day.crease.day.service.ImportExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api("导入excel到数据库")
@Controller
@RequestMapping("/importexcel")
public class ImportExcelController {

    @Resource
    ImportExcelService importExcelService;


    @ApiOperation("导入excel")
    @RequestMapping(value = "/importExcel",method = RequestMethod.POST)
    @ResponseBody
   public List<Map<String,Object>> importExcel(MultipartFile file){
       return importExcelService.importExcel(file);
   }


}
