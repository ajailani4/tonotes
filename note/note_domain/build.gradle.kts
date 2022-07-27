apply {
    from("$rootDir/build_base.gradle")
}

dependencies {
    "implementation"(project(Modules.core))

    "implementation"(Coroutines.coroutines)
}