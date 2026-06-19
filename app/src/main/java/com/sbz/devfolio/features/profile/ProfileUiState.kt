package com.sbz.devfolio.features.profile

data class ProfileUiState(
    val name: String = "Shabaj Ansari",
    val title: String = "Android Software Engineer",
    val bio: String = "Android Software Engineer with 3 years of experience building scalable consumer applications, reward-based platforms, and Android SDKs using Kotlin and modern Android architecture. Experienced in developing high performance Android applications using MVVM, Coroutines, Retrofit, Firebase, and real-time communication technologies. Proven track record of building reward-driven user experiences, Android SDKs, and consumer applications serving millions of users.",
    val skills: List<String> = listOf("Kotlin", "Java", "Android SDK", "Jetpack Compose", "MVVM", "Clean Architecture", "Coroutines", "Flow", "Room", "Retrofit", "Firebase", "WebSockets", "AppLovin", "ironSource")
)
