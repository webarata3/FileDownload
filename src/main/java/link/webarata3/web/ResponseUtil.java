package link.webarata3.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

public class ResponseUtil {
    public static String urlEncode(String fileName) {
        // Java標準では、半角スペースが+にエンコードされる
        // String encodeFileName = URLEncoder.encode(fileName, "UTF-8");
        URLCodec codec = new URLCodec("UTF-8");
        String encodeFileName = "";
        try {
            encodeFileName = codec.encode(fileName);
        } catch (EncoderException e) {
            // 来ないはず
            return "bad_fine_name";
        }
        return encodeFileName.replaceAll("\\+", "%20").replaceAll("%2B", "+");
    }

    // ①
    public static void download1(File file, HttpServletResponse response) throws IOException {
        String fileName = file.getName();
        String encodeFileName = urlEncode(fileName);

        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeFileName + "\"");
        IoUtil.copy(new FileInputStream(file), response.getOutputStream());
    }

    // ②
    public static void download2(File file, HttpServletResponse response) throws IOException {
        String fileName = file.getName();
        String encodeFileName = urlEncode(fileName);

        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        // RFC6266
        response.setHeader("Content-Disposition", "attachment; filename*=utf-8''" + encodeFileName);
        IoUtil.copy(new FileInputStream(file), response.getOutputStream());
    }

    // ③
    public static void download3(File file, HttpServletResponse response) throws IOException {
        String fileName = file.getName();

        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + MimeUtility.encodeWord(fileName, "UTF-8", "B") + "\"");
        IoUtil.copy(new FileInputStream(file), response.getOutputStream());
    }

    // ④
    public static void download4(File file, HttpServletResponse response) throws IOException {
        String fileName = file.getName();

        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        response.addHeader("Content-Disposition",
                "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        IoUtil.copy(new FileInputStream(file), response.getOutputStream());
    }

}
