package com.example.liuqianli.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Tool:IntelliJ IDEA
 * Description:
 * Date：2024-11-22-18:07
 *
 * @ Author:两袖青蛇
 */
@Data
public class FileStorageServiceRequestDTO {

    String groupId;

    MultipartFile file;

    String filename;
}
