package parzulpan.com.java9;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : Java9 try 语句的改进
 */

public class TryTest {
    public static void main(String[] args) {
        // Java8
        try(InputStreamReader reader = new InputStreamReader(System.in)) {
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Java9
        InputStreamReader reader = new InputStreamReader(System.in);
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        try (reader; writer){
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
