JC = javac
JFLAGS = -g
JAR = -cp .:*

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $(JAR) $*.java

CLASSES = \
	  Course.java \
	  FrontEnd.java \
	  HistoricalData.java \
	  Instructor.java \
	  Main.java \
	  Parser.java \
	  Preferences.java \
	  Requirements.java \
	  Rule.java \
	  ScheduleDisplay.java \
	  Scheduler.java \
	  Section.java	\
	  Semester.java \
	  Track.java	\
	  Utility.java	\
	  GeneticAlgorithm.java	\
	  Chromosome.java	\
	  RuleTestMain.java	\
	  AScheduler.java       \
	  ASemester.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class


