apply {
    from("$rootDir/build_base.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))

    "implementation"(Coroutines.coroutines)

    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.converterMoshi)
}