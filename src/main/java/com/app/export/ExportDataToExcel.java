package com.app.export;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.time.LocalDate;

import java.awt.*;
import java.io.*;
import java.sql.*;

import static com.app.manager.DatabaseManager.getConnection;

/**
     * A simple Java program that exports data from database to Excel file.
     * @author Nam Ha Minh
     * (C) Copyright codejava.net
     */
    public class ExportDataToExcel {


        public void export(Date dateFrom, Date dateTo) {
            String excelFilePath = "CostCar.xlsx";

            try (Connection conn = getConnection()) {
                LocalDate date = LocalDate.now();
                Date date1 = Date.valueOf(date);
                String sql = " SELECT \"Гражданска отговорност\" as name ,m.reg_plate as car, ca.name as cat, t.date_from, t.date_to " +
                        " FROM tpl t JOIN cost c ON c.id=t.id_cost JOIN motorvehicle m ON c.id_car=m.id JOIN catauto ca ON m.id_cat=ca.id" +
                        " WHERE t.date_to BETWEEN ? and ?" +
                        " UNION" +
                        " SELECT \"Автокаско\" as name ,m.reg_plate as car, ca.name as cat, t.date_from, t.date_to " +
                        " FROM otherins t JOIN cost c ON c.id=t.id_cost JOIN motorvehicle m ON c.id_car=m.id JOIN catauto ca ON m.id_cat=ca.id" +
                        " WHERE t.date_to BETWEEN ? and ?" +
                        " UNION" +
                        " SELECT \"Технически преглед\" as name ,m.reg_plate as car, ca.name as cat, t.date_from, t.date_to " +
                        " FROM techreview t JOIN cost c ON c.id=t.id_cost JOIN motorvehicle m ON c.id_car=m.id JOIN catauto ca ON m.id_cat=ca.id" +
                        " WHERE t.date_to BETWEEN ? and ?";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);


                preparedStatement.setDate(1, dateFrom);
                preparedStatement.setDate(2, dateTo);
                preparedStatement.setDate(3, dateFrom);
                preparedStatement.setDate(4, dateTo);
                preparedStatement.setDate(5, dateFrom);
                preparedStatement.setDate(6, dateTo);

                ResultSet result = preparedStatement.executeQuery();

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Разходи");

                writeHeaderLine(sheet);

                writeDataLines(result, workbook, sheet);

                FileOutputStream outputStream = new FileOutputStream(excelFilePath);
                workbook.write(outputStream);
                //workbook.close();

                preparedStatement.close();

                Desktop.getDesktop().open(new File(excelFilePath));
            } catch (SQLException e) {
                System.out.println("Datababse error:");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("File IO error:");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    private void writeHeaderLine(XSSFSheet sheet) {

        Row headerRow = sheet.createRow(0);

        Cell headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Номер на автомобил");

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Дата на плащане на застраховката");

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Дата на изтичане:");

        headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Вид разход");

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Категория автомобил");
    }

    private void writeDataLines(ResultSet result, XSSFWorkbook workbook,
                                XSSFSheet sheet) throws SQLException {
        int rowCount = 1;

        while (result.next()) {
            String name = result.getString("name");
            Timestamp date_from = result.getTimestamp("date_from");
            Timestamp date_to = result.getTimestamp("date_to");
            String car = result.getString("car");
            String cat = result.getString("cat");
                /*float rating = result.getFloat("id_type");
                Timestamp timestamp = result.getTimestamp("id_type");
                String comment = result.getString("id_type");*/

            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(name);

            cell = row.createCell(columnCount++);
            cell.setCellValue(car);

            cell = row.createCell(columnCount++);
            cell.setCellValue(cat);

            cell = row.createCell(columnCount++);
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
            cell.setCellStyle(cellStyle);

            cell.setCellValue(date_from);

            cell = row.createCell(columnCount++);

            CellStyle cell2Style = workbook.createCellStyle();
            CreationHelper creation2Helper = workbook.getCreationHelper();
            cell2Style.setDataFormat(creation2Helper.createDataFormat().getFormat("yyyy-MM-dd"));
            cell.setCellStyle(cell2Style);


            cell.setCellValue(date_to);



        }
    }



}
