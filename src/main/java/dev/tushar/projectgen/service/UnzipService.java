package dev.tushar.projectgen.service;

import dev.tushar.projectgen.exception.ProjectGenerationException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class UnzipService {

    public Path saveTempZipFile(String artifactId, byte[] content) {
        try {
            Path tempFile = Files.createTempFile(artifactId, ".zip");
            Files.write(tempFile, content);
            return tempFile;
        } catch (IOException e) {
            throw new ProjectGenerationException("Failed to save temporary zip file.", e);
        }
    }

    public void inMemoryUnzip(byte[] zipContent, Path destDir) {
        try (var zis = new ZipInputStream(new ByteArrayInputStream(zipContent))) {
            ZipEntry e;
            while ((e = zis.getNextEntry()) != null) {
                Path path = destDir.resolve(e.getName()).normalize();
                if (e.isDirectory())
                    Files.createDirectories(path);
                else {
                    Files.createDirectories(path.getParent());
                    zis.transferTo(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
                }
                zis.closeEntry();
            }
        } catch (IOException ex) {
            throw new ProjectGenerationException("Failed to unzip project (in-memory)", ex);
        }
    }

}