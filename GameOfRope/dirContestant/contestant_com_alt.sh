CODEBASE="file:///home/"$1"/GameOfRope/dirContestant/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.ClientGameOfRopeContestant localhost 22147