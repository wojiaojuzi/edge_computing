package com.ecs.cmd;

import java.awt.*;
import java.net.URI;

public class InitFront {
    private String directory;
    public InitFront() {
        directory = System.getProperty("user.dir");
        System.out.println(directory);
        new JavaCMD("cd " + directory + "&&" + "cd C:\\Users\\lenovo\\Desktop\\resources 2\\resources" + "&&" + "python3 -m http.server 8000").start();

        Desktop desktop = Desktop.getDesktop();
        if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                URI uri = URI.create("http://127.0.0.1:8000/templates/cool.html");
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
