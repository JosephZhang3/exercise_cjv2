#include "Printf4.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <float.h>

//TODO 这些c代码都是什么意思？似懂非懂
char* find_format(const char format[])
{
    char* p;
    char* q;

    p = strchr(format,'%');
    while (p != NULL && *(p+1) == '%')// 跳过 %%
        p = strchr(p+2,'%');
    if(p == NULL)
        return NULL;

    p++;
    q = strchr(p,'%');
    while(q != NULL && *(q +1 ) == '%')
        q = strchr(q+2,'%');
    if(q != NULL)
        return NULL;

    q = p + strspn(p," -0+#");
    q += strspn(q, "0123456789");
    if(*q == '.')
    {
        q++;
        q += strspn(q, "0123456789");
    }
    if(strchr("eEfFgG",*q) == NULL)
        return NULL;

    return p;
}


//参数说明
//JNIEnv*  JNI环境，给本地方法划分的内存区域
//jclass变量，Java Class对应的c变量
//jobject
//jstring
//jdouble
JNIEXPORT void JNICALL Java_Printf4_fprint
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
    if(format == NULL)
    {
        (*env)->ThrowNew(
                         env,
                         (*env)->FindClass(env,"java/lang/NullPointException"),
                         "Printf4.fprint: format is null"
        );
        return;
    }    

    //java格式字符转c格式字符数组
    cformat = (*env)->GetStringUTFChars(env,format,NULL);
    fmt = find_format(cformat);

    //没有从输入的格式字符串提取到有用的格式，抛非法参数异常给JVM，然后退出
    if(fmt == NULL)
    {
        (*env)->ThrowNew(
                         env,
                         (*env)->FindClass(env,"java/lang/IllegalArgumentException"),
                         "Printf4.fprint: format is invalid"
        );
    }

    //根据格式和宽度分配内存
    width = atoi(fmt);
    if(width == 0)
    {
        width = DBL_DIG + 10;
    }
    cstr = (char*)malloc(strlen(cformat) + width);

    //分配内存后没有得到可用的指针（句柄），抛内存溢出异常给JVM，然后退出
    if(cstr == NULL)
    {
        (*env)->ThrowNew(
                         env,
                         (*env)->FindClass(env,"java/lang/OutOfMemoryError"),
                         "Printf4.fPrint: malloc faild"
        );
    }

    sprintf(cstr,cformat,x);

    (*env)->ReleaseStringUTFChars(env,format,cformat);

    //调用java类PrintWriter.print()方法
    class_PrintWriter = (*env)->GetObjectClass(env,out);
    id_print = (*env)->GetMethodID(env,class_PrintWriter,"print","(C)V");//最后一个参数表示：V 返回类型为void，C入参为字符数组
    
    for(i = 0;cstr[i] != 0 && !(*env)->ExceptionOccurred(env);i++)
    {
        (*env)->CallVoidMethod(env,out,id_print,cstr[i]);//如果没有出现异常，则调用先前说的java类中的方法
    }

    free(cstr);
}
