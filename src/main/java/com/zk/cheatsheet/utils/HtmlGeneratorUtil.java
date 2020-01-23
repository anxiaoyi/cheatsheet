package com.zk.cheatsheet.utils;

import com.zk.cheatsheet.bean.Sheet;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HtmlGeneratorUtil {

    public static void copy(String saveFolder) throws IOException {
        FileUtils.copyFileToDirectory(
                new File("docs" + File.separator + "index.html"), new File(saveFolder)
        );
        FileUtils.copyFileToDirectory(
                new File("docs" + File.separator + "cheatsheet_logo.png"),
                new File(saveFolder)
        );
    }

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

            String r = merge(template, ctx);

            String htmlName = saveFolder + File.separator + name;
            FileUtils.write(new File(htmlName.toLowerCase() + ".html"), r, "UTF-8");
        }

        // ===========================================
        // 生成所有 index.html 文件
        // ===========================================
        List<Sheet> resourceList = Arrays.asList(markdownFiles).stream().map(e -> {
            String name = e.getName().replace(".md", "");
            String link = "/" + name.toLowerCase() + ".html";

            return new Sheet(link, name);
        }).collect(Collectors.toList());

        template = velocityEngine.getTemplate(markdownFolder + File.separator + "index.html");
        VelocityContext ctx = new VelocityContext();
        ctx.put("resourceList", resourceList);
        String index = merge(template, ctx);

        // ===========================================
        // 拷贝 index.html 资源
        // ===========================================
        FileUtils.write(new File(saveFolder + File.separator + "index.html"), index, "UTF-8");
    }

    private static String merge(Template template, VelocityContext ctx) {
        StringWriter sw = new StringWriter();
        template.merge(ctx, sw);
        return sw.toString();
    }

    private static void copyFile(String srcPath, String targetPath) throws IOException {
        FileUtils.copyFile(new File(srcPath), new File(targetPath));
    }

}
