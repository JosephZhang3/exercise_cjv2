#include "Printf3.h"
#include <string.h>
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

JNIEXPORT void JNICALL Java_Printf3_fprint(JNIEnv* env, jclass cl, jobject out, jstring format, jdouble x)
{
    const char* cformat;
    char* fmt;
    jstring str;
    jclass class_PrintWriter;
    jmethodID id_print;

    cformat = (*env)->GetStringUTFChars(env,format,NULL);
    //JNI函数调用，转换java格式为c格式，jstring -> const  char*  注意：java中String类型不可变，所以这里C的char*类型也必须一样不可变
    fmt = find_format(cformat);

    if(fmt == NULL)
        str = format;
    else
    {
        char* cstr;
        int width = atoi(fmt);
        if(width == 0)
            width = DBL_DIG + 10;

	cstr = (char*)malloc(strlen(cformat) + width);//分配内存
        sprintf(cstr,cformat,x);
        str = (*env)->NewStringUTF(env,cstr);
        free(cstr);//释放内存
    }
    (*env)->ReleaseStringUTFChars(env,format,cformat);
    
    //从这里开始，我们调用out.print(Str)，其中，out表示PrintWirter类的实例对象
    class_PrintWriter = (*env)->GetObjectClass(env,out);

    //根据类、方法名、方法混编签名，得到方法ID
    id_print = (*env)->GetMethodID(env,class_PrintWriter,"print","(Ljava/lang/String;)V");
    //调用对象out的print方法
    (*env)->CallVoidMethod(env,out,id_print,str);
}
