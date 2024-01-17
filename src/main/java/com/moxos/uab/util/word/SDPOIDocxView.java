package com.moxos.uab.util.word;


import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class SDPOIDocxView {

    private XWPFDocument document;

    public SDPOIDocxView(String templatePath) throws IOException, InvalidFormatException {
        document = openDocument(templatePath);
    }

    private XWPFDocument openDocument(String filePath) throws InvalidFormatException, IOException {
        FileInputStream fis = new FileInputStream(filePath);
        return new XWPFDocument(OPCPackage.open(fis));
    }

    public void writeAndClose(HttpServletResponse out) throws IOException {
        document.enforceReadonlyProtection();
        document.write(out.getOutputStream());
        document.close();
    }

    private void iterateParagraphs(XWPFDocument doc, Consumer<XWPFParagraph> consumer) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            consumer.accept(p);
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        consumer.accept(p);
                    }
                }
            }
        }
    }

    private static Boolean hasParagraphs(XWPFDocument doc, Function<XWPFParagraph, Boolean> function) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            if (function.apply(p)) {
                return true;
            }
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        if (function.apply(p)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    static Boolean hasText(XWPFDocument doc, String findText) {
        return hasParagraphs(doc, p -> p.getText().contains(findText));
    }

    public void replace(Map<String, String> fieldsForReport) {
        iterateParagraphs(document, p -> replaceParagraph(p, fieldsForReport));
    }

    private void replaceParagraph(XWPFParagraph paragraph, Map<String, String> fieldsForReport) throws POIXMLException {
        String find, text, runsText;
        List<XWPFRun> runs;
        XWPFRun run, nextRun;
        for (String key : fieldsForReport.keySet()) {
            text = paragraph.getText();
            if (!text.contains("${")) {
                return;
            }
            find = "${" + key + "}";
            if (!text.contains(find)) {
                continue;
            }
            runs = paragraph.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                run = runs.get(i);
                runsText = run.getText(0);
                if (runsText.contains("${")
                        || (runsText.contains("$") && runs.get(i + 1).getText(0).substring(0, 1).equals("{"))) {
                    while (!openTagCountIsEqualCloseTagCount(runsText)) {
                        nextRun = runs.get(i + 1);
                        runsText = runsText + nextRun.getText(0);
                        paragraph.removeRun(i + 1);
                    }
                    run.setText(runsText.contains(find)
                            ? runsText.replace(find, fieldsForReport.get(key))
                            : runsText, 0);
                }
            }
        }
    }

    private boolean openTagCountIsEqualCloseTagCount(String runText) {
        int openTagCount = runText.split("\\$\\{", -1).length - 1;
        int closeTagCount = runText.split("}", -1).length - 1;
        return openTagCount == closeTagCount;
    }
}
