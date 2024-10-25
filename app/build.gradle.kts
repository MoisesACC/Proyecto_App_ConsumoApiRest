plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.app.consumoapirest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.consumoapirest"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    dependencies {
        // Dependencias existentes
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)

        // Retrofit para manejar la comunicación con el servicio REST
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")

        // Converter Gson para convertir JSON a objetos Java
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

        // (Opcional) OkHttp para registro de logs de las solicitudes HTTP
        implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    }


}