#include <jni.h>
#include <stdlib.h>

/* 说明：把JVM嵌入到c或者c++程序中  */

#ifdef _wINDOWS

//windows下，需要手工加载JVM类库
#include <windows.h>
static HINSTANCE loadJVMLibrary(void);
typedef jint (JNICALL *CreateJavaVM_t)(JavaVM **,void **,JavaVMInitArgs *);

#endif

int main()
{
    JavaVMOption option[2];
    JavaVMInitArgs vm_args;
    JavaVM *jvm;
    JNIENV *env;
    long status;

    jclass class_Welcome;
    jclass class_String;
    jobjectArray args;
    jmethodID id_main;//主方法的方法id

#ifdef _WINDOWS
  HINSTANCE hjvmlib;
  CreateJavaVM_t createJavaVM;
#endif

  options[0].optionString = "-Djava.class.path=.";//指定类路径为当前路径

}
