plugins {
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

// register unique id
gradlePlugin {
    plugins {
        register("customProjectPlugin") {
            id = "naukma.fi2.customplugin"
            implementationClass = "naukma.fi2.CustomProjectPlugin"
        }
    }
}