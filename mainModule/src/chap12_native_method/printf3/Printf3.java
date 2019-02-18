import java.io.PrintWriter;

//在本地c函数中调用java方法（逆向调用）
class Printf3
{
    public native static void fprint(PrintWriter out,String s,double x);

    static
    {
        System.loadLibrary("Printf3");
    }
}
