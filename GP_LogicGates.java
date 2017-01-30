// Either implement NOT, or implement everything in NAND/NOR
import java.io.*;
import java.lang.Math;
class GP_LogicGates
 {   
     String goal = "";
     String pop[];       // Generation population
     String pops[];      // After selection
     String popc[];      // After crossover
     String popm[];      // After mutation
     String bfit = "";   // Current best fit expression
     int bfitns = 0;     // Current best fitness
     int fit[];          // Fitness values
     int popsz = 0;      // Population size     
     int dmax=4;         // Maximum tree depth
     double pc = 0.6;    // Crossover probability
     double pm = 0.1;    // Mutation probability
     int gen = 0;        // Generation number
     int T_no = 0;       // Number of variables
     int F_no = 6;       // Number of functions
     double f_prob = 0.5;
     String T[][];
     String F[][] = {{"f1", "f2","f3",  "f4", "f5", "f6"  },
                     {"2",  "2", "2",   "2",  "2",  "2"   }};
                  // {"AND","OR","NAND","NOR","XOR","XNOR"}
     
     // Initialize 1st generation     
     public GP_LogicGates(int nvar)
     {
         T_no=nvar;
         T=new String [2][T_no];
         int i,j,inx;
         for(i=1;i<=T_no;i++)
          {
              T[0][i-1]="x"+i;
              T[1][i-1]="";
          }
         for(i=0;i<(Math.pow(2,T_no));i++)      // Logic Gate Initialization 
          {
               j=i;
               for(inx=0;inx<T_no;inx++)
               {
                   T[1][inx]=T[1][inx]+(j%2);   //(inx = 1 --> LSB) (inx = T_no --> MSB)
                   j/=2;
               }
          }
         //for(i=0;i<T_no;i++) 
               //System.out.println(T[0][i]+"\t = "+T[1][i]);  // Variable values
     }
     
     protected StringBuffer addFT(int d,StringBuffer s)
     {
         StringBuffer sb = new StringBuffer("");
         
         if(Math.random()<f_prob && d<(dmax-1))
              sb.append(F[0][(int)Math.floor(F_no*Math.random())]).append("(").append(addFT(d+1,s)).append(",").append(addFT(d+1,s)).append(")");
         else
              sb.append(T[1][(int)Math.floor(T_no*Math.random())]);
         return sb;
     }
     
     protected int fitns(String chmsm)
     {
         int fitval=0;
         for(int i=0;i<chmsm.length();i++)
             if((goal.charAt(i)=='1' && chmsm.charAt(i)=='1')||(goal.charAt(i)=='0' && chmsm.charAt(i)=='0')) fitval++;
         return fitval;
     }
     
     protected int slctn()
     {
         int tfit=0,i,j;
         pops=new String[popsz];
         double roult;
         double pfit[]=new double[popsz];
         for(i=0;i<popsz;i++)
          {
              tfit+=fit[i];
              if(fit[i]>bfitns)
               {
                   bfitns = fit[i];
                   bfit = pop[i];
               }
          }
         System.out.println("Generation fitness\t: "+tfit+"/"+(int)(Math.pow(2,T_no)*popsz));
         System.out.println("Best fitness\t\t: "+bfitns+"/"+(int)Math.pow(2,T_no)+"\nBest Expression \t: "+bfit);
         // SOP("\nProbabilistic fitness\n");
         for(i=0;i<popsz;i++)
          {
              pfit[i]=((double)(fit[i]))/tfit;  //SOP("Member "+(i+1)+"\t= "+pfit[i]);
          }
         //SOP("\nSelected population\n");
         for(i=0;i<popsz;i++)
          {
              roult = Math.random();
              for(j=0;j<popsz;j++)
               {
                   roult-=pfit[j];
                   if(roult<0) 
                    {
                        pops[i]=pop[j]; //SOP("Member "+(i+1)+"\t= "+pops[i]);
                        break;
                    }
               }
          }
         return bfitns;
     }
     
     protected void crsvr()
     {
         int i,k,nb1=0,nb2=0,cpts1=0,cpts2=0,cpte1=0,cpte2=0,cnt=0;
         popc=new String[popsz];
         for(i=0;i<popsz-1;i=i+2) // <even popsz check in main fn>
          {
              if(Math.random()<pc)
               {
                   nb1=0;
                   nb2=0;
                   //System.out.println("Selected for Crossover");
                   // Count total no of brackets
                   for(k=0;k<pops[i].length();k++)
                    {
                        if(pops[i].charAt(k)=='(') nb1++;
                    }
                   for(k=0;k<pops[i+1].length();k++)
                    {
                        if(pops[i+1].charAt(k)=='(') nb2++;
                    }
                   // Randomly select which '(' is the crossover starting point
                   cpts1=(int)Math.floor(Math.random()*nb1); 
                   cpts2=(int)Math.floor(Math.random()*nb2);
                   // Get position index of selected '('
                   for(k=0;k<pops[i].length();k++)
                    {
                        if(pops[i].charAt(k)=='(')      cpts1--;
                        if(cpts1==0)
                         {
                             cpts1=k;
                             break;
                         }
                    }
                   for(k=0;k<pops[i+1].length();k++)
                    {
                        if(pops[i+1].charAt(k)=='(')    cpts2--;
                        if(cpts2==0)
                         {
                             cpts2=k;
                             break;
                         }
                    }
                   // Get matching end index of selected '('
                   if(cpts1==0)
                        cpte1=(int)pops[i].length()-1;
                   else
                    {
                        cnt=0;
                        for(k=cpts1;k<pops[i].length();k++)
                         {
                             if(pops[i].charAt(k)=='(')  cnt++;
                             else if(pops[i].charAt(k)==')') 
                              {
                                  cnt--;
                                  if(cnt==0)
                                   {
                                       cpte1=k;
                                       break;
                                   }
                              }
                         }
                    }
                   if(cpts2==0)
                        cpte2=(int)pops[i+1].length()-1;
                   else
                    {
                        cnt=0;
                        for(k=cpts2;k<pops[i+1].length();k++)
                         {
                             if(pops[i+1].charAt(k)=='(')  cnt++;
                             else if(pops[i+1].charAt(k)==')') 
                              {
                                  cnt--;
                                  if(cnt==0)
                                   {
                                       cpte2=k;
                                       break;
                                   }
                              }
                         }
                    }
                   // Get index of 'f' just before selected '('
                   if(cpts1!=0)
                    {
                        for(k=cpts1-1;k>=0;k--)
                         {
                             if(pops[i].charAt(k)=='f')
                              {
                                  cpts1=k;
                                  break;
                              }
                         }
                    }
                   if(cpts2!=0)
                    {
                        for(k=cpts2-1;k>=0;k--)
                         {
                             if(pops[i+1].charAt(k)=='f')
                              {
                                  cpts2=k;
                                  break;
                              }
                         }
                    } 
                   popc[i] = pops[i].substring(0,cpts1) + pops[i+1].substring(cpts2,cpte2+1) + pops[i].substring(cpte1+1);
                   popc[i+1] = pops[i+1].substring(0,cpts2) + pops[i].substring(cpts1,cpte1+1) + pops[i+1].substring(cpte2+1);
               }
              else  // Directly copy if not selected for crossover
               {
                   popc[i] = pops[i];
                   popc[i+1] = pops[i+1];
               }
          }
     }
     
     protected void mutet()
     {
         int i,mpts=0,mpte=0,k,nb,cnt;
         popm=new String[popsz];
         for(i=0;i<popsz;i++)
          {
               
              if(Math.random()<pm)
               {
                   nb=0;
                   // Count total no of brackets
                   for(k=0;k<popc[i].length();k++)
                        if(popc[i].charAt(k)=='(') nb++;                 
                    // Randomly select which '(' is the crossover starting point
                   mpts=(int)Math.floor(Math.random()*nb);
                   // Get position index of selected '('
                   for(k=0;k<popc[i].length();k++)
                    {
                        if(popc[i].charAt(k)=='(')      mpts--;
                        if(mpts==0)
                         {
                             mpts=k;
                             break;
                         }
                    }
                    //GET DEPTH
                   // Get matching end index of selected '('
                   if(mpts ==0)
                        mpte =(int)popc[i].length()-1;
                   else
                    {
                        cnt=0;
                        for(k=mpts;k<popc[i].length();k++)
                         {
                             if(popc[i].charAt(k)=='(')  cnt++;
                             else if(popc[i].charAt(k)==')') 
                              {
                                  cnt--;
                                  if(cnt==0)
                                   {
                                       mpte =k;
                                       break;
                                   }
                              }
                         }
                    }
                    // Get index of 'f' just before selected '('
                   if(mpts!=0)
                    {
                        for(k=mpts-1;k>=0;k--)
                         {
                             if(popc[i].charAt(k)=='f')
                              {
                                  mpts=k;
                                  break;
                              }
                         }
                    }
                   cnt = 0; // make new entire tree replacing single node tree
                   for(k=0;k<mpts;k++)
                    {
                        if     (popc[i].charAt(k)=='(')    cnt++;
                        else if(popc[i].charAt(k)==')')    cnt--;
                    }
                   //SOP(" Selected Mutation Points  : "+mpts+" & "+mpte+" of : "+popc[i]); 
                   popm[i] = popc[i].substring(0,mpts)+addFT(cnt, new StringBuffer("")).toString()+popc[i].substring(mpte+1);
                   //SOP(" Mutated String : "+i+" = "+popm[i]);
               }
              else  // Directly copy if not selected for mutation
                   popm[i] = popc[i];
          }
     }
}