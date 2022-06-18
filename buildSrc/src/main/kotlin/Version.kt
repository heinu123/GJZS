import org.gradle.api.JavaVersion
import org.gradle.api.Project
import java.io.File
import java.util.Properties

object Version {
    const val kotlin = "1.6.21"
    const val ksp = "1.0.5"
    val java = JavaVersion.VERSION_11

    private const val defaultNdkVersion = "23.1.7779620"
    private const val defaultCMakeVersion = "3.22.1"

    fun getNdkVersion(): String {
        return defaultNdkVersion
    }

    fun getCMakeVersion(): String {
        return defaultCMakeVersion
    }

    private fun getLocalProperty(project: Project, propertyName: String): String? {
        val rootProject = project.rootProject
        val localProp = File(rootProject.projectDir, "local.properties")
        if (!localProp.exists()) {
            return null
        }
        val localProperties = Properties()
        localProp.inputStream().use {
            localProperties.load(it)
        }
        return localProperties.getProperty(propertyName, null)
    }

    private fun getEnvVariable(name: String): String? {
        return System.getenv(name)
    }
}
