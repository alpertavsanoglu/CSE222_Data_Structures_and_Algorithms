	/**
	*Inherits from FileSystemElement and represents a file.
	*/
public class File extends FileSystemElement{								//represent a file Class
	/**
	* Constructs a new File with a specified name and parent directory.
	*
	* @param name the name of the file.
	* @param parent the parent directory of this file.
	*/
	public File(String name, FileSystemElement parent){						//constructor for file
		super(name, parent);
	}
	/**
	* Printing the file including its name.
	*
	* @param prefix  string for printing file with name
	*/
	@Override
	public void print(String prefix){
		System.out.println(prefix + "File: " + getName());					//for printing file with name
	}
}
