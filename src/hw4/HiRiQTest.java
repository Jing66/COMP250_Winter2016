package hw4;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Hashtable;


public class HiRiQTest {
	static ArrayList<HiRiQ> garbage;
	static Hashtable<Integer, Integer> mark;
	static ArrayList<String> sucPath;
	static int level;
	static int COUNT;
	/***********findPath wrapper method: initialize static variables*****************/
	static void findPath (boolean[] input){
		
		
		HiRiQ cur = new HiRiQ((byte) 2);
		cur.store(input);
cur.print();
		level = cur.getLevel();
		sucPath = new ArrayList<String>();
		garbage= new ArrayList<HiRiQ>();
		mark = new Hashtable<Integer, Integer>();
		boolean initBot = false;
		
		ArrayList<String> pathTest;
		pathTest = cur.moveChoice(true);
		if(pathTest.size()==0){
			pathTest=cur.moveChoice(false);
			mark.put(cur.config+cur.weight,1);
			cur=cur.move(pathTest.get(0), garbage);
			sucPath.add(pathTest.get(0));
			initBot=true;
		}
		if(pathTest.size()==0) return;

		sucPath = find(cur, pathTest.get(0));
		if(initBot) sucPath.add(0,pathTest.get(0));
System.out.print("Garbage: \n"+garbage);
System.out.print("FINAL PATH:"+sucPath);
System.out.print("\nnumber checked: "+COUNT);
	}

	
	/*******************recursion*********************/
	//solve the configuration using recursion
	static ArrayList<String> find(HiRiQ input, String path){
	COUNT++;
		//check how many times cur has been visited & mark it
		if(!mark.containsKey(input.config+input.weight)) mark.put(input.config+input.weight, 1);
		else{
			int numVisited = mark.get(input.config+input.weight);
			numVisited++;
			mark.put(input.config+input.weight, numVisited);
		}


		HiRiQ cur = input;
	
		//get the list of pathways moving up & down
		ArrayList<String> pathDown = input.moveChoice(true);
		ArrayList<String> pathUp = input.moveChoice(false);
		

		//Base case: solved
		if(input.IsSolved()){
			return sucPath;
		}

		//Base case 2: bottom && not solved
		else if(pathDown.size()==0&&!input.IsSolved()){
			return null;
		}
		
		//else: in the middle of the tree
		else{
			ArrayList<String> child;
			int count;
			
			//search child
			child=null; //initialize
			for(count=-1; count<pathDown.size()-1;){
				count++;
				
				path=pathDown.get(count); 
				HiRiQ next = cur.move(pathDown.get(count), garbage);//update cur to child

				if(mark.containsKey(next.config+next.weight)) continue; //skip visited child
				
				child = find(next, pathDown.get(count));
				if(child!=null)break;

			}

			//update height of the tree
			if(cur.getLevel()<level) {level = cur.getLevel(); }
			
			//goal is in breaked child
			if(child!=null){
				sucPath.add(0,path);
				return sucPath;
			}

			//goal is in parent, move up
			else{
				HiRiQ prev;
				//done searching all the child of this node, put into garbage
				if(cur.getLevel()>level){
					garbage.add(cur); 
					return null;
				}
				else{
					//get to parent
   				for(int i=0; i<pathUp.size();i++){
						path=pathUp.get(i);
						prev = cur.move(path, garbage);

						//skip the parent visited
						if(mark.containsKey(prev.config+prev.weight)) continue;
						child=find(prev, path);
						if(child!=null) break;
					}
					if(child!=null){
						sucPath.add(0,path);
						return sucPath;
					}
					else {
						return find(cur.move(path, garbage), path);
					}
				}
			}

		}


	}

	public static void main(String[] args) {
				//true  = white, false=black
		

		
		HiRiQ W=new HiRiQ((byte) 0) ;
		//W.print(); System.out.println(W.IsSolved());
		HiRiQ X=new HiRiQ((byte) 1) ;
		X.print(); System.out.println(X.IsSolved());
		HiRiQ Y=new HiRiQ((byte) 2) ;
		//Y.print(); System.out.println(Y.IsSolved());
		HiRiQ Z=new HiRiQ((byte) 3) ;
		//Z.print(); System.out.println(Z.IsSolved());
		
		//boolean[] B={false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, true, false, false, false, true, false, false, false, true, false, false, false, false, false};
		
		//Z.store(B);
System.out.print("original is: \n");
		//Z.print(); System.out.println(Z.IsSolved());
			//***********tester methods goes here*********************/
		//findPath(B);
		/*for(HiRiQ a: test){
			System.out.print("\n");
			a.print();
		}*/
		
	}
}


 class HiRiQ {
	
	//int is used to reduce storage to a minimum...
	  public int config;
	  public byte weight;
	  

	//initialize the configuration to one of 4 START setups n=0,1,2,3
	 public  HiRiQ(byte n)
	  {
		 
		  if (n==0)
		   {config=65536/2;weight=1;}
		  else
			  if (n==1)
			  {config=4403916;weight=11;}
			  else
				  if (n==2)
				  {config=-1026781599; weight=21;}
				  else
				  {config=-2147450879; weight=32;}
	  }
	  
	  //initialize the configuration to one of 4 START setups n=0,10,20,30

	  public boolean IsSolved()
	  {
		  return( (config==65536/2) && (weight==1) );
	  }

	//transforms the array of 33 booleans to an (int) cinfig and a (byte) weight.
	  public void store(boolean[] B)
	  {
	  int a=1;
	  config=0;
	  weight=(byte) 0;
	  if (B[0]) {weight++;}
	  for (int i=1; i<32; i++)
	   {
	   if (B[i]) {config=config+a;weight++;}
	   a=2*a;
	   }
	  if (B[32]) {config=-config;weight++;}
	  }

	//transform the int representation to an array of booleans.
	//the weight (byte) is necessary because only 32 bits are memorized
	//and so the 33rd is decided based on the fact that the config has the
	//correct weight or not.
	 boolean[] load(boolean[] B)
	  {
	  byte count=0;
	  int fig=config;
	  B[32]=fig<0;
	  if (B[32]) {fig=-fig;count++;}
	  int a=2;
	  for (int i=1; i<32; i++)
	   {
	   B[i]= fig%a>0;
	   if (B[i]) {fig=fig-a/2;count++;}
	   a=2*a;
	   }
	  B[0]= count<weight;
	  return(B);
	  }
	  
	//prints the int representation to an array of booleans.
	//the weight (byte) is necessary because only 32 bits are memorized
	//and so the 33rd is decided based on the fact that the config has the
	//correct weight or not.
	  void printB(boolean Z)
	  {if (Z) {System.out.print("[ ]");} else {System.out.print("[@]");}}
	  
	  void print()
	  {
	  byte count=0;
	  int fig=config;
	  boolean next,last=fig<0;
	  if (last) {fig=-fig;count++;}
	  int a=2;
	  for (int i=1; i<32; i++)
	   {
	   next= fig%a>0;
	   if (next) {fig=fig-a/2;count++;}
	   a=2*a;
	   }
	  next= count<weight;
	  
	  count=0;
	  fig=config;
	  if (last) {fig=-fig;count++;}
	  a=2;

	  System.out.print("      ") ; printB(next);
	  for (int i=1; i<32; i++)
	   {
	   next= fig%a>0;
	   if (next) {fig=fig-a/2;count++;}
	   a=2*a;
	   printB(next);
	   if (i==2 || i==5 || i==12 || i==19 || i==26 || i==29) {System.out.println() ;}
	   if (i==2 || i==26 || i==29) {System.out.print("      ") ;};
	   }
	   printB(last); System.out.println() ;

	  }
	
	
 
	    /*********************my own methods for HiRiQ object ***************************/
	    /*******Define: Children (moveDown) = #black++ (W substitution), Parent (moveUp)*
	  	* = #white++ (B substitution); true=white, black=false;**************************/
	  
		//get the current level (i,e, how many blacks. goal level = 32)
		int getLevel(){
			boolean[] arr=new boolean[33];
			this.load(arr);
			int curLev = 0;
			for (int i=0; i<33;i++){
				if(arr[i]==false) curLev++;
			}
			return curLev;
		}


		//return the movements/path toward its children (dir==true) OR parent (dir==false)
		 ArrayList<String> moveChoice(boolean dir){
			ArrayList<String> mvDown = new ArrayList<String>();
			boolean[] arr=new boolean[33];
			this.load(arr);
			String mvmt;
			//checking for horizontal pattern
			for (int i=0; i<31;i++){
				//don't need to check those
				if(i==1|| i==2||i==4||i==5||i==11||i==12||i==18||i==19||i==25||i==26||i==28||i==29) continue;
				mvmt = checkSub(arr, i, 1, 2, dir);
				if(mvmt!=null) mvDown.add(mvmt);
				}

			//checking for vertical pattern
			for(int i=0;i<25;i++){
				if(i==0||i==1||i==2){
					mvmt = checkSub(arr, i, 3, 8, dir);
				}
				else if(i==3||i==4||i==5){
					//check i+5, i+12
					mvmt = checkSub(arr, i, 5, 12, dir);
				}
				else if(i==8||i==9||i==10||i==7 || i==12||i==6||i==11){
					//check i+7,i+14
					mvmt = checkSub(arr, i, 7, 14, dir);
				}
				else if(i==15||i==16||i==17){
					//check i+7, i+12
					mvmt = checkSub(arr, i, 7, 12, dir);
				}
				else if(i==22||i==23||i==24){
					//check i+5,i+8
					mvmt = checkSub(arr, i, 5, 8, dir);
				}
				else continue;
				if(mvmt!=null) mvDown.add(mvmt);
			}
			
			
		   return mvDown;
		}

		//moveChoice helper: given a boolean[], find the 3 entries satisfy W substitution: WWB->WBB/BWW->WBB
		String checkSub(boolean[] arr, int index, int offset1, int offset2, boolean tag){
			//if (arr[i] XOR arr[i+2]) == true (WWB/WBB/BWW/BBW) && arr[i+1]==white
			if(((arr[index]&&!arr[index+offset2])||(!arr[index]&&arr[index+offset2]))&&(arr[index+offset1]==tag)){
					//catenate
					String output = Integer.toString(index);
					if(tag) output = output.concat("W");
					else output = output.concat("B");
					String tmp2 = Integer.toString(index+offset2);
					output = output.concat(tmp2);
					return output;
				}
			else return null;
		}

		
		
		//move the HiRiQ according to the movement given; using garbage to recycle
		HiRiQ move( String mvmt, ArrayList<HiRiQ> garbage){
			HiRiQ pre = this;
			HiRiQ output;
			char mvChoice;
			//if nothing can be re-used, make new object; else, use the last one in garbage
			if(garbage.size()==0) output = new HiRiQ((byte)1);
			else {
				output = garbage.get(garbage.size()-1);
				garbage.remove(garbage.size()-1);
			}
			
			//get the array representation of unmodified HiRiQ
			boolean[] arr = new boolean[33];
			pre.load(arr);

			//parse the mvmt, left & right num = before & after W/B; mvChoice = W/B
			String[] mv = mvmt.split("W|B");
			int leftNum = Integer.parseInt(mv[0]);
			int rightNum = Integer.parseInt(mv[1]);
			if(leftNum>9) mvChoice = mvmt.charAt(2);
			else mvChoice = mvmt.charAt(1);
			int midNum= getMid(leftNum, rightNum);
			
			//Re-set the boolean array: left & right pixel flip
			arr[leftNum] = !arr[leftNum];
			arr[rightNum] = !arr[rightNum];
			//middle pixel =0 if W, =1 if B
			arr[midNum] = (mvChoice=='W'?false:true);

			//re-store the boolean into the output HiRiQ
			output.store(arr);
			
			return output;
		}

		//HiRiQ move(HiRiQ,String,List<HiRiQ>) helper method to flip pixel during substitution
		int getMid(int left, int right){
			int mid =  -1;
			int dif = right-left;
			if(dif==2) mid=left+1;
			else if (dif==8 && (left==1||left==2||left==0)) mid = left+3;
			else if(dif==12&& (left==3||left==4||left==5)) mid=left+5;
			else if(dif==14) mid=left+7;
			else if (dif==12&& (left==15||left==16||left==17)) mid=left+7;
			else mid=left+5;

			return mid;
		}
		
}

