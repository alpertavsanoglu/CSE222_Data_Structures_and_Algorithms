JC = javac
JFLAGS = -classpath .
JD = javadoc
JDFLAGS = -protected -splitindex -use -author -version -d ./javadoc
RM = rm
JR = java

CLASSES = \
		FileSystem.java \
		File.java \
		FileSystemElement.java \
		Directory.java \
		Main.java

all : Main.class

run : 
	$(JR) Main

classes : $(CLASSES:.java=.class)

%.class : %.java
	$(JC) $(JFLAGS) $<

doc:
	$(JD) $(JDFLAGS) *.java 

clean:
	$(RM) -f *.class 
	$(RM) -f ./javadoc

cleandoc:
	$(RM) -r ./javadoc
	
cleanfile:
	$(RM) *.txt

