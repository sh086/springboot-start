//package com.shooter.springboot.common.utils;
//
//import org.apache.poi.ss.usermodel.*;
//import org.springframework.stereotype.Component;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.IntStream;
//
//@Component
//public class ExcelUtils {
//
//    /**
//     * 从文件服务器下载Excel并读取到List
//     */
//    public <T> List<T> readExcelFromUrl (String url, Class<T> clazz) {
//        if (url == null || "".equals(url)) {
//            return null;
//        }
//        try {
//            byte[] data = ReqClientUtil.callGetForByte(FileUtil.getSign(url));
//            if (data == null || data.length == 0) {
//                throw new BusinessException("从文件服务器下载Excel失败");
//            }
//            // 生成Workbook
//            InputStream inputStream = new ByteArrayInputStream(data);
//            Workbook wb = WorkbookFactory.create(inputStream);
//            // 将Workbook的第一个Sheet解析到Class模板中
//            return readWorkbookToList(wb, 0, clazz);
//        } catch (Exception e) {
//            log.error("解析Excel文件异常", e);
//            throw new BusinessException("Excel文件解析失败：" + StringHelper.subString(e.getMessage(),500));
//        }
//    }
//
//    private <T> List<T> readWorkbookToList(Workbook wb, int sheetIndex, Class<T> clazz) {
//        if (sheetIndex < 0 || sheetIndex >= wb.getNumberOfSheets()) {
//            throw new BusinessException("工作表索引超出范围");
//        }
//
//        // 读取第sheetIndex个Sheet
//        Sheet sheet = wb.getSheetAt(sheetIndex);
//        // 获取Excel标题
//        Row headerRow = sheet.getRow(0);
//        // 若标题为空则返回空
//        if (headerRow == null) {
//            return Collections.emptyList();
//        }
//
//        // 获取Excel标题行的 名称 和 索引 对应关系
//        Map<String, Integer> columnMap = createExcelHeadWithClazzMap(headerRow,clazz);
//        DataFormatter formatter = new DataFormatter();
//        List<T> dataList = new ArrayList<>();
//
//        AtomicInteger count = new AtomicInteger(2);
//        // 使用IntStream处理行索引范围 只处理1到最后一行
//        IntStream.rangeClosed(1, sheet.getLastRowNum())
//                .mapToObj(sheet::getRow)
//                .filter(Objects::nonNull)
//                .forEach(dataRow -> {
//                    JSONObject record = new JSONObject();
//                    record.put("no", count.getAndIncrement());
//                    columnMap.forEach((colName, colIndex) -> {
//                        Cell cell = dataRow.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
//                        record.put(colName, cell != null ? formatter.formatCellValue(cell) : "");
//                    });
//                    dataList.add(record.toJavaObject(clazz));
//                });
//        return dataList;
//    }
//
//    /**
//     * 将Excel标题行的与Class模板关联
//     * */
//    private Map<String, Integer> createExcelHeadWithClazzMap(Row headerRow,Class<?> clazz) {
//
//        //处理Class模板中名称和类名的关系
//        HashMap<String,String> map = Maps.newHashMap();
//        for (Field field : clazz.getDeclaredFields()) {
//            String fieldName = field.getName();
//            // 获取特定注解的属性
//            ExcelField myAnnotation = field.getAnnotation(ExcelField.class);
//            if (myAnnotation != null) {
//                map.put(myAnnotation.value(),fieldName);
//            }
//        }
//
//        // 从Excel的首行读取名称与索引的关系，并根据名称绑定类名
//        DataFormatter formatter = new DataFormatter();
//        Map<String, Integer> columnMap = new HashMap<>();
//        Set<String> duplicateCheck = new HashSet<>();
//
//        for (Cell headerCell : headerRow) {
//            String columnName = formatter.formatCellValue(headerCell).trim();
//            if (columnName.isEmpty()) {
//                throw new BusinessException("Excel标题行存在空列，位置：" + headerCell.getAddress());
//            }
//            if (!duplicateCheck.add(columnName)) {
//                throw new BusinessException("Excel标题行存在重复列名：" + columnName);
//            }
//            columnMap.put(map.get(columnName), headerCell.getColumnIndex());
//        }
//
//        if (columnMap.isEmpty()) {
//            throw new BusinessException("Excel文件缺少有效的标题行");
//        }
//
//        return columnMap;
//    }
//}