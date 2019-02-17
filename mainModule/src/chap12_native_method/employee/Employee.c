#include<stdio.h>
#include "Employee.h"

JNIEXPORT void JNICALL Java_Employee_raiseSalary
  (JNIEnv* env, jobject this_obj, jdouble byPercent)
{
    jclass class_Employee = (*env)->GetObjectClass(env,this_obj);//获取对象所属的类

    jfieldID id_salary = (*env)->GetFieldID(env,class_Employee,"salary","D");//获取field ID

    jdouble salary = (*env)->GetDoubleField(env,this_obj,id_salary);//从object中获取field value

    salary *= (1 + byPercent/100);//计算

    (*env)->SetDoubleField(env,this_obj,id_salary,salary);///设置object中的field value
}
