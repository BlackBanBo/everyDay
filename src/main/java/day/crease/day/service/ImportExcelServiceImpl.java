package day.crease.day.service;

import day.crease.day.service.ImportExcelService;
import day.crease.day.util.ReadExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author yuanzhongping
 * @date 2020-6-11
 * @apiNote 导入excel业务接口实现类
 */
@Service
public class ImportExcelServiceImpl implements ImportExcelService {

    @Autowired
    ReadExcelUtil readExcelUtil;


    @Override
    public List<Map<String,Object>> importExcel(MultipartFile file) {
        List<Map<String,Object>> list = ReadExcelUtil.removeRepeatMapByKey(readExcelUtil.excelToList(file),"SYSTEM_UNIQUE_IDENTIFIER");
        Map<String,String> map = new HashMap<>();
        map.put("","");
        map.forEach((k,v)->{

        });
        return list;
    }
}
