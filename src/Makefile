JC = javac
JFLAGS = -g

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	  Parser.java \
	  Requirements.java \
	  Rule.java    \
	  Section.java \
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


