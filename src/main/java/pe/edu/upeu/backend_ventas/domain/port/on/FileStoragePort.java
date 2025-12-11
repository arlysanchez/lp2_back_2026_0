package pe.edu.upeu.backend_ventas.domain.port.on;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStoragePort {
    String save (MultipartFile file) throws IOException;
    void delete(String filename);
}
