CODEBASE="file:///home/"$1"/GameOfRope/dirGeneralRepository/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerGameOfRopeGeneralRepository 22141 localhost 22147