public class MergeSort extends SortAlgorithm {
	
	public MergeSort(int input_array[]) {						// Constructor for initialize MergeSort with array
		super(input_array);
	}
	
	private void merge(int[] left_Arr, int[] right_Arr, int[] array){					// merge method for merge arrays
		int i = 0, j = 0, k = 0;
		while(i < left_Arr.length && j < right_Arr.length){
 			comparison_counter = comparison_counter + 1;						// we compared numbers so increment comparison counter
			array[k++] = left_Arr[i] <= right_Arr[j] ? left_Arr[i++] : right_Arr[j++];		// rewrite elements so original array
		}
		while(i < left_Arr.length){
			array[k++] = left_Arr[i++];								// rewrite elements so original array
		}
		while(j < right_Arr.length){
			array[k++] = right_Arr[j++];								// rewrite elements so original array
		}
	}

    private void sort(int[] array){						// Sort method for merge sort
		if(array == null || array.length <= 1){				// Error check for if arr empty or 1 element
			return;
		}
		int[] left_Arr = new int[array.length / 2];			// Temp array for sort
		int[] right_Arr = new int[array.length - array.length / 2];	// Temp array for sort
		System.arraycopy(array, 0, left_Arr, 0, array.length / 2);			// Copy elements to temp array
		if(array.length - array.length / 2 >= 0)
			System.arraycopy(array, array.length / 2, right_Arr,0, array.length - array.length / 2);		// Copy elements to temp array
		sort(left_Arr);							// recursively sort method
		sort(right_Arr);						// recursively sort method
		merge(left_Arr, right_Arr, array);				// merge arrays
	}
    
    @Override
    public void sort() {
	sort(arr);										// helper sort method
    }
    
    @Override
    public void print() {								// To print bubble sort message
    	System.out.print("Merge Sort\t=>\t");
    	super.print();
    }
}
