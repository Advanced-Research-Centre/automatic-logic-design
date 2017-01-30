import java.io.*;
import java.lang.Math;
import javax.script.*;
public class GP_LogicGates_Backup_1
 {
     static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
     static ScriptEngine engine = (new ScriptEngineManager()).getEngineByName("JavaScript");
     static String pop[];
     static double f_prob = 0.5;
     static int dmax=4;
     static int T_no = 0;
     static int F_no = 6;
     static String T[][];
     static String F[][] = {{"AND","OR","NAND","NOR","XOR","XNOR"},
                            {"f1", "f2","f3",  "f4", "f5", "f6"  },
                            {"2",  "2", "2",   "2",  "2",  "2"   }};
     //static String F[][]={{"ADD","SUB","MUL","DIV","MOD","MUL10"},{"+","-","*","/","%","*10*"}};//"&&","||","!=","==","<",">","<=",">="};
     //static String T[]={"1","2","3","5","7","11","13","17","19"};
     
     // Initialize 1st generation     
     private static void initT(int nvar)
     {
         T_no = nvar;
         T=new String [2][T_no];
         int i,j,inx;
         for(i=1;i<=T_no;i++)
          {
              T[0][i-1]="x"+i;
              T[1][i-1]="";
          }
         // Logic Gate Initialization 
         
         for(i=0;i<(Math.pow(2,T_no));i++)
          {
               j=i;
               for(inx=0;inx<T_no;inx++)
               {
                   T[1][inx]=T[1][inx]+(j%2); //(inx = 1 --> LSB) (inx = T_no --> MSB)
                   j/=2;
               }
          }
         for(i=0;i<T_no;i++) System.out.println(T[0][i]+"\t = "+T[1][i]);  // Variable values
     }
     
     private static StringBuffer addFT(int d,StringBuffer s)
     {
         StringBuffer sb = new StringBuffer("");
         if(Math.random()<f_prob && d<(dmax-1))
          {
              sb.append(F[1][(int)Math.floor(F_no*Math.random())]).append("(").append(addFT(d+1,s)).append(",").append(addFT(d+1,s)).append(")");
              //sb.append("(").append(addFT(d+1,s)).append(F[1][(int)Math.floor(F_no*Math.random())]).append(addFT(d+1,s)).append(")");
          }
         else
              sb.append(T[1][(int)Math.floor(T_no*Math.random())]);
         return sb;
     }
         
     // Evaluate using recursion
     public static StringBuffer evaltr(StringBuffer fneq)throws Exception
     {
         //System.out.println("StringBuffer fneq -->"+fneq.toString());
         StringBuffer g=new StringBuffer(""); 
         for(int i=0;i<fneq.length();i++)
           {
               if(fneq.charAt(i)=='f')
                {
                    int nf=0,inx=i+1,evlt=1;
                    for(int k=i+1;k<fneq.length();k++)
                        {
                            if(fneq.charAt(k)=='f') 
                             {
                                 nf++;
                                 inx=k;
                                 break;
                             }
                        }
                    int cdpt=0,strt=i+1,mid=i+1,end=i+1;
                    if(nf!=0)
                     {
                         for(int j=inx+1;j<fneq.length();j++)
                          {
                              if(fneq.charAt(j)=='(') 
                               {
                                   if(cdpt==0) strt=j;
                                   cdpt++;
                               }     
                              else if(fneq.charAt(j)==')') 
                              {
                                   cdpt--;
                                   if(cdpt==0)  
                                    {
                                        fneq=(new StringBuffer(fneq.substring(0,inx))).append(evaltr(new StringBuffer(fneq.substring(inx,j+1)))).append(new StringBuffer(fneq.substring(j+1)));
                                        evlt=1;
                                    }
                              }
                          }
                     }
                    if(evlt==1)  //fneq doesn't have any more nested function
                     {
                         for(int j=i+1;j<fneq.length();j++)
                          {
                              if(fneq.charAt(j)=='(') 
                               {
                                   strt=j;
                                   break;
                               }
                          }
                         for(int j=i+1;j<fneq.length();j++)
                          {
                              if(fneq.charAt(j)==',') 
                               {
                                   mid=j;
                                   break;
                               }
                          }
                         for(int j=i+1;j<fneq.length();j++)
                          {
                              if(fneq.charAt(j)==')') 
                               {
                                   end=j;
                                   break;
                               }
                          }
                         //System.out.println("\nEvaluating function number     : "+fneq.substring(i+1,strt));
                         //System.out.println("Evaluating function expression : "+fneq.toString()); 
                         switch(Integer.parseInt(fneq.substring(i+1,strt)))
                          {
                              case 1: g=f1(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      //System.out.println("Evaluated function value       : "+g.toString());
                                      break;
                              case 2: g=f2(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      //System.out.println("Evaluated function value       : "+g.toString());
                                      break;
                              case 3: g=f3(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      //System.out.println("Evaluated function value       : "+g.toString());
                                      break;
                              case 4: g=f4(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      //System.out.println("Evaluated function value       : "+g.toString());
                                      break;
                              case 5: g=f5(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      //System.out.println("Evaluated function value       : "+g.toString());
                                      break;
                              case 6: g=f6(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      //System.out.println("Evaluated function value       : "+g.toString());
                                      break;
                              
                          }
                         return g; 
                        }
                    }
          }
         return fneq;
     }
     
     public static void main(int nvar,int popsz,int dmx)throws Exception
     {
         dmax=dmx;
         System.out.println("Variable Values :\n");
         initT(nvar);
         String sb;
         System.out.println("\nInitial Population :\n");
         pop=new String[popsz];
         for(int i=0;i<popsz;i++)
          {
              pop[i]=addFT(0, new StringBuffer("")).toString();
              System.out.println(pop[i]);
          }
          
         System.out.println("\nPopulation Fitness :\n");
         for(int i=0;i<popsz;i++)
          {
              System.out.println("Member "+(i+1)+"\t= "+evaltr(new StringBuffer(pop[i])).toString());
          }
         //System.out.println("\nReturn Value : "+evaltr(new StringBuffer("f1(f2(f3(f4(0101,0011),0101),0101),0101)"+"")).toString());
     }
     
     public static StringBuffer f1(StringBuffer v1, StringBuffer v2) // AND
     {
         StringBuffer res=new StringBuffer("");
         for(int i=0;i<v1.length();i++)
          {
             if(v1.charAt(i)=='1' && v2.charAt(i)=='1') res.append("1");
             else res.append("0");
          }
         return res;
     }
     
     public static StringBuffer f2(StringBuffer v1, StringBuffer v2) // OR
     {
         StringBuffer res=new StringBuffer("");
         for(int i=0;i<v1.length();i++)
          {
             if(v1.charAt(i)=='0' && v2.charAt(i)=='0') res.append("0");
             else res.append("1");
          }
         return res;
     }
     
     public static StringBuffer f3(StringBuffer v1, StringBuffer v2) // NAND
     {
         StringBuffer res=new StringBuffer("");
         for(int i=0;i<v1.length();i++)
          {
             if(v1.charAt(i)=='1' && v2.charAt(i)=='1') res.append("0");
             else res.append("1");
          }
         return res;
     }
     
     public static StringBuffer f4(StringBuffer v1, StringBuffer v2) // NOR
     {
         StringBuffer res=new StringBuffer("");
         for(int i=0;i<v1.length();i++)
          {
             if(v1.charAt(i)=='0' && v2.charAt(i)=='0') res.append("1");
             else res.append("0");
          }
         return res;
     }
     
     public static StringBuffer f5(StringBuffer v1, StringBuffer v2) // XOR
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
     
     public static StringBuffer f6(StringBuffer v1, StringBuffer v2) // XNOR
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