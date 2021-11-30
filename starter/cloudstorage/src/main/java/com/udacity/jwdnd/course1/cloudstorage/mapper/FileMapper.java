package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer createFile(UserFile userFile);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    UserFile getFileByFileName(String fileName, Integer userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    UserFile getFileByFileId(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<UserFile> getFilesByUserId(Integer userId);

    @Update("UPDATE FILES SET filename = #{fileName}, filetype = #{fileType}, filesize = #{fileSize}, userid = #{userId}, filedata = #{fileData} WHERE fileid = #{fileId}")
    void updateFile(UserFile userFile);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    void deleteFileByFileId(Integer fileId);
}