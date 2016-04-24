/**
 * 
 */
package mediadecrypter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

import util.Config;

/**
 * Created by gournandi on 4/05/16.
 */
public class FileUtils {
	private static String TAG = "FileUtils";
	private Context context;

	/**
	 * 
	 */
	public FileUtils(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	public static void createDir(String path) {
		File lessonPlanDirectory = new File(path);
		lessonPlanDirectory.mkdirs();
		if (!lessonPlanDirectory.exists()) {
			if (Config.DEBUG) {
				Log.e(TAG, "Couldn't create dir in " + (path));
			}
			return;
		}
		if (Config.DEBUG) {
			Log.e(TAG, "Created a new folder in " + (path));
		}
	}

	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static String[] getAllFileTitlesIn(String path) {
		String[] filenames = null;
		File dir = Environment.getExternalStorageDirectory();
		File lessonPlanDirectory = new File(dir + path);
		if (lessonPlanDirectory.exists()) {
			filenames = lessonPlanDirectory.list();
		}
		return filenames;
	}

	public static File[] getAllFilesIn(String path, boolean addSDCardPath) {
		File[] files = null;
		if (addSDCardPath) {
			File dir = Environment.getExternalStorageDirectory();
			path = dir + File.separator +path;
		}
		File lessonPlanDirectory = new File(path);
		if (lessonPlanDirectory.exists()) {
			files = lessonPlanDirectory.listFiles();
		}
		return files;
	}

	public static File getFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file;
		}
		return null;
	}

	public static boolean haveSameNameExcludingExtention(String name1,
			String name2) {
		if (name1 == null || name2 == null) {
			return false;
		}
		int index = name1.lastIndexOf(".");
		if (index != -1) {
			name1 = name1.substring(0, index);
		}
		index = name2.lastIndexOf(".");
		if (index != -1) {
			name2 = name2.substring(0, index);
		}
		return name1.equalsIgnoreCase(name2);
	}

	public static String getStringContent(File file) {
		if (file == null) {
			return "";
		}
		int ch;
		StringBuffer strContent = new StringBuffer("");
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			while ((ch = fin.read()) != -1)
				strContent.append((char) ch);
			fin.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return strContent.toString();
	}

	public static boolean isHiddenFile(File file) {
		return (file != null && (file.isHidden() || file.getName().startsWith(
				".")));
	}
	
}
