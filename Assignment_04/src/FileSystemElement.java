import java.sql.Timestamp;
	/**
	*An abstract class with common attributes and methods for both files and directories. 
	*/
public abstract class FileSystemElement{									//an abstract class for files and directories
	protected String name;											//the name of the element.
	protected Timestamp dateCreated;									//The creation timestamp.
	protected FileSystemElement parent;									// Reference to the parent directory.

	public FileSystemElement(String name, FileSystemElement parent){
		this.name = name;										//the name of the element.
		this.parent = parent;										// Reference to the parent directory.
		this.dateCreated = new Timestamp(System.currentTimeMillis());					//The creation timestamp.
	}
	/**
	* getter for name
	* @return name for getter
	*/
	public String getName(){										//getter for name
		return name;
	}
	/**
	* getter for darectreated
	* @return datecreated for getter
	*/
	public Timestamp getDateCreated(){									//for timestamp
		return dateCreated;
	}
	/**
	* getter for parent
	* @return parent for getter
	*/
	public Directory getParent(){										//getter for parent
		return (Directory) parent;
	}
	/**
	* setter for parent
	*
	* @param parent for setting.
	*/
	public void setParent(FileSystemElement parent){							//setter for parent
		this.parent = parent;
	}
	/**
	* Printing file information.
	*
	* @param prefix print.
	*/
	public abstract void print(String prefix);								//printing directory info
}
