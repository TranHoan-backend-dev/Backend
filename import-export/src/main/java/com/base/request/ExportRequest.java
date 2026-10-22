package com.base.request;

import java.util.List;

public record ExportRequest(
        String fileName,
        Boolean isMultipleSheet,
        List<SheetData> data
) {

    public record SheetData(
            String title,
            List<MultiContents> contents
    ) {

    }

    public record MultiContents(
            String subTitle,
            List<Object> headerCells,
            List<List<Object>> tableRows,
            int cellWidth,
            int cellHeight
    ) {
    }

}
