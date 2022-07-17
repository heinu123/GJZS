//
// Created by qwq233 on 6/19/2022.
//

#include <stdio.h>
#include <string.h>
#include <jni.h>
#include <sys/types.h>
#include <inttypes.h>
#include <stdlib.h>
#include <unistd.h>
#include <dirent.h>
#include <sys/stat.h>
#include <android/log.h>
#include <errno.h>
#include "genuine.h"

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "GJZS", __VA_ARGS__)

jint JNI_OnLoad(JavaVM *, void *) {
    JNIEnv *env = 0;

    if (!checkGenuine(env)) {
        LOGE("checkGenuine: failed!");
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}
