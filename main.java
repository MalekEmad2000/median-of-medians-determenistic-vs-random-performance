package medians;

import java.util.Arrays;
import java.util.Random;

public class main {
	
	
// function to swap when we partition
 static void swap(int[] array, int i, int j)
	    {
	        int temp = array[i];
	        array[i] = array[j];
	        array[j] = temp;
	        
	    }
// partition function we always consider last element as pivot if u want to change pivot swap with last element
 
 static int partition(int array[], int lo, int hi)
 {   
	 
	 
     int x = array[hi], i = lo;
     for (int j = lo; j <= hi - 1; j++)
     {
         if (array[j] <= x)
         {
             swap(array, i, j);
             i++;
         }
     }
     swap(array, i, hi);
     return i;
 }
 // generate random pivot
	static int randomoizedPartition(int [] array,int  lo ,int hi){
		
		
		Random r=new Random();
		int pivot = lo + r.nextInt(hi-lo+1);
		swap(array,hi,pivot);
        return partition(array,lo,hi);
		
	}
	// partition around a certain number
	static int kthPartition(int []array,int lo,int hi,int x) {
		
		   for (int i = lo; i < hi; i++) {
		        if (array[i] == x)
		        swap(array, i, hi);
		
		   }
		return partition(array,lo,hi);
		
	}
	// get median be randomized approach 
	static int randomizedMedian(int [] array,int lo,int hi,int i) {
		
		 // function from CLRS
		
		if(hi==lo)
			return array[lo];
		int r=randomoizedPartition(array,lo,hi);
		int k=r-lo+1;
		
		if(i==k) {
			return array[r];
		}
	    else if(i<k) {
			
			return(randomizedMedian(array,lo,r-1,i));
		}else {

			
			return(randomizedMedian(array,r+1,hi,i-k));
		}
		
			
	}
	static int getMedianDetermenistic(int []array) {
		
		
		
		return medianOfMedians(array,0,array.length-1,(array.length/2)+1);
		
		
		
		
	}
	// just making function take the array
	static int getMedianRandomized(int []array) {
		
		int lo=0;
		int hi=array.length-1;
	    int  medianIndex=((hi+lo+1)/2)+1;
		return randomizedMedian(array,lo,hi,medianIndex);
		
		
	}
	static int medianOfMedians(int []array,int lo,int hi,int k) {
	
		// varying size of array
		int size=hi-lo+1;
		 
		
			
	    // no of groups of division
		int groups;
		
		if(size%5==0) // perfect division
	      groups=(hi-lo+1)/5;
		else
		  groups=((hi-lo+1)/5)+1; // if there is any remainder add another group
		
		// divide into groups of 5
		// array to store all medians
		int []medians=new int[groups];
		// getting all medians
		for(int i=0;i<groups;i++) {
			// median of each group
			if(lo+i*5+5>size) {
				medians[i]=getMedianBySorting(array,lo+i*5,lo+i*5+(size%5));	
				
			}
		
			else
			medians[i]=getMedianBySorting(array,lo+i*5,lo+i*5+5);
		
			
			}

		// the median of medians
		int medianMedians;
		// get median of medians recursively
		if(medians.length==1)
		 medianMedians= medians[medians.length-1];
		else
	    medianMedians= medianOfMedians(medians,0,medians.length-1,(medians.length)/2);
		
		
		// partition around median of medians
		int index=kthPartition(array,lo,hi,medianMedians);
	 
		
		// like the randomized one as in CLRS
		if(index-lo==k-1)
			return array[index];
	 if(index-lo>k-1)
		return medianOfMedians(array,lo,index-1,k);                 // look left
	 else
		return medianOfMedians(array,index+1,hi,k-index+lo-1);       // look right
		
		

	}
	

	// using native sorting for getting median for median of medians 
	
  static	int getMedianBySorting(int []array,int lo, int hi){
	 
	  Arrays.sort(array,lo,hi);
      return array[lo +(hi-lo)/2];
		
	
	}
	

	public static void main(String[] args) {
		
		
		 Random random = new Random();
		 int [] array=new int[10000000] ;
	
		for (int i=0;i<10000000;i++) {
			
			array[i]=random.nextInt();
			
		}
		
		final long startTime = System.nanoTime();
		
		int ans=getMedianRandomized(array);
		
		final long duration = System.nanoTime() - startTime;
		
		System.out.println("median using ranndomized is "+ans);
		System.out.println("duration of randomized method  is "+duration);
		
		System.out.println("-----------------------------------------------");
		
		final long startTime1 = System.nanoTime();
		int ans1=getMedianDetermenistic(array);
		final long duration1 = System.nanoTime() - startTime1;
	 
		System.out.println("median using deterministic method is "+ans1);
		System.out.println("duration of deterministic method  is "+duration1);
		
		
	
	}
}
