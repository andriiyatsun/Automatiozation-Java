plugins {
    java
    // connect plugin by ID
    id("naukma.fi2.customplugin")
}

group = "ua.edu.ukma"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// 1 task
tasks.register("cleanCustomFiles") {
    group = "custom plugin"
    description = "Видаляє кастомні файли, згенеровані плагіном"

    doLast {
        val customDir = layout.buildDirectory.dir("custom").get().asFile
        if (customDir.exists() && customDir.deleteRecursively()) {
            println("Директорію 'custom' успішно видалено.")
        } else {
            println("Немає файлів для видалення.")
        }
    }
}