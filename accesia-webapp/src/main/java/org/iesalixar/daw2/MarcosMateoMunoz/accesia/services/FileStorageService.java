package org.iesalixar.daw2.MarcosMateoMunoz.accesia.services;

import jakarta.annotation.PostConstruct;
import org.iesalixar.daw2.MarcosMateoMunoz.accesia.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

/**
 * Guarda y borra las imágenes subidas en la carpeta configurada en app.upload-dir.
 */
@Service
public class FileStorageService {

    /** Tamaño máximo permitido por imagen: 2 MB. */
    public static final long MAX_SIZE_BYTES = 2L * 1024 * 1024;

    private static final Map<String, String> EXTENSIONS = Map.of(
            "image/jpeg", "jpg",
            "image/png", "png",
            "image/gif", "gif",
            "image/webp", "webp");

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    private Path root;

    @PostConstruct
    void init() {
        root = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new FileStorageException("No se pudo crear la carpeta de subidas: " + root, e);
        }
    }

    public boolean isAllowedType(String contentType) {
        return contentType != null && EXTENSIONS.containsKey(contentType.toLowerCase());
    }

    /** Guarda la imagen con un nombre aleatorio y devuelve ese nombre. */
    public String store(MultipartFile file) {
        String extension = EXTENSIONS.get(file.getContentType().toLowerCase());
        String filename = UUID.randomUUID() + "." + extension;
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new FileStorageException("No se pudo guardar la imagen", e);
        }
    }

    /** Borra la imagen si existe (no falla si el nombre es null o el fichero no está). */
    public void delete(String filename) {
        if (filename == null || filename.isBlank()) {
            return;
        }
        Path target = root.resolve(filename).normalize();
        if (!target.startsWith(root)) {
            return;
        }
        try {
            Files.deleteIfExists(target);
        } catch (IOException e) {
            throw new FileStorageException("No se pudo borrar la imagen", e);
        }
    }
}
