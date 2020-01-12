package com.zk.cheatsheet;

import com.zk.cheatsheet.utils.HtmlGeneratorUtil;

import java.io.IOException;

public class MainApp {

    private static final String FOLDER = "docs";

    public static void main(String...args) {
        try {
            HtmlGeneratorUtil.generate(FOLDER, "build/site");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
