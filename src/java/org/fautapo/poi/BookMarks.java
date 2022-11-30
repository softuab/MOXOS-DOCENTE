/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.Set;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * Use POI for Word file-related operations, and encapsulate in docx format
 *
 * @author
 *
 * <p>
 * Modification History:</p>
 * <p>
 * Date Author Description</p>
 * <p>
 * ------------------------------------------------------------------</p>
 * <p>
 * </p>
 * <p>
 * </p>
 */
public class BookMarks {

    /**
     * Save the label defined in the Word file *
     */
    private HashMap<String, BookMark> _bookmarks = null;

    /**
     * Constructor to analyze the document and parse out all tags
     *
     * @param document Word OOXML document instance.
     */
    public BookMarks(XWPFDocument document) {

        //Initialize tag cache
        this._bookmarks = new HashMap<String, BookMark>();

        // First parse the tags in the ordinary paragraphs of the document
        this.procParaList(document.getParagraphs());

        //Using cumbersome methods to get labels from all the tables, the processing is relatively primitive and simple
        List<XWPFTable> tableList = document.getTables();

        for (XWPFTable table : tableList) {
            //Get the column information of the table
            List<XWPFTableRow> rowList = table.getRows();
            for (XWPFTableRow row : rowList) {
                //Get the column information in the row
                List<XWPFTableCell> cellList = row.getTableCells();
                for (XWPFTableCell cell : cellList) {
                    //Analyze label information one by one
                    //this.procParaList(cell.getParagraphs(), row);
                    this.procParaList(cell);
                }
            }
        }
    }

    /**
     * According to the label name, get the relevant definition of the label, if
     * it does not exist, return empty
     *
     * @param bookmarkName tag name
     * @return returns the encapsulated object
     */
    public BookMark getBookmark(String bookmarkName) {
        BookMark bookmark = null;
        if (this._bookmarks.containsKey(bookmarkName)) {
            bookmark = this._bookmarks.get(bookmarkName);
        }
        return bookmark;
    }

    /**
     * Get all the label information collection
     *
     * @return Cached label information collection
     */
    public Collection<BookMark> getBookmarkList() {
        return (this._bookmarks.values());
    }

    /**
     * Returns the tag name iterator in the document
     *
     * @return Iterator converted by Map KEY
     */
    public Iterator<String> getNameIterator() {
        return (this._bookmarks.keySet().iterator());
    }

    private void procParaList(XWPFTableCell cell) {
        List<XWPFParagraph> paragraphList = cell.getParagraphs();

        for (XWPFParagraph paragraph : paragraphList) {
            //Get the tag mark in the paragraph
            List<CTBookmark> bookmarkList = paragraph.getCTP().getBookmarkStartList();
            for (CTBookmark bookmark : bookmarkList) {
                this._bookmarks.put(bookmark.getName(),
                        new BookMark(bookmark, paragraph, cell));
            }
        }
    }

    /**
     * Parse the tags in the table
     *
     * @param paragraphList the passed paragraph list
     * @param tableRow corresponding table row object
     */
    private void procParaList(List<XWPFParagraph> paragraphList, XWPFTableRow tableRow) {

        NamedNodeMap attributes = null;
        Node colFirstNode = null;
        Node colLastNode = null;
        int firstColIndex = 0;
        int lastColIndex = 0;

        //Loop judgment, parse the tags in the paragraph
        for (XWPFParagraph paragraph : paragraphList) {
            //Get the tag mark in the paragraph
            List<CTBookmark> bookmarkList = paragraph.getCTP().getBookmarkStartList();

            for (CTBookmark bookmark : bookmarkList) {
                // With a bookmark in hand, test to see if the bookmarkStart tag
                // has w:colFirst or w:colLast attributes. If it does, we are
                // dealing with a bookmarked table cell. This will need to be
                // handled differnetly - I think by an different concrete class
                // that implements the Bookmark interface!!
                attributes = bookmark.getDomNode().getAttributes();
                if (attributes != null) {

                    // Get the colFirst and colLast attributes. If both - for
                    // now - are found, then we are dealing with a bookmarked
                    // cell.
                    colFirstNode = attributes.getNamedItem("w:colFirst");
                    colLastNode = attributes.getNamedItem("w:colLast");
                    if (colFirstNode != null && colLastNode != null) {

                        // Get the index of the cell (or cells later) from them.
                        // First convefrt the String values both return to primitive
                        // int value. TO DO, what happens if there is a
                        // NumberFormatException.
                        firstColIndex = Integer.parseInt(colFirstNode.getNodeValue());
                        lastColIndex = Integer.parseInt(colLastNode.getNodeValue());
                        // if the indices are equal, then we are dealing with a#
                        // cell and can create the bookmark for it.
                        if (firstColIndex == lastColIndex) {
                            this._bookmarks.put(bookmark.getName(),
                                    new BookMark(bookmark, paragraph,
                                            tableRow.getCell(firstColIndex)));
                        } else {
                            System.out.println("This bookmark " + bookmark.getName()
                                    + " identifies a number of cells in the "
                                    + "table. That condition is not handled yet.");
                        }
                    } else {
                        this._bookmarks.put(bookmark.getName(),
                                new BookMark(bookmark, paragraph, tableRow.getCell(1)));
                    }
                } else {
                    this._bookmarks.put(bookmark.getName(),
                            new BookMark(bookmark, paragraph, tableRow.getCell(1)));
                }
            }
        }
    }

    /**
     * Parse tags in ordinary paragraphs
     *
     * @param paragraphList incoming paragraph
     */
    private void procParaList(List<XWPFParagraph> paragraphList) {
        for (XWPFParagraph paragraph : paragraphList) {
            List<CTBookmark> bookmarkList = paragraph.getCTP().getBookmarkStartList();
            //Loop to add tags
            for (CTBookmark bookmark : bookmarkList) {
                this._bookmarks.put(bookmark.getName(),
                        new BookMark(bookmark, paragraph));
            }
        }
    }
}
