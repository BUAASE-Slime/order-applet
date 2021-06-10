package com.example.demo.utils;

import com.example.demo.bean.Food;
import com.example.demo.bean.Type;
import com.example.demo.global.GlobalData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
 * excel数据导入操作
 * */

@Slf4j
public class ExcelImportUtils {


    /*
     * 菜品类目批量导入
     * 要求
     * 1，必须以.xlsx结尾的excel文件
     * 2，表格内容必须按照下面顺序
     * 0：类目名，1：type值
     *
     * */
    public static List<Type> excelToFoodLeimuList(InputStream inputStream) {
        List<Type> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            System.out.println("总行数有多少行" + rowLength);
            //工作表的列
            Row row = sheet.getRow(0);

            //总列数
            int colLength = row.getLastCellNum();
            System.out.println("总列数有多少列" + colLength);
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                Type goodInfo = new Type();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String data = cell.getStringCellValue();
                        data = data.trim();
                        //列：0：类目名，1：type值
                        if (j == 0) {
                            goodInfo.setAdminId(GlobalData.ADMIN_ID);//属于那个商家
                            goodInfo.setLeimuName(data);
                        } else if (j == 1) {
                            goodInfo.setLeimuType(Integer.parseInt(data));
                        }
                    }
                }
                list.add(goodInfo);
                //log.error("每行数据={}", menuInfo);
            }
        } catch (Exception e) {
            log.error("excel导入抛出的错误={}", e);
        }
        return list;
    }


    /*
     * 菜品(商品)批量导入
     * 要求
     * 1，必须以.xlsx结尾的excel文件
     * 2，表格内容必须按照下面顺序
     * 0商品名，1单价，2库存，3类目，4描述，5图片链接
     *
     * */
    public static List<Food> excelToFoodInfoList(InputStream inputStream) {
        List<Food> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                Food goodInfo = new Food();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String data = cell.getStringCellValue();
                        data = data.trim();
                        //列： 0商品名，1单价，2库存，3类目，4描述，5图片链接
                        if (j == 0) {
                            goodInfo.setAdminId(GlobalData.ADMIN_ID);//属于那个商家
                            goodInfo.setFoodName(data);
                        } else if (j == 1) {
                            goodInfo.setFoodPrice(new BigDecimal(data));
                        } else if (j == 2) {
                            goodInfo.setFoodStock(Integer.parseInt(data));
                        } else if (j == 3) {
                            goodInfo.setLeimuType(Integer.parseInt(data));
                        } else if (j == 4) {
                            goodInfo.setFoodDesc(data);
                        } else if (j == 5) {
                            goodInfo.setFoodIcon(data);
                        }
                    }
                }
                list.add(goodInfo);
            }
        } catch (Exception e) {
            log.error("excel导入抛出的错误={}", e);
        }
        return list;
    }

}
