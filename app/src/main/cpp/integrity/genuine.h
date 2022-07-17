#include <stdbool.h>

#define GENUINE_NAME {0x67, 0x60, 0x3e, 0x76, 0x7b, 0x67, 0x68, 0x74, 0x60, 0x2d, 0x63, 0x6f, 0x7c, 0x74, 0x0}
#define GENUINE_SIZE 0x057b
#define GENUINE_HASH 0xcf879070

/* genuine false handler */
#define GENUINE_FALSE_CRASH
// #define GENUINE_FALSE_NATIVE

/* genuine fake handler */
#define GENUINE_FAKE_CRASH
// #define GENUINE_FAKE_NATIVE

/* genuine overlay handler */
// #define GENUINE_OVERLAY_CRASH
// #define GENUINE_OVERLAY_NATIVE

/* genuine odex handler */
// #define GENUINE_ODEX_CRASH
// #define GENUINE_ODEX_NATIVE

/* genuine dex handler */
#define GENUINE_DEX_CRASH
// #define GENUINE_DEX_NATIVE

/* genuine proxy handler */
#define GENUINE_PROXY_CRASH
// #define GENUINE_PROXY_NATIVE

/* genuine error handler */
#define GENUINE_ERROR_CRASH
// #define GENUINE_ERROR_NATIVE

/* genuine fatal handler */
#define GENUINE_FATAL_CRASH
// #define GENUINE_FATAL_NATIVE

/* genuine noapk handler */
#define GENUINE_NOAPK_CRASH
// #define GENUINE_NOAPK_NATIVE

bool checkGenuine(JNIEnv *env);
