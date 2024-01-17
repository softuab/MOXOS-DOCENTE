package com.moxos.uab.util.word;

import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.w3c.dom.Node;

import javax.servlet.http.HttpServletResponse;

public class MSWordTool {

    /**
     * Document object used internally *
     */
    private XWPFDocument document;
    private BookMarks bookMarks = null;

    /**
     * Set a template for the document
     *
     * @param templatePath template file name
     */
    public void setTemplate(String templatePath) throws InvalidFormatException {
        try {
            FileInputStream fis = new FileInputStream(templatePath);
            this.document = new XWPFDocument(OPCPackage.open(fis));
            bookMarks = new BookMarks(document);
            fis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * For label replacement example, in the incoming Map, the key represents
     * the label name, and the value is the replacement information
     *
     * @param indicator
     */
    public void replaceBookMark(Map<String, String> indicator) {
        //Loop to replace
        Iterator<String> bookMarkIter = bookMarks.getNameIterator();
        while (bookMarkIter.hasNext()) {
            String bookMarkName = bookMarkIter.next();

            //Get the label name
            BookMark bookMark = bookMarks.getBookmark(bookMarkName);

            // make replacement
            if (indicator.get(bookMarkName) != null) {
                bookMark.insertTextAtBookMark(indicator.get(bookMarkName), BookMark.REPLACE);
            }
        }
    }

    public void replaceHtmlBookMark(Map<String, String> indicator) throws Exception {
        //Loop to replace
        Iterator<String> bookMarkIter = bookMarks.getNameIterator();
        while (bookMarkIter.hasNext()) {
            String bookMarkName = bookMarkIter.next();
            //Get the label name
            BookMark bookMark = bookMarks.getBookmark(bookMarkName);
            // make replacement
            if (indicator.get(bookMarkName) != null) {
                bookMark.insertHtmlAtBookMark(indicator.get(bookMarkName), bookMarkName);
            }
        }
    }

    public void replaceBookMarkTable(Map<String, Table> indicator) {
        //Loop to replace
        Iterator<String> bookMarkIter = bookMarks.getNameIterator();
        while (bookMarkIter.hasNext()) {
            String bookMarkName = bookMarkIter.next();
            //Get the label name
            BookMark bookMark = bookMarks.getBookmark(bookMarkName);
            // make replacement
            if (indicator.get(bookMarkName) != null) {
                bookMark.insertTableAtBookMark(indicator.get(bookMarkName), BookMark.INSERT_BEFORE);
            }
        }
    }

    public void fillTableAtBookMark(String bookMarkName, List<Map<String, String>> content) {
        //rowNum to compare which row of the table the label is
        int rowNum = 0;

        //Get the label first
        BookMark bookMark = bookMarks.getBookmark(bookMarkName);
        Map<String, String> columnMap = new HashMap<String, String>();
        Map<String, Node> styleNode = new HashMap<String, Node>();

        //Whether the label is in the table
        if (bookMark.isInTable()) {

            //Get the Table object and Row object corresponding to the label
            XWPFTable table = bookMark.getContainerTable();
            XWPFTableRow row = bookMark.getContainerTableRow();
            CTRow ctRow = row.getCtRow();
            List<XWPFTableCell> rowCell = row.getTableCells();
            for (int i = 0; i < rowCell.size(); i++) {
                columnMap.put(i + "", rowCell.get(i).getText().trim());
                //System.out.println(rowCell.get(i).getParagraphs().get(0).createRun().getFontSize());
                //System.out.println(rowCell.get(i).getParagraphs().get(0).getCTP());
                //System.out.println(rowCell.get(i).getParagraphs().get(0).getStyle());
                //Get the xml of the cell paragraph, get the root node
                Node node1 = rowCell.get(i).getParagraphs().get(0).getCTP().getDomNode();
                //Traverse all child nodes of the root node
                for (int x = 0; x < node1.getChildNodes().getLength(); x++) {
                    if (node1.getChildNodes().item(x).getNodeName().equals(BookMark.RUN_NODE_NAME)) {
                        Node node2 = node1.getChildNodes().item(x);
                        //Traverse all the own points with the node "w:r" and find the node with the node name "w:rPr"
                        for (int y = 0; y < node2.getChildNodes().getLength(); y++) {
                            if (node2.getChildNodes().item(y).getNodeName().endsWith(BookMark.STYLE_NODE_NAME)) {
                                //Save the node with the node "w:rPr" (font format) in HashMap
                                styleNode.put(i + "", node2.getChildNodes().item(y));
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
            //Loop comparison, find the position of the line, delete and change line
            for (int i = 0; i < table.getNumberOfRows(); i++) {
                if (table.getRow(i).equals(row)) {
                    rowNum = i;
                    break;
                }
            }
            table.removeRow(rowNum);
            for (int i = 0; i < content.size(); i++) {
                //Create a new row, the number of cells is the number of cells in the first row of the table,
                //When adding data later, judge whether the number of cells is consistent
                XWPFTableRow tableRow = table.createRow();
                CTTrPr trPr = tableRow.getCtRow().addNewTrPr();
                CTHeight ht = trPr.addNewTrHeight();
                ht.setVal(BigInteger.valueOf(360));
            }
            //Get the number of table rows
            int rcount = table.getNumberOfRows();
            for (int i = rowNum; i < rcount; i++) {
                XWPFTableRow newRow = table.getRow(i);
                // Determine whether the number of cells in newRow is the number of cells in the row where the bookmark is located
                if (newRow.getTableCells().size() != rowCell.size()) {
                    //Calculate the absolute value of the difference between newRow and the number of cells in the row where the bookmark is located
                    //If the number of cells in newRow is more than the number of cells in the row where the bookmark is located, it cannot be processed by this method, and it can be completed by replacing the text in the table
                    //If the number of cells in newRow is less than the number of cells in the row where the bookmark is located, fill in the few cells
                    int sub = Math.abs(newRow.getTableCells().size() - rowCell.size());
                    //Fill in the missing cells
                    for (int j = 0; j < sub; j++) {
                        newRow.addNewTableCell();
                    }
                }
                List<XWPFTableCell> cells = newRow.getTableCells();

                for (int j = 0; j < cells.size(); j++) {
                    XWPFParagraph para = cells.get(j).getParagraphs().get(0);
                    XWPFRun run = para.createRun();
                    if (content.get(i - rowNum).get(columnMap.get(j + "")) != null) {
                        //Change the value of the cell, the title bar does not need to change the value of the cell
                        run.setText(content.get(i - rowNum).get(columnMap.get(j + "")) + "");
                        //Set the font format of the cell paragraph to the font format of the original cell
                        run.getCTR().getDomNode().insertBefore(styleNode.get(j + "").cloneNode(true), run.getCTR().getDomNode().getFirstChild());
                    }
                    para.setAlignment(ParagraphAlignment.CENTER);
                }
            }
        }
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

    public void replaceText(Map<String, String> bookmarkMap, String bookMarkName) {

        //Get the label first
        BookMark bookMark = bookMarks.getBookmark(bookMarkName);
        //Get the bookmarked table
        XWPFTable table = bookMark.getContainerTable();
        //Get all the tables
        //Iterator<XWPFTable> it = document.getTablesIterator();

        if (table != null) {
            //Get all rows of the table
            int rcount = table.getNumberOfRows();
            for (int i = 0; i < rcount; i++) {
                XWPFTableRow row = table.getRow(i);

                //Get all the cells of the changed line
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell c : cells) {
                    for (Entry<String, String> e : bookmarkMap.entrySet()) {
                        if (c.getText().equals(e.getKey())) {

                            //Delete the cell content
                            c.removeParagraph(0);

                            //Assign a value to the cell
                            c.setText(e.getValue());
                        }
                    }
                }
            }
        }
    }

    public void saveAs(HttpServletResponse response) throws IOException {
        this.document.write(response.getOutputStream());
        document.close();
    }
    public void saveAs(ByteArrayOutputStream outputStream) throws IOException {
        this.document.write(outputStream);
        document.close();
    }
}
