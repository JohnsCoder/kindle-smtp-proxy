//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailContentHandler {
    public EmailContentHandler() {
    }

    public String ExtractLink(String emailText) {
        String urlPattern = "(https?://[^\\s]+)";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(emailText);
        List<String> links = new ArrayList();

        while(matcher.find()) {
            links.add(matcher.group(1));
        }

        if (!links.isEmpty()) {
            System.out.println("Extracted Link: " + (String)links.get(0));
            return (String)links.get(0);
        } else {
            return null;
        }
    }
}
