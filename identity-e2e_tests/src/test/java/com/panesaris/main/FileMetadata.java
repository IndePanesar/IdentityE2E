/**
 * Author : Inderpal Panesar
 * Date   : 03/2018
 * Description :
 *  This class just sets up file data.
 */

package com.panesaris.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.nio.file.Path;


public class FileMetadata
{
    private static Logger _logger = LoggerFactory.getLogger(FileMetadata.class);

    // File properties
    private String _fileName;
    private String _fileMimeType;
    private String _fileSize;
    private String _fileExtension;
    private Path _filePath;

    // Getter methods returning the file data
    public String GetFileName()
    {
        return _fileName;
    }

    public String GetFileMimeType()
    {
        return _fileMimeType;
    }

    public String GetFileSize()
    {
        return _fileSize;
    }

    public String GetFileExtension()
    {
        return _fileExtension;
    }

    public Path GetFilePath()
    {
        return _filePath;
    }

    // Constructor
    public FileMetadata(File p_File)
    {
        _fileName = p_File.getName();
        _fileMimeType = new MimetypesFileTypeMap().getContentType(p_File);
        _fileSize = String.format("%dKB", p_File.length()/1024 + 1);
        _fileExtension = "";
        if (_fileName.contains("."))
        {
            String[] exts = _fileName.split("\\.");
            _fileExtension = exts[exts.length - 1];
        }
        _filePath = p_File.toPath();

        _logger.info(String.format("File processed :- name = %s , extension = %s, mimetype = $s, size = %s, path = %s",
                _fileName, _fileExtension, _fileMimeType, _fileSize, _filePath.toString()));
    }
}
