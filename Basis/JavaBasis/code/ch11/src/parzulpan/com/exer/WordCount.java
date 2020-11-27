package parzulpan.com.exer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 获取文本上每个字符出现的次数，并将结果写入文件
 */

public class WordCount {
    public static void main(String[] args) {

        worldCount("ch11/word.txt", "ch11/wordCount.txt");

    }

    public static void worldCount(String srcFilePath, String destFilePath) {
        HashMap<Character, Integer> word = new HashMap<Character, Integer>();

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(new File(srcFilePath)));
            bw = new BufferedWriter(new FileWriter(new File(destFilePath)));

            int data;
            while ((data = br.read()) != -1) {
                if (word.containsKey((char)data)) {
                    word.put((char)data, word.get((char)data) + 1);
                } else {
                    word.put((char)data, 1);
                }
            }

            Set<Map.Entry<Character, Integer>> entries = word.entrySet();
            for(Map.Entry<Character, Integer> e : entries) {
                bw.write(e.getKey() + " : " + e.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (bw != null) {
                bw.close();
            }
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
