package com.base.service.export;

import java.nio.file.Path;

public abstract class ExportService {
    protected String pattern = "%s.%s";

    public String buildFileName(String fileName, String format) {
        var path = Path.of(String.format(pattern, fileName, format));
        return path.toAbsolutePath().toString();
    }
}
