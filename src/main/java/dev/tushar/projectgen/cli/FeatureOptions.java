package dev.tushar.projectgen.cli;

import picocli.CommandLine.Option;

public class FeatureOptions {

    @Option(
            names = "--with-jwt",
            description = "Add a basic JWT implementation to the project."
    )
    public boolean enableJwt;

}