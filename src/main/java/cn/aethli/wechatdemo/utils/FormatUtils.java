package cn.aethli.wechatdemo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-15 17:24
 **/
public class FormatUtils {

  public static void amr2mp3(String fileName) {
    String path = FormatUtils.class.getResource("/ffmpeg-win/bin/ff.bat").getPath().substring(1);
    String command = String
        .format("cmd /c start %s e:\\dl\\%s e:\\dl\\%s.mp3", path, fileName, fileName);
    String result;
    try {
      Runtime runtime = Runtime.getRuntime();
      Process exec = runtime.exec(command);
      InputStream in = exec.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
      while ((result = bufferedReader.readLine()) != null) {
        System.out.println(result);
      }
      in.close();
      bufferedReader.close();
      Thread.sleep(5000);
      exec.destroy();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
