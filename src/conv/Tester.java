package conv;

import java.util.ArrayList;
import java.util.Arrays;

public class Tester {
	public static void main(String[] args) {
	
	class Number{
	//=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

		//=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
		/*********************************************************
	     *intConv: convert an short of base B to base            *
	     *********************************************************/
		public short[] intConv(short[] input, short from, short to){
	    	//present the input in B into decimal
	    	int sum=0;
	    	for (int i=0; i<input.length;i++){
	    		sum=(int) (sum + (input[i]*Math.pow(from, i)));
	    	}
	        //recursively divide input by R
	    	short M = (short) ((short)(Math.log(sum)/Math.log(to))+1);
	    	short[] u = new short[M];
	    	for(short j=0; j<M && sum!=0; j++){
	    		u[M-j-1]=(short) ((int)sum%to);
	    		sum/=to;
	    	}
	    	return u;
	    }
	    
	       /*******************************************************
	        * reverse: reverse order of array and return short[]  *
	        *******************************************************/
	       public short[] reverse(short[] input){
	    	   short[] output=new short[input.length];
	    	   for(short i=0; i<input.length;i++){
	    		   output[i]=input[input.length-i-1];
	    	   }
	    	   return output;
	       }

		
	       /***********************************************************
	        *fracConv: convert a fraction of base B to base R             *
	        ***********************************************************/
	          public void fracConv(Number B, short from, short to){
	       	  
	        	  short[] input= B.NonRep;
	           //+++++++++++++++++++++++++++++++++++++++++++++++++++ 
	           //represent base R in base B
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
	       	   //+++++++++++++++++++++++++++++++++++++++++++++++++++
	       	   //initialize Arraylist rec to store all fraction parts.
	        	ArrayList<Integer> rec = new ArrayList<Integer>();
	       	   rec.add(getFrac(input, input.length));
	       	   short vsize=0; short wsize=0;
	       	   short[] record=new short[input.length]; int fracPart=0;
	       	   for(int i=0;i<input.length;i++){
	       		   record[i]=input[i];
	       	   }
	          	
	       	   //recursively multiply by newBase, check for repeating fraction part(first input.length terms of the array)
	       	   find: while(!fracZero(record, input.length)){
	       		
	       		   short[] tmp = multiply(record, newBase, from);
	       		   
	       		   record=new short[tmp.length];
	       		   for(int i=0; i<record.length;i++){
	       			   record[i]=tmp[i];
	       		   }
	       		
	       		   fracPart = getFrac(record, input.length);  //get the fraction part of the number after multiplication
	       		   
	       		   rec.add(fracPart);
	       		   for(int i=0;i<rec.size()-1;i++){    //check if the fraction part appeared before
	       			   if(rec.get(i)==fracPart){ 
	       				   vsize=(short)i;
	       				   break find;                 //Found repeated fraction part, break loop
	       			   }   
	       		   } 
	       	   }
	       	   //++++++++++++++++++++++++++++++++++++++++++++++
	       	   //Extract non-fraction part into another array and convert it into base R
	       	   
	       	   //get rid of all the zeros at the end (produced by multiplication)
	       	   int count=record.length;
	       	   while(count>0&&record[count-1]==0){
	       		   count--;
	       	   }
	       	   
	       	   short[] toConv = new short[count-input.length];
	       	   
	       	   for(int k=0;k<toConv.length;k++){
	       		   toConv[k]=record[k+input.length];
	       	   }
	       	   
	       	  //convert non-fraction part into base R. Problem here is: if 
	       	  //the non-fraction part is too long (multiplied for too many times to
	       	  //get the repeated fraction part), intConv does not work.
	       	   short[] vAndW=intConv(toConv, from, to);
	       	   //+++++++++++++++++++++++++++++++++++++++++++++  
	       	   //separate vAndW into V and W
	       	   //case: no-repeating W
	       	   if(fracZero(record, input.length)){
	       		   this.NonRep=reverse(vAndW);
	       		   this.Rep=new short[0];
	       	   }
	       	  
	       	   //case: repeating W
	       	   else{
	       		   rec.remove(rec.size()-1);
	       		   wsize=(short) (rec.size()-vsize);
	       		   short num0=(short) (rec.size()-vAndW.length); //calculate number of 0s in right after binary point
	       		   
	       		   //distributing digits into V and W
	       		   short[] v = new short[vsize];
	       		   short[] w = new short[wsize];
	       		   for(short i=(short) (vsize-1); i>=num0; i--){
	       			   v[i]=vAndW[i-num0];
	       		   }
	       		   if(vsize<num0){
	       			    for(short i=0; i<vsize; i++){
	       			       v[i]=0;
	       		     }
	       			    for(short i=0; i<vAndW.length;i++){
	       			     w[i+num0-vsize]=vAndW[i];
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
	       				  v[i]=vAndW[i-num0];
	       			  }
	       			  for(short i=0;i<wsize;i++){
	       				  w[i]=vAndW[i+vsize-num0];
	       			  }
	       		   }
	       		   else{
	       			   for(short i=0; i<num0; i++){
	       				   v[i]=0;
	       			   }
	       			   for(short i=0;i<wsize;i++){
	       				   w[i]=vAndW[i];
	       			   }
	       		   }
	       		   
	       		   B.NonRep=reverse(v);
	       		   B.Rep=reverse(w);
	       	   }
	          }
		
	          /**********************************************************
	           * multiply: multiply two numbers on a given base, return *
	           *           short[] in reverse order                     *
	           **********************************************************/
	          public short[] multiply(short[]a, short[]b, short base){
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
	   					
	   				}
	   				output[i]=(short) (sum%base);
	   				carry=(short) (sum/base);
	   			}
	   			
	   			return output;
	   		}
		
	          /**********************************************************
	           * fracZero:return false if nonzero occured in frac part  *
	           **********************************************************/
	          public boolean fracZero(short[] input, int fracLength){
	        	  boolean check = true;
	        	  for(int i=0; i<fracLength;i++){
	        		  if(input[i]!=0){
	        			  check=false;
	        		  }
	        	  }
	        	  return check;
	          }
	          
	          /******************************************************************************
	           * getFrac: return a number representation of the fraction part of input array*
	           ******************************************************************************/
	          public int getFrac(short[]input, int fracLength){
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
	         	  }
	         	  for(int i=tmp.size()-1;i>=0;i--){
	         		  output+=tmp.get(i)*Math.pow(10, i);
	         	  }
	         	
	         	  return output;
	           }
		
	          
		//=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
		public Number convert(Number A, short Base) {
			Number B=new Number();
		    B.Base=Base;
	    	short[] bNotRev=intConv(A.Int,A.Base, Base); 
	    	B.Int=reverse(bNotRev);
	    	B.NonRep=A.NonRep;
	    	fracConv(B, A.Base, Base);
	    	return B;
	   	}
		//=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
		
	//=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
	    
	    public void printShortArray(short[] S) {
	        for (int i = S.length-1; i>=0; i--) {
	        	System.out.print(S[i]);
	        }
	    }   
	    public void printNumber(Number N) {
	        System.out.print("(");
	        N.printShortArray(N.Int);
	        System.out.print(".");
	        N.printShortArray(N.NonRep);
	        System.out.print("{");
	        N.printShortArray(N.Rep);
	        System.out.print("})_");
	        System.out.println(N.Base);
	    }
		short Base; short[] Int,NonRep,Rep;
	};
	
Number N1=new Number() ;
    	N1.Base=10; N1.Int=new short[2]; N1.NonRep=new short[3];
    	N1.Int[1]=1; N1.Int[0]=9;
    	N1.NonRep[2]=2; N1.NonRep[1]=4; N1.NonRep[0]=7;
    	N1.Rep=new short[0];
    	N1.printNumber(N1);
    
    	Number N2=new Number() ;
    	short R=2;
    	N2=N1.convert(N1,R);
    	N2.printNumber(N2);

	}

}