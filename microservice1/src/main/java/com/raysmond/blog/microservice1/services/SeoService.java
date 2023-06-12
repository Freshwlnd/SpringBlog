package com.raysmond.blog.microservice1.services;

import com.raysmond.blog.microservice1.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequestMapping("/SeoService")
public class SeoService {

    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    @Autowired
    private AppSetting appSetting;


    public String createSitemap(List<Post> posts) {

        String slash = appSetting.getMainUri().trim().endsWith("/") ? "" : "/";

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("urlset");
            root.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
            doc.appendChild(root);

            for (Post post : posts) {
                Element url = doc.createElement("url");
                //loc
                Element loc = doc.createElement("loc");
                loc.appendChild(doc.createTextNode(String.format("%s%sposts/%s", appSetting.getMainUri().trim(), slash, post.getPermalink() == null || post.getPermalink().isEmpty() ? post.getId() : post.getPermalink())));
                url.appendChild(loc);
                //lastmod
                //yyyy-MM-dd'T'HH:mm:ss
                Element lastMod = doc.createElement("lastmod");
                lastMod.appendChild(doc.createTextNode(dateFormatter.format(post.getUpdatedAt())));
                url.appendChild(lastMod);
                //
                Element changeFreq = doc.createElement("changefreq");
                changeFreq.appendChild(doc.createTextNode("daily"));
                url.appendChild(changeFreq);

                root.appendChild(url);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StringWriter sw = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
            return null;
        }

    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Autowired
    PostService postService;

    List<Post> posts = null;

    private void init() {
        posts = new ArrayList<>();
        posts.add(this.postService.getPost(1L));
    }

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        if (posts == null) {
            init();
        }

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    createSitemap_test(posts);
                }
                break;
            case "createSitemap":
                createSitemap_test(posts);
                break;
        }

        return "test";
    }


    String createSitemap_test(List<Post> posts) {

//        String slash = appSetting.getMainUri().trim().endsWith("/") ? "" : "/";
        String slash = "http://localhost/".trim().endsWith("/") ? "" : "/";

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("urlset");
            root.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
            doc.appendChild(root);

            for (Post post : posts) {
                Element url = doc.createElement("url");
                //loc
                Element loc = doc.createElement("loc");
//                loc.appendChild(doc.createTextNode(String.format("%s%sposts/%s", appSetting.getMainUri().trim(), slash, post.getPermalink() == null || post.getPermalink().isEmpty() ? post.getId() : post.getPermalink())));
                loc.appendChild(doc.createTextNode(String.format("%s%sposts/%s", "http://localhost/".trim(), slash, post.getPermalink() == null || post.getPermalink().isEmpty() ? post.getId() : post.getPermalink())));
                url.appendChild(loc);
                //lastmod
                //yyyy-MM-dd'T'HH:mm:ss
                Element lastMod = doc.createElement("lastmod");
                lastMod.appendChild(doc.createTextNode(dateFormatter.format(post.getUpdatedAt())));
                url.appendChild(lastMod);
                //
                Element changeFreq = doc.createElement("changefreq");
                changeFreq.appendChild(doc.createTextNode("daily"));
                url.appendChild(changeFreq);

                root.appendChild(url);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StringWriter sw = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
            return null;
        }

    }

}
