/**
 * Author : Inderpal Panesar
 * Date   : 03/2018
 * Description :
 *  This is the main class for the scan for files service
 */

package com.panesaris.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.HashMap;

public class ScanForFilesService
{

	/*public static void main(String[] args) {
		ScanForFilesService test = new ScanForFilesService();
		try {
			test.readExcel("C:\\identityTestData","vehicleTestdata.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

    // Service function to scan directory for files and retrieve information about each file found - Filename, extension, mime type and size
    // returns a Map of file name and FileMetadata type
    public HashMap <String, FileMetadata> ScanDirectoryForFiles(String p_DirToScan) throws IOException
    {
        File directory = new File(p_DirToScan);
        // Check the item given is a directory
        if (!directory.isDirectory())
        {
            throw new NotDirectoryException(p_DirToScan + " - Not a valid directory.");
        }

        // Instance of the hash map
        HashMap<String, FileMetadata> fileDetailsMap = new HashMap<String, FileMetadata>();

        // Iterate through the items located in the directory and if its a file get it
        // and populate the metadata
        for (File fileEntry : directory.listFiles())
        {
            if (fileEntry.isFile())
            {
                FileMetadata fmd = new FileMetadata(fileEntry);
                fileDetailsMap.put(fileEntry.getName(), fmd);
            }
            else
            {
                // Item located is a directory
                System.out.println(String.format("Ignoring directory : %s", fileEntry.getName()));
            }
        }

        return fileDetailsMap;
    }

    // Service function to retrieve files for given fileExtension in a given directory
    // This service function makes use of the ScanDirectoryForFiles to get all files in the
    // given directory and filter for the required extension
    public HashMap<String, File> GetFilesByExtensionFromTheDirectory(String p_DirToScan, String[] p_Extensions) throws IOException
    {
        HashMap<String, File> fileMap = new HashMap<String, File>();
        HashMap <String, FileMetadata> fileDetailsMap = ScanDirectoryForFiles(p_DirToScan);

        // Loop through all file metadata to retrieve the files extensions required
        for (FileMetadata fileMetadata : fileDetailsMap.values())
        {
            // Select the file if the extension type matches one of the given types
            for (String extension : p_Extensions)
            {
                if (fileMetadata.GetFileExtension().toLowerCase().equals(extension.toLowerCase()))
                {
                    File file = new File(fileMetadata.GetFilePath().toString());
                    fileMap.put(fileMetadata.GetFileName(), file);
                }
            }
        }

        return fileMap;
    }
}
