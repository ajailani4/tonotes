apply {
    from("$rootDir/build_compose.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.accountDomain))
}