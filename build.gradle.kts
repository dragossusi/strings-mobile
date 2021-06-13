buildscript {
    repositories {
        maven {
            url = uri("https://repo.binom.pw/releases")
        }
    }
}

plugins {
    kotlin("multiplatform") version "1.5.10"
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
    jvm {
        compilations.getByName("main") {
            kotlinOptions {
                jvmTarget = "15"
            }
        }
        tasks.getByName<Jar>("jvmJar") {
            manifest {
                attributes["Main-Class"] = "sample.MainKt"
            }
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
                api("pw.binom.io:file:0.1.29")
//                api("com.squareup.okio:okio-multiplatform:2.10.0")
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