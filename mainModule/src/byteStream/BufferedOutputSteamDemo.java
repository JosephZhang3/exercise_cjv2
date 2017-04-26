package byteStream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 以文件为输出目的地的，带缓冲的字节输出流。
 */
public class BufferedOutputSteamDemo {

    public static void main(String[] args) {
        FileOutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            os = new FileOutputStream("c:" + File.separator + "test.txt", true);//参数true表示在文件尾接着写入，不给出此参数表示直接覆盖
            //设置缓冲区大小为10个字节。只要字节序列的长度不小于缓冲区大小，就能被放入输出流，否则会被滞留在缓冲区。
            bos = new BufferedOutputStream(os, 1024);
            String content = "sample text like this";
            bos.write(content.getBytes());//将字符串转换为字节数组，然后写入文件
            //bos.flush();//刷新缓冲区（其实close方法中包含自动调用flush方法，所以，这是可选的）
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            try {
                bos.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}