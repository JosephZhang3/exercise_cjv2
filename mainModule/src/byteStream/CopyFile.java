package byteStream;

import java.io.*;

//复制单个文件test.txt从源路径c:/source到目标路径c:/target
public class CopyFile {
    public static void main(String[] args) {
        File sourceFile = new File("c:" + File.separator + "source" + File.separator + "test.txt");
        // 判断文件在源路径中是否存在，只有存在才能copy
        if (sourceFile.exists()) {
            File targetFile = new File("c:" + File.separator + "target" + File.separator + "test.txt");
            // 判断目标路径中是否已经有了同名文件，如果有则删除后新建
            if (targetFile.exists()) {
                targetFile.delete();
                try {
                    targetFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//如果没有则直接新建
                try {
                    targetFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileInputStream fis = null;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            byte data[] = new byte[1024];
            try {
                fis = new FileInputStream("c:" + File.separator + "source" + File.separator + "test.txt");
                bis = new BufferedInputStream(fis);
                fos = new FileOutputStream("c:" + File.separator + "target" + File.separator + "test.txt", true);
                bos = new BufferedOutputStream(fos);
                //使用字节流，所以，首先从输入流读取字节放入data[]中，然后将data[]中的字节写到输出流。
                while (bis.read(data) != -1) {
                    bos.write(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bis.close();
                    fis.close();
                    bos.close();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("源文件不存在！");
        }
    }
}
