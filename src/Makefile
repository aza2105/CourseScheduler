JC = javac
JFLAGS = -g

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	  Main.java \
	  Parser.java \
	  Requirements.java \
	  Rule.java    \
	  Section.java \
	  Semester.java \
	  Utility.java	\
	  Instructor.java \
	  Preferences.java \
	  Course.java \
	  CourseReview.java \
	  Scheduler.java \
	  Track.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class


