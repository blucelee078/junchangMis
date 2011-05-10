/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package junchangmis.student;

import publicmethod.*;
import junchangmis.codecSetting.*;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Label;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import java.util.Locale;
import java.util.Vector;
import java.util.StringTokenizer;

/**
 *
 * @author developers
 */
public class IdAcountExcel {

    private WritableCellFormat times;
    private WritableCellFormat timeone;
    private WritableCellFormat timestiao;
    private String inputFile;
    String filename;
    formalStu student;

    public IdAcountExcel(formalStu student, String filename) {
        this.student = student;
        this.filename = filename;
    }

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void write() throws IOException, WriteException {
        try {
            //    File file = new File(inputFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "CN"));
            //Excel获得文件
            Workbook wb = Workbook.getWorkbook(new File("example.xls"));
//打开一个文件的副本，并且指定数据写回到原文件
            WritableWorkbook book = Workbook.createWorkbook(new File(PrintTest.getPath() + "\\学生证和收据\\" + filename), wb, wbSettings);
            WritableSheet sheet = book.getSheet(0);
            createLabel(sheet);
            createContent(sheet);
            book.write();
            book.close();
        } catch (BiffException ex) {
            Logger.getLogger(IdAcountExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createContent(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        addLabel(sheet, 2, 1, student.id);
        addLabel(sheet, 1, 2, student.name);

        String codeTriple = codecMethod.getCodeTripleMeaning(student.classCode);//获得总编码的翻译结果
        StringTokenizer st = new StringTokenizer(codeTriple, " ");
        Vector<String> temp = new Vector<String>();
        while (st.hasMoreTokens()) {
            //System.out.println(st.nextToken());
            temp.add(st.nextToken());
        }
        addLabel(sheet, 3, 2, temp.get(0) + " " + temp.get(1));

        String className = temp.get(2);
        addLabel(sheet, 1, 3, className);

        addLabel(sheet, 3, 3, student.tuition + "元");

        Integer other = Integer.valueOf(student.costumeFee) + Integer.valueOf(student.bookFee) + Integer.valueOf(student.otherFee);
        addLabel(sheet, 1, 4, Integer.toString(other) + "元");

        addLabel(sheet, 3, 4, student.sumFee + "元");

        addLabelone(sheet, 3, 5, DateCompute.getCurrentDateCHN());

        addLabel(sheet, 6, 2, student.name);
        addLabel(sheet, 6, 3, student.id);
        addLabel(sheet, 6, 4, codeTriple);

        addLabeltiao(sheet, 0, 6, "收费留存   姓名：" + student.name + " 学费:" + student.tuition + "元 其他:" + Integer.toString(other) + "元  总费：" + student.sumFee + "元 " + DateCompute.getCurrentDateCHN());
    }

    private void createLabel(WritableSheet sheet)
            throws WriteException {
        WritableFont times12ptBoldHead = new WritableFont(WritableFont.TIMES, 12);
        times = new WritableCellFormat(times12ptBoldHead);
        times.setAlignment(jxl.format.Alignment.CENTRE);
        times.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        times.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.MEDIUM);
        times.setWrap(true);

        WritableFont times10ptBoldHead = new WritableFont(WritableFont.TIMES, 10);
        timeone = new WritableCellFormat(times10ptBoldHead);
        timeone.setAlignment(jxl.format.Alignment.RIGHT);
        timeone.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

        timeone.setWrap(true);

        timestiao = new WritableCellFormat(times12ptBoldHead);
        timestiao.setAlignment(jxl.format.Alignment.LEFT);
        timestiao.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        timestiao.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DASHED);
        timestiao.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.DASHED);
        timestiao.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.DASHED);
        timestiao.setWrap(true);

    }

    private void addLabeltiao(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, timestiao);
        sheet.addCell(label);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

    private void addLabelone(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, timeone);
        sheet.addCell(label);
    }

    public static void main(String[] args) throws WriteException, IOException {
        {
            /*       try {
            //Excel获得文件
            Workbook wb = Workbook.getWorkbook(new File("example.xls"));

            //打开一个文件的副本，并且指定数据写回到原文件
            WritableWorkbook book = Workbook.createWorkbook(new File("exampleone.xls"), wb);
            WritableSheet sheet = book.getSheet(0);
            //createLabel(sheet);

            sheet.addCell(new Label(1, 2, "樊骏"));
            sheet.addCell(new Label(3, 2, "数学 英语 语文"));
            sheet.addCell(new Label(1, 3, "五"));
            sheet.addCell(new Label(3, 3, "1-1"));
            sheet.addCell(new Label(1, 4, "900"));
            sheet.addCell(new Label(3, 4, "90"));

            sheet.addCell(new Label(6, 2, "樊骏"));
            sheet.addCell(new Label(6, 3, "数学 英语 语文"));
            sheet.addCell(new Label(6, 4, "五"));


            /*   jxl.write.Number number = new jxl.write.Number(2, 3, 24);
            sheet.addCell(number);
            jxl.write.Label label = new jxl.write.Label(2, 11, "可以读取Excel 95, 97, 2000文件可以读或写Excel 97及其以后版本的的公式（不过我发现好像有bug）生成Excel 97格式的电子表格 ");
            sheet.addCell(label);
            jxl.write.Label label4 = new jxl.write.Label(5, 4, "可以读取Excel 95, 97, 2000文件可以读或写Excel 97及其以后版本的的公式（不过我发现好像有bug）生成Excel 97格式的电子表格 ");
            sheet.addCell(label4);
            jxl.write.Label label2 = new jxl.write.Label(1, 11, "2005.11.5-2005.11.25");
            sheet.addCell(label2);
            jxl.write.Label label3 = new jxl.write.Label(1, 12, "2005.11.15-2005.11.25");
            sheet.addCell(label3);*/

            /*         book.write();
            book.close();
            } catch (Exception e) {
            System.out.println(e);
            }*/
            /*   UpdateExcel test = new UpdateExcel();
            test.setOutputFile("exampleone.xls");
            test.write();
            String filename="exampleone.xls";
            PrintTest.print(PrintTest.getPath() + "\\" + filename);*/
        }
    }
}
