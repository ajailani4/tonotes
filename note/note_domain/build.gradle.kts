apply {
    from("$rootDir/build_base.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.noteData))

    "implementation"(Coroutines.coroutines)

    "implementation"(WorkManager.workRuntime)

    "implementation"(Hilt.hiltWork)
    "implementation"(Hilt.hiltCompiler)
}