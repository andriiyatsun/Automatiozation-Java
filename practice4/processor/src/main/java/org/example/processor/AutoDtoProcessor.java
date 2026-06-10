package org.example.processor;

import org.example.annotations.AutoDto;
import org.example.annotations.NotNull;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("org.example.annotations.AutoDto")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class AutoDtoProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) return false;

        for (Element element : roundEnv.getElementsAnnotatedWith(AutoDto.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                String className = typeElement.getSimpleName().toString();
                String packageName = processingEnv
                        .getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();

                String dtoClassName = className + "Dto";

                try {
                    JavaFileObject builderFile = processingEnv.getFiler()
                            .createSourceFile(packageName + "." + dtoClassName);

                    try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
                        out.println("package " + packageName + ";");
                        out.println("import org.example.annotations.NotNull;");
                        out.println("public class " + dtoClassName + " {");

                        for (Element enclosed : typeElement.getEnclosedElements()) {

                            if (enclosed.getKind() == ElementKind.FIELD) {
                                String fieldName = enclosed.getSimpleName().toString();
                                String fieldType = enclosed.asType().toString();

                                if (enclosed.getAnnotation(NotNull.class) != null) {
                                    out.println("    @NotNull");
                                }
                                out.println("    public " + fieldType + " " + fieldName + ";");
                            }
                        }
                        out.println("}");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}