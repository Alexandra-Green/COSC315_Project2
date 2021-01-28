JC = javac
JCR = java

.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

CLASSES = \
	Request.java \
	Consumer.java \
	Producer.java \
	Buffer.java \
	
default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class *~

.PHONY: default clean classes

