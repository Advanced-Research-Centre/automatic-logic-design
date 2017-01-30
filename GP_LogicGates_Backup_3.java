// Either implement NOT, or implement everything in NAND/NOR
import java.io.*;
import java.lang.Math;
import javax.script.*;
public class GP_LogicGates_Backup_3
 {
     static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
     static ScriptEngine engine = (new ScriptEngineManager()).getEngineByName("JavaScript");
     
     static String goal = "";
     static String pop[];       // Generation population
     static String pops[];      // After selection
     static String popc[];      // After crossover
     static String popm[];      // After mutation
     static int fit[];          // Fitness values
     static int popsz = 0;      // Population size     
     static int dmax=4;         // Maximum tree depth
     static double pc = 0.6;    // Crossover probability
     static double pm = 0.1;    // Mutation probability
     static int gen = 0;        // Generation number
     static int T_no = 0;       // Number of variables
     static int F_no = 6;       // Number of functions
     static double f_prob = 0.5;
     static String T[][];
     static String F[][] = {{"f1", "f2","f3",  "f4", "f5", "f6"  },
                            {"2",  "2", "2",   "2",  "2",  "2"   }};
                         // {"AND","OR","NAND","NOR","XOR","XNOR"}
     
     // Initialize 1st generation     
     public GP_LogicGates_Backup_3()
     {
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
         for(i=0;i<T_no;i++) 
               System.out.println(T[0][i]+"\t = "+T[1][i]);  // Variable values
     }
     
     private StringBuffer addFT(int d,StringBuffer s)
     {
         StringBuffer sb = new StringBuffer("");
         
         if(Math.random()<f_prob && d<(dmax-1))
          {
              sb.append(F[0][(int)Math.floor(F_no*Math.random())]).append("(").append(addFT(d+1,s)).append(",").append(addFT(d+1,s)).append(")");
          }
         else
              sb.append(T[1][(int)Math.floor(T_no*Math.random())]);
         return sb;
     }
     
     private int fitns(String chmsm)
     {
         int fitval=0;
         for(int i=0;i<chmsm.length();i++)
          {
             if((goal.charAt(i)=='1' && chmsm.charAt(i)=='1')||(goal.charAt(i)=='0' && chmsm.charAt(i)=='0')) fitval++;
          }
         return fitval;
     }
     
     private void slctn()
     {
         int tfit=0,i,j;
         pops=new String[popsz];
         double roult;
         double pfit[]=new double[popsz];
          //fit=new int[popsz];
         for(i=0;i<popsz;i++)
          {
              tfit+=fit[i];
          }
         System.out.println("\nGeneration fitness\t: "+tfit);
         System.out.println("\nProbabilistic fitness\n");
         for(i=0;i<popsz;i++)
          {
              pfit[i]=((double)(fit[i]))/tfit;
              System.out.println("Member "+(i+1)+"\t= "+pfit[i]);
          }
         System.out.println("\nSelected population\n");
         for(i=0;i<popsz;i++)
          {
              roult = Math.random();
              for(j=0;j<popsz;j++)
               {
                   //System.out.println("roulette : "+roult);
                   roult-=pfit[j];
                   //System.out.println("j: "+j);
                   //System.out.println("roulette ee: "+roult);
                   if(roult<0) 
                    {
                        //System.out.println("init pop: "+pop[i]);
                        pops[i]=pop[j];
                        System.out.println("Member "+(i+1)+"\t= "+pops[i]);
                        break;
                    }
               }
          }
     }
          
     public static void main(String args[])throws Exception
     {
         System.out.print("No. of Variables\t : ");
         T_no=Integer.parseInt(br.readLine());
         System.out.print("Population Size \t : ");
         popsz=Integer.parseInt(br.readLine());
         System.out.print("Max. Tree Depth \t : ");
         dmax=Integer.parseInt(br.readLine());
         
         System.out.print("Target Output   \t : ");
         goal=br.readLine();
         int lendif = (int)(Math.pow(2,T_no)-goal.length());
         if(lendif>0)
          {
              for(int i=0;i<lendif;i++)
                  goal="0"+goal;
              System.out.println("\nMSB in Goal zero padded to match with Gene length");
          }
         else if(lendif<0)
          {
              goal=goal.substring(0,goal.length()+lendif);
              System.out.println("\nLSB in Goal truncated to match with Gene length");
          }
         System.out.println("\nGoal : "+goal);
          
         System.out.println("\nVariable Values :\n");
         GP_LogicGates_Backup_3 prog = new GP_LogicGates_Backup_3();
  
         System.out.println("\nInitial Population :\n");
         pop=new String[popsz];
         for(int i=0;i<popsz;i++)
          {
              pop[i]=prog.addFT(0, new StringBuffer("")).toString();
              System.out.println(pop[i]);
          }
          
         Expression_Parsing epsr = new Expression_Parsing(); 
         String val[]=new String[popsz];
         System.out.println("\nPopulation Value :\n");
         for(int i=0;i<popsz;i++)
          {
              val[i]=epsr.evaltr(new StringBuffer(pop[i])).toString();
              System.out.println("Member "+(i+1)+"\t= "+val[i]);
          }
         // Test Strings :
         //System.out.println("\nReturn Value : "+evaltr(new StringBuffer("f1(f2(f3(f4(0101,0011),0101),0101),0101)"+"")).toString());
         //System.out.println("\nReturn Value : "+evaltr(new StringBuffer("f4(f1(0000111100001111,0101010101010101),0000000011111111)"+"")).toString());
         //System.out.println("\nReturn Value : "+evaltr(new StringBuffer("f4(0000000011111111,f1(0000111100001111,0101010101010101))"+"")).toString());
         //System.out.println("\nReturn Value : "+evaltr(new StringBuffer("f6(f1(0101010101010101,f3(0011001100110011,0000000011111111)),f4(f4(0000111100001111,0000111100001111),f3(0000000011111111,0000000011111111)))"+"")).toString());
         
         System.out.println("\nInitial Fitness :\n");
         fit=new int[popsz];
         for(int i=0;i<popsz;i++)
          {
              fit[i]=prog.fitns(val[i]);
              System.out.println("Member "+(i+1)+"\t= "+fit[i]);
          }
         
         System.out.println("\nSelection by Roulette Wheel :");
         prog.slctn();
     }
 }