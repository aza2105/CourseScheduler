JC = javac
JFLAGS = -g

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	  Main.java \
	  Parser.java \
	  Requirements.java \
	  Section.java \
	  Semester.java \
	  Utility.java	\
	  Track.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class


