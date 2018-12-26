package com.tongchen.twatcher.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by TongChen at 16:25 on 2018/12/26.
 * <p>
 * Description: 文件操作工具类
 */
public class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException("FileUtils doesn't need to be initialized!");
    }

    public static void copyFile2Target(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
