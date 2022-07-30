object Hilt {
    private const val hiltAndroidVersion = "2.42"
    private const val hiltVersion = "1.0.0"

    const val android = "com.google.dagger:hilt-android:$hiltAndroidVersion"
    const val androidCompiler = "com.google.dagger:hilt-android-compiler:$hiltAndroidVersion"
    const val androidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltAndroidVersion"
    const val testing = "com.google.dagger:hilt-android-testing:$hiltAndroidVersion"
    const val hiltWork = "androidx.hilt:hilt-work:$hiltVersion"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:$hiltVersion"
}