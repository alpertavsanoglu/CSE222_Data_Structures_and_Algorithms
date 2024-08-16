JC = javac
JFLAGS = -classpath .

JD = javadoc
JDFLAGS = -protected -splitindex -use -author -version -d ./javadoc

RM = rm
JR = java

# List of your Java files
CLASSES = Main.java AVLTree.java GUIVisualization.java Stock.java StockDataManager.java InputFileGenerator.java

# Default target executed when no arguments are given to make.
all: input classes run

# Target for running the program with -Xint flag and using input.txt as input
run: Main.class
	$(JR) -Xint Main input.txt

# Target for generating the input.txt file
input: InputFileGenerator.class
	$(JR) InputFileGenerator

# Target for building all classes.
classes: $(CLASSES:.java=.class)

# Pattern rule for building a .class file from a .java file.
%.class: %.java
	$(JC) $(JFLAGS) $<

# Target for generating documentation.
doc:
	$(JD) $(JDFLAGS) $(CLASSES)

# Target for cleaning class files.
clean:
	$(RM) -f *.class
	$(RM) -f *.txt

# Target for cleaning documentation.
cleandoc:
	$(RM) -r javadoc
	
