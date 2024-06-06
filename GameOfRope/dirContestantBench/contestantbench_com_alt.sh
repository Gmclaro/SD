CODEBASE="file:///home/"$1"/GameOfRope/dirContestantBench/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerGameOfRopeContestantBench 22149 localhost 22147