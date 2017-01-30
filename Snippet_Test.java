import javax.script.*;
import javax.script.ScriptEngine;
import java.lang.Math;
import java.io.*;

public class Snippet_Test {
  static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
  static ScriptEngine engine = (new ScriptEngineManager()).getEngineByName("JavaScript");
  public static void main() throws Exception
  {
   int a[][]={{1,2,3,4},{4,5,6,7}};
   StringBuffer sb=new StringBuffer("aritraa");
   System.out.println(5+(sb.substring(3,8).toString()));
    }
    
 public static String evalt()throws Exception
      {
          double fn=0;
          System.out.println("=");
          String fneq=br.readLine();
         // for(int i=0;i<nvar;i++)
          // {
          //     engine.put(("x"+(i+1)),n[i]);
          //  }
          fn=Double.parseDouble(engine.eval(fneq)+"");
          return "gg";
      }   
}
