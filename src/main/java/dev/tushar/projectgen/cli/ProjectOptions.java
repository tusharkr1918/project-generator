package dev.tushar.projectgen.cli;

import picocli.CommandLine.Option;

public class ProjectOptions {

    @Option(names = "--groupId", defaultValue = "dev.tushar", description = "The groupId for the project.", order = 1)
    public String groupId;

    @Option(names = "--artifactId", defaultValue = "shortify", description = "The artifactId for the project.", order = 2)
    public String artifactId;

    @Option(names = "--name", defaultValue = "shortify", description = "The name of the project.", order = 3)
    public String name;

    @Option(names = "--description", defaultValue = "A cool URL shortener project.", description = "The project description.", order = 4)
    public String description;

    @Option(names = "--packageName", defaultValue = "dev.tushar.shortify", description = "The root package name.", order = 5)
    public String packageName;

    @Option(names = "--packaging", defaultValue = "jar", description = "The project packaging (jar or war).", order = 6)
    public String packaging;

    @Option(names = "--bootVersion", defaultValue = "3.5.0", description = "The Spring Boot version.", order = 7)
    public String bootVersion;

    @Option(names = "--javaVersion", defaultValue = "21", description = "The Java version.", order = 8)
    public String javaVersion;

    @Option(names = "--build", defaultValue = "maven", description = "The build tool (maven or gradle).", order = 9)
    public String build;

    @Option(names = "--language", defaultValue = "java", description = "The programming language.", order = 10)
    public String language;

    @Option(names = "--dependencies", defaultValue = "web,devtools,data-jpa,lombok,security", description = "Comma-separated list of dependencies.", order = 11)
    public String dependencies;
}