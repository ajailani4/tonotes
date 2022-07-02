object AndroidX {
    private const val coreKtxVersion = "1.8.0"
    private const val lifecycleRuntimeVersion = "2.4.1"
    private const val splashScreenVersion = "1.0.0-alpha01"

    const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${lifecycleRuntimeVersion}"
    const val splashScreen = "androidx.core:core-splashscreen:$splashScreenVersion"
}

object AndroidXTest {
    private const val JUnitVersion = "1.1.3"
    private const val espressoCoreVersion = "3.4.0"
    private const val coreTestingVersion = "2.1.0"
    private const val testRunnerVersion = "1.4.0"

    const val JUnit = "androidx.test.ext:junit:$JUnitVersion"
    const val espressoCore = "androidx.test.espresso:espresso-core:$espressoCoreVersion"
    const val coreTesting = "androidx.arch.core:core-testing:$coreTestingVersion"
    const val testRunner = "androidx.test:runner:$testRunnerVersion"
}