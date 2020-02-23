package file1.file2;
import java.io.*;

public class  FileTest{

	public static void main(String[] args) {
		String separator = File.separator;
		String filename = "myfile.txt";
		String directory = "mydir1" + separator + "mydir2";
		//String directory = "mydir1/mydir2";  linux
		//String directory = "mydir1\\mydir2";  windows		
		File file = new File(directory ,filename);
		if (file.exists()) {
			System.out.println("文件名" + file.getAbsolutePath());
			System.out.println("文件大小" + file.length());
		} else {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
