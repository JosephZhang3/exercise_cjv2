import java.io.PrintWriter;

class Printf4
{
    public static native void fPrint(PrintWriter pw,String format,double x);

       static
{
 System.loadLibrary("Printf4");
}
}