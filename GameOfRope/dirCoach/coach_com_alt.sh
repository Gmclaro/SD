CODEBASE="file:///home/"$1"/GameOfRope/dirCoach/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.ClientGameOfRopeCoach localhost 22147