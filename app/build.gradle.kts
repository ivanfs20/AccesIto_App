plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt") // Habilita kapt para el procesamiento de anotaciones
}

android {
    namespace = "com.example.gui"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gui"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.protolite.well.known.types)
    implementation(libs.androidx.room.runtime.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Otras dependencias (corrigiendo sintaxis)
    implementation("androidx.room:room-runtime:2.5.0") // Dependencia principal de Room
    kapt("androidx.room:room-compiler:2.5.0") // Para el procesamiento de anotaciones

    // Si usas Room con Kotlin, también puedes agregar esta dependencia
    implementation("androidx.room:room-ktx:2.5.0")

    // Dependencia para pruebas unitarias con Room (opcional)
    testImplementation("androidx.room:room-testing:2.5.0")
// https://mvnrepository.com/artifact/org.postgresql/postgresql

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0") // o la versión más reciente

    // Room y Room KTX
    implementation("androidx.room:room-ktx:2.4.2") // Asegúrate de tener room-ktx

    //dependencias para los bits
    implementation ("com.google.zxing:core:3.5.1")
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")

    //dependencia para los pdf
    implementation ("com.itextpdf:itext7-core:7.2.5")  // iText 7, la versión más reciente

}
