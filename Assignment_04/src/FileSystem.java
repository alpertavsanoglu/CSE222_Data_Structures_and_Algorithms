import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

/**
* Manages the root directory and contains methods for user interactions such as navigating the directory tree, and managing files and directories
*/

public class FileSystem{
	private Directory root;

	public FileSystem(){									//for default absoulute directory
		this.root = new Directory("root", null);
		Directory home = new Directory("home", root);
		root.addElement(home);
		Directory user = new Directory("user", home);
		home.addElement(user);
		Directory documents = new Directory("Documents", user);
		user.addElement(documents);
	}
	public Directory getRoot(){								//get root
		return root;
	}
	/**
	* Creates a file within the specified directory.
	*
	* @param name  the file for create
	* @param bottom the directory for create place
	*/
	public void createFile(String name, Directory bottom){
		File create_File = new File(name, bottom);
		bottom.addElement(create_File);						//add file to directory
	}
	/**
	* Creates a directory within the specified directory.
	*
	* @param name  the directory for create
	* @param bottom the directory for create place
	*/
	public void createDirectory(String name, Directory bottom){
		Directory create_Directory = new Directory(name, bottom);
		bottom.addElement(create_Directory);					//add directory
	}
	/**
	*  Lists all contents of the specified directory, separating the output between directories and files.
	*
	* @param dir the directory whose contents are to be listed.
	*/
	public void listContents(Directory dir){
		if(dir != null){
			System.out.println("Listing contents of " + existRoad(dir) + ":");
			List<FileSystemElement> directories = new ArrayList<>();
			List<FileSystemElement> files = new ArrayList<>();
			for(FileSystemElement elem : dir.getChildren()){
				if(elem instanceof Directory){
					directories.add(elem);
				}
				else if(elem instanceof File){
					files.add(elem);
				}
			}
			for(FileSystemElement elem : directories){
				System.out.println(" * " + elem.getName() + "/");
			}
        		for(FileSystemElement elem : files){
				System.out.println( elem.getName());
			}
		}
		else{
			System.out.println("No current directory set or directory is empty.\n");
		}
	}
	/**
	* Deletes a file or directory from a specified directory. If the target is a directory, it recursively deletes all contents.
	*
	* @param name the name of the file or directory to delete.
	* @param parent the directory from which the file or directory will be deleted.
	*/
	public void deleteFileOrDirectory(String name, Directory parent){
		FileSystemElement elem = findElementByName(name, parent);
		if(elem != null){
			if(elem instanceof Directory){
				recursiveDelete((Directory) elem);
			}
			parent.removeElement(elem);
			System.out.println(elem instanceof Directory ? "Directory deleted: " + name+"\n" : "File deleted: " + name+"\n");
		}
		else{
			System.out.println("File/Directory named > " + name + " not found under " + parent.getName() + ".\n");
		}
	}
	/**
	* Recursively deletes all contents of a directory, including all subdirectories and files.
	*
	* @param dir the directory from which to start deleting contents.
	*/
	private void recursiveDelete(Directory dir){
		LinkedList<FileSystemElement> children = new LinkedList<>(dir.getChildren());
		for(FileSystemElement elem : children){
			if(elem instanceof Directory){
				recursiveDelete((Directory) elem);
			}
			dir.removeElement(elem);
		}
	}
	/**
	* Moves a file or directory from one directory to another. If the source and destination are the same, the operation is aborted.
	*
	* @param name the name of the file or directory to move.
	* @param oldParent the current directory from which the element will be moved.
	* @param newParent the destination directory to which the element will be moved.
	*/
	public void moveElement(String name, Directory oldParent, Directory newParent){
		if(oldParent == newParent){
			System.out.println("The source and destination directories are the same.\n");
			return;
		}
		if(newParent == null){
			System.out.println("Destination directory is invalid.\n");
			return;
		}
		FileSystemElement toMove = findElementByName(name, oldParent);
		if(toMove != null){
			String destinationPath = existRoad(newParent);
			oldParent.removeElement(toMove);
			newParent.addElement(toMove);
			if(toMove instanceof Directory){
				System.out.println("Directory moved: " + name + " to " + destinationPath + "\n");
			}
			else{
				System.out.println("File moved: " + name + " to " + destinationPath + "\n");
			}
		}
		else{
			System.out.println("File/Directory named > " + name + " not found under " + oldParent.getName() + ".\n");
		}
	}
	/**
	* Searches for a file or directory by name starting from a specified directory and traverses all subdirectories.
	*
	* @param name the name of the file or directory to search for.
	* @param startDir the directory from which the search will start.
	* @return a string indicating the path of the found item or "Not Found" if the item does not exist.
	*/
	public String search(String name, Directory startDir){
		String result = recursiveSearch(name, startDir, startDir.getName());
		if(result != null){
			return "Found: " + result;
		}
		else{
			return "Not Found";
		}
	}
	private String recursiveSearch(String name, Directory dir, String path){
		for(FileSystemElement elem : dir.getChildren()){
			String currentPath = path + "/" + elem.getName();
			if(elem.getName().equals(name)){
				return currentPath;
			}
			if(elem instanceof Directory){
				String found = recursiveSearch(name, (Directory) elem, currentPath);
				if (found != null){
					return found;
				}
			}
		}
		return null;
	}
	/**
	* Prints current directory tree starting from the given directory.
	* All directories and files within the structure are recursively printed.
	*
	* @param dir the directory from which to start printing.
	* @param prefix a string prefix used for formatting the printed tree structure.
	* @param currentDirectory the directory currently being accessed by the user.
	*/
	public void printDirectoryTree(Directory dir, String prefix, Directory currentDirectory){
		String directoryMarker = "*";
		String currentDirMarker = (dir == currentDirectory) ? " (Current Directory)" : "";
		System.out.println(prefix + directoryMarker + " " + dir.getName() + "/" + currentDirMarker);
		String newPrefix = prefix + "   ";
		if(dir == currentDirectory){
			for(FileSystemElement elem : dir.getChildren()){
				System.out.println(newPrefix + (elem instanceof Directory ? "*" : "") + " " + elem.getName() );
			}
		}
		else{
			for(FileSystemElement elem : dir.getChildren()){
				if(elem instanceof Directory){
					if(isOnPathToCurrent((Directory) elem, currentDirectory)){
						printDirectoryTree((Directory) elem, newPrefix, currentDirectory);
					}
				}
			}
		}
	}
	/**
	* Sorts the contents of the specified directory by the creation date of each file and directory.
	*
	* @param dir the directory whose contents will be sorted.
	*/
	public void sortDirectoryByDate(Directory dir){
		if(dir == null){
			System.out.println("Directory is null, cannot sort.\n");
			return;
		}
		System.out.println("Sorted contents of " + existRoad(dir) + " by date created:");
		dir.getChildren().sort((e1, e2) -> e1.getDateCreated().compareTo(e2.getDateCreated()));
		for(FileSystemElement elem : dir.getChildren()){
			if(elem instanceof Directory){
				System.out.println(" * " + elem.getName() + "/ (" + elem.getDateCreated() + ")");
			}
			else{
				System.out.println("   " + elem.getName() + " (" + elem.getDateCreated() + ")");
			}
		}
	}
////////HELPER FUNCTIONS
	/**
	* Determines whether a given directory is on the path from the root to the current directory.
	*
	* @param dir the directory to check.
	* @param currentDirectory the current directory as the endpoint of the path.
	* @return true if dir is on the path to currentDirectory, false otherwise.
	*/
	private boolean isOnPathToCurrent(Directory dir, Directory currentDirectory){
		Directory temp = currentDirectory;
		while (temp != null){
			if(temp == dir){
				return true;
			}
			temp = temp.getParent();
		}
		return false;
	}
	public String existRoad(Directory directory){
		StringBuilder path = new StringBuilder();
		Directory current = directory;
		while(current != null && current.getParent() != null){
			path.insert(0, "/" + current.getName());
			current = current.getParent();
		}
		return "root" + path.toString();  // assuming root is the top-most directory without a parent
	}
	/**
	* Searches for a file or directory by name within a specified directory.
	*
	* @param name the name of the file or directory to find.
	* @param parent the directory to search within.
	* @return the FileSystemElement if found, or null if no such element exists in the parent directory.
	*/
	private FileSystemElement findElementByName(String name, Directory parent){
		for(FileSystemElement elem : parent.getChildren()){
			if(elem.getName().equals(name)){
				return elem;
			}
		}
		return null;
	}
	public Directory changeRoad(String path){
		if(path.equals("/") || path.equals("root") || path.isEmpty()){
			return root;
		}
		String[] parts = path.split("/");
		Directory current = root;
		for(String part : parts){
			if(part.isEmpty() || part.equals("root")) continue;
			Directory nextDir = findDirectory(part, current);
			if(nextDir == null){
				return null;
			}
			current = nextDir;
		}
		return current;
	}
	/**
	* Searches for a directory by name within the given parent directory.
	*
	* @param name the name of the directory to find.
	* @param current the parent directory within which to search for the directory.
	* @return the Directory if found within the current directory, or null otherwise.
	*/
	private Directory findDirectory(String name, Directory current){
		for(FileSystemElement elem : current.getChildren()){
			if(elem instanceof Directory && elem.getName().equals(name)){
				return (Directory) elem;
			}
		}
		return null;
	}
/////////EXTRA
	/**
	* Prints the entire directory tree starting from the given directory.
	* All directories and files within the structure are recursively printed.
	*
	* @param dir the directory from which to start printing.
	* @param prefix a string prefix used for formatting the printed tree structure.
	* @param currentDirectory the directory currently being accessed by the user.
	*/
	public void printWholeTree(Directory dir, String prefix, Directory currentDirectory){
		String directoryMarker = (dir instanceof Directory) ? "*" : "";
		String currentDirMarker = (dir == currentDirectory) ? " (Current Directory)" : "";
		System.out.println(prefix + directoryMarker + " " + dir.getName() + currentDirMarker);
		if(dir.getChildren() != null){
			for(FileSystemElement elem : dir.getChildren()){
				if(elem instanceof Directory){
					printWholeTree((Directory) elem, prefix + "      ", currentDirectory);
				}
				else{
					System.out.println(prefix + "      " + elem.getName());
				}
			}
		}
	}   	
}
