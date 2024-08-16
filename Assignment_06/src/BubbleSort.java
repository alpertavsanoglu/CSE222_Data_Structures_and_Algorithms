public class BubbleSort extends SortAlgorithm {

	public BubbleSort(int input_array[]) {						// Constructor for initialize BubbleSort with array
		super(input_array);
	}
	
    @Override
    public void sort() {								// Sort method for bubble sort
		int i,j,swap_check=0;							// To chech swap condition
		if(arr == null || arr.length <= 1){				// Error check for if arr empty or 1 element
			return;
		}
		i=0;
		while(swap_check==0){
			swap_check=1;							// initilaze swap_check as false for default
			j=0;
			while(j<arr.length-1-i){
				comparison_counter = comparison_counter + 1;		// we compared numbers so increment comparison counter
				if(arr[j]>arr[j+1]){
					swap(j, j+1);					// Swap arr[j] and arr[j+1]
					swap_check = 0;					// we swapped numbers so check swap_check with 1
				}
				j++;
			}
			i++;
		}
	}
    
    @Override
    public void print() {								// To print bubble sort message
    	System.out.print("Bubble Sort\t=>\t");
    	super.print();
    }
}
