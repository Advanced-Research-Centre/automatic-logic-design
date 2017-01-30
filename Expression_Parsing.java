
 class Expression_Parsing
  {
     Function_Evaluate call=new Function_Evaluate();     
     // Evaluate using recursion
     public StringBuffer evaltr(StringBuffer fneq)throws Exception
     {
         StringBuffer g=new StringBuffer(""); 
         for(int i=0;i<fneq.length();i++)
           {
               if(fneq.charAt(i)=='f')
                {
                    int nf=0,nf1=0,nf2=0,inx=i+1,evlt=1;
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
                         for(int j=i+1;j<fneq.length();j++)
                          {
                              if(fneq.charAt(j)=='(') cdpt++;
                              else if(fneq.charAt(j)==')') cdpt--;
                              if(fneq.charAt(j)==',') 
                                   if(cdpt==1) mid=j;
                          }
                     }
                    if(nf!=0)
                     {
                         for(int j=i+1;j<fneq.length();j++)
                          {
                              if(fneq.charAt(j)=='(') 
                               {
                                   if(cdpt==0) strt=j;
                                   cdpt++;
                               }
                              else if(fneq.charAt(j)==')') 
                              {
                                   cdpt--;
                                   nf1=0;
                                   nf2=0;
                                   if(cdpt==1)  
                                    {
                                        for(int k=inx;k<mid;k++)
                                         {
                                             if(fneq.charAt(k)=='f') 
                                                  nf1++;
                                         }
                                        for(int k=(mid+1);k<fneq.length();k++)
                                         {
                                             if(fneq.charAt(k)=='f') 
                                                  nf2++;
                                         }
                                        if(nf1==0 || nf2==0)
                                             fneq=(new StringBuffer(fneq.substring(0,inx))).append(evaltr(new StringBuffer(fneq.substring(inx,j+1)))).append(new StringBuffer(fneq.substring(j+1)));
                                        else
                                             fneq=(new StringBuffer(fneq.substring(0,inx))).append(evaltr(new StringBuffer(fneq.substring(inx,mid)))).append(",").append(evaltr(new StringBuffer(fneq.substring(mid+1,fneq.length()-1)))).append(new StringBuffer(fneq.substring(fneq.length()-1)));
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
                         switch(Integer.parseInt(fneq.substring(i+1,strt)))
                          {
                              case 1: g=call.f1(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      break;
                              case 2: g=call.f2(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      break;
                              case 3: g=call.f3(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      break;
                              case 4: g=call.f4(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      break;
                              case 5: g=call.f5(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      break;
                              case 6: g=call.f6(new StringBuffer(fneq.substring(strt+1,mid)),new StringBuffer(fneq.substring(mid+1,end)));
                                      break;
                          }
                         return g; 
                     }
                }
          }
         return fneq;
     }
  }