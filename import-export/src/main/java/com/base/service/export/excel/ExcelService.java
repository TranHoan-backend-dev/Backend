package com.base.service.export.excel;

import com.base.utils.FileFormat;
import com.base.utils.filestyle.ExcelStyle;
import com.base.request.ExportRequest;
import com.base.service.export.ExportService;
import com.common.utilities.StringUtilities;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ExcelService extends ExportService implements IExcelService {
    private final FileFormat format = FileFormat.EXCEL;

    @Override
    public String getFileName(@NonNull ExportRequest request) {
        return buildFileName(request.fileName(), format.getFormat());
    }

    @Override
    public byte[] exportExcelSingleSheet(@NonNull ExportRequest request) {
        if (!request.isMultipleSheet()) {
            var sheetData = request.data();
            if (sheetData != null && sheetData.size() == 1) {
                try (
                        var workbook = new XSSFWorkbook();
                        var outputStream = new ByteArrayOutputStream()
                ) {
                    var data = sheetData.getFirst();
                    buildSheet(data, workbook);

                    workbook.write(outputStream);
                    return outputStream.toByteArray();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new byte[0];
    }

    @Override
    public byte[] exportExcelMultiSheet(@NonNull ExportRequest request) {
        if (request.isMultipleSheet()) {
            var sheetsList = request.data();
            if (sheetsList != null) {
                if (sheetsList.size() > 1) {
                    try (
                            var workbook = new XSSFWorkbook();
                            var outputStream = new ByteArrayOutputStream()
                    ) {
                        sheetsList.forEach(data -> buildSheet(data, workbook));
                        workbook.write(outputStream); // chi ghi 1 lan sau khi da co du cac sheet
                        return outputStream.toByteArray();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (sheetsList.size() == 1) {
                    exportExcelSingleSheet(request);
                }
            }
        }
        return new byte[0];
    }

    /**
     * Tạo sheet mới
     * created by txhoan 18/10/2026
     *
     * @param data     Dữ liệu sử dụng trong sheet
     * @param workbook File excel hiện tại
     */
    private void buildSheet(ExportRequest.@NonNull SheetData data, @NonNull Workbook workbook) {
        var contents = data.contents();
        var maxValue = 1;
        for (var content : contents) {
            var temp = Math.max(content.headerCells().size(), content.tableRows().getFirst().size());
            if (temp > maxValue) {
                maxValue = temp;
            }
        }
        var mergedCellsLength = maxValue;

        var safeTitle = StringUtilities.sanitizeSheetName(data.title());
        var sheet = workbook.createSheet(safeTitle);
        buildMergedCellsTitle(safeTitle, mergedCellsLength, sheet);
        contents.forEach(content -> {
            buildSubTitle(content.subTitle(), sheet);
            buildTableData(content.headerCells(), content.tableRows(), sheet, content.cellWidth(), content.cellHeight());
        });
    }

    /**
     * Tạo tiêu đề lớn được trộn cell
     * created by txhoan 17/10/2026
     *
     * @param title             Tiêu đề lớn
     * @param mergedCellsLength Số cell được trộn
     * @param sheet             Sheet làm việc hiện tại
     */
    private void buildMergedCellsTitle(String title, int mergedCellsLength, @NonNull Sheet sheet) {
        var workbook = sheet.getWorkbook();
        if (workbook != null && mergedCellsLength > 1) {
            var row = sheet.createRow(0);
            var cell = row.createCell(0);
            cell.setCellValue(title);

            sheet.addMergedRegion(
                    // thu tu tu row -> col
                    // first row - lastRow
                    // first col - last col
                    new CellRangeAddress(
                            0,
                            0,
                            0,
                            mergedCellsLength - 1
                    )
            );

            buildTextInCell(
                    cell, workbook, true, ExcelStyle.FONT_FAMILY, ExcelStyle.FONT_SIZE_TITLE,
                    IndexedColors.BLACK.getIndex(), false, HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER, true
            );

            row.setHeightInPoints(30);
            sheet.createRow(1); // cach tieu de voi phan noi dung ben duoi
        }
    }

    /**
     * Tạo tiêu đề phụ bên trên bảng dữ liệu
     * created by txhoan 17/10/2026
     *
     * @param subTitle Tiêu đề phụ
     * @param sheet    Sheet làm việc hiện tại
     */
    private void buildSubTitle(String subTitle, @NonNull Sheet sheet) {
        var workbook = sheet.getWorkbook();
        if (!StringUtilities.isNullOrEmpty(subTitle) && !StringUtilities.isNullOrWhiteSpace(subTitle) && workbook != null) {
            int lastRow = sheet.getPhysicalNumberOfRows(); // bat dau tao o hang cuoi cung + 1
            var row = sheet.createRow(lastRow);
            var cell = row.createCell(0);
            cell.setCellValue(subTitle);

            buildTextInCell(
                    cell, workbook, false, ExcelStyle.FONT_FAMILY, ExcelStyle.FONT_SIZE_SUB_TITLE,
                    IndexedColors.BLACK.getIndex(), false, HorizontalAlignment.LEFT,
                    VerticalAlignment.CENTER, false
            );
        }
    }

    /**
     * Tạo bảng chứa dữ liệu cần trình bày
     * created by txhoan 18/10/2026
     *
     * @param headerCells danh sách các giá trị trên header
     * @param tableData   danh sách dữ liệu bảng theo từng hàng
     * @param sheet       Sheet làm việc hiện tại
     * @param cellWidth   độ rộng cell
     * @param cellHeight  độ cao cell
     */
    private void buildTableData(List<Object> headerCells, List<List<Object>> tableData, @NonNull Sheet sheet, int cellWidth, int cellHeight) {
        var workbook = sheet.getWorkbook();
        if (headerCells != null && !headerCells.isEmpty()) {
            // build header first
            buildRowsInTable(
                    headerCells, sheet, workbook,
                    false, true,
                    cellWidth, cellHeight
            );

            // build table body
            if (tableData != null && !tableData.isEmpty()) {
                tableData.forEach((cell) -> {
                    buildRowsInTable(
                            cell, sheet, workbook, true,
                            false,
                            cellWidth, cellHeight
                    );
                });
            }

            int lastRowIndex = sheet.getPhysicalNumberOfRows();
            sheet.createRow(lastRowIndex); // tao hang trong o cuoi
        }
    }

    /**
     * Build từng hàng dữ liệu một trong thân bảng
     * created by txhoan 18/10/2026
     *
     * @param values     Các giá trị trong 1 hàng
     * @param sheet      Sheet làm việc hiện tại
     * @param workbook   file excel
     * @param isWrapText flag cho phép wrap text hay không
     * @param isHeader   flag đánh dấu là hàng table header
     * @param cellWidth  độ rộng cell
     * @param cellHeight chiều cao cell
     */
    private void buildRowsInTable(
            @NonNull List<Object> values, @NonNull Sheet sheet, Workbook workbook,
            boolean isWrapText, boolean isHeader,
            int cellWidth, int cellHeight
    ) {
        int lastRowIndex = sheet.getPhysicalNumberOfRows();
        var row = sheet.createRow(lastRowIndex);
        IntStream.range(0, values.size()).forEach((index) -> {
            var item = values.get(index);
            var cell = row.createCell(index);

            var style = workbook.createCellStyle();
            var font = workbook.createFont();
            style.setWrapText(isWrapText);

            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            if (isHeader) {
                // Header luon can giua
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            } else {
                // Table body: can chinh theo kieu dữ liệu
                // - Ngay thang nam (Date, LocalDate, LocalDateTime, LocalTime): can giua
                // - So, tien te (Number va cac subclass): can phai
                // - Cac kieu khac (String, ...): can trai
                if (item instanceof Date
                        || item instanceof LocalDate
                        || item instanceof LocalDateTime
                        || item instanceof LocalTime) {
                    style.setAlignment(HorizontalAlignment.CENTER);
                } else if (item instanceof Number) {
                    style.setAlignment(HorizontalAlignment.RIGHT);
                } else {
                    style.setAlignment(HorizontalAlignment.LEFT);
                }
            }

            font.setBold(isHeader);
            style.setFont(font);

            cell.setCellStyle(style);
            cell.setCellValue(item.toString());

            sheet.setColumnWidth(
                    index,
                    cellWidth > 0 ? cellWidth * 256 : 256
            );
            row.setHeightInPoints(Math.max(cellHeight, 15));
        });
    }

    /**
     * Tao text cho 1 cell cụ thể
     * created by txhoan
     *
     * @param cell                cell cần gán text hiện tại
     * @param workbook            file excel hiện tại
     * @param isBold              flag cho phép in đậm text
     * @param fontName            tên font chữ
     * @param fontSize            cỡ chữ
     * @param color               màu chữ
     * @param isItalic            flag cho phép in nghieng
     * @param horizontalAlignment cần theo chiều ngang
     * @param verticalAlignment   cần theo chiều dọc
     * @param isWrapText          flag cho phép wrap text hay không
     */
    private void buildTextInCell(
            @NonNull Cell cell, @NonNull Workbook workbook, boolean isBold, @NonNull ExcelStyle fontName,
            @NonNull ExcelStyle fontSize, short color, boolean isItalic, HorizontalAlignment horizontalAlignment,
            VerticalAlignment verticalAlignment, boolean isWrapText
    ) {
        var cellStyle = workbook.createCellStyle();
        var font = workbook.createFont();
        font.setBold(isBold);
        font.setFontName(fontName.getValue().toString());
        font.setFontHeightInPoints(((Number) fontSize.getValue()).shortValue());
        font.setColor(color);
        font.setItalic(isItalic);

        cellStyle.setFont(font);
        cellStyle.setAlignment(horizontalAlignment); // can theo chieu ngang
        cellStyle.setVerticalAlignment(verticalAlignment); // can theo chieu doc
        cellStyle.setWrapText(isWrapText);

        cell.setCellStyle(cellStyle);
    }
}
