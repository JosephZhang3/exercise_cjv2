#include “Printf4.h”
#include <string.h>
#include <Stdio.h>
#include <stdlib.h>
#include <float.h>



//JNIEnv*  JNI环境，给本地方法划分的内存区域
//jclass变量，Java Class对应的c变量
//jobject
//jstring
//jdouble
JNIEXPORT void JNICALL Java_Printf4_fPrint
  (JNIEnv* env, jclass cl, jobject out, jstring format, jdouble x)
{
    const char* cformat;
    char* fmt;
    jclass class_PrintWriter;
    jmethodID id_print;
    char* cstr;
    int width;
    int i;

    //格式为null，抛空指针异常给JVM，然后退出
    
    //没有从输入的格式字符串提取到有用的格式，抛非法参数异常给JVM，然后退出

    //分配内存后没有得到可用的指针（句柄），抛内存溢出异常给JVM，然后退出


}
