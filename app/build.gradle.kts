plugins {
    id("com.android.application")
}

android {
    namespace = "net.petcontrol.PetControlApi22"
    compileSdk = 34

    defaultConfig {
        applicationId = "net.petcontrol.PetControlApi22"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        /*
        // Define la ubicación del esquema de la base de datos
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/app/database"
            }
        }
        */
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

    /*
    room {
        // Ubicación donde se exportará el esquema de la base de datos --> app/database
        // $projectDir representa la ubicación del directorio raíz del proyecto
        var schemaLocation = "$projectDir/app/database"
    }
    */

}
/*
// Añadir argumentos al compilador Java en versiones modernas de Gradle
tasks.withType(JavaCompile) {
    options.compilerArgs.add("-Aroom.schemaLocation=$projectDir/app/database")
}
*/

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    //implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    //implementation("com.android.car.ui:car-ui-lib:2.0.0")
    implementation("com.android.car.ui:car-ui-lib:2.6.0")
    implementation("androidx.annotation:annotation:1.8.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Glide
    //implementation("com.github.bumptech.glide:glide:4.16.0")


    // Room --> BD
    //implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    // Procesador de anotaciones (@)
    //annotationProcessor("androidx.room:room-compiler:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.6.1")


    // Google maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    // Google anuncios
    //implementation("com.google.android.gms:play-services-ads:23.1.0")
}

//fun room(function: () -> Unit) {}
