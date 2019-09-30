package by.epam.todolist.util;

import by.epam.todolist.entity.Attachment;
import by.epam.todolist.entity.Task;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.research.ws.wadl.Resource;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

/**
 * The class saves the file, generates a path to it
 *
 * @author Alesia Skarakhod
 */
public class FileUtil {

    private static final String FILE_STORE = "file_store.properties";
    private static final String UPLOAD_FOLDER = "upload_folder";
    private static final String DEEP = "deep";

    /**
     *The method saves the file and returns information about it
     * @param inputStream file (InputStream)
     * @param fileDetail FormDataBodyPart
     * @return attachment
     */
    public static Attachment saveFile(InputStream inputStream, FormDataBodyPart fileDetail) {

        String baseFolder = PropertyUtils.getProperties(FILE_STORE).getProperty(UPLOAD_FOLDER);

        String generatedPath = baseFolder + getPath();

        File fileStoreDir = new File(generatedPath);
        if (fileStoreDir.mkdirs()) {

            String generatedName = UUID.randomUUID().toString();

            try (FileOutputStream fileOutputStream = new FileOutputStream(generatedPath + generatedName);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

                byte[] buffer = getByteArray(inputStream);
                bufferedOutputStream.write(buffer, 0, buffer.length);

                return Attachment
                        .builder()
                        .originalName(fileDetail.getContentDisposition().getFileName())
                        .generatedPath(generatedPath)
                        .generatedName(generatedName)
                        .mimeType(fileDetail.getMediaType().toString())
                        .build();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * The method returns string of path like that: /f/g/q/k/t/x/f/
     * it depends on DEEP
     *
     * @return String path
     */
    private static String getPath() {
        Integer deep = Integer.valueOf(PropertyUtils.getProperties(FILE_STORE).getProperty(DEEP));
        StringBuilder path = new StringBuilder("/");
        for (int p = 0; p < deep; p++) {
            path.append(getLetter()).append("/");
        }
        return path.toString();
    }

    /**
     * The method returns random letter between 'a' and 'z'
     * 97 - 'a' letter
     * 122 - 'z' letter
     *
     * @return String letter
     */
    private static String getLetter() {
        return String.valueOf((char) (new Random().nextInt((122 - 97) + 1) + 97));
    }

    /**
     * The method turns the file (InputStream) into an array of bytes
     * @param inputStream file
     * @return an array of bytes
     * @throws IOException, expected error class
     */
    private static byte[] getByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toByteArray();

    }

    /**
     * The method deletes the file physically
     * @param filePath, String
     * @param fileName, String
     * @return boolean true, if file delete
     */
    public static boolean removeFile(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
