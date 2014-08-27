package org.kesler.pkpvdimport.export;

import org.apache.poi.ss.usermodel.*;
import org.kesler.pkpvdimport.domain.Cause;
import org.kesler.pkpvdimport.domain.Package;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Формирование реестра передачи в архив
 */
public class ArchiveXLSXExporter extends XLSXExporter {

    private XLSXExportEnum exportEnum = XLSXExportEnum.FOR_ARCHIVE;


    @Override
    public XLSXExportEnum getEnum() {
        return exportEnum;
    }

    @Override
    protected void prepare() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Sheet sh = wb.createSheet();

        sh.setColumnWidth(0, 256*5);
        sh.setColumnWidth(1, 256*30);
        sh.setColumnWidth(2, 256*15);
        sh.setColumnWidth(3, 256*50);
        sh.setColumnWidth(4, 256*50);
        sh.setColumnWidth(5, 256*10);

        // Создаем заголовок
        Row titleRow = sh.createRow(0);

        // Стиль
        CreationHelper createHelper = wb.getCreationHelper();

        CellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setWrapText(true);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);


        CellStyle dateCellStyle = wb.createCellStyle();
        dateCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        dateCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        dateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        dateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d.m.yyyy"));


        Cell cell = titleRow.createCell(0);
        cell.setCellValue("№ п/п");
        cell.setCellStyle(cellStyle);

        cell = titleRow.createCell(1);
        cell.setCellValue("Код");
        cell.setCellStyle(cellStyle);

        cell = titleRow.createCell(2);
        cell.setCellValue("Дата");
        cell.setCellStyle(cellStyle);

        cell = titleRow.createCell(3);
        cell.setCellValue("Групповой тип");
        cell.setCellStyle(cellStyle);

        cell = titleRow.createCell(4);
        cell.setCellValue("Тип");
        cell.setCellStyle(cellStyle);

        cell = titleRow.createCell(5);
        cell.setCellValue("Пошлина");
        cell.setCellStyle(cellStyle);

        // формируем список основных дел

        List<Cause> causes = new ArrayList<Cause>();
        for (Package pack: packages)
            causes.addAll(pack.getCauses());


        // Заполняем таблицу

        for (int rownum = 0; rownum < causes.size(); rownum++) {
            Cause cause = causes.get(rownum);
            Row row = sh.createRow(rownum+1);
            for (int colnum = 0; colnum < 6; colnum++) {
                cell = row.createCell(colnum);

                switch (colnum) {
                    case 0:
                        cell.setCellValue(rownum+1);
                        break;
                    case 1:
                        cell.setCellValue(cause.getRegnum());
                        break;
                    case 2:
                        cell.setCellValue(cause.getBeginDate());
                        cell.setCellStyle(dateCellStyle);
                        break;
                    case 3:
                        cell.setCellValue(cause.getGroupType());
                        break;
                    case 4:
                        cell.setCellValue(cause.getType());
                        break;
                    case 5:
                        cell.setCellValue(cause.getTotalCharge());
                        break;
                    default:
                        break;
                }

                cellStyle = wb.createCellStyle();
                cellStyle.setWrapText(true);
                cellStyle.setBorderTop(CellStyle.BORDER_THIN);
                cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
                cellStyle.setBorderRight(CellStyle.BORDER_THIN);
                cellStyle.setBorderBottom(CellStyle.BORDER_THIN);

                cell.setCellStyle(cellStyle);
            }
        }


    }

 }
