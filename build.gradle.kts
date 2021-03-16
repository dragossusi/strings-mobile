buildscript {
    repositories {
        maven {
            url = uri("https://repo.binom.pw/releases")
        }
    }
}
plugins {
    kotlin("multiplatform") version "1.4.31"
    `maven-publish`
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.binom.pw/releases")
    }
}

//details
group = "ro.dragossusi"
version = "0.2"

kotlin {

    //java
    jvm() {
        compilations.getByName("main") {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        withJava()
        tasks.getByName<Jar>("jvmJar") {
            manifest {
                attributes["Main-Class"] = "sample.MainKt"
            }
//            from {
////                configurations.runtimeClasspath.collect {
////                    if (it.isDirectory()) it
////                    else zipTree(it)
////                }
//            }
        }
    }

    //windows
    mingwX64("mingw") {
        binaries {
            executable {

            }
        }
    }

    //macos
    macosX64("macos") {
        binaries {
            executable {

            }
        }
    }

    //linux
    linuxX64("linux") {
        binaries {
            executable {

            }
        }
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("pw.binom.io:file:0.1.28")
                api("com.github.ajalt.clikt:clikt:3.1.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
//        mingwMain {
//        }
//        mingwTest {
//        }
    }

}