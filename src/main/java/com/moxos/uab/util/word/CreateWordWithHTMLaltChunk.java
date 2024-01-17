package com.moxos.uab.util.word;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.namespace.QName;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ooxml.POIXMLRelation;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackagingURIHelper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.jsoup.Jsoup;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTBodyImpl;

public class CreateWordWithHTMLaltChunk {

    public static void addHtml(XWPFDocument doc, String id, String html) throws Exception {
        OPCPackage oPCPackage = doc.getPackage();
        PackagePartName partName = PackagingURIHelper.createPartName("/word/" + id + ".html");
        PackagePart part = oPCPackage.createPart(partName, "text/html");
        class HtmlRelation extends POIXMLRelation {

            public HtmlRelation() {
                super("text/html",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/aFChunk",
                        "/word/htmlDoc#.html");
            }
        }
        class HtmlDocumentPart extends POIXMLDocumentPart {

            private HtmlDocumentPart(PackagePart part) throws Exception {
                super(part);
            }

            @Override
            protected void commit() throws IOException {
                try (OutputStream out = part.getOutputStream()) {
                    try (Writer writer = new OutputStreamWriter(out, "UTF-8")) {
                        writer.write(html);
                    }
                }
            }
        };
        HtmlDocumentPart documentPart = new HtmlDocumentPart(part);
        doc.addRelation(id, new HtmlRelation(), documentPart);
        CTBodyImpl b = (CTBodyImpl) doc.getDocument().getBody();
        QName ALTCHUNK = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "altChunk");
        XmlComplexContentImpl altchunk = (XmlComplexContentImpl) b.get_store().add_element_user(ALTCHUNK);
        QName ID = new QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "id");
        SimpleValue target = (SimpleValue) altchunk.get_store().add_attribute_user(ID);
        target.setStringValue(id);
    }

    public static void addHtml(XWPFParagraph para, int posicion, String id, String html) throws Exception {
        OPCPackage oPCPackage = para.getBody().getXWPFDocument().getPackage();
        PackagePartName partName = PackagingURIHelper.createPartName("/word/" + id + ".html");
        PackagePart part = oPCPackage.createPart(partName, "text/html");
        class HtmlRelation extends POIXMLRelation {

            public HtmlRelation() {
                super("text/html",
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/aFChunk",
                        "/word/htmlDoc#.html");
            }
        }
        class HtmlDocumentPart extends POIXMLDocumentPart {

            private HtmlDocumentPart(PackagePart part) throws Exception {
                super(part);
            }

            @Override
            protected void commit() throws IOException {
                try (OutputStream out = part.getOutputStream()) {
                    try (Writer writer = new OutputStreamWriter(out, "UTF-8")) {
                        writer.write(html);
                    }
                }
            }
        };
        HtmlDocumentPart documentPart = new HtmlDocumentPart(part);
        para.getBody().getXWPFDocument().addRelation(id, new HtmlRelation(), documentPart);
        XmlCursor cursor = para.getCTP().newCursor();
        cursor.toEndToken(); //now we are at end of the paragraph
        //there always must be a next start token. Either a p or at least sectPr.
        while (cursor.toNextToken() != org.apache.xmlbeans.XmlCursor.TokenType.START);
        //now we can insert the CTAltChunk here
        QName ALTCHUNK = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "altChunk");
        String uri = ALTCHUNK.getNamespaceURI();
        cursor.beginElement("altChunk", uri);
        cursor.toParent();
        XmlComplexContentImpl altchunk = (XmlComplexContentImpl) cursor.getObject();
        QName ID = new QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "id");
        SimpleValue target = (SimpleValue) altchunk.get_store().add_attribute_user(ID);
        target.setStringValue(id);
        para.getBody().getXWPFDocument().removeBodyElement(posicion);
    }

}
