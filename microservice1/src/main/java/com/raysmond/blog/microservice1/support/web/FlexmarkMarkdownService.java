package com.raysmond.blog.microservice1.support.web;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.ext.youtube.embedded.YouTubeLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Service
@Qualifier("flexmark")
@RequestMapping("/FlexmarkMarkdownService")
public class FlexmarkMarkdownService implements MarkdownService, SyntaxHighlightService {
    @Override
    public String renderToHtml(String content) {

        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        options.set(Parser.EXTENSIONS, Arrays.asList(
                TablesExtension.create(),
                StrikethroughExtension.create(),
                YouTubeLinkExtension.create(),
                WikiLinkExtension.create(),
                SuperscriptExtension.create()/*,
                EnumeratedReferenceExtension.create()*/
        ));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(content);
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"

        return html;
    }

    @Override
    public String highlight(String content) {
        return content;
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        String content = "content";

        switch (method) {
            case "all":
                for (int i = 0; i < 6; i++) {
                    renderToHtml_test(content);
                }
                break;
            case "renderToHtml":
                renderToHtml_test(content);
                break;
        }

        return "test";
    }

    public String renderToHtml_test(String content) {

        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        options.set(Parser.EXTENSIONS, Arrays.asList(
                TablesExtension.create(),
                StrikethroughExtension.create(),
                YouTubeLinkExtension.create(),
                WikiLinkExtension.create(),
                SuperscriptExtension.create()/*,
                EnumeratedReferenceExtension.create()*/
        ));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(content);
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"

        return html;
    }


}
