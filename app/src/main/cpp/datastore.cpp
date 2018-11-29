#include <jni.h>
#include "../../../../../../Users/root/AppData/Local/Android/sdk/ndk-bundle/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/jni.h"

/*
 * WARNING:   !! PLEASE!! COMPILE ANDROID PROJECT in *RELEASE* Build Variant mode
 *            to ensure that DEBUG SYMBOLS ARE STRIPPED FROM THE OUTPUT C++ BINARY  :-)
 *
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 */

extern "C" {

    JNIEXPORT jstring JNICALL Java_com_example_fitness_view_HomeFragment_getApiKey
            (JNIEnv *env, jclass obj) {
        return env->NewStringUTF("d31fe793adf546658bd67e2b6a7fd11a");
    }

}