package com.example.liuqianli.controller;

import com.example.liuqianli.common.Response;
import com.example.liuqianli.dto.FileStorageServiceRequestDTO;
import com.example.liuqianli.service.FileStorageService;
import com.example.liuqianli.service.FileStorageService.FileDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Tool:IntelliJ IDEA
 * Description:
 * Date：2024-11-22-15:06
 *
 * @ Author:两袖青蛇
 */
@RestController
@RequestMapping("/ext")
@Api(value = "FileController", tags = {"文件操作接口"})
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @ApiOperation(value = "GetList接口", notes = "用于获取指定GroupID下有多少文件")
    @PostMapping("/File/GetList")
    public Response getFileList(@RequestBody FileStorageServiceRequestDTO request) {
        List<FileDetails> fileList = fileStorageService.getFileList(request.getGroupId());
        return Response.<List<FileDetails>>builder()
                .code("Success")
                .isOk("true")
                .message("")
                .msg("")
                .data(fileList)
                .build();
    }

    @ApiOperation(value = "Upload上传文件接口", notes = "接收一个文件并保存到服务器")
    @PostMapping("/File/Upload")
    public Response uploadFile(@RequestParam String groupId, @RequestParam("file") MultipartFile file,@RequestParam String filename) {
        if (file.getSize() > 1024 * 1024) { // 1MB limit
            return  Response.builder()
                    .code("Error")
                    .isOk("false")
                    .message("文件大小超过1MB")
                    .msg("文件大小超过1MB")
                    .data(null)
                    .build();
        }

        try {
            fileStorageService.saveFile(groupId, filename, file);
        } catch (Exception e) {
            return Response.builder()
                    .code("Error")
                    .isOk("false")
                    .message(e.toString())
                    .msg(e.toString())
                    .data(null)
                    .build();

        }

        List<FileDetails> updatedFileList = fileStorageService.getFileList(groupId);
        return Response.<List<FileDetails>>builder()
                .code("Success")
                .isOk("true")
                .message("")
                .msg("")
                .data(updatedFileList)
                .build();
    }

    @ApiOperation(value = "Remove接口", notes = "用于删除指定GroupID下的指定文件")
    @DeleteMapping("/File/Remove")
    public Response deleteFile(@RequestBody FileStorageServiceRequestDTO fileDetails) {
        try {
            fileStorageService.deleteFile(fileDetails.getGroupId(), fileDetails.getFilename());
        } catch (RuntimeException e) {
            return Response.builder()
                    .code("Error")
                    .isOk("false")
                    .message(e.toString())
                    .msg(e.toString())
                    .data(null)
                    .build();
        }

        List<FileDetails> updatedFileList = fileStorageService.getFileList(fileDetails.getFilename().split("\\.")[0]);
        return Response.<List<FileDetails>>builder()
                .code("Success")
                .isOk("true")
                .message("")
                .msg("")
                .data(updatedFileList)
                .build();
    }
}
