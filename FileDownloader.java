import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader {

    public static void downloadFile(String fileURL, String saveDir) {
        try {
            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            // Check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");

                if (disposition != null) {
                    // Extract file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10, disposition.length() - 1);
                    }
                } else {
                    // Extract file name from URL
                    fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
                }

                System.out.println("Content-Type = " + httpConn.getContentType());
                System.out.println("Content-Disposition = " + disposition);
                System.out.println("Content-Length = " + httpConn.getContentLength());
                System.out.println("fileName = " + fileName);

                // Opens input stream from the HTTP connection
                BufferedInputStream inputStream = new BufferedInputStream(httpConn.getInputStream());

                // Opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveDir + "/" + fileName);

                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                System.out.println("File downloaded");
            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }
            httpConn.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
