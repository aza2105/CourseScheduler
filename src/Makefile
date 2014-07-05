JC = javac
JFLAGS = -g
JAR = -cp .:*

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $(JAR) $*.java

CLASSES = \
	  AScheduler.java \
	  ASemester.java \
	  Chromosome.java \
	  Course.java \
	  FrontEnd.java \
	  HistoricalData.java \
	  GeneticAlgorithm.java \
	  Instructor.java \
	  Main.java \
	  Parser.java \
	  Preferences.java \
	  Requirements.java \
	  Rule.java \
	  RuleTestMain.java \
	  ScheduleDisplay.java \
	  Scheduler.java \
	  Section.java	\
	  Semester.java \
	  Track.java	\
	  Utility.java	

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class inputPrefs.csv


