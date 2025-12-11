package pe.edu.upeu.backend_ventas.infrastructure.adapter.storage;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.backend_ventas.domain.port.on.FileStoragePort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class LocalFileStorageAdapter implements FileStoragePort {
    private final Path rootLocation = Paths.get("images");

    public LocalFileStorageAdapter(){
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("no se pudo inicializar el almacenamiento de archivos",e);
        }
    }


    @Override
    public String save(MultipartFile file) throws IOException {
        if (file.isEmpty()){
            throw new IOException("error to save the file");
        }
        String originalFilename = file.getOriginalFilename();
        String extension ="";
        if (originalFilename !=null && originalFilename.contains(".")){
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFilename))
                .normalize().toAbsolutePath();
        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())){
            throw new IOException("No se puede guardar el archivo fuera del directorio actual");
        }
        Files.copy(file.getInputStream(),destinationFile);
        return uniqueFilename;
    }

    @Override
    public void delete(String filename) {
        if (filename == null || filename.isEmpty()){
            return;
        }
        try {
            Path file = rootLocation.resolve(filename);
            Files.deleteIfExists(file);
        }catch (IOException e){
            System.err.println("Error al eliminar el archivo"+filename+ " - " +e.getMessage());
        }

    }
}
