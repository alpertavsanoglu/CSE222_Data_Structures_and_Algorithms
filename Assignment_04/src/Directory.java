import java.util.LinkedList;
/**
*Inherits from FileSystemElement and represents a directory that can contain other files or directories.
*/
public class Directory extends FileSystemElement{						//FileSystemElement class
	private LinkedList<FileSystemElement> children;						//A Linked List containing child elements.
	/**
	* Constructs a new Directory with a specified name and parent directory.
	*
	* @param name the name of the directory.
	* @param parent the parent directory of this directory.
	*/
	public Directory(String name, FileSystemElement parent){					//constructor for directory
		super(name, parent);
		children = new LinkedList<>();								//define linked list
	}
	/**
	* Adding a children  to this directory.
	*
	* @param element the file system element to add.
	*/
	public void addElement(FileSystemElement element){						//add element(child) to the directory
		children.add(element);
	}
	/**
	* Removing a children from this directory.
	*
	* @param element the file system element to remove.
	*/
	public void removeElement(FileSystemElement element){						//remove element(child) to the directory
		children.remove(element);
	}
	/**
	* Returning all children belongs to directory.
	*
	* @return a LinkedList of FileSystemElement representing the children.
	*/
	public LinkedList<FileSystemElement> getChildren(){						//for return LL which have elements
		return children;
	}
	/**
	* Printing recursively directory and  elements.
	*
	* @param prefix for formatting output.
	*/
	@Override
	public void print(String prefix){								//for printing elements
		System.out.println(prefix + "Directory: " + getName());
		for(FileSystemElement elem : children){
			elem.print(prefix + "  ");
		}
	}
}
