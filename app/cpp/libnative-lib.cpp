#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_seekercapitaltest_data_network_retrofit_SecureKey_getApiKey(JNIEnv *env, jobject thiz) {
    std::string appId = "zeJ7R4axOlx60gJFDdxrxgBOVenejmtP";
    return env->NewStringUTF(appId.c_str());
}