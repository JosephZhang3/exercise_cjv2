package internationalize.resource_package;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * 属性文件解析器
 */
public class PropertiesFileResolver {

    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("demo");
        System.out.println("name:" + bundle.getString("name"));
        System.out.println("region:" + bundle.getString("region"));
        //遍历所有属性键
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            System.out.println(keys.nextElement());
        }
    }

}
