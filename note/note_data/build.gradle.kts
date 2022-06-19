apply {
    from("$rootDir/build_base.gradle")
}

dependencies {
    "implementation"(project(Modules.core))

    "implementation"(Coroutines.coroutines)

    "implementation"(Room.runtime)
    "annotationProcessor"(Room.compiler)
    "kapt"(Room.compiler)
    "implementation"(Room.ktx)
}