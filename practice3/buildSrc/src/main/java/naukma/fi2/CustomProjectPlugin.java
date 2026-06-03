package naukma.fi2;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CustomProjectPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // Реєструємо наші задачі
        project.getTasks().register("printDevInfo", DeveloperInfoTask.class);
        project.getTasks().register("createManifest", GenerateManifestTask.class);
    }
}