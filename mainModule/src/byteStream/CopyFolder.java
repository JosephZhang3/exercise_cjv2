package byteStream;

import java.io.*;

/**
 * 复制文件夹（考虑文件夹中还存在文件夹的情况）
 *
 * @author Jianghao Zhang on 2017/4/26
 */
public class CopyFolder {
    public static void main(String[] args) {
        String sourcePath = "c:" + File.separator + "source";//被复制的文件夹的路径
        String targetPath = "c:" + File.separator + "target";//粘贴后形成的文件夹的路径
        File sourceFolder = new File(sourcePath);
        File targetFolder = new File(targetPath);
        // 如果源文件夹不存在，报错
        if (!sourceFolder.exists()) {
            System.out.println(sourceFolder + "源文件夹路径错误！！！");
        } else {
            System.out.println("源文件夹路径为" + sourceFolder);
            System.out.println("目标文件夹路径为" + targetFolder);
            System.out.println();
            copy("c:" + File.separator + "source", "c:" + File.separator + "target");
        }
    }

    /**
     * @param sourcePath 源文件夹路径
     * @param targetPath 目标路径
     */
    public static void copy(String sourcePath, String targetPath) {
        File sourceFolder = new File(sourcePath);
        File targetFolder = new File(targetPath);
        /*
         * 如果目标文件夹不存在则创建（第一次调用时可能已经存在也可能不存在，之后的每次调用因为是子文件夹所以肯定不存在），
		 * 源文件夹每次调用肯定存在，不用判断
		 */
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }
        File files[] = sourceFolder.listFiles();//为源文件夹下的所有成员（可能是文件也可能是文件夹）创建集合
        for (File fileObject : files) {
            // 如果遍历到的是文件，复制该文件到目标位置
            if (fileObject.isFile()) {
                System.out.println("本次复制的文件名称为：" + fileObject.getName());
                System.out.println("粘贴到的目标路径为：" + targetPath);
                System.out.println();

                FileInputStream fis = null;
                BufferedInputStream bis = null;
                FileOutputStream fos = null;
                BufferedOutputStream bos = null;
                byte data[] = new byte[1024];
                try {
                    fis = new FileInputStream(fileObject);
                    bis = new BufferedInputStream(fis);

                    // 给出粘贴到的目标位置（形成的文件的绝对路径）
                    File thisFile = new File(targetPath + File.separator
                            + fileObject.getName());

                    fos = new FileOutputStream(thisFile);
                    bos = new BufferedOutputStream(fos);
                    // 边读边写，注意：调用read方法和write方法都要添加data参数！！
                    while (bis.read(data) != -1) {
                        bos.write(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bis.close();
                        fis.close();
                        bos.close();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {// 如果遍历到的是文件夹,再次调用自身
                copy(sourcePath + File.separator + fileObject.getName(), targetPath + File.separator
                        + fileObject.getName());
            }
        }
    }

}
