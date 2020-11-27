package parzulpan.com.exer;

import java.io.*;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc :
 */

public class CopyFile {
    public static void main(String[] args) {
        //


        //
    }

    /**
     * 文件流
     * @param srcFilePath 源文件路径
     * @param destFilePath  目标文件路径
     * @return  复制是否成功
     */
    public static boolean copyFileWithFileInputOutputStream(String srcFilePath, String destFilePath) {
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            int data;

            byte[] buffer = new byte[1024];
            while ((data = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, data);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 缓冲流
     * @param srcFilePath 源文件路径
     * @param destFilePath  目标文件路径
     * @return  复制是否成功
     */
    public static boolean copyFileWithBufferInputOutputStream(String srcFilePath, String destFilePath) {
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            int data;
            byte[] buffer = new byte[1024];
            while ((data = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // 关闭资源时，要求先关闭外层的流，再关闭外层的流。但是可以直接关闭外层的流，内层的流会自动关闭。
        try {
            if (bos != null) {
                bos.close();
            }
            if (bis != null) {
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
