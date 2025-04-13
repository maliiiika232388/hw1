package org.example.demo11;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;

public class HelloApplication extends Application {

    private final List<Record> records = new ArrayList<>();
    private final ComboBox<Integer> yearBox = new ComboBox<>();
    private final BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Excel", "*.xlsx", "*.xls")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                loadData(file);

                Set<Integer> years = new TreeSet<>();
                for (Record r : records) {
                    years.add(r.date().getYear());
                }

                yearBox.setItems(FXCollections.observableArrayList(years));
                yearBox.setOnAction(e -> showChart(yearBox.getValue()));

                if (!years.isEmpty()) {
                    yearBox.setValue(years.iterator().next());
                    showChart(yearBox.getValue());
                }

                root.setTop(yearBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Simple Sales Chart");
        stage.show();
    }

    private void loadData(File file) throws IOException {
        records.clear();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook book = new XSSFWorkbook(fis)) {

            Sheet sheet = book.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                double price = row.getCell(2).getNumericCellValue();
                int quantity = (int) row.getCell(3).getNumericCellValue();
                double total = row.getCell(4).getNumericCellValue();
                LocalDate date = row.getCell(5).getDateCellValue()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                records.add(new Record(id, name, price, quantity, total, date));
            }
        }
    }

    private void showChart(int year) {
        double[] monthly = new double[12];

        for (Record r : records) {
            if (r.date().getYear() == year) {
                int i = r.date().getMonthValue() - 1;
                monthly[i] += r.total();
            }
        }

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);

        chart.setTitle("Monthly Sales for " + year);
        xAxis.setLabel("Month");
        yAxis.setLabel("Sales");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (int i = 0; i < 12; i++) {
            String month = LocalDate.of(2000, i + 1, 1)
                    .getMonth()
                    .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            series.getData().add(new XYChart.Data<>(month, monthly[i]));
        }

        chart.getData().add(series);
        root.setCenter(chart);
    }

    public record Record(int id, String name, double price, int quantity, double total, LocalDate date) {}

    public static void main(String[] args) {
        launch();
    }
}
