CODEBASE="file:///home/"$1"/GameOfRope/dirPlayground/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerGameOfRopePlayground 22146 localhost 22147