plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

// ---------------------------------------------------------------------------
// Version — driven by CI env vars; falls back to sensible local defaults.
// In the release workflow, VERSION_NAME and VERSION_CODE are injected from
// the latest git tag (e.g. tag v1.2.3 → versionName=1.2.3, versionCode=10203)
// ---------------------------------------------------------------------------
val ciVersionName: String = System.getenv("VERSION_NAME") ?: "1.0.0"
val ciVersionCode: Int    = System.getenv("VERSION_CODE")?.toIntOrNull() ?: 10000

android {
    namespace = "com.sbz.devfolio"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.sbz.devfolio"
        minSdk = 24
        targetSdk = 36
        versionCode = ciVersionCode
        versionName = ciVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // ---------------------------------------------------------------------------
    // Signing — reads 4 env vars injected by the GitHub Actions release workflow.
    // Locally the storeFile env var will be absent, so the block is skipped and
    // the debug key is used automatically for assembleDebug.
    // ---------------------------------------------------------------------------
    val keystorePath = System.getenv("KEYSTORE_PATH")
    if (keystorePath != null) {
        signingConfigs {
            create("release") {
                storeFile     = file(keystorePath)
                storePassword = System.getenv("STORE_PASSWORD") ?: ""
                keyAlias      = System.getenv("KEY_ALIAS")      ?: ""
                keyPassword   = System.getenv("KEY_PASSWORD")   ?: ""
            }
        }
    }

    buildTypes {
        release {
            // Enable R8 code shrinking, obfuscation, and resource shrinking
            isMinifyEnabled    = true
            isShrinkResources  = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Wire signing config when running in CI
            val keystorePathForRelease = System.getenv("KEYSTORE_PATH")
            if (keystorePathForRelease != null) {
                signingConfig = signingConfigs.getByName("release")
            }
        }

        debug {
            // Keep debug fast — no shrinking
            isMinifyEnabled   = false
            isShrinkResources = false
            applicationIdSuffix  = ".debug"
            versionNameSuffix    = "-debug"
        }
    }

    // ---------------------------------------------------------------------------
    // Lint — generate XML + HTML reports so CI can upload them as artifacts
    // ---------------------------------------------------------------------------
    lint {
        abortOnError   = true
        xmlReport      = true
        htmlReport     = true
        xmlOutput      = file("reports/lint-results.xml")
        htmlOutput     = file("reports/lint-results.html")
        // Baseline keeps noisy pre-existing issues from blocking new PRs
        baseline       = file("lint-baseline.xml")
        // Ignore generated code, tests, and pre-compose compatibility checks
        checkTestSources       = false
        ignoreTestSources      = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Unit testing
    testImplementation(libs.junit)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}