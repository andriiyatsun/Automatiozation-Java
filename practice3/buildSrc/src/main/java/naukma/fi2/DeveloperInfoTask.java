package naukma.fi2;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public abstract class DeveloperInfoTask extends DefaultTask {

    @TaskAction
    public void run() {
        System.out.println("=================================");
        System.out.println("Проєкт: " + getProject().getName());
        System.out.println("Розробник: Andrii Yatsun");
        System.out.println("Група: fi2");
        System.out.println("=================================");
    }
}