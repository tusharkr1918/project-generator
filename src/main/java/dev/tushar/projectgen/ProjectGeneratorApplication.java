package dev.tushar.projectgen;

import dev.tushar.projectgen.cli.ProjectGeneratorCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@RequiredArgsConstructor
@SpringBootApplication
public class ProjectGeneratorApplication implements CommandLineRunner {

    private final ProjectGeneratorCommand projectGeneratorCommand;

    public static void main(String[] args) {
        SpringApplication.run(ProjectGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        new CommandLine(projectGeneratorCommand).execute(args);
    }
}