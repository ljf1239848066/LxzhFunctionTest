package com.lxzh123.funcdemo;

import org.apache.tools.ant.DirectoryScanner;
import org.junit.Test;

import java.util.Arrays;

public class DirectoryScannerTest {
    @Test
    public void addition_isCorrect() {
        String scandir = "D:\\Project\\Release\\onelogin\\v2.3.5\\";
        String wildcard = "*20??????.apk";
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(scandir);
        scanner.setIncludes(new String[] { wildcard });
        scanner.setCaseSensitive(true);
        scanner.scan();
        String[] uploadFiles = scanner.getIncludedFiles();
        System.out.println("uploadFiles:" + Arrays.toString(uploadFiles));
    }
}
