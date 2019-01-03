package com.example.demo.controller;


import com.example.demo.model.Photo;
import com.example.demo.model.PhotoRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController

public class PhotoController {

    @Autowired
    PhotoRepository photoRepository;

    @GetMapping("/photos")
    public List<Photo> getAllPhotes() {
        return photoRepository.findAll();
    }

    @PostMapping("/photos")
    public void createPhoto(@Valid @RequestParam Photo photo){
        photoRepository.save(photo);
    }

    @PostMapping(value = "/photo/upload")
    public ResponseEntity<?> uploadAttachment(@RequestParam("file")MultipartFile sourceFile , @RequestParam("title") String title ) throws IOException {
        String sourceFileName = sourceFile.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        File destinationFile;
        String destinationFileName;

        do{
            destinationFileName = RandomStringUtils.randomAlphanumeric(32)+"."+sourceFileNameExtension;
            destinationFile = new File("/Users/minjun/Desktop/spring/Photo/upload_pic/"+destinationFileName);
        } while(destinationFile.exists());
        destinationFile.getParentFile().mkdirs();
        sourceFile.transferTo(destinationFile);
        Photo response = new Photo();
        response.setUrl(destinationFileName);
        title= title.substring(1,title.length()-1);
        response.setTitle(title);
        photoRepository.save(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/photo/showPhoto/{filename}")
    private void fileDownload(HttpServletResponse response , @PathVariable(value = "filename") String filename)throws IOException {
        byte[] fileByte = FileUtils.readFileToByteArray(new File("/Users/minjun/Desktop/spring/Photo/upload_pic/"+filename));
        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition", "attachment; FileName=\"" + URLEncoder.encode(filename, "UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding",  "binary");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @GetMapping("/photo/showPicturesByName")
    public List<Photo> showPicturesByName(){
        List<Photo> photos =  photoRepository.findAll();

        Collections.sort(photos, new Comparator<Photo>() {
            @Override
            public int compare(Photo o1, Photo o2) {
                int compare_title = o1.getTitle().compareTo(o2.getTitle());
                if (compare_title < 0 ){
                    return -1;
                } else if (compare_title > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        return photos;
    }

    @GetMapping("/photo/showPicturesByTime")
    public List<Photo> showPicturesByTime(){
        List<Photo> photos =  photoRepository.findAll();

        Collections.sort(photos, new Comparator<Photo>() {
            @Override
            public int compare(Photo o1, Photo o2) {
                if (o1.getCreatedAt().before(o2.getCreatedAt())) {
                    return -1;
                } else if (o1.getCreatedAt().after(o2.getCreatedAt())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        return photos;
    }

    // Get a Single Note
//    @GetMapping("/notes/{id}")
//    public Photo getNoteById(@PathVariable(value = "id") Long photoId) {  //이렇게 하거나 파라미터를 (@PathVariable String var) 이런식으로 해도 되는데 이렇게 할때는 GetMapping에서 { } 안에값과 동일하게 해야된다.
//        //PathVariable은 경로에 변수를 넣어주고 활용할때 자주 사용한다.
//        return photoRepository.findById(photoId)
//                .orElseThrow(() -> new ResourceNotFoundException("Photo", "id", photoId));
//    }
}
