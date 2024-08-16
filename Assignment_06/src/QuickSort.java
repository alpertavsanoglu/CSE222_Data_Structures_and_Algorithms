public class QuickSort extends SortAlgorithm {

	public QuickSort(int input_array[]) {				// Constructor for initialize  QuickSort with array
		super(input_array);
	}
	
    private int partition(int[] array,int startp, int endp){				// partition method for selection sort
		int pivot = arr[endp]; 				//pivot number is last number
		int i = startp-1;
		if(arr == null || arr.length <= 1){				// Error check for if arr empty or 1 element
			return 0;
		}
		while(startp<endp){
			comparison_counter = comparison_counter+1;		// we compared numbers so increment comparison counter
			if(arr[startp] < pivot){
				i++;
				swap(i,startp);					// Swap the elements arr[i] and arr[j]
			}
			startp++;
		}
		swap(i+1, endp);							// Swap the elements arr[i] and arr[endp]
		i++;
		return i;
	}

    private void sort(int[] array,int startp, int endp){			// Sort method for quick sort
		if (startp < endp) {
			int partition_Index = partition(arr,startp, endp);	//find partition_Index with helper function
			sort(arr,startp, partition_Index - 1);			// recursively sort method
			sort(arr,partition_Index + 1, endp);			// recursively sort method
		}
	}
    @Override
    public void sort() {
    	sort(arr, 0, arr.length-1);									// helper sort method
    }

    @Override
    public void print() {								// To print quick sort message
    	System.out.print("Quick Sort\t=>\t");
    	super.print();
    }
}
