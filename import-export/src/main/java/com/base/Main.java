package com.base;

import com.base.request.ExportRequest;
import com.base.service.export.excel.ExcelService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ExcelService excelService = new ExcelService();
        excelService.exportExcelSingleSheet(new ExportRequest(
                "Hoan", false,
                List.of(new ExportRequest.SheetData(
                                "SheetName",
                                List.of(
                                        new ExportRequest.MultiContents(
                                                "meow",
                                                List.of(
                                                        "Human", "Iam not human",
                                                        "Babe", "Fuck you shit",
                                                        "Babe", "Fuck you shit",
                                                        "Babe", "Fuck you shit",
                                                        "Babe", "Fuck you shit"
                                                ),
                                                List.of(
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu")
                                                ),
                                                20,
                                                20
                                        ),
                                        new ExportRequest.MultiContents(
                                                "subtitlesubtitlesubtitlesubtitlesubtitlesubtitlesubtitlesubtitle",
                                                List.of(
                                                        "Human", "Iam not human",
                                                        "Babe", "Fuck you shit",
                                                        "Babe", "Fuck you shit",
                                                        "Babe", "Fuck you shit",
                                                        "Babe", "Fuck you shit"
                                                ),
                                                List.of(
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu"),
                                                        List.of("bebe", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu", "mamam", "cukcu")
                                                ),
                                                20,
                                                20
                                        )
                                )
                        )
                )
        ));

//        excelService.exportExcelMultiSheet(new ExportRequest(
//                "Hoan", true,
//                List.of(
//                        // ===== SHEET 1: Danh sach nhan vien =====
//                        new ExportRequest.SheetData(
//                                "Danh sach nhan vien",
//                                "Bao cao nhan su thang 10/2026",
//                                List.of(
//                                        "Ho va ten",    // String -> can trai
//                                        "Chuc vu",      // String -> can trai
//                                        "Ngay sinh",    // LocalDate -> can giua
//                                        "Ngay vao lam", // LocalDate -> can giua
//                                        "So ngay cong", // Integer -> can phai
//                                        "Luong co ban", // Long -> can phai
//                                        "Phu cap",      // Double -> can phai
//                                        "Tong thu nhap" // Long -> can phai
//                                ),
//                                List.of(
//                                        List.of("Nguyen Van An",    "Lap trinh vien",       LocalDate.of(1995, 3,  15), LocalDate.of(2020, 6,  1),  26, 15_000_000L, 500_000.0,   15_500_000L),
//                                        List.of("Tran Thi Bich",    "Kiem thu phan mem",    LocalDate.of(1998, 7,  22), LocalDate.of(2022, 1,  10), 24, 12_000_000L, 300_000.0,   12_300_000L),
//                                        List.of("Le Minh Duc",      "Quan ly du an",        LocalDate.of(1990, 11, 5),  LocalDate.of(2018, 3,  20), 26, 25_000_000L, 1_000_000.0, 26_000_000L),
//                                        List.of("Pham Thi Lan",     "Ke toan",              LocalDate.of(1993, 5,  30), LocalDate.of(2019, 9,  15), 22, 18_000_000L, 700_000.0,   18_700_000L),
//                                        List.of("Hoang Van Khanh",  "Thiet ke UI/UX",       LocalDate.of(1997, 2,  14), LocalDate.of(2021, 4,  5),  25, 14_000_000L, 450_000.0,   14_450_000L),
//                                        List.of("Do Thi Mai",       "Nhan su",              LocalDate.of(1994, 8,  10), LocalDate.of(2017, 12, 1),  26, 16_000_000L, 600_000.0,   16_600_000L),
//                                        List.of("Vu Quoc Hung",     "DevOps",               LocalDate.of(1992, 4,  25), LocalDate.of(2016, 7,  15), 26, 22_000_000L, 800_000.0,   22_800_000L),
//                                        List.of("Nguyen Thi Thu",   "Marketing",            LocalDate.of(1999, 1,  3),  LocalDate.of(2023, 2,  20), 20, 10_000_000L, 200_000.0,   10_200_000L),
//                                        List.of("Bui Dinh Long",    "Bao mat",              LocalDate.of(1991, 9,  17), LocalDate.of(2015, 5,  10), 26, 28_000_000L, 1_200_000.0, 29_200_000L),
//                                        List.of("Ly Thi Tuyet",     "Chuyen vien du lieu",  LocalDate.of(1996, 6,  8),  LocalDate.of(2020, 10, 3),  25, 17_000_000L, 650_000.0,   17_650_000L),
//                                        List.of("Tran Van Binh",    "Ky su backend",        LocalDate.of(1993, 12, 20), LocalDate.of(2018, 8,  12), 26, 20_000_000L, 750_000.0,   20_750_000L),
//                                        List.of("Nguyen Hoang Nam", "Ky su frontend",       LocalDate.of(2000, 3,  5),  LocalDate.of(2023, 6,  1),  18, 11_000_000L, 250_000.0,   11_250_000L),
//                                        List.of("Phan Thi Huong",   "Phan tich nghiep vu",  LocalDate.of(1995, 7,  30), LocalDate.of(2019, 1,  15), 24, 19_000_000L, 700_000.0,   19_700_000L),
//                                        List.of("Dao Minh Tri",     "Giam doc cong nghe",   LocalDate.of(1985, 10, 12), LocalDate.of(2010, 3,  1),  26, 50_000_000L, 3_000_000.0, 53_000_000L),
//                                        List.of("Vo Thi Kim Anh",   "Truong nhom kiem thu", LocalDate.of(1990, 2,  28), LocalDate.of(2014, 9,  20), 26, 30_000_000L, 1_500_000.0, 31_500_000L)
//                                ),
//                                20,
//                                20
//                        ),
//
//                        // ===== SHEET 2: Danh sach du an =====
//                        new ExportRequest.SheetData(
//                                "Danh sach du an",
//                                "Bao cao tien do du an quy 4/2026",
//                                List.of(
//                                        "Ten du an",         // String -> can trai
//                                        "Khach hang",        // String -> can trai
//                                        "Ngay bat dau",      // LocalDate -> can giua
//                                        "Ngay ket thuc",     // LocalDate -> can giua
//                                        "So thanh vien",     // Integer -> can phai
//                                        "Ngan sach (VND)",   // Long -> can phai
//                                        "Tien do (%)",       // Double -> can phai
//                                        "Trang thai"         // String -> can trai
//                                ),
//                                List.of(
//                                        List.of("He thong quan ly nhan su",    "Cong ty ABC",       LocalDate.of(2026, 1,  10), LocalDate.of(2026, 6,  30), 8,  500_000_000L, 95.5,  "Hoan thanh"),
//                                        List.of("Ung dung ban hang mobile",    "Cong ty XYZ",       LocalDate.of(2026, 3,  1),  LocalDate.of(2026, 9,  15), 12, 800_000_000L, 72.0,  "Dang thuc hien"),
//                                        List.of("Portal khach hang B2B",       "Tap doan DEF",      LocalDate.of(2026, 2,  15), LocalDate.of(2026, 12, 31), 15, 1_200_000_000L, 45.0, "Dang thuc hien"),
//                                        List.of("He thong bao cao tai chinh",  "Ngan hang GHI",     LocalDate.of(2025, 10, 1),  LocalDate.of(2026, 4,  30), 6,  350_000_000L, 100.0, "Hoan thanh"),
//                                        List.of("Nen tang thuong mai dien tu", "Startup JKL",       LocalDate.of(2026, 5,  1),  LocalDate.of(2027, 2,  28), 20, 2_000_000_000L, 30.0, "Dang thuc hien"),
//                                        List.of("Dashboard phan tich du lieu", "Cong ty MNO",       LocalDate.of(2026, 4,  1),  LocalDate.of(2026, 8,  31), 5,  200_000_000L, 88.0,  "Gan hoan thanh"),
//                                        List.of("He thong dat lich kham",      "Benh vien PQR",     LocalDate.of(2026, 6,  1),  LocalDate.of(2026, 11, 30), 9,  650_000_000L, 55.0,  "Dang thuc hien"),
//                                        List.of("App giao hang noi bo",        "Logistics STU",     LocalDate.of(2025, 12, 1),  LocalDate.of(2026, 5,  31), 7,  420_000_000L, 100.0, "Hoan thanh"),
//                                        List.of("He thong CRM ban le",         "Chuoi sieu thi VWX", LocalDate.of(2026, 7, 1),  LocalDate.of(2027, 1,  31), 11, 900_000_000L, 15.5,  "Moi bat dau"),
//                                        List.of("Cong cu quan ly kho",         "Nha may YZ",        LocalDate.of(2026, 3,  15), LocalDate.of(2026, 10, 15), 6,  300_000_000L, 78.0,  "Gan hoan thanh")
//                                ),
//                                25,
//                                20
//                        )
//                )
//        ));
    }
}
