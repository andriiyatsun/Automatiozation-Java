package ua.edu.ukma;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

@Mojo(name = "combine", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class CombineSourcesMojo extends AbstractMojo {

    // отримуємо шлях до папки з вихідним кодом проєкту, де викликано плагін
    @Parameter(defaultValue = "${project.build.sourceDirectory}", property = "sourceDir", required = true)
    private File sourceDirectory;

    // отримуємо шлях до папки target, куди збережемо результат
    @Parameter(defaultValue = "${project.build.directory}/combined-code.txt", property = "outputFile", required = true)
    private File outputFile;

    @Override
    public void execute() throws MojoExecutionException {
        if (!sourceDirectory.exists()) {
            getLog().warn("Директорія з кодом не знайдена: " + sourceDirectory.getAbsolutePath());
            return;
        }

        try {
            outputFile.getParentFile().mkdirs();
            Files.deleteIfExists(outputFile.toPath());
            outputFile.createNewFile();

            getLog().info("Збираємо весь Java код у файл: " + outputFile.getAbsolutePath());

            // проходимо по всіх .java файлах у директорії
            try (Stream<Path> paths = Files.walk(sourceDirectory.toPath())) {

                paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".java"))
                        .forEach(this::appendFileContent);
            }

            getLog().info("Код успішно зібрано!");
        } catch (IOException e) {
            throw new MojoExecutionException("Помилка при злитті файлів", e);
        }
    }

    private void appendFileContent(Path javaFile) {
        try {
            //роздільник із назвою файлу
            String header = "\n\n// ===== FILE: " + javaFile.getFileName() + " =====\n";
            Files.write(outputFile.toPath(), header.getBytes(), StandardOpenOption.APPEND);

            // код
            Files.write(outputFile.toPath(), Files.readAllBytes(javaFile), StandardOpenOption.APPEND);
        } catch (IOException e) {
            getLog().error("Не вдалося прочитати файл: " + javaFile, e);
        }
    }
}