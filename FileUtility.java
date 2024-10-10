import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileFinder {

    public static void main(String[] args) {
        String directoryPath = "your/directory/path"; // Replace with your directory path
        List<File> excelFiles = findExcelFiles(new File(directoryPath));

        // Print the found Excel files
        for (File file : excelFiles) {
            System.out.println("Found Excel File: " + file.getAbsolutePath());
        }
    }

    public static List<File> findExcelFiles(File directory) {
        List<File> excelFiles = new ArrayList<>();

        if (directory.isDirectory()) {
            // Get all files and directories in the current directory
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Recursively search in subdirectories
                        excelFiles.addAll(findExcelFiles(file));
                    } else if (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")) {
                        // Add Excel files to the list
                        excelFiles.add(file);
                    }
                }
            }
        } else {
            System.out.println("Provided path is not a directory: " + directory.getAbsolutePath());
        }

        return excelFiles;
    }
}
