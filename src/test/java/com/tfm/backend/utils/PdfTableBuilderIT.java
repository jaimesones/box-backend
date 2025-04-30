package com.tfm.backend.utils;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.tfm.backend.services.utils.PdfCoreBuilder;
import com.tfm.backend.services.utils.PdfTableBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class PdfTableBuilderIT {

    private PdfTableBuilder pdfTableBuilder;
    private PdfCoreBuilder pdfCoreBuilder;
    private Document document;
    private Table table;

    @BeforeEach
    public void setup() {
        pdfCoreBuilder = Mockito.mock(PdfCoreBuilder.class);
        document = Mockito.mock(Document.class);
        table = Mockito.mock(Table.class);
        pdfTableBuilder = new PdfTableBuilder(pdfCoreBuilder, document, 90, 15, 25, 35);
        pdfTableBuilder.buildTable();
    }

    @Test
    public void testTableColumnsHeader() {
        pdfTableBuilder.tableColumnsHeader("Header1", "Header2", "Header3", "Header4");
        Mockito.verify(table).addHeaderCell(" ");
        Mockito.verify(table, Mockito.times(4)).addHeaderCell(Mockito.anyString());
    }

    @Test
    public void testTableCells() {
        pdfTableBuilder.tableCells("Cell1", "Cell2", "Cell3", "Cell4");
        Mockito.verify(table).addCell("1");
        Mockito.verify(table, Mockito.times(4)).addCell(Mockito.anyString());
    }

    @Test
    public void testTableColspanRight() {
        pdfTableBuilder.tableColspanRight("Value");
        Mockito.verify(table).addCell(Mockito.any(Cell.class));
    }

    @Test
    public void testBuildTable() {
        PdfCoreBuilder result = pdfTableBuilder.buildTable();
        Mockito.verify(document).add(Mockito.eq(table)); // Utiliza Mockito.eq() para comparar el mock 'table'
        Assertions.assertEquals(pdfCoreBuilder, result);
    }
}

