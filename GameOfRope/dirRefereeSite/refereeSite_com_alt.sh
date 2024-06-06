CODEBASE="file:///home/"$1"/GameOfRope/dirRefereeSite/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerGameOfRopeRefereeSite 22148 localhost 22147