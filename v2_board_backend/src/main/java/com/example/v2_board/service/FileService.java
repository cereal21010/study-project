package com.example.v2_board.service;

import com.example.v2_board.vo.BoardVO;
import com.example.v2_board.vo.FileVO;
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
    private final FileUtils fileUtils;

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

    public List<FileVO> selectList(int boardSeq) throws Exception {
        return fileMapper.selectList(boardSeq);
    }

    public FileVO getOne(int seq) throws Exception {
        return fileMapper.getOne(seq);
    }

    public void saveFile(List<MultipartFile> files, BoardVO vo) throws Exception {

        for( MultipartFile file : files ){
            if(isPermissionFileMimeType(file.getOriginalFilename())) {
                throw new Exception("잘못된 확장자 입니다.");
            }
        }

        List<FileVO> fileInfos = fileUtils.parseFileInfo(vo, files);
        for( FileVO fileInfo : fileInfos ) {
            fileMapper.insert(fileInfo);
        }
    }

    public void deleteOne(FileVO vo) throws Exception {
        fileMapper.deleteOne(vo);
    }



    private boolean isPermissionFileMimeType( String saveName ) throws Exception {

        final String[] PERMISSION_FILE_EXT_ARR = {"exe", "sh", "zip", "alz"};

        String ext = saveName.substring(saveName.lastIndexOf(".") + 1).toLowerCase();
        boolean isPermissionFileMimeType = false;

        for( int i = 0; i < PERMISSION_FILE_EXT_ARR.length; i++ ) {
            if( PERMISSION_FILE_EXT_ARR[i].equals(ext) ) {
                isPermissionFileMimeType = true;
                break;
            }
        }

        return isPermissionFileMimeType;

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
