package day.crease.day.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author yuanzhongping
 * @date 2020-6-11
 * @apiNote 导入excel业务接口
 */
public interface ImportExcelService {

    List<Map<String,Object>> importExcel(MultipartFile file);
}
