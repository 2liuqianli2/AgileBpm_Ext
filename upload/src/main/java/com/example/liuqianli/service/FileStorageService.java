package com.example.liuqianli.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Tool:IntelliJ IDEA
 * Description:
 * Date：2024-11-22-15:13
 *
 * @ Author:两袖青蛇
 */
@Service
public class FileStorageService {

    @Value("${file.storage.dir}")
    private String storageDir;

    public List<FileDetails> getFileList(String groupId) {
        Path groupPath = Paths.get(storageDir, groupId);
        if (!Files.isDirectory(groupPath)) {
            return new ArrayList<>();
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(groupPath)) {
            // 将 DirectoryStream 转换为 Stream<Path>
            Stream<Path> pathStream = StreamSupport.stream(stream.spliterator(), false);
            return pathStream.map(path -> {
                File file = path.toFile();
                String  names = file.getName();
                String[] split = names.split("\\.");
                return new FileDetails(split[0], getFileExtension(file.getName()), file.length());
            }).collect(Collectors.toList());
        } catch (IOException | DirectoryIteratorException e) {
            throw new RuntimeException("Failed to get file list for groupId: " + groupId, e);
        }
    }

    public void saveFile(String groupId, String filename, MultipartFile file) throws IOException {
        Path groupPath = Paths.get(storageDir, groupId);
        if (Files.notExists(groupPath)) {
            Files.createDirectories(groupPath);
        }

        // 构建文件的完整路径
        Path filePath = Paths.get(storageDir).resolve(groupId).resolve(filename);

        if (Files.exists(filePath)) {
            throw new RuntimeException("File already exists: " + filename);
        }

        // 将文件保存到指定路径
        file.transferTo(filePath.toFile());
    }

    public void deleteFile(String groupId, String filename) {
        Path filePath = Paths.get(storageDir, groupId, filename);
        if (Files.notExists(filePath)) {
            throw new RuntimeException("File not found: " + filename);
        }

        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + filename, e);
        }
    }

    private String getFileExtension(String filename) {
        String[] parts = filename.split("\\.");
        return parts.length > 1 ? parts[parts.length - 1] : "";
    }
    @Data
    public static class FileDetails {
        private String filename;
        private String ext;
        private long size;

        public FileDetails(String filename, String ext, long size) {
            this.filename = filename;
            this.ext = ext;
            this.size = size;
        }
    }
}
