package tests;


import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;


public class ZipFilesTests {
    private ClassLoader classLoader = ZipFilesTests.class.getClassLoader();

    @Test
    @DisplayName("Unpacking and test File(xlsx) from Zip archive")
    void unpackingXlsFromZipAndTestFile() throws Exception {
        try (ZipInputStream zipInputStream = new ZipInputStream(
                classLoader.getResourceAsStream("Test_Files.zip"))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().contains(".xlsx")) {
                    XLS exel = new XLS(zipInputStream);
                    Assertions.assertEquals(exel.excel.getSheetAt(0).getRow(2).getCell(2)
                            .getStringCellValue(), "QAGURU");
                    Assertions.assertEquals(exel.excel.getSheetAt(0).getRow(1).getCell(1)
                            .getStringCellValue(), "Document");
                    Assertions.assertEquals(exel.excel.getSheetAt(0).getRow(0).getCell(0)
                            .getStringCellValue(), "Test");
                }
            }
        }
    }

    @Test
    @DisplayName("Unpacking and test File(PDF) from Zip Archive")
    void unpackingPdfFromZipAndTestFile() throws Exception {
        try (ZipInputStream zipInputStream = new ZipInputStream(
                classLoader.getResourceAsStream("Test_Files.zip"))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().contains(".pdf")) {
                    PDF pdf = new PDF(zipInputStream);
                    assertThat(pdf.text).contains("TEST DOCUMENT QAGURU");
                    break;
                }
            }
        }
    }

    @Test
    @DisplayName("Unpacking and test File(CSV) from Zip Archive")
    void unpackingCsvFromZipAndTestFile() throws Exception {
        try (ZipInputStream zipInputStream = new ZipInputStream(
                classLoader.getResourceAsStream("Test_Files.zip"))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().contains(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zipInputStream));
                    List<String[]> fileFiling = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[]{"Yatoro;TeamSpirit"}, fileFiling.get(0));
                    Assertions.assertArrayEquals(new String[]{"Dendi;B8"}, fileFiling.get(1));
                    Assertions.assertArrayEquals(new String[]{"Durachiyo;GG"}, fileFiling.get(2));
                    break;

                }
            }
        }
    }
}
