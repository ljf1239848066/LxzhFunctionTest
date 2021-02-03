#include "jnilib.h"

#include <android/log.h>
//#include "thread.h"

#define TAG "Native-Lib"
#define LOGI(FORMAT,...) __android_log_print(ANDROID_LOG_INFO, TAG, FORMAT, ##__VA_ARGS__);
#define LOGE(FORMAT,...) __android_log_print(ANDROID_LOG_ERROR, TAG, FORMAT, ##__VA_ARGS__);

//extern "C" JNIEXPORT jstring JNICALL
//Java_com_lxzh123_jnitest_MainActivity_stringFromJNI(
//        JNIEnv* env,
//        jobject /* this */) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}
//
//extern "C" JNIEXPORT jstring JNICALL
//Java_com_lxzh123_jnitest_MainActivity_stringFromJNI1(
//        JNIEnv* env,
//        jobject /* this */,
//        jint jlevel) {
//    std::string rst = "";
//    switch(jlevel) {
//        case 0:
//            rst = "Case0";
//            break;
//        case 1:
//            rst = "Case1";
//            break;
//        case 2:
//            rst = "Case2";
//            break;
//        case 3:
//            rst = "Case3";
//            break;
//        default:
//            rst = "CaseDefault";
//            break;
//    }
//    std::string hello = rst;
//    return env->NewStringUTF(hello.c_str());
//}


#define JNIREG_CLASS "com/lxzh123/funcdemo/jni/Helper"//指定要注册的类

jstring JNICALL sfj1(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

jstring JNICALL sfj2(
        JNIEnv *env,
        jobject /* this */,
        jint jlevel) {
    std::string hello = getString(jlevel);
    return env->NewStringUTF(hello.c_str());
}

std::string getString(jint level) {
    std::string rst = "";
    switch (level) {
        case 0:
            rst = "Case0";
            break;
        case 1:
            rst = "Case1";
            break;
        case 2:
            rst = "Case2";
            break;
        case 3:
            rst = "Case3";
            break;
        default:
            rst = "CaseDefault";
            break;
    }
    return rst;
}

jstring JNICALL sfj3(
        JNIEnv *env,
        jobject/* this */,
        jint jlevel) {
    std::string rtn = "error";
    jstring rst = env->NewStringUTF(rtn.c_str());
    LOGI("sfj3 start...");
    /**
     *找到要调用的类
     */
    jclass javaClass = env->FindClass(JNIREG_CLASS);   //注意，这里的使用的斜杠而不是点
    if(javaClass == NULL){
        return rst;
    }
    LOGI("sfj3 javaClass is not null...");

    jmethodID javaMethod = env->GetMethodID(javaClass,"callInstanceMethod","(I)I");
    if(javaMethod == NULL){
        LOGI("sfj3 callInstanceMethod is null...");
        env->DeleteLocalRef(javaClass);
        return rst;
    }
    LOGI("sfj3 callInstanceMethod is not null...");

    /**
     *获取构造方法
     */
    jmethodID javaStruct = env->GetMethodID(javaClass,"<init>","()V");
    if(javaStruct == NULL){
        LOGI("sfj3 javaStruct is null...");
        return rst;
    }
    LOGI("sfj3 javaStruct is not null...");

    /**
     *获取实例对象
     */
    jobject javaInstance = env->NewObject(javaClass,javaStruct,NULL);
    if(javaInstance == NULL){
        LOGI("sfj3 javaInstance is null...");
        env->DeleteLocalRef(javaClass);
        return rst;
    }
    LOGI("sfj3 javaInstance is not null...");

    /**
     *调用方法，参数是 jobject，jmethodID，传递的参数
     */
    jint rtnFromJava1 = env->CallIntMethod(javaInstance, javaMethod, jlevel);
    LOGI("sfj3 javaInstance CallIntMethod success...");

    /**
     *获取静态方法操作的对象 ，参数分别是 jclass,方法名称，方法签名
     */
    jmethodID javaStaticMethod = env->GetStaticMethodID(javaClass,"callStaticMethod","(I)I");
    if(javaStaticMethod == NULL){
        LOGI("sfj3 javaStaticMethod is null...");
        env->DeleteLocalRef(javaClass);
        return rst;
    }
    LOGI("sfj3 javaStaticMethod is not null...");


    jint rtnFromJava2 = env->CallStaticIntMethod(javaClass, javaStaticMethod, jlevel);
    LOGI("sfj3 javaClass CallStaticIntMethod success...");

    rtn = getString(rtnFromJava2);
    LOGI("sfj3 invoke getString success...");

    //删除引用
    env->DeleteLocalRef(javaClass);
//    env->DeleteLocalRef(javaMethod);
//    env->DeleteLocalRef(data);

    rst = env->NewStringUTF(rtn.c_str());
    return rst;
}

static JNINativeMethod gMethods[] = {
        {"stringFromJNI",  "()Ljava/lang/String;", (void *) sfj1},
        {"stringFromJNI1", "(I)Ljava/lang/String;", (void *) sfj2},
        {"stringFromJNI2", "(I)Ljava/lang/String;", (void *) sfj3}
};

JNIEXPORT int JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    jclass javaClass = env->FindClass(JNIREG_CLASS);
    if (javaClass == NULL) {
        return JNI_ERR;
    }
    if (env->RegisterNatives(javaClass, gMethods, NELEM(gMethods)) < 0) {
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}