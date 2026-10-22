package com.base.service.export.excel;

import com.base.request.ExportRequest;

public interface IExcelService {
    /**
     * Thuật toán xuất ra file excel đơn sheet
     * created by txhoan 17/10/2026
     */
    byte[] exportExcelSingleSheet(ExportRequest request);

    /**
     * Thuật toán xuất file excel đa sheet
     * created by txhoan 17/10/2026
     */
    byte[] exportExcelMultiSheet(ExportRequest request);

    String getFileName(ExportRequest request);
}
