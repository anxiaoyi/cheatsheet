package com.zk.cheatsheet.utils;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;

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
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();
        Template template = velocityEngine.getTemplate(markdownFolder + File.separator + "sheet_template.html");

        for (File md : markdownFiles) {
            String html = MarkdownUtil.markdownToHtml(md);
            String name = md.getName().replace(".md", "");

            VelocityContext ctx = new VelocityContext();
            ctx.put("name", name);
            ctx.put("keywords", name + " cheat sheet");
            ctx.put("content", html);

            StringWriter sw = new StringWriter();
            template.merge(ctx, sw);
            String r = sw.toString();

            String htmlName = saveFolder + File.separator + name;
            FileUtils.write(new File(htmlName.toLowerCase() + ".html"), r, "UTF-8");
        }

        // ===========================================
        // 拷贝其他资源
        // ===========================================
        copyFile(markdownFolder + File.separator + "index.html", saveFolder + File.separator + "index.html");
    }

    private static void copyFile(String srcPath, String targetPath) throws IOException {
        FileUtils.copyFile(new File(srcPath), new File(targetPath));
    }

}
