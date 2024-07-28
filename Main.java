public class Main {
    public static void main(String[] args) {
        String fileURL = "https://ncert.nic.in/textbook/pdf/cesa1dd.zip";
        String saveDir = "C:\\Users\\91989\\Desktop";
        FileDownloader.downloadFile(fileURL, saveDir);
    }
}
