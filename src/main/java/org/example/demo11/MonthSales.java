package org.example.demo11;
import java.util.List;
public class MonthSales {    
  private String month;    
  private List<Product> products;    
  public MonthSales(String month, List<Product> products) {        
    this.month = month;        
    this.products = products;    
  }    
  public String getMonth() {        
    return month;    
  }    
  public List<Product> getProducts() {        
    return products;    }    
  public String getName() {        
    return "";    
  }    
  public Object getSum() {       
    return null;    
  }
}
