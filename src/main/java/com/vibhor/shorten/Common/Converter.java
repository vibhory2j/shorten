package com.vibhor.shorten.Common;

import com.vibhor.shorten.Config.RedisDataConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Converter {

    public static Logger log = LoggerFactory.getLogger(Converter.class);

    public static final Converter INSTANCE = new Converter();

    private static HashMap<Character, Integer> charToIntTable;
    private static List<Character> intToCharTable;

    private Converter() {
        initializeCharToIntTable();
        initializeIntToCharTable();
    }

    private void initializeCharToIntTable() {

        charToIntTable = new HashMap<>();

        for (int i = 0; i < 26; i++) {
            char c = 'a';
            c += i;
            charToIntTable.put(c, i);
        }

        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i-26);
            charToIntTable.put(c, i);
        }

        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            charToIntTable.put(c, i);
        }

    }

    private void initializeIntToCharTable() {
        intToCharTable = new ArrayList<>();

        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            intToCharTable.add(c);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i-26);
            intToCharTable.add(c);
        }
        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            intToCharTable.add(c);
        }

    }

    public String shortenUrl(Long id) {
        List<Integer> base62Id = new LinkedList<>();

        //convert to base62
        while (id > 0) {
            int remainder = (int) (id % 62);
            ((LinkedList)base62Id).addFirst(remainder);
            id /= 62;
        }

        StringBuilder uniqueURLID = new StringBuilder();

        for (int digit: base62Id) {
            uniqueURLID.append(intToCharTable.get(digit));
        }
        return uniqueURLID.toString();
    }

    public Long getDictionaryKeyFromUniqueID(String uniqueID) {
        long id = 0L;

        List<Character> base62Number = new ArrayList<>();

        for (int i = 0; i < uniqueID.length(); ++i) {
            base62Number.add(uniqueID.charAt(i));
        }

        int exp = base62Number.size() - 1;
        for (int i = 0; i < base62Number.size(); ++i, --exp) {
            int base10 = charToIntTable.get(base62Number.get(i));
            id += (base10 * Math.pow(62.0, exp));
        }

        return id;
    }
}
