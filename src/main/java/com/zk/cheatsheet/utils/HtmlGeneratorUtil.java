package com.zk.cheatsheet.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class HtmlGeneratorUtil {

    public static void generate(String markdownFolder, String saveFolder) throws IOException {
        // ===========================================
        // 列举所有 markdown 文件
        // ===========================================
        File folder = new File(markdownFolder);
        File[] markdownFiles = folder.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getAbsolutePath().endsWith(".md");
            }

        });

        // ===========================================
        // 生成所有 markdown 文件
        // ===========================================
        for (File md : markdownFiles) {
            String html = MarkdownUtil.markdownToHtml(md);
            String htmlName = saveFolder + File.separator + md.getName().replace("md", "html");
            FileUtils.write(new File(htmlName), html, "UTF-8");
        }
    }

}
