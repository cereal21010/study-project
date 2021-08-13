package com.example.v2_board.service;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.dto.FileDTO;
import com.example.v2_board.mapper.FileMapper;
import com.example.v2_board.utills.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

//    private final Path dirLocation;
    private final FileMapper fileMapper;

    /*@Autowired
    public FileService(FileUploadProperties fileUploadProperties) {
        this.dirLocation = Paths.get( fileUploadProperties.getLocation() )
                .toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init(){
        try{
            Files.createDirectories(this.dirLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public List<FileDTO> selectList(int boardSeq) throws Exception {
        return fileMapper.selectList(boardSeq);
    }

    public FileDTO getOne(int seq) throws Exception {
        return fileMapper.getOne(seq);
    }

    public void saveFile(List<MultipartFile> files, BoardDTO dto) throws Exception {
        List<FileDTO> fileInfos = new FileUtils().parseFileInfo(dto, files);
        for( FileDTO fileInfo : fileInfos ){
            fileMapper.insert(fileInfo);
        }
    }

    public void deleteOne(FileDTO dto) throws Exception {
        fileMapper.deleteOne(dto);
    }

    /*public String saveFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        Path location = this.dirLocation.resolve(fileName);
        try {
            *//*실제 파일이 upload 되는 부분*//*
            Files.copy( multipartFile.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }*/

    /*public Resource loadFile(String fileName) throws FileNotFoundException {
        try {
            Path file = this.dirLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if( resource.exists() || resource.isReadable() ) {
                return resource;
            }else {
                throw new FileNotFoundException("Could not find file");
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not download file");
        }
    }*/

}
