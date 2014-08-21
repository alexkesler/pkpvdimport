package org.kesler.pkpvdimport.export;

/**
 * Created by alex on 13.06.14.
 */
public class XLSXExporterFactory {

    public static XLSXExporter createReestrExporter(XLSXExportEnum exportEnum) {
        switch (exportEnum) {
            case FOR_ARCHIVE:
                return new ArchiveXLSXExporter();
            default:
                return null;
        }
    }
}
