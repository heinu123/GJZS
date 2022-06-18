import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp") version "${Version.kotlin}-${Version.ksp}"
}

var keystorePwd: String? = null
var alias: String? = null
var pwd: String? = null
var keystore: File? = null

if (project.rootProject.file("local.properties").exists() || (System.getenv("KEYSTORE_PATH") != null)) {
    if (System.getenv("KEYSTORE_PATH") != null) {
        keystore = file(System.getenv("KEYSTORE_PATH"))
        keystorePwd = System.getenv("KEYSTORE_PASSWORD")
        alias = System.getenv("KEY_ALIAS")
        pwd = System.getenv("KEY_PASSWORD")
    } else {
        keystorePwd = gradleLocalProperties(rootDir).getProperty("KEYSTORE_PASSWORD")
        alias = gradleLocalProperties(rootDir).getProperty("KEY_ALIAS")
        pwd = gradleLocalProperties(rootDir).getProperty("KEY_PASSWORD")
        keystore = file(gradleLocalProperties(rootDir).getProperty("KEYSTORE_PATH"))
    }
}

android {
    defaultConfig {
        applicationId = "io.github.gjzs"

        resourceConfigurations += listOf("zh", "en")

        externalNativeBuild {
            cmake {
                arguments += listOf(
                    "-DGJZS_VERSION=$versionName",
                    "-DCMAKE_C_COMPILER_LAUNCHER=ccache",
                    "-DCMAKE_CXX_COMPILER_LAUNCHER=ccache",
                    "-DNDK_CCACHE=ccache"
                )
                targets += "gjzs"
            }
        }
    }
    if (keystore != null) {
        signingConfigs {
            create("release") {
                storeFile = keystore
                storePassword = keystorePwd
                keyAlias = alias
                keyPassword = keystorePwd
                enableV2Signing = true
                enableV3Signing = true
                enableV4Signing = true
            }
        }
    }
    buildTypes {
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
            if (System.getenv("KEYSTORE_PATH") != null) {
                signingConfig = signingConfigs.getByName("release")
            }
            kotlinOptions.suppressWarnings = true
        }
        getByName("debug") {
            isShrinkResources = false
            isMinifyEnabled = false
            isCrunchPngs = false
            proguardFiles("proguard-rules.pro")
        }
    }

    kotlinOptions.jvmTarget = Version.java.toString()

    externalNativeBuild {
        cmake {
            path = File(projectDir, "src/main/cpp/CMakeLists.txt")
            version = Version.getCMakeVersion()
        }
    }

    buildFeatures {
        viewBinding = true
    }
    lint {
        checkDependencies = true
    }
    namespace = "io.github.gjzs"
}

dependencies {
    implementation(projects.mainStyle)
    implementation(projects.common)

    implementation(platform("com.google.firebase:firebase-bom:30.1.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")

    val appCenterSdkVersion = "4.4.3"
    implementation("com.microsoft.appcenter:appcenter-analytics:${appCenterSdkVersion}")
    implementation("com.microsoft.appcenter:appcenter-crashes:${appCenterSdkVersion}")
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    if (name.contains("release", true)) {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xno-call-assertions",
                "-Xno-receiver-assertions",
                "-Xno-param-assertions",
            )
        }
    }
}

tasks.register("checkGitSubmodule") {
    val projectDir = rootProject.projectDir
    doLast {
        listOf(
            "libs/ui/mainStyle".replace('/', File.separatorChar),
        ).forEach {
            val submoduleDir = File(projectDir, it)
            if (!submoduleDir.exists()) {
                throw IllegalStateException(
                    "submodule dir not found: $submoduleDir" +
                            "\nPlease run 'git submodule init' and 'git submodule update' manually."
                )
            }
        }
    }
}
tasks.getByName("preBuild").dependsOn(tasks.getByName("checkGitSubmodule"))

interface Injected {
    @get:Inject
    val eo: ExecOperations
}

