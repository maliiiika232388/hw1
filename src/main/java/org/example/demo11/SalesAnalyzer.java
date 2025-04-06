package org.example.demo11;import org.apache.poi.ss.usermodel.*;import org.apache.poi.xssf.usermodel.XSSFWorkbook;import java.io.File;import java.io.FileInputStream;import java.io.IOException;import java.util.ArrayList;import java.util.List;public class SalesAnalyzer {    // Исправленный путь к файлу (должно быть правильное значение, используйте двойной слэш)    private static final String FILE_PATH = "C:\\Users\\user\\Downloads\\Telegram Desktop\\PRODUCTS(1).xlsx";    public List<MonthSales> getSalesData() {        List<MonthSales> sales = new ArrayList<>();        try {            FileInputStream fis = new FileInputStream(new File(FILE_PATH));            Workbook workbook = new XSSFWorkbook(fis);            Sheet sheet = workbook.getSheetAt(0);  // Читаем первый лист            String currentMonth = null;            List<Product> currentProducts = new ArrayList<>();            // Чтение строк Excel            for (Row row : sheet) {                if (row.getRowNum() == 0) continue; // Пропускаем заголовок                String month = row.getCell(0).getStringCellValue();  // Месяц                String productName = row.getCell(1).getStringCellValue();  // Название товара                double productPrice = row.getCell(2).getNumericCellValue();  // Цена товара                int productQuantity = (int) row.getCell(3).getNumericCellValue();  // Количество товара                double totalSale = row.getCell(4).getNumericCellValue();  // Итоговая продажа товара                // Если месяц изменился, добавляем текущие данные о месяцах                if (currentMonth != null && !currentMonth.equals(month)) {                    sales.add(new MonthSales(currentMonth, currentProducts));                    currentProducts = new ArrayList<>();  // Сбрасываем список продуктов                }                // Создаем объект Product для текущей строки и добавляем его в список                Product product = new Product(productName, productPrice, productQuantity);                currentProducts.add(product);                currentMonth = month; // Обновляем текущий месяц            }            // Добавляем последний месяц в список            if (currentMonth != null) {                sales.add(new MonthSales(currentMonth, currentProducts));            }            workbook.close();        } catch (IOException e) {            e.printStackTrace();        }        return sales;    }}