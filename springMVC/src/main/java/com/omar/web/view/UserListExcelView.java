package com.omar.web.view;

import com.omar.spring.model.User;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Component("userListExcel")
public class UserListExcelView extends AbstractXlsView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "inline;filename="+
                URLEncoder.encode("用户表格.xls", "UTF-8"));
        List<User> userList = (List<User>) model.get("userList");
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("users");
        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("id");
        header.createCell(1).setCellValue("姓名");
        header.createCell(2).setCellValue("手机号");
        header.createCell(3).setCellValue("生日");

        int rowNum = 1;
        for (User user : userList) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(user.getUserId());
            row.createCell(1).setCellValue(user.getUserName());
            row.createCell(2).setCellValue(user.getPhoneNo());
            String createDate = DateFormatUtils.format(user.getBirthDay(),
                    "yyyy-MM-dd");
            row.createCell(3).setCellValue(createDate);
            rowNum++;
        }
    }
}
