package com.zk.cheatsheet.utils;

import org.apache.commons.io.FileUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.File;
import java.io.IOException;

public class MarkdownUtil {

    public static String markdownToHtml(File markdownFile) throws IOException {
        String fileContent = FileUtils.readFileToString(markdownFile, "UTF-8");

        Parser parser = Parser.builder().build();
        Node document = parser.parse(fileContent);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

}
