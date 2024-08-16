JC = javac
JFLAGS = -g
TARGET = Main
SOURCES = Main.java Person.java SocialNetworkGraph.java
JD = javadoc
JDFLAGS = -protected -splitindex -use -author -version -d ./javadoc

default: $(TARGET)

$(TARGET): $(SOURCES)
	$(JC) $(JFLAGS) $^
	java $(TARGET)

doc:
	$(JD) $(JDFLAGS) *.java 

cleandoc:
	rm -r ./javadoc
	
cleanfile:
	rm *.txt
	
clean:
	rm -f *.class

.PHONY: clean default

