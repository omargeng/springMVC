package com.omar.web.view;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.omar.spring.model.User;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Component("userListPdf")
public class UserListPdfView extends AbstractPdfView {


    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfWriter, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "inline; filename="+
                URLEncoder.encode("用户表格.pdf", "UTF-8"));
        List<User> userList = (List<User>) model.get("userList");
        Table table = new Table(4);
        table.setWidth(80);
        table.setBorder(1);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        BaseFont cnBaseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        Font cnFont = new Font(cnBaseFont, 10, Font.NORMAL, Color.BLUE);

        table.addCell(buildFontCell("id",cnFont));
        table.addCell(buildFontCell("姓名",cnFont));
        table.addCell(buildFontCell("手机号",cnFont));
        table.addCell(buildFontCell("生日",cnFont));
        for (User user : userList) {
            table.addCell(user.getUserId());
            table.addCell(buildFontCell(user.getUserName(),cnFont));
            table.addCell(user.getPhoneNo());
            String createDate = DateFormatUtils.format(user.getBirthDay(),
                    "yyyy-MM-dd");
            table.addCell(createDate);
        }
        document.add(table);

    }

    private Cell buildFontCell(String content, Font font) throws RuntimeException{
        try {
            Phrase phrase = new Phrase(content, font);
            return new Cell(phrase);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
