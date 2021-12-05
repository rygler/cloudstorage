package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/home/file")
public class FileController {
    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping
    public String postFile(Authentication authentication, FileForm fileForm, Model model) {
        if (fileForm.getFile().isEmpty()) {
            //todo: add description
            model.addAttribute("error", true);
            model.addAttribute("message", "No file selected!");
            return "result";
        }

        int userId = userService.getUserId(authentication.getName());

        if (!fileService.isFileNameAvailable(fileForm.getFile().getOriginalFilename(), userId)) {
            //todo: add description
            model.addAttribute("error", true);
            model.addAttribute("message", "A file with this name already exists!");
            return "result";
        }

        fileForm.setUserId(userId);

        try {
            fileService.createFile(fileForm);
            System.out.println("file created");
            model.addAttribute("success", true);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", true);
            model.addAttribute("message", "Oops. Something went wrong on our end. Try again later.");
        }

        return "result";
    }

    @GetMapping("/delete")
    public String deleteFile(int fileId, Model model) {
        fileService.deleteFile(fileId);
        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("/view")
    public ResponseEntity<Resource> getFile(int fileId) {
        UserFile file = fileService.getFileByFileId(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Integer.parseInt(file.getFileSize()))
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(resource);
    }


        private static final String EXTERNAL_FILE_PATH = "C:/fileDownloadExample/";

//        @RequestMapping("/view/{fileId:.+}")
//        public void downloadResource(HttpServletRequest request, HttpServletResponse response,
//                                     @PathVariable("fileId") int fileId) throws IOException {
//
//            File file = new File(EXTERNAL_FILE_PATH + fileName);
//            if (file.exists()) {
//
//                //get the mimetype
//                String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//                if (mimeType == null) {
//                    //unknown mimetype so set the mimetype to application/octet-stream
//                    mimeType = "application/octet-stream";
//                }
//
//                response.setContentType(mimeType);
//
//                /**
//                 * In a regular HTTP response, the Content-Disposition response header is a
//                 * header indicating if the content is expected to be displayed inline in the
//                 * browser, that is, as a Web page or as part of a Web page, or as an
//                 * attachment, that is downloaded and saved locally.
//                 *
//                 */
//
//                /**
//                 * Here we have mentioned it to show inline
//                 */
//                response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
//
//                //Here we have mentioned it to show as attachment
//                //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
//
//                response.setContentLength((int) file.length());
//
//                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//
//                FileCopyUtils.copy(inputStream, response.getOutputStream());
//
//            }
//        }

}
