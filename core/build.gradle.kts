apply {
    from("$rootDir/build_base.gradle")
}

dependencies {
    "implementation"(DataStore.preferencesDataStore)

    "implementation"(Coroutines.coroutines)

    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.converterMoshi)
}