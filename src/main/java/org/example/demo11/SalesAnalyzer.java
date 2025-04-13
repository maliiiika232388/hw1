package org.example.demo11;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class SalesAnalyzer {      
  private static final String FILE_PATH = "C:\\Users\\user\\Downloads\\Telegram Desktop\\PRODUCTS(1).xlsx";    
  public List<MonthSales> getSalesData() {        
    List<MonthSales> sales = new ArrayList<>();        
    try {            
      FileInputStream fis = new FileInputStream(new File(FILE_PATH));            
      Workbook workbook = new XSSFWorkbook(fis);            
      Sheet sheet = workbook.getSheetAt(0);             
      String currentMonth = null;            
      List<Product> currentProducts = new ArrayList<>();                  
      for (Row row : sheet) {               
        if (row.getRowNum() == 0) continue;               
        String month = row.getCell(0).getStringCellValue();             
        String productName = row.getCell(1).getStringCellValue();                
        double productPrice = row.getCell(2).getNumericCellValue();                 
        int productQuantity = (int) row.getCell(3).getNumericCellValue();              
        double totalSale = row.getCell(4).getNumericCellValue();                              
        if (currentMonth != null && !currentMonth.equals(month)) {                   
          sales.add(new MonthSales(currentMonth, currentProducts));                    
          currentProducts = new ArrayList<>(); 
        }               
        Product product = new Product(productName, productPrice, productQuantity);                
        currentProducts.add(product);                
        currentMonth = month;            
      }                    
      if (currentMonth != null) {                
        sales.add(new MonthSales(currentMonth, currentProducts));            
      }            
      workbook.close();        
    } catch (IOException e) {            
      e.printStackTrace();       
    }        
    return sales;    
  }
}
