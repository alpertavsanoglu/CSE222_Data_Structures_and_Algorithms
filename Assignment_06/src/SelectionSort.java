public class SelectionSort extends SortAlgorithm {

	public SelectionSort(int input_array[]) {				// Constructor for initialize SelectionSort with array
		super(input_array);
	}

    @Override
    public void sort() {							// Sort method for selection sort
		int i,j,num_smallest;
		if(arr == null || arr.length <= 1){				// Error check for if arr empty or 1 element
			return;
		}
		i=0;
		while(i<arr.length-1){
			num_smallest=i;						// Firstly we need to find minimum element in array
			j=i+1;
			while(j<arr.length){
				comparison_counter = comparison_counter + 1;		// we compared numbers so increment comparison counter
				num_smallest = (arr[j] < arr[num_smallest]) ? j : num_smallest;	//check min element
				j++;
			}
			swap(num_smallest, i);					// Swap the elements arr[num_smallest] and arr[i]
			i++;
		}
	}

    @Override
    public void print() {							// To print Selection sort message
    	System.out.print("Selection Sort\t=>\t");
    	super.print();
    }
}
