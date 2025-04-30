package com.tfm.backend.utils;

import com.tfm.backend.services.utils.PdfCoreBuilder;
import com.tfm.backend.services.utils.PdfTableBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PdfCoreBuilderIT {

    private PdfCoreBuilder pdfCoreBuilder;

    @BeforeEach
    public void setup() {
        pdfCoreBuilder = new PdfCoreBuilder("/path/to/pdf", "filename");
    }

    @Test
    public void testParagraphEmphasized() {
        String text = "Sample Text";
        PdfCoreBuilder result = pdfCoreBuilder.paragraphEmphasized(text);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testParagraph() {
        String text = "Sample Text";
        PdfCoreBuilder result = pdfCoreBuilder.paragraph(text);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testLine() {
        PdfCoreBuilder result = pdfCoreBuilder.line();
        Assertions.assertNotNull(result);
    }

    @Test
    public void testBarcode() {
        String code = "123456789";
        PdfCoreBuilder result = pdfCoreBuilder.barcode(code);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testQrCode() {
        String code = "123456789";
        PdfCoreBuilder result = pdfCoreBuilder.qrCode(code);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testHead() {
        PdfCoreBuilder result = pdfCoreBuilder.head();
        Assertions.assertNotNull(result);
    }

    @Test
    public void testFoot() {
        PdfCoreBuilder result = pdfCoreBuilder.foot();
        Assertions.assertNotNull(result);
    }

    @Test
    public void testTable() {
        float[] widths = { 100f, 200f, 150f };
        PdfTableBuilder tableBuilder = pdfCoreBuilder.table(widths);
        Assertions.assertNotNull(tableBuilder);
    }

}
