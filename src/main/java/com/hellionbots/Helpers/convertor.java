package com.hellionbots.Helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class convertor {
    public File convertTextToPdf(String text) {
        Document pdfDoc = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(pdfDoc, new FileOutputStream("txt.pdf")).setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            pdfDoc.open();

            Font myfont = new Font();
            myfont.setStyle(Font.NORMAL);
            myfont.setSize(11);
            pdfDoc.add(new Paragraph("\n"));

            Paragraph para = new Paragraph(text + "\n", myfont);
            para.setAlignment(Element.ALIGN_JUSTIFIED);
            pdfDoc.add(para);
            pdfDoc.close();

            File file = new File("txt.pdf");

            return file;
        } catch (DocumentException | IOException e) {
            return null;
        }
    }

    public File generatePDFFromImage(File file) {
        Document document = new Document();
        String input = file.getName() + ".png";

        PdfWriter writer;
        try {
            FileOutputStream fos = new FileOutputStream("pdfimage.pdf");
            writer = PdfWriter.getInstance(document, fos);
            writer.open();
            document.open();
            document.close();
            writer.close();

            document.add(Image.getInstance((new URL(input))));

            return new File("pdfimage.png");
        } catch (DocumentException | IOException e) {
            return null;
        }

    }
}
