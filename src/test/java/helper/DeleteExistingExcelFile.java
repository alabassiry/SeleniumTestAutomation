package helper;

import java.io.File;

public class DeleteExistingExcelFile {
	public static void deleteFile(String fileloc) {
		try {
			File f = new File(fileloc);
			if (f.delete()) {
				System.out.println("Found: " + f.getName() + " and deleted");

			} else {
				System.out.println("Failed to delete the file: " + f.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
