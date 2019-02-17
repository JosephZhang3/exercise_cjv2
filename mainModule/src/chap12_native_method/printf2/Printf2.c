#include "Printf2.h"
#include <string.h>
#include <stdlib.h>
#include <float.h>

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

JNIEXPORT jstring JNICALL Java_Printf2_sprint(JNIEnv* env, jclass cl, jstring format, jdouble x)
{
    const char* cformat;
    char* fmt;
    jstring ret;

    cformat = (*env)->GetStringUTFChars(env,format,NULL);//JNI函数调用，转换java格式为c格式
    fmt = find_format(cformat);

    if(fmt == NULL)
        ret = format;
    else
    {
        char* cret;
        int width = atoi(fmt);
        if(width == 0)
            width = DBL_DIG + 10;

	cret = (char*)malloc(strlen(cformat) + width);//分配内存
        sprintf(cret,cformat,x);
        ret = (*env)->NewStringUTF(env,cret);
        free(cret);//释放内存
    }
    (*env)->ReleaseStringUTFChars(env,format,cformat);
    return ret;
}
