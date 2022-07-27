apply {
    from("$rootDir/build_base.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.noteDomain))

    "implementation"(Coroutines.coroutines)

    "implementation"(WorkManager.workRuntime)

    "implementation"(Hilt.hiltWork)
    "implementation"(Hilt.hiltCompiler)

    "implementation"(Room.runtime)
    "annotationProcessor"(Room.compiler)
    "kapt"(Room.compiler)
    "implementation"(Room.ktx)

    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.converterMoshi)
}