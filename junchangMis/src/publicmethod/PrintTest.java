/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package publicmethod;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.File;

/**
 *
 * @author developers
 */
public class PrintTest {

    public PrintTest() {
    }

    public static void printDoc(String path) {
        ComThread.InitSTA();
        ActiveXComponent app = new ActiveXComponent("Word.Application");
        try {
            app.setProperty("Visible", new Variant(false));
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.call(docs, "Open", path).toDispatch();
            Dispatch.call(doc, "PrintOut");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            ComThread.Release();
        }
    }

    public static void print(String path) {
        ComThread.InitSTA();
        ActiveXComponent xl = new ActiveXComponent("Excel.Application");
        try {
// System.out.println("version=" + xl.getProperty("Version"));
//不打开文档
            Dispatch.put(xl, "Visible", new Variant(true));
            Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();
//打开文档
            Dispatch excel = Dispatch.call(workbooks, "Open", path).toDispatch();
//开始打印
            Dispatch.get(excel, "PrintOut");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//始终释放资源
            ComThread.Release();
        }
    }
    public static void showTable(String path) {
        ComThread.InitSTA();
        ActiveXComponent xl = new ActiveXComponent("Excel.Application");
        try {
// System.out.println("version=" + xl.getProperty("Version"));
//不打开文档
            Dispatch.put(xl, "Visible", new Variant(true));
            Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();
//打开文档
            Dispatch excel = Dispatch.call(workbooks, "Open", path).toDispatch();
//开始打印
           // Dispatch.get(excel, "PrintOut");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//始终释放资源
            ComThread.Release();
        }
    }

    public static String getPath() {
        File f = new File("");
        return f.getAbsolutePath().trim();
    }

    public static void main(String[] args) {
        File f = new File("");
        //  print(f.getAbsolutePath().trim() + "\\学科收费统计表\\数学收费表");
        // PrintTest ob=new PrintTest();
        // System.out.println(ob.getDirectory());

        // System.out.println(f.getAbsolutePath());
        print(f.getAbsolutePath().trim() + "\\example.xls");
    }
}
