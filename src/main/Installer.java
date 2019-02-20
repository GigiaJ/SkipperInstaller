package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Installer {
    private static String userDirectory;
    private static File baseFolder;
    private static File browserCookiesFolder;
    //private static File settingsFile;
    private static File skipperFile;

    public static void main(String[] args) throws IOException {
        startUp();
        if (!skipperFile.exists()) {
            URL website = new URL("https://github.com/GigiaJ/Skipper/releases/download/2.6.0/Skipperwin64.jar");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(skipperFile.getAbsolutePath());
            FileChannel.open(Paths.get("Skipper.jar"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        }
        @SuppressWarnings("unused")
        Process skipperProcess = Runtime.getRuntime().exec("java -jar " + skipperFile.getAbsolutePath());
        System.exit(0);
    }

    protected static void startUp() throws IOException {
        if (OSCheck.isWindows()) {
            Path thisFile = Paths.get("this").toAbsolutePath();
            String userPath = thisFile.toString();
            String directory = userPath.substring(9);
            String userName = directory.substring(0, directory.indexOf("\\"));
            int userNameLength = userName.length();
            userDirectory = userPath.substring(0, 9 + userNameLength);

            baseFolder = new File(userDirectory + "\\AppData\\Local\\Skipper");
            browserCookiesFolder = new File(userDirectory + "\\AppData\\Local\\Skipper\\BrowserStorage");
            //settingsFile = new File(userDirectory + "\\AppData\\Local\\Skipper\\Settings.txt");
            skipperFile = new File(userDirectory + "\\AppData\\Local\\Skipper\\Skipper.jar");
        } else if (OSCheck.isMac()) {
            Path thisFile = Paths.get("this").toAbsolutePath();
            String userPath = thisFile.toString();
            String directory = userPath.substring(9);
            String userName = directory.substring(0, directory.indexOf("/"));
            int userNameLength = userName.length();
            userDirectory = userPath.substring(0, 9 + userNameLength);

            baseFolder = new File(userDirectory + "/Library/Application Support/Skipper");
            baseFolder.mkdir();
            browserCookiesFolder = new File(userDirectory + "/Library/Application Support/Skipper/BrowserStorage");
            browserCookiesFolder.mkdir();
            //settingsFile = new File(userDirectory + "/Library/Application Support/Skipper/Settings.txt");
            skipperFile = new File(userDirectory + "/Library/Application Support/Skipper/Skipper.jar");
        } else

        {
            System.out.println("Your OS is not support!!");
        }
    }

}
