import java.io.*;
import java.lang.Math;
class GP_Main
 {
     public static void main(String args[])throws Exception
     {
         BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
         System.out.print("No. of Variables\t : ");
         int nvar=Integer.parseInt(br.readLine());
         
         //System.out.println("\nVariable Values \t :");
         GP_LogicGates prog = new GP_LogicGates(nvar);
         
         System.out.print("Population Size \t : ");
         prog.popsz=Integer.parseInt(br.readLine());
         System.out.print("Max. Tree Depth \t : ");
         prog.dmax=Integer.parseInt(br.readLine());
         System.out.print("Crossover probability \t : ");
         prog.pc=Double.parseDouble(br.readLine());
         System.out.print("Mutation probability \t : ");
         prog.pm=Double.parseDouble(br.readLine());
         System.out.print("Max. Generations\t : ");
         prog.gen=Integer.parseInt(br.readLine());
         
         int i=0,gnrtn=0,bfit;
         System.out.print("Target Output   \t : ");
         prog.goal=br.readLine();
         int lendif = (int)(Math.pow(2,prog.T_no)-prog.goal.length());
         if(lendif>0)
          {
              for(i=0;i<lendif;i++)
                  prog.goal="0"+prog.goal;
              System.out.println("\nWarning : MSB in Target Output zero padded to match with Chromosome length");
          }
         else if(lendif<0)
          {
              prog.goal=prog.goal.substring(0,prog.goal.length()+lendif);
              System.out.println("\nWarning : LSB in Target Output truncated to match with Chromosome length");
          }
         System.out.println("Goal : "+prog.goal);
         
         System.out.println("\nInitial Population Generation.....");
         prog.pop=new String[prog.popsz];
         for(i=0;i<prog.popsz;i++)
             prog.pop[i]=prog.addFT(0, new StringBuffer("")).toString();   //SOP(prog.pop[i]);
        
         prog.fit=new int[prog.popsz];
         Expression_Parsing epsr = new Expression_Parsing(); 
         String val[]=new String[prog.popsz];
         
         for(gnrtn=0;gnrtn<prog.gen;gnrtn++)
          {
              System.out.println("\nGeneration \t\t: "+(gnrtn+1));
              System.out.println("Expression Evaluation\t.....");
              for(i=0;i<prog.popsz;i++)
                 val[i]=epsr.evaltr(new StringBuffer(prog.pop[i])).toString();    // SOP("Member "+(i+1)+"\t= "+val[i]);
                       
              System.out.println("Fitness Evaluation\t.....");
              for(i=0;i<prog.popsz;i++)
                 prog.fit[i]=prog.fitns(val[i]);  //SOP("Member "+(i+1)+"\t= "+prog.fit[i]);
                            
              // Selection by Roulette wheel 
              System.out.println("Performing Selection\t.....");
              bfit = prog.slctn();
              
              if(bfit==((int)Math.pow(2,prog.T_no)))
               {
                   System.out.println("Maximum fitness reached : Exact solution found");
                   break;
               }
         
              System.out.println("Performing Crossover\t.....");
              prog.crsvr();
              
              System.out.println("Performing Mutation\t.....");
              prog.mutet();
         
              // Making crossover population as the next generation population
              for(i=0;i<prog.popsz;i++)
                 prog.pop[i]=prog.popm[i];    //SOP("Member "+(i+1)+"\t= "+prog.fit[i]);
          }
         //display statistics
     }
 }