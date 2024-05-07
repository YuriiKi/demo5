package ua.kivshar.yurii.demo5;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseHTML {
    String html;

    public void main(String[] args) {
        html = "<html><head><title>download.platezhka.com.ua - /Dispatcher/Archive/Reklama/</title></head><body><H1>download.platezhka.com.ua - /Dispatcher/Archive/Reklama/</H1><hr>\n" +
                "\n" +
                "<pre><A HREF=\"/Dispatcher/Archive/\">[В родительский каталог]</A><br><br>14.01.2022    16:53     12402234 <A HREF=\"/Dispatcher/Archive/Reklama/City24_advertising_1280%D1%851024_v3.avi\">City24_advertising_1280х1024_v3.avi</A><br>20.05.2022    14:33     61361828 <A HREF=\"/Dispatcher/Archive/Reklama/K-Lite_Codec_Pack_1700_Mega.exe\">K-Lite_Codec_Pack_1700_Mega.exe</A><br>24.05.2012    11:58     17118428 <A HREF=\"/Dispatcher/Archive/Reklama/K-Lite_Codec_Pack_840_Full.exe\">K-Lite_Codec_Pack_840_Full.exe</A><br>07.06.2023    11:14        &lt;dir&gt; <A HREF=\"/Dispatcher/Archive/Reklama/Mono/\">Mono</A><br>04.08.2023    13:29        &lt;dir&gt; <A HREF=\"/Dispatcher/Archive/Reklama/SMS/\">SMS</A><br>16.02.2018    18:35     22761524 <A HREF=\"/Dispatcher/Archive/Reklama/video2monitor.avi\">video2monitor.avi</A><br>23.06.2022    11:07       540581 <A HREF=\"/Dispatcher/Archive/Reklama/%D0%A0%D0%B5%D0%BA%D0%BB%D0%B0%D0%BC%D0%B0%20%D0%BD%D0%B0%20%D0%B4%D1%80%D1%83%D0%B3%D0%BE%D0%BC%D1%83%20%D0%BC%D0%BE%D0%BD%D1%96%D1%82%D0%BE%D1%80%D1%96.docx\">Реклама на другому моніторі.docx</A><br></pre><hr></body></html>";

        Document doc = Jsoup.parse(html);
        Element preElement = doc.selectFirst("pre");
        Elements links = preElement.select("a[href]");

        for (Element link : links) {
            System.out.println("Link: " + link.attr("href"));
            System.out.println("Text: " + link.text());
        }
    }

    public ParseHTML(String html) {
        this.html = html;
    }

    public Map<String, String> getLinksList (String html){
        Map<String, String> linkMap = new HashMap<>();
        Document doc = Jsoup.parse(html);
        Element preElement = doc.selectFirst("pre");
        assert preElement != null;
        Elements links = preElement.select("a[href]");
        for (Element link : links) {
            String href = link.attr("href");
            linkMap.put(link.text(), href);
            System.out.println("Link: " + href);
            System.out.println("Text: " + link.text());
        }
        return linkMap;
    }
}
