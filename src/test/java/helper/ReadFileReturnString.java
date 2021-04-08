package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadFileReturnString {
	public static String readFileWithinCurrentDirectoryReturnString(String directory) throws Exception {
		BufferedReader reader = new BufferedReader(
				new FileReader(new File(System.getProperty("user.dir")) + directory));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();

		String content = stringBuilder.toString();

		return content;
	}
}
