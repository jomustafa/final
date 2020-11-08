package com.example.messagingstompwebsocket.brainbright.utilities;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GreekLangUtils {

    private static final Map<Character, Character> LATIN_TO_GREEK = new HashMap<>();

    static {
        LATIN_TO_GREEK.put('\u0041', '\u0391');
        LATIN_TO_GREEK.put('\u0042', '\u0392');
        LATIN_TO_GREEK.put('\u0043', '\u0043');
        LATIN_TO_GREEK.put('\u0044', '\u0394');
        LATIN_TO_GREEK.put('\u0045', '\u0395');
        LATIN_TO_GREEK.put('\u0046', '\u03A6');
        LATIN_TO_GREEK.put('\u0047', '\u0393');
        LATIN_TO_GREEK.put('\u0048', '\u0397');
        LATIN_TO_GREEK.put('\u0049', '\u0399');
        LATIN_TO_GREEK.put('\u004A', '\u004A');
        LATIN_TO_GREEK.put('\u004B', '\u039A');
        LATIN_TO_GREEK.put('\u004C', '\u039B');
        LATIN_TO_GREEK.put('\u004D', '\u039C');
        LATIN_TO_GREEK.put('\u004E', '\u039D');
        LATIN_TO_GREEK.put('\u004F', '\u039F');
        LATIN_TO_GREEK.put('\u0050', '\u03A1');
        LATIN_TO_GREEK.put('\u0051', '\u0051');
        LATIN_TO_GREEK.put('\u0052', '\u03A1');
        LATIN_TO_GREEK.put('\u0053', '\u03A3');
        LATIN_TO_GREEK.put('\u0054', '\u03A4');
        LATIN_TO_GREEK.put('\u0055', '\u03A5');
        LATIN_TO_GREEK.put('\u0056', '\u0056');
        LATIN_TO_GREEK.put('\u0057', '\u0057');
        LATIN_TO_GREEK.put('\u0058', '\u03A7');
        LATIN_TO_GREEK.put('\u0059', '\u03A5');
        LATIN_TO_GREEK.put('\u005A', '\u0396');
    }

    static List<String> latinToGreek(List<String> latinList) {
        List<String> greekList = new LinkedList<>();
        for (String s : latinList) {
            greekList.add(removeDiacritics(latinToGreek(s)));
        }
        return greekList;
    }

    static String[] latinToGreek(String[] latinList) {
        List<String> greekList = new LinkedList<>();
        for (String s : latinList) {
            greekList.add(removeDiacritics(latinToGreek(s)));
        }
        return greekList.toArray(new String[latinList.length]);
    }

    public static String latinToGreek(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Character repl = LATIN_TO_GREEK.get(chars[i]);
            if (repl != null) {
                chars[i] = repl;
            }
        }
        return new String(chars);
    }

    public static String removeDiacritics(String s) {
        String newS = Normalizer.normalize(s, Normalizer.Form.NFD);
        newS = newS.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return newS;
    }

}

