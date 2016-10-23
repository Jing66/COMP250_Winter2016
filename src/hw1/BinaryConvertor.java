package hw1;

import java.util.ArrayList;
import java.util.Arrays;

public class BinaryConvertor {
	
   
	
    /*******************************************************
     *intConv: convert an short of base B to base R          *
     *******************************************************/
    public static short[] intConv(short[] input, short from, short to){
    	/*
        ArrayList<shorteger> xtemp = new ArrayList<shorteger>();
    	for(short i=0; input!=0;i++){
    		xtemp.add(i, input%10);
    		input /= 10;
    	}*/
    	int sum=0;
    	for (int i=0; i<input.length;i++){
    		sum=(int) (sum + (input[i]*Math.pow(from, i)));
    		System.out.println("sum one time="+input[i]*Math.pow(from, i));
    	}
    	System.out.print("sum:"+sum);
        //convert base
    	short M = (short) ((short)(Math.log(sum)/Math.log(to))+1);
    	short[] u = new short[M];
    	for(short j=0; j<M && sum!=0; j++){
    		u[M-j-1]=(short) ((int)sum%to);
    		sum/=to;
    	}
    	return u;
    }
    
    
    /***********************************************************
     *conv: convert a fraction of base B to base R             *
     ***********************************************************/
       public static void fracConv(short[] input, short from, short to){
    	   short newBase[];
       	if(from<to){
       	   short[] oldBase;
	           if(to>=10){
	        	   oldBase= new short[2];
	        	   oldBase[1]=(short)(to/10);
	        	   oldBase[0]=(short)(to%10);
	           }
	           else{
	        	   oldBase = new short[1];
	        	   oldBase[0]=(short)(to);
	           }
	       	   newBase = reverse(intConv(oldBase, (short)10, from));
	       	  
          }
          else{
       	   if(to>=10){
       		   newBase= new short[2];
       		   newBase[0]=(short) (to%10);
       		   newBase[1]=(short) (to/10);
       	   }
       	   else{
       		   newBase = new short[1];
       		   newBase[0]=(short)to;
       	   }
          }
       	System.out.println("oldToBase in fromBase form:"+Arrays.toString(newBase)); 
    	   
    	   //calculate the fraction in decimal
    	   double tmp=0;
    	   for(short k=0; k<input.length; k++){
    		   tmp+=(double)input[k]*Math.pow(from, -input.length+k);
    	   }
    	   System.out.println("tmp:"+tmp);//倒input. checked
    	   
    	   
    	   //++++++++++++++++++++++++++++++++++++++++
    	   //check repeating number
    	   short carry=(short) Math.pow(10, input.length);
    	   short frac=(short)(tmp*carry);
    	   System.out.print(frac+"\\"+carry);
    	   
    	   ArrayList<Short> rec=new ArrayList<Short>();
    	   short nonFrac; int k=0; short record=0; short find=0;
    	   repeat: do{
    		  if(frac<carry){
    			  rec.add(frac);
    			  record = frac;
    		  }
    		  else{
    			  record = (short)(frac%carry);
    			  rec.add (record);
    			  System.out.print("frac:"+frac+"record:"+record+"\\");  ///checked
    		  }
    		   
    		  nonFrac=(short) (frac/carry);
    		  
    		   frac*=to;
    		   System.out.print("\nFrac:"+frac);  ///checked
    		   System.out.println("count:"+rec.size());///checked
    		   
    		   for(k=0;k<rec.size()&k<find;k++){
    			   if(record==(short)(rec.get(k))){
    				   break repeat;
    			   }
    		   }
    		   find++;
    	   }while(record!=0);
    				   
    	   //++++++++++++++++++++++++++++++++++++++++++++++
    	   //convert nonFrac into short[]
    	   short temp=(short) (frac/carry);
    	   ArrayList<Short> nonFracList = new ArrayList<Short>();
       	       while(temp!=0){
       		      nonFracList.add((short)(temp%10));
       		      temp/= 10;
       		      System.out.print("temp:"+temp);
          	}
    	   short[] toConv =new short[nonFracList.size()];
    	     for(short i=0; i<toConv.length;i++){
    	    	 toConv[i]=nonFracList.get(i);
    	     }
    	   System.out.println("toConv:"+Arrays.toString(toConv));  ///checked
    	   //+++++++++++++++++++++++++++++++++++++++++++++  
    	   //case: no-repeating W
    	   if(frac%carry==0){
    		   short[] v = intConv(toConv, from, to);
    		   
    		   System.out.print("v= "+Arrays.toString(v)+"w:{}");
    		   
    	   }
    	   
    	   //case: repeating W
    	   else{
    		   short vsize; short wsize; 
    		  size:  for(vsize=0; vsize<rec.size();vsize++){
    			        if (rec.get(vsize)==frac%carry){
    				   break size;
    			   }
    		   }
    		  
    		  
    		   wsize=(short) (rec.size()-vsize);
    		   
    		   short[] intmed=intConv(toConv, (short) 10, to);
    		   short num0=(short) (rec.size()-intmed.length);
    		   
    		   
    		   //distributing digits shorto V and W
    		   short[] v = new short[vsize];
    		   short[] w = new short[wsize];
    		   for(short i=(short) (vsize-1); i>=num0; i--){
    			   v[i]=intmed[i-num0];
    		   }
    		   if(vsize<num0){
    			    for(short i=0; i<vsize; i++){
    			       v[i]=0;
    		     }
    			    for(short i=0; i<intmed.length;i++){
    			     w[i+num0-vsize]=intmed[i];
    		     }
    			    for(short i=0; i<num0-vsize; i++){
    			    	w[i]=0;
    			    }
    		   }
    		   
    		   else if (vsize>num0){
    			   for(short i=0; i<num0;i++){
    				   v[i]=0;
    				 
    			   }
    			  for(short i=num0;i<vsize;i++){
    				  v[i]=intmed[i-num0];
    			  }
    			  for(short i=0;i<wsize;i++){
    				  w[i]=intmed[i+vsize-num0];
    			  }
    		   }
    		   else{
    			   for(short i=0; i<num0; i++){
    				   v[i]=0;
    			   }
    			   for(short i=0;i<wsize;i++){
    				   w[i]=intmed[i];
    			   }
    		   }
    		   System.out.print("V="+Arrays.toString(v)+"\nW="+Arrays.toString(w));
    	   }
       }
    
       /*******************************************************
        * reverse                                      *
        *******************************************************/
       public static short[] reverse(short[] input){
    	   short[] output=new short[input.length];
    	   for(short i=0; i<input.length;i++){
    		   output[i]=input[input.length-i-1];
    	   }
    	   return output;
       }
       
    
       /*******************************************************
        * multiply: multiply two numbers on a given base      *
        *******************************************************/
       public static short[] multiply(short[]a, short[]b, short base){
			short[]output = new short[a.length+b.length];
			short[][] tmp = new short[b.length][a.length+b.length];
			short prod;
			for(int j=0;j<b.length;j++){
				short carry=0;
				for(int i=0;i<a.length;i++){
					prod=(short)(a[i]*b[j]+carry);
					carry=(short)(prod/base);
					tmp[j][i+j]=(short) (prod%base);
					
				}
				tmp[j][a.length+j]=carry;
				
			}
			short carry=0;
			for(int i=0;i<(a.length+b.length);i++){
				short sum=carry;
				for(int j=0;j<b.length;j++){
					sum+=tmp[j][i];
					System.out.print("sum:"+sum);
				}
				output[i]=(short) (sum%base);
				carry=(short) (sum/base);
				System.out.println("\ncarry:"+carry);
			}
			
			return output;
		}
       
       /**********************************************************
        * fracZero:return false if nonzero occured in frac part  *
        **********************************************************/
       public static boolean fracZero(short[] input, int fracLenth){
     	  boolean check = true;
     	  for(int i=0; i<fracLenth;i++){
     		  if(input[i]!=0){
     			  check=false;
     		  }
     	  }
     	  return check;
       }
       
       
       /******************************************************************************
        * getFrac: return number representation of the fraction part of input array  *
        ******************************************************************************/
       public static int getFrac(short[]input, int fracLength){
     	  int output=0;
     	  ArrayList<Short> tmp=new ArrayList<Short>();
     	  for(int i=0;i<fracLength;i++){
     		  if (input[i]<(short)10){
     			  tmp.add(input[i]);
     		  }
     		  else{
     			  tmp.add((short) (input[i]/10));
     			  tmp.add((short)(input[i]%10));
     		  }
     		  //System.out.print(tmp);
     	  }
     	  for(int i=tmp.size()-1;i>=0;i--){
     		  output+=tmp.get(i)*Math.pow(10, i);
     	  }
     	
     	  return output;
       }
       
    /*******************************************************
     * main: testing                                       *
     *******************************************************/
    public static void  main(String[] arg){
    	
    	//System.out.prshort(Arrays.toString(BinaryConvertor.shortConv(12,10, 2)));
    	short[] x={2, 1, 6, 0, 10, 0, 6, 7, 8, 4, 1, 3, 5, 11, 0, 6, 9, 1, 1};
    	//fracConv(x,(short)10,(short)2);
    	
    	//System.out.println(Arrays.toString(x));
    	short[] y={5,0,5};
    	short[] z={0,2};
    	short[] tmp=BinaryConvertor.intConv(x,(short)12,(short)8);
    	//System.out.println(Arrays.toString(tmp));
    	//short[] multi=BinaryConvertor.multiply(y, z, (short)10);
    	System.out.println("\nmulti"+Arrays.toString(BinaryConvertor.multiply(y, z, (short)8)));
    	//System.out.print(BinaryConvertor.multiply(y, z, (short)10));
    	//System.out.println(BinaryConvertor.getFrac(x,2));
    	
    }

}
