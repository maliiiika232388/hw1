module com.example.demo1 {    
  requires javafx.controls;    
  requires javafx.fxml;    
  requires javafx.web;    
  requires org.controlsfx.controls;    
  requires com.dlsc.formsfx;    
  requires net.synedra.validatorfx;    
  requires org.kordamp.ikonli.javafx;   
  requires org.kordamp.bootstrapfx.core;    
  requires eu.hansolo.tilesfx;    
  requires com.almasb.fxgl.all;    
  requires org.apache.poi.ooxml;    
  requires commons.math3;    
  opens org.example.demo11 to javafx.fxml;   
  exports org.example.demo11;
}
