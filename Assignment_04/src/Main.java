import java.util.Scanner;
/**
* Main Class ensures simple, intuitive text-based menu to navigate through the system’s functionalities like
* Change directory
* List directory contents
* Create file/directory
* Delete file/directory
* Move file/directory
* Search file/directory
* Print directory tree
* Sort contents by date created
* Exit
* Extra printing whole tree
*/
public class Main {
	private static FileSystem fs = new FileSystem();
	private static Scanner scanner = new Scanner(System.in);
	private static Directory currentDirectory = fs.changeRoad("root/home/user/Documents");		//absolute path (default exist path)
    
	public static void main(String[] args){
		System.out.print("\n");
		while (true){
			System.out.println("===== File System Management Menu =====");
			System.out.println("1. Change directory");
			System.out.println("2. List directory contents");
			System.out.println("3. Create file/directory");
			System.out.println("4. Delete file/directory");
			System.out.println("5. Move file/directory");
			System.out.println("6. Search file/directory");
			System.out.println("7. Print directory tree");
			System.out.println("8. Sort contents by date created");
			System.out.println("9. Exit");
			System.out.println("10. Extra--Print Whole Tree");
			System.out.println("Please select an option:");
			int choice = scanner.nextInt();
			scanner.nextLine();							// consume the newline left by nextInt
			switch(choice){
				case 1:
					changeDirectory();					//case1 for changing directory
				break;
				case 2:
					listContents();						//case 2 for listing contents
				break;
				case 3:
					createFileOrDirectory();				//case 3 for creating file or directory
				break;
				case 4:
					deleteFileOrDirectory();				//case 4 for deleting file or directory
				break;
				case 5:
					moveFileOrDirectory();					//case 5 for moving file or directory
				break;
				case 6:
					searchFileOrDirectory();				//case 6 for searching file or directory
				break;
				case 7:
					printDirectoryTree();					//case 7 for printing current directory from root
				break;
				case 8:
					sortContentsByDate();					//case 8 for sorting childs acccording to creation time
				break;
				case 9:
					System.out.println("Exiting...");			//case 9 for exiting
					scanner.close();
					System.exit(0);
				break;
				case 10:
					printWholeTree();					//case 10 extra for printing whole tree
				break;
				default:
					System.out.println("Invalid option. Please try again.");		//invalid input
			}
		}
	}
	/**
	* Changes the current working directory to a new directory specified by the user.
	*/
	private static void changeDirectory(){
		System.out.println("Current directory: " + fs.existRoad(currentDirectory));
		System.out.print("Enter new directory path: ");
		String takeDirectory = scanner.nextLine();								//take new directory path from user
		Directory newHome = fs.changeRoad(takeDirectory);
		if(newHome != null){
			currentDirectory = newHome;									//currntDirectory now new directory
			System.out.println("Directory changed to: " + fs.existRoad(currentDirectory) + "\n");
			}
			else{
				System.out.println("Directory not found.\n");						//error handling
		}
	}
	/**
	* Lists all contents of the current directory.
	*/
	private static void listContents(){
		fs.listContents(currentDirectory);									//for listing contents of current directory
		System.out.println("\n");
	}
	/**
	* Createing file or a directory in the current directory based on user input.
	*/
	private static void createFileOrDirectory(){
		System.out.println("Current directory: " + fs.existRoad(currentDirectory));
		System.out.print("Create file or directory (f/d): ");
		String str = scanner.nextLine();									//take file or directory info
		System.out.print("Enter name for new " + (str.equals("d") ? "directory" : "file") + ": ");
		String name = scanner.nextLine();									//take name for element
		if(str.equals("f")){
			fs.createFile(name, currentDirectory);								//create file
			System.out.println("File created: " + name + "\n");
		}
		else if(str.equals("d")){
			fs.createDirectory(name, currentDirectory);							//create directory
			System.out.println("Directory created: " + name + "\n");
		}
	}
	/**
	* Deleting file or a directory in the current directory based on user input.
	*/
	private static void deleteFileOrDirectory(){
		System.out.println("Current directory: " + fs.existRoad(currentDirectory));
		System.out.print("Enter name of file/directory to delete: ");
		String name = scanner.nextLine();									//Take name for deleting
		fs.deleteFileOrDirectory(name, currentDirectory);							//delete file or directory
	}
	/**
	* moving file or a directory in the current directory based on user input.
	*/
	private static void moveFileOrDirectory(){
		System.out.println("Current directory: " + fs.existRoad(currentDirectory));
		System.out.print("Enter the name of file/directory to move: ");
		String name = scanner.nextLine();									//take name for moving file or directory
		System.out.print("Enter new directory path: ");
		String newWay = scanner.nextLine();									//take path for moving
		Directory newHoome = fs.changeRoad(newWay);
		if(newHoome != null && currentDirectory != null){
			fs.moveElement(name, currentDirectory, newHoome);						//move the file or directory
		}
		else{
			if(newHoome == null){										//error about target directory
				System.out.println("Target directory not found." + "\n");
			}
			if(currentDirectory == null){									//error about current directory
				System.out.println("Current directory is not set." + "\n");
			}
		}
	}
	/**
	* Searching file or a directory based on user input.
	*/
	private static void searchFileOrDirectory(){
		System.out.print("Enter search query: ");
		String info = scanner.nextLine();										//Take input for search
		System.out.println("Searching from root...");
		String find_res = fs.search(info, fs.getRoot());								//Search input (file or directory)
		System.out.println(find_res+"\n");
	}
	/**
	* Printing Directory tree from root to current
	*/
	public static void printDirectoryTree(){
		Directory base = fs.getRoot();
		fs.printDirectoryTree(base, "", currentDirectory);								//print directory from root to current
		System.out.print("\n");
	}
	/**
	* Sorting File and directories according to the creating time
	*/
	private static void sortContentsByDate(){
		if(currentDirectory != null){
			fs.sortDirectoryByDate(currentDirectory);								//sort childs according to the creating time
			System.out.print("\n");
		}
		else{
			System.out.println("Current directory is not set. Cannot sort.\n");					// errror while sorting
		}
	}
/////////EXTRA
	/**
	* Printing Directory tree from root to last child
	*/
	private static void printWholeTree(){
		fs.printWholeTree(fs.getRoot(), "", currentDirectory);								//Printing Directory tree from root to last child
	}
}
