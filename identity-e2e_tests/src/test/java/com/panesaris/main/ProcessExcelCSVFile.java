/**
 * Author : Inderpal Panesar
 * Date   : 03/2018
 * Description :
 *  This is the main class for processing Excel/CSV files
 */
package com.panesaris.main;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class ProcessExcelCSVFile
{

    private ScanForFilesService _scanforfilesservice;

    public ProcessExcelCSVFile()
    {
        _scanforfilesservice = new ScanForFilesService();
    }

    // Function fo read Excel/csv file extensions maybe of type xls or xlsx and returns 2D array of the data
    public String[][] ReadExcelCSVFile(String p_DirPath, String p_ExcelFile, String p_SheetName )
    {
        String[] extensions = new String[]{"xls", "xlsx", "csv"};
        String[][] dataArray = null;
        try
        {
            HashMap<String, File> fileMap = _scanforfilesservice.GetFilesByExtensionFromTheDirectory(p_DirPath, extensions);

            // Check the file map is not empty
            if (fileMap.isEmpty())
            {
                System.out.println(String.format("No excel, (.xls, .xlsx, .csv), files located in directory %s", p_DirPath));
                return null;
            }

            // Have some files, locate the file we want and process it
            File xlFile = null;
            for (String filename : fileMap.keySet())
            {
                if (filename.equals(p_ExcelFile))
                {
                    xlFile = fileMap.get(filename);
                }
            }

            if (xlFile == null)
            {
                System.out.println(String.format("File %s was not located in directory %s", p_ExcelFile, p_DirPath));
                return null;
            }

            // Excel file located now process it
            FileInputStream fs_Input = new FileInputStream(xlFile);
            Workbook workbook = WorkbookFactory.create(fs_Input);

            // Read the data sheet inside the workbook by its name
            Sheet dataSheet = workbook.getSheet(p_SheetName);

            // Locate the number of data rows on sheet, row zero as header
            int dataRow = 0;

            int totalRows = dataSheet.getLastRowNum();
            int totalCols = dataSheet.getRow(0).getLastCellNum();

            // initialize the dataArray
            dataArray = new String[totalRows][totalCols];

            // Use a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            // Go through the rows and extract the cell data, start at row 1, 0 assume(?) header
            for (int currRow = 1; currRow < totalRows; currRow++, dataRow++)
            {
                // Start at column 0 for this row
                for (int dataCol = 0; dataCol < totalCols; dataCol++)
                {
                    Cell dataCell = dataSheet.getRow(currRow).getCell(dataCol);
                    dataArray[dataRow][dataCol] = dataFormatter.formatCellValue(dataCell);
                    System.out.println(String.format("Cell value at (%d, %d) = %s", dataRow, dataCol, dataArray[dataRow][dataCol]));
                }
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        }

        catch(IOException e)
        {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        }

        catch(Exception e)
        {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        }

        return(dataArray);
    }
}
