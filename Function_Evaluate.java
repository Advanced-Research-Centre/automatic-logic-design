 class Function_Evaluate
  {
     protected static StringBuffer f1(StringBuffer v1, StringBuffer v2) // AND
     {
         StringBuffer res=new StringBuffer("");
         for(int i=0;i<v1.length();i++)
          {
             if(v1.charAt(i)=='1' && v2.charAt(i)=='1') res.append("1");
             else res.append("0");
          }
         return res;
     }
     
     protected static StringBuffer f2(StringBuffer v1, StringBuffer v2) // OR
     {
         StringBuffer res=new StringBuffer("");
         for(int i=0;i<v1.length();i++)
          {
             if(v1.charAt(i)=='0' && v2.charAt(i)=='0') res.append("0");
             else res.append("1");
          }
         return res;
     }
     
     protected static StringBuffer f3(StringBuffer v1, StringBuffer v2) // NAND
     {
         StringBuffer res=new StringBuffer("");
         for(int i=0;i<v1.length();i++)
          {
             if(v1.charAt(i)=='1' && v2.charAt(i)=='1') res.append("0");
             else res.append("1");
          }
         return res;
     }
     
     protected static StringBuffer f4(StringBuffer v1, StringBuffer v2) // NOR
     {
         StringBuffer res=new StringBuffer("");
         for(int i=0;i<v1.length();i++)
          {
             if(v1.charAt(i)=='0' && v2.charAt(i)=='0') res.append("1");
             else res.append("0");
          }
         return res;
     }
     
     protected static StringBuffer f5(StringBuffer v1, StringBuffer v2) // XOR
     {
         StringBuffer res=new StringBuffer("");
         for(int i=0;i<v1.length();i++)
          {
             if(v1.charAt(i)=='1' && v2.charAt(i)=='0') res.append("1");
             else if(v1.charAt(i)=='0' && v2.charAt(i)=='1') res.append("1");
             else res.append("0");
          }
         return res;
     }
     
     protected static StringBuffer f6(StringBuffer v1, StringBuffer v2) // XNOR
     {
         StringBuffer res=new StringBuffer("");
         for(int i=0;i<v1.length();i++)
          {
             if(v1.charAt(i)=='1' && v2.charAt(i)=='1') res.append("1");
             else if(v1.charAt(i)=='0' && v2.charAt(i)=='0') res.append("1");
             else res.append("0");
          }
         return res;
     }      
 } //end of Class