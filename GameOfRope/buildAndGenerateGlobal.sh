echo "Compiling the project."
javac -cp ".:./genclass.jar" -source 1.8 -target 1.8 commonInfra/*.java clientSide/*/*.java serverSide/*/*.java interfaces/*.java



echo "Distributing intermediate code to the different execution environments."
##############################################

echo "  RMI registry"
rm -rf dirRMIRegistry/interfaces
mkdir -p dirRMIRegistry/interfaces
cp interfaces/*.class dirRMIRegistry/interfaces

######################################################################
echo "  Register Remote Objects"
rm -rf dirRegistry/interfaces/ dirRegistry/serverSide
mkdir -p dirRegistry/interfaces dirRegistry/serverSide dirRegistry/serverSide/main dirRegistry/serverSide/objects
cp serverSide/main/ServerRegisterRemoteObject.class dirRegistry/serverSide/main
cp serverSide/objects/RegisterRemoteObject.class dirRegistry/serverSide/objects
cp interfaces/Register.class dirRegistry/interfaces



#######################################################################

echo "  General Repository of Information"

rm -rf dirGeneralRepository/interfaces/ dirGeneralRepository/serverSide dirGeneralRepository/clientSide dirGeneralRepository/commonInfra
mkdir -p dirGeneralRepository/serverSide dirGeneralRepository/serverSide/main dirGeneralRepository/serverSide/objects dirGeneralRepository/clientSide dirGeneralRepository/clientSide/entities dirGeneralRepository/interfaces dirGeneralRepository/commonInfra
cp serverSide/main/SimulParse.class serverSide/main/ServerGameOfRopeGeneralRepository.class dirGeneralRepository/serverSide/main
cp serverSide/objects/GeneralRepository.class dirGeneralRepository/serverSide/objects
cp interfaces/GeneralRepositoryInterface.class interfaces/Register.class dirGeneralRepository/interfaces
cp clientSide/entities/ContestantState.class  clientSide/entities/RefereeState.class clientSide/entities/CoachState.class dirGeneralRepository/clientSide/entities
cp commonInfra/*.class dirGeneralRepository/commonInfra


#######################################################################

echo "Contestant Bench"

rm -rf dirContestantBench/serverSide dirContestantBench/clientSide dirContestantBench/interfaces
mkdir -p dirContestantBench/serverSide dirContestantBench/serverSide/main dirContestantBench/serverSide/objects dirContestantBench/clientSide dirContestantBench/clientSide/entities dirContestantBench/interfaces dirContestantBench/commonInfra
cp serverSide/main/SimulParse.class serverSide/main/ServerGameOfRopeContestantBench.class dirContestantBench/serverSide/main
cp serverSide/objects/ContestantBench.class dirContestantBench/serverSide/objects
cp interfaces/*.class dirContestantBench/interfaces
cp clientSide/entities/ContestantState.class  clientSide/entities/RefereeState.class clientSide/entities/CoachState.class dirContestantBench/clientSide/entities
cp commonInfra/*.class dirContestantBench/commonInfra


#######################################################################

echo "Playground"

rm -rf dirPlayground/serverSide dirPlayground/clientSide dirPlayground/interfaces
mkdir -p dirPlayground/serverSide dirPlayground/serverSide/main dirPlayground/serverSide/objects dirPlayground/clientSide dirPlayground/clientSide/entities dirPlayground/interfaces dirPlayground/commonInfra
cp serverSide/main/SimulParse.class serverSide/main/ServerGameOfRopePlayground.class dirPlayground/serverSide/main
cp serverSide/objects/Playground.class dirPlayground/serverSide/objects
cp interfaces/*.class dirPlayground/interfaces
cp clientSide/entities/ContestantState.class  clientSide/entities/RefereeState.class clientSide/entities/CoachState.class dirPlayground/clientSide/entities
cp commonInfra/*.class dirPlayground/commonInfra

#######################################################################

echo "Referee Site"

rm -rf dirRefereeSite/serverSide dirRefereeSite/clientSide dirRefereeSite/interfaces
mkdir -p dirRefereeSite/serverSide dirRefereeSite/serverSide/main dirRefereeSite/serverSide/objects dirRefereeSite/clientSide dirRefereeSite/clientSide/entities dirRefereeSite/interfaces dirRefereeSite/commonInfra
cp serverSide/main/SimulParse.class serverSide/main/ServerGameOfRopeRefereeSite.class dirRefereeSite/serverSide/main
cp serverSide/objects/RefereeSite.class dirRefereeSite/serverSide/objects
cp interfaces/*.class dirRefereeSite/interfaces
cp clientSide/entities/ContestantState.class  clientSide/entities/RefereeState.class clientSide/entities/CoachState.class dirRefereeSite/clientSide/entities
cp commonInfra/*.class dirRefereeSite/commonInfra

#######################################################################

echo "Referee"

rm -rf dirReferee/clientSide dirReferee/interfaces dirReferee/serverSide
mkdir -p dirReferee/clientSide dirReferee/clientSide/entities dirReferee/clientSide/main dirReferee/interfaces dirReferee/serverSide dirReferee/serverSide/main
cp serverSide/main/SimulParse.class dirReferee/serverSide/main
cp clientSide/main/ClientGameOfRopeReferee.class dirReferee/clientSide/main
cp clientSide/entities/Referee*.class dirReferee/clientSide/entities
cp interfaces/*.class dirReferee/interfaces

#######################################################################

echo "Coach"
rm -rf dirCoach/clientSide dirCoach/interfaces dirCoach/serverSide
mkdir -p dirCoach/clientSide dirCoach/clientSide/entities dirCoach/clientSide/main dirCoach/interfaces dirCoach/serverSide dirCoach/serverSide/main dirCoach/commonInfra
cp serverSide/main/SimulParse.class dirCoach/serverSide/main
cp clientSide/main/ClientGameOfRopeCoach.class dirCoach/clientSide/main
cp  clientSide/entities/Coach*.class dirCoach/clientSide/entities
cp commonInfra/*.class dirCoach/commonInfra
cp interfaces/*.class dirCoach/interfaces

#######################################################################
echo "Contestant"
rm -rf dirContestant/clientSide dirContestant/interfaces dirContestant/serverSide
mkdir -p dirContestant/clientSide dirContestant/clientSide/entities dirContestant/clientSide/main dirContestant/interfaces dirContestant/serverSide dirContestant/serverSide/main
cp serverSide/main/SimulParse.class dirContestant/serverSide/main
cp clientSide/main/ClientGameOfRopeContestant.class dirContestant/clientSide/main
cp clientSide/entities/Contestant*.class dirContestant/clientSide/entities
cp interfaces/*.class dirContestant/interfaces


#######################################################################

echo "Compressing execution environments."

echo "RMI registry"
rm -f dirRMIRegistry.zip
zip -rq dirRMIRegistry.zip dirRMIRegistry


echo "Register Remote Objects"
rm -f dirRegistry.zip
zip -rq dirRegistry.zip dirRegistry


echo "General Repository of Information"
rm -f dirGeneralRepository.zip
zip -rq dirGeneralRepository.zip dirGeneralRepository

echo "Contestant Bench"
rm -f dirContestantBench.zip
zip -rq dirContestantBench.zip dirContestantBench

echo "Playground"
rm -f dirPlayground.zip
zip -rq dirPlayground.zip dirPlayground


echo "Referee Site"
rm -f dirRefereeSite.zip
zip -rq dirRefereeSite.zip dirRefereeSite

echo "Referee"
rm -f dirReferee.zip
zip -rq dirReferee.zip dirReferee

echo "Coach"
rm -f dirCoach.zip
zip -rq dirCoach.zip dirCoach

echo "Contestant"
rm -f dirContestant.zip
zip -rq dirContestant.zip dirContestant






