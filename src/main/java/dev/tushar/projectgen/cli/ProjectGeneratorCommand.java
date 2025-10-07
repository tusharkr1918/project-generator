package dev.tushar.projectgen.cli;

import dev.tushar.projectgen.exception.ProjectGenerationException;
import dev.tushar.projectgen.service.DownloaderService;
import dev.tushar.projectgen.service.UnzipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

@Command(
    name = "generate",
    mixinStandardHelpOptions = true,
    description = "Generates a new Spring Boot project."
)
@Component
@RequiredArgsConstructor
public class ProjectGeneratorCommand implements Runnable {

    private final DownloaderService downloaderService;
    private final UnzipService unzipService;

    @Option(
            names = {"-o", "--outputDir"},
            defaultValue = ".",
            description = "The directory to save the final project directory."
    )
    private String outputDir;

    @Mixin
    private final ProjectOptions projectOptions = new ProjectOptions();

    @Mixin
    private final FeatureOptions featureOptions = new FeatureOptions();

    @Override
    public void run() {
        try {
            String requestBody = buildRequestBody();
            byte[] zipContent = downloaderService.downloadProjectZip(requestBody);

            Path destDir = Paths.get(outputDir).resolve(projectOptions.artifactId);
            unzipService.inMemoryUnzip(zipContent, destDir);

            // -- Add features
            if (featureOptions.enableJwt) {
                System.out.println("Adding JWT feature...");
                // TODO: Call the new TemplateService
            }

            System.out.println("Project created successfully at: " + destDir.toAbsolutePath());
        } catch (ProjectGenerationException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private String buildRequestBody() {
        StringJoiner joiner = new StringJoiner("&")
                .add("groupId=" + encode(projectOptions.groupId))
                .add("artifactId=" + encode(projectOptions.artifactId))
                .add("name=" + encode(projectOptions.name))
                .add("description=" + encode(projectOptions.description))
                .add("packageName=" + encode(projectOptions.packageName))
                .add("packaging=" + encode(projectOptions.packaging))
                .add("bootVersion=" + encode(projectOptions.bootVersion))
                .add("javaVersion=" + encode(projectOptions.javaVersion))
                .add("build=" + encode(projectOptions.build))
                .add("language=" + encode(projectOptions.language))
                .add("dependencies=" + encode(projectOptions.dependencies));
        return joiner.toString();
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}