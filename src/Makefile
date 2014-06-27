JC = javac
JFLAGS = -g
JAR = -cp .:../*

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $(JAR) $*.java

CLASSES = \
	  Parser.java \
	  Requirements.java \
	  Rule.java    \
	  Section.java \
	  Utility.java	\
	  Instructor.java \
	  Preferences.java \
	  Semester.java	\
	  Course.java \
	  CourseReview.java \
	  Track.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class


