package com.base.controller;

import com.base.request.ExportRequest;
import com.base.service.export.excel.IExcelService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExportController {
    IExcelService excelService;
    MediaType mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    @PostMapping("/excel")
    public ResponseEntity<byte[]> getExcelService(@NonNull @RequestBody ExportRequest request) {
        var content = request.isMultipleSheet() ? excelService.exportExcelMultiSheet(request) : excelService.exportExcelSingleSheet(request);
        var fileName = excelService.getFileName(request);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(fileName, StandardCharsets.UTF_8)
                                .build()
                                .toString()
                )
                .body(content);
    }
}
