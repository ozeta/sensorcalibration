package it.enea.monica.calibration;
/**
 * Created by ozeta on 17/11/2017.
 */

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringParser {
    private String buffer;
    private String rex = "(<=>)(.*)(\\*@\\*)";
    private Pattern pattern;
    private HashMap<String, String> result;

    public StringParser() {
        buffer = null;
        pattern = Pattern.compile(rex);
        result = null;

    }

    public int parse(char c) {
        return parse(String.valueOf(c));
    }

    public int parse(String str) {
        result = null;
        buffer += str;
        Matcher matcher = pattern.matcher(buffer);
        int counter = 0;
        if (matcher.find()) {
            String tmp = matcher.group(2);
            String[] kv = tmp.split("#");
            result = new HashMap<String, String>();
            for (String s : kv) {
                String[] kkv = s.split(":");
                result.put(kkv[0], kkv[1]);
            }
            buffer = buffer.substring(matcher.end());
            counter++;
        }
        return counter;
    }

    public HashMap<String, String> getResult() {
        return result;
    }
}
