apply {
    from("$rootDir/build_base.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.accountData))

    "implementation"(Coroutines.coroutines)
}