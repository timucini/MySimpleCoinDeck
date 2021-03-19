import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin

plugins {
    id(Plugins.spotless) version Versions.spotless
}

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(Classpaths.androidGradlePlugin)
        classpath(Classpaths.kotlinGradlePlugin)
        classpath(Classpaths.daggerHiltPlugin)
        classpath(Classpaths.navigationSafeArgsPlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

subprojects {
    project.pluginManager.apply(Plugins.spotless)

    spotless {
        java {
            target("**/*.java")
            trimTrailingWhitespace()
            removeUnusedImports()
            googleJavaFormat()
        }

        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint("0.40.0").userData(
                hashMapOf(
                    "indent_size" to "4",
                    "android" to "true",
                    "max_line_length" to "200"
                )
            )
        }

        kotlinGradle {
            target("**/*.gradle.kts")
            ktlint("0.40.0").userData(
                hashMapOf(
                    "indent_size" to "4",
                    "android" to "true",
                    "max_line_length" to "200"
                )
            )
        }

        format("misc") {
            target("**/.gitignore", "**/*.gradle", "**/*.md", "**/*.sh", "**/*.yml")
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    project.plugins.whenPluginAdded {
        when (this) {
            is AppPlugin, is LibraryPlugin -> {
                the<com.android.build.gradle.BaseExtension>().apply {
                    buildFeatures.viewBinding = true

                    compileOptions {
                        targetCompatibility = JavaVersion.VERSION_1_8
                        sourceCompatibility = JavaVersion.VERSION_1_8
                    }

                    packagingOptions {
                        exclude("**/attach_hotspot_windows.dll")
                        exclude("META-INF/DEPENDENCIES")
                        exclude("META-INF/*.kotlin_module")
                        exclude("META-INF/AL2.0")
                        exclude("META-INF/LGPL2.1")
                    }

                    lintOptions.lintConfig = rootProject.file("lint.xml")
                }
            }
            is JavaPlugin -> {
                the<JavaPluginConvention>().apply {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
            }
        }
    }
}
