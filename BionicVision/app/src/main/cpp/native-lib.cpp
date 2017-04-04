#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_software_1a_com_bionicvision_LaunchActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
