package naukma.fi2;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class GenerateManifestTask extends DefaultTask {
    @TaskAction
    public void run() {
        File manifestDir = new File(getProject().getLayout().getBuildDirectory().getAsFile().get(), "custom");
        manifestDir.mkdirs();
        File file = new File(manifestDir, "build-info.txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Build triggered successfully.");
            System.out.println("Manifest generated at: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}