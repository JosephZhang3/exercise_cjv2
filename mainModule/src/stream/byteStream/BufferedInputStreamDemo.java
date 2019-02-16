package stream.byteStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 以文件为输入来源的，带缓冲的字节输入流。
 * test.txt文件的内容如下：
 * “8:45 AM 4/25/2017
 * This is a test case of Java stream.”
 */
public class BufferedInputStreamDemo {

    public static void main(String[] args) {
        FileInputStream is = null;
        BufferedInputStream bis = null;
        byte data[] = new byte[1024];
        try {
            is = new FileInputStream("c:" + File.separator + "test.txt");
            bis = new BufferedInputStream(is);
            while (bis.read(data) != -1) {//每次循环都将读取出的字节放入data数组中，如果data数组存在元素则清空后再放入
                System.out.println(new String(data));//将字节数组转换为字符串
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //先关闭外层管道，再关闭内层管道，注意次序。
                bis.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
