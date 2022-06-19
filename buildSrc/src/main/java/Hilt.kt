object Hilt {
    private const val hiltVersion = "2.40.5"
    private const val lifecycleVMVersion = "1.0.0-alpha03"
    private const val compilerVersion = "1.0.0"

    const val android = "com.google.dagger:hilt-android:$hiltVersion"
    const val androidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
    const val lifecycleVM = "androidx.hilt:hilt-lifecycle-viewmodel:$lifecycleVMVersion"
    const val compiler = "androidx.hilt:hilt-compiler:$compilerVersion"
    const val androidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
}