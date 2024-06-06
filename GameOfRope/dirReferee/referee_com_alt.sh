CODEBASE="file:///home/"$1"/GameOfRope/dirReferee/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.ClientGameOfRopeReferee localhost 22147