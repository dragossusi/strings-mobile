plugins {
    kotlin("multiplatform")
    `maven-publish`
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
                implementation("com.github.ajalt.clikt:clikt:3.1.0")
                implementation(project(":library"))
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