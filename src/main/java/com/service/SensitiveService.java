package com.service;

import org.apache.commons.lang.CharUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {

    public static final String REPLACE_WORD = "***";

    private class TrieNote {

        private boolean end;

        private Map<Character, TrieNote> childNotes = new HashMap<>();

        public boolean isEnd() {
            return end;
        }

        public void setNoteIsEnd() {
            this.end = true;
        }

        public TrieNote getChildNote(Character value) {
            return this.childNotes.get(value);
        }

        public void setChildNotes(Character c, TrieNote note) {
            childNotes.put(c, note);
        }
    }

    private TrieNote root = new TrieNote();

    private boolean isSymbol(Character character) {
        int intCharacter = (int) character;
        return !CharUtils.isAsciiAlphanumeric(character) && (intCharacter < 0x2E80 || intCharacter > 0x9FFF);
    }

    public void addWord(String charac) {

        TrieNote parentNote = root;
        for (int i = 0; i < charac.length(); i++) {
            char c = charac.charAt(i);

            if (isSymbol(c)) {
                continue;
            }

            TrieNote note = parentNote.getChildNote(c);

            if (note == null) {
                note = new TrieNote();
                parentNote.setChildNotes(c, note);
            }
            parentNote = parentNote.getChildNote(c);

            if (i == charac.length() - 1) {
                parentNote.setNoteIsEnd();
            }
        }
    }

    public StringBuffer filter(String text) {
        StringBuffer result = new StringBuffer();
        TrieNote tempNote = root;
        int begin = 0;
        int position = 0;

        while (position < text.length()) {
            char c = text.charAt(position);
            if (isSymbol(c)) {
                if (tempNote == root) {
                    result.append(c);
                    begin++;
                }
                position++;
                continue;
            }

            tempNote = tempNote.getChildNote(c);

            if (tempNote == null) {
                result.append(text.charAt(begin));
                begin++;
                position = begin;
                tempNote = root;

            } else if (tempNote.isEnd()) {
                result.append(REPLACE_WORD);
                begin = position + 1;
                position = begin;

                tempNote = root;

            } else {
                position++;

                if (position == text.length()) {
                    result.append(text.charAt(begin));
                    begin++;
                    position = begin;
                    tempNote = root;
                }
            }

        }
        return result;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("E:/idea/zhifou/src/main/resources/sensitiveWords");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String str;
        while ((str = bufferedReader.readLine())!= null) {
            this.addWord(str);
        }
        fileInputStream.close();
        bufferedReader.close();
    }

}
