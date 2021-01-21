package day.crease.day.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.processing.FilerException;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取excel文件
 *
 * @author yuanzhongping
 */
@Component
public class ReadExcelUtil {
    //获取excel文件路径
    public Workbook getExcel(String filePath) {
        Workbook workbook = null;
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return workbook;
        } else {
            //获取后缀名
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            try {
                InputStream inputStream = new FileInputStream(filePath);
                //判断文件类型
                if (".xls".equals(fileType)) {
                    workbook = new HSSFWorkbook(inputStream);
                } else if (".xlsx".equals(fileType)) {
                    workbook = new XSSFWorkbook(inputStream);
                }
                return workbook;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return workbook;

    }


    /**
     * 返回工作簿对象
     *
     * @param file
     */
    public Workbook getWorkbook(MultipartFile file) {
        Workbook workbook = null;
        try {
            //获取文件后缀
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            System.out.println(suffix);
            //判断文件类型
            if (".xls".equals(suffix)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (".xlsx".equals(suffix)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                return workbook;
            }
        }
        return workbook;
    }


    //将导入的excel数据转为list结构返回
    public List<Map<String,Object>> excelToList(MultipartFile file) {
        Workbook workbook = getWorkbook(file);
        List<Map<String,Object>> resultList = new ArrayList<>();
        if (workbook != null) {
            //取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            //取出表头，索引从第0开始
            Row head = sheet.getRow(0);
            StringBuffer sb = new StringBuffer();
            //获取文件标题字段，对应数据库字段名
            int headNum = 0;
            for (int i = 0; i < head.getLastCellNum(); i++) {
                if (head.getCell(i).getStringCellValue() != null && !"".equals(head.getCell(i).getStringCellValue())) {
                    headNum++;
                    sb.append(head.getCell(i).getStringCellValue().trim() + "|");
                }
            }

            System.out.println("---------模板字段-------------"+sb);
            //模板的数据项在第二行
            for (int i = 2; i < sheet.getLastRowNum(); i++) {
                Map<String,Object> dataMap = new HashMap<>();
                Row row = sheet.getRow(i);
                //过滤空行
                Iterator<Cell> cellIterator = row.iterator();
                int num = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell == null || cell.equals("")) {
                        num++;
                    }
                    if(num >= row.getLastCellNum()){
                        continue;
                    }
                }
                int blankNum = 0;
                for(int j = 0; j<headNum;j++){
                    Cell cell = row.getCell(j);
                    if(changeCellType(cell).equals("")){
                        blankNum++;
                    }
                    //处理单元格数据格式问题
                    dataMap.put(head.getCell(j).getStringCellValue(),changeCellType(cell));
                }
                //跳过全部为空的行
                if(blankNum<headNum){
                    resultList.add(dataMap);
                }
            }
        }
        return resultList;
    }

    /**
     * 根据不通格式转换单元格类型
     * @param cell 单元格对象
     * @return
     */
    public String changeCellType(Cell cell){
        String cellStr = "";
        if(cell == null || cell.toString().trim().equals("")){
            cellStr = "";
            //处理Boolean
        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
            cellStr = String.valueOf(cell.getBooleanCellValue());
            //处理数值型
        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cellStr = String.valueOf(cell.getNumericCellValue()).replace(".0","");
            //空值型
        }else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
            cellStr = "";
        }else {
            cellStr = cell.getStringCellValue();
        }
        return cellStr;
    }

    /**
     * 读取文件内容
     * @param workbook
     */
    public void analyzeExcel(Workbook workbook) {
        //读取sheet 从0计数
        Sheet sheet = workbook.getSheetAt(0);
        //读取行数
        int rowNum = sheet.getLastRowNum();
        for (int i = 0; i < rowNum; i++) {
            //获取第i行
            Row row = sheet.getRow(i);
            //获取当前行的列数
            int colNum = row.getLastCellNum();
            for (int j = 0; j < colNum; j++) {
                //获取单元格
                Cell cell = row.getCell(j);
                if (cell == null) {
                    System.out.println("-------------null------------");
                } else {
                    //System.out.println(cell.toString());
                    split(cell.toString());
                }
            }
            System.out.println();
        }
    }

    /**
     * 根据传入字段名去重
     * @param list
     * @param mapKey
     * @return
     */
    public static List<Map<String, Object>> removeRepeatMapByKey(List<Map<String, Object>>
                                                                         list, String mapKey){
        System.out.println("--------------------------");
        //把list中的数据转换成msp,去掉同一id值多余数据，保留查找到第一个id值对应的数据
        String[] listColum=mapKey.split(",");
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Map> msp = new HashMap<>();

        for(int i = list.size()-1 ; i>=0; i--){
            Map map = list.get(i);
            String id="";
            for (String s : listColum) {
                //重复的key
                id+=map.get(s);
            }
            map.remove(mapKey);
            msp.put(id, map);
        }
        //把msp再转换成list,就会得到根据某一字段去掉重复的数据的List<Map>
        Set<String> mspKey = msp.keySet();
        for(String key: mspKey){
            Map newMap = msp.get(key);
            newMap.put(mapKey, key);
            listMap.add(newMap);
        }
        return listMap;
    }

    /**
     * 根据map中某个字段排序-升序
     * * @param list
     */
    public void fieldSort(List<Map<String,Object>> list){
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String sort1 = String.valueOf(o1.get("SYSTEM_UNIQUE_IDENTIFIER"));
                String sort2 = String.valueOf(o2.get("SYSTEM_UNIQUE_IDENTIFIER"));
                return sort1.compareTo(sort2);
            }
        });
    }


    public void split(String cellStr) {
        //获取日期
        String reg = "(2020)(((0[13578]|1[02])([0-2]\\d|3[01]))|((0[469]|11)([0-2]\\d|30))|(02([01]\\d|2[0-8])))";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(cellStr);
        String date = null;
        while (matcher.find()) {
            //System.out.println (matcher.group ());
            date = matcher.group();
        }
        String a = "固网APP";
        String b = "固网SITE";
        String c = "移网APP";
        String d = "移网SITE";
        //System.out.println(date+"\n"+a+"\n"+b+"\n"+c+"\n"+d+"\n");
        System.out.println(date);
        String reg2 = "(固网APP)(.*?)(用户数下降)([\\s\\S])*?;";
        Pattern pattern2 = Pattern.compile(reg2);
        Matcher matcher2 = pattern2.matcher(cellStr);
        while (matcher2.find()) {
            System.out.println(a + "\n" + matcher2.group());
        }
        String reg3 = "(固网SITE)(.*?)(用户数下降)([\\s\\S])*?;";
        Pattern pattern3 = Pattern.compile(reg3);
        Matcher matcher3 = pattern3.matcher(cellStr);
        while (matcher3.find()) {
            System.out.println(b + "\n" + matcher3.group());
        }
        String reg4 = "(移网APP)(.*?)(用户数下降)([\\s\\S])*?;";
        Pattern pattern4 = Pattern.compile(reg4);
        Matcher matcher4 = pattern4.matcher(cellStr);
        while (matcher4.find()) {
            System.out.println(c + "\n" + matcher4.group());
        }
        String reg5 = "(移网SITE)(.*?)(用户数下降)([\\s\\S])*?;";
        Pattern pattern5 = Pattern.compile(reg5);
        Matcher matcher5 = pattern5.matcher(cellStr);
        while (matcher5.find()) {
            System.out.println(d + "\n" + matcher5.group());
        }


    }

    /*public static void main(String[] args) {
        ReadExcel readExcel = new ReadExcel();
        Workbook workbook = readExcel.getExcel("E:\\dpi运营—各省份需求处理\\202003月份\\0215-0308.xlsx");
        if(workbook == null){
            System.out.println("获取文件失败");
        }else {
            readExcel.analyzeExcel(workbook);
        }
    }*/
}
