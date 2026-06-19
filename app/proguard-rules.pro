# =============================================================================
# DevFolio — ProGuard / R8 Rules
# =============================================================================
# R8 in full-mode is the default when proguard-android-optimize.txt is used.
# This file layers project-specific keeps on top of that baseline.
# =============================================================================


# ---------------------------------------------------------------------------
# 1. GENERAL — Preserve debugging info in stack traces
# ---------------------------------------------------------------------------

# Keep source file names and line numbers for crash reports (e.g. Firebase Crashlytics)
-keepattributes SourceFile,LineNumberTable

# Rename the SourceFile attribute so obfuscated stack traces still map correctly
-renamesourcefileattribute SourceFile

# Keep generic signatures (needed by Retrofit + Gson for type tokens)
-keepattributes Signature

# Keep checked exceptions, annotations, and enclosing methods (Compose internals need them)
-keepattributes Exceptions,InnerClasses,EnclosingMethod,*Annotation*


# ---------------------------------------------------------------------------
# 2. KOTLIN
# ---------------------------------------------------------------------------

# Kotlin metadata required at runtime for reflection-free serialisation libs
-keep class kotlin.Metadata { *; }

# Kotlin coroutines — keep debug-agent and internal dispatcher
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Kotlin companion objects and object declarations
-keepclassmembers class * {
    @kotlin.jvm.JvmStatic *;
    @kotlin.jvm.JvmField *;
}


# ---------------------------------------------------------------------------
# 3. RETROFIT 2
# ---------------------------------------------------------------------------

# Keep the Retrofit interface and its annotations
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Retrofit uses reflection to invoke interface methods; keep all declared methods
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Retain generic type info on Call<T> so Retrofit can resolve the response type
-keepattributes Signature
-keep class retrofit2.** { *; }
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}


# ---------------------------------------------------------------------------
# 4. GSON (JSON serialisation / deserialisation)
# ---------------------------------------------------------------------------

# Gson uses reflection to access fields of data classes passed to TypeToken<T>
-keepattributes Signature
-keepattributes *Annotation*

# Keep Gson core classes
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }

# Keep all data model classes used as Gson type tokens (PortfolioResponse and friends)
-keep class com.sbz.devfolio.core.network.model.** { *; }

# Suppress warnings about missing Gson internal classes on older platforms
-dontwarn com.google.gson.**


# ---------------------------------------------------------------------------
# 5. OKHTTP (Retrofit's HTTP client)
# ---------------------------------------------------------------------------

-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class okio.** { *; }

# OkHttp's internal platform detection uses reflection
-keep class okhttp3.internal.platform.** { *; }
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**


# ---------------------------------------------------------------------------
# 6. COIL (Image loading)
# ---------------------------------------------------------------------------

-keep class coil.** { *; }
-dontwarn coil.**


# ---------------------------------------------------------------------------
# 7. JETPACK COMPOSE
# ---------------------------------------------------------------------------

# Compose relies heavily on Kotlin compiler plugins; R8 handles most of it
# automatically. These rules guard the edge cases.

# Keep Compose runtime internals used by the slot table / recomposition machinery
-keep class androidx.compose.runtime.** { *; }

# Compose UI and graphics
-keep class androidx.compose.ui.** { *; }

# Keep @Composable lambdas from being fully inlined away (important for previews
# in libraries, harmless in app modules)
-keepclasseswithmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# Material 3 — keep ripple and theming internals
-keep class androidx.compose.material3.** { *; }

# Navigation Compose — keep NavBackStackEntry serialisation
-keep class androidx.navigation.** { *; }
-keepnames class * implements androidx.navigation.NavArgs


# ---------------------------------------------------------------------------
# 8. ANDROID JETPACK (ViewModel, Lifecycle, Activity)
# ---------------------------------------------------------------------------

# ViewModels instantiated via reflection by ViewModelProvider
-keep class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
-keep class * extends androidx.lifecycle.AndroidViewModel {
    <init>(android.app.Application);
}

# Lifecycle observer callbacks
-keep class * implements androidx.lifecycle.LifecycleObserver {
    <methods>;
}

# Keep SavedStateHandle keys (if used in future)
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(androidx.lifecycle.SavedStateHandle);
}

# Activity/Fragment entry points
-keep class * extends android.app.Activity
-keep class * extends android.app.Application
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver


# ---------------------------------------------------------------------------
# 9. APP-SPECIFIC KEEPS
# ---------------------------------------------------------------------------

# Keep Application class (entry point for AppContainer DI)
-keep class com.sbz.devfolio.DevFolioApplication { *; }

# Keep DI container (accessed via reflection-style cast in screens)
-keep class com.sbz.devfolio.di.** { *; }

# Keep all UseCase, Repository, and ViewModel classes (named in ViewModelProvider factories)
-keep class com.sbz.devfolio.core.domain.** { *; }
-keep class com.sbz.devfolio.core.network.repository.** { *; }
-keep class com.sbz.devfolio.features.**.** extends androidx.lifecycle.ViewModel { *; }


# ---------------------------------------------------------------------------
# 10. SUPPRESS COMMON HARMLESS WARNINGS
# ---------------------------------------------------------------------------

-dontwarn javax.annotation.**
-dontwarn sun.misc.**
-dontwarn java.lang.invoke.**
-dontwarn org.codehaus.**
