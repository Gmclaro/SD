#!/bin/bash

# Ports: 22140-22150
# l040101-ws##.ua.pt.
HOSTNAME_SD="sd105"

GENERAL_REPO_IP="l040101-ws01.ua.pt"
GENERAL_REPO_PORT=22141
REFEREE_SITE_IP="l040101-ws06.ua.pt"
REFEREE_SITE_PORT=22142
CONTESTANT_BENCH_IP="l040101-ws05.ua.pt"
CONTESTANT_BENCH_PORT=22143
PLAYGROUND_IP="l040101-ws07.ua.pt"
PLAYGROUND_PORT=22144

CONTESTANT_IP="l040101-ws08.ua.pt"
COACH_IP="l040101-ws09.ua.pt"
REFEREE_IP="l040101-ws10.ua.pt"

##############################################################
# Vars

BUILD_DIR="./bin"
GENERAL_REPO_DIR="$BUILD_DIR/dirGeneralRepository"
REFEREE_SITE_DIR="$BUILD_DIR/dirRefereeSite"
CONTESTANT_BENCH_DIR="$BUILD_DIR/dirContestantBench"
PLAYGROUND_DIR="$BUILD_DIR/dirPlayground"
CONTESTANT_DIR="$BUILD_DIR/dirContestant"
COACH_DIR="$BUILD_DIR/dirCoach"
REFEREE_DIR="$BUILD_DIR/dirReferee"

##############################################################

# Compile, Distribute code and Compress execution environments

read -p "rm .class and $BUILD_DIR? (y/n)" answer
if [ "$answer" = "y" ] || [ -z "$answer" ]; then
    rm ./*/*/*.class
    rm ./commonInfra/*.class
    rm -rf $BUILD_DIR
    
fi

# Compiling source code
read -p "compile? (y/n)" answer
if [ "$answer" = "n" ]; then
    exit 0
fi

echo "Compiling source code."
javac -cp ".:./genclass.jar" ./*/*/*.java
sleep 0.5

mkdir $BUILD_DIR
echo "Distributing intermediate code to the different execution environments to $BUILD_DIR."

echo "  General Repository of Information"
mkdir -p $GENERAL_REPO_DIR $GENERAL_REPO_DIR/serverSide/main $GENERAL_REPO_DIR/serverSide/entities $GENERAL_REPO_DIR/serverSide/sharedRegions $GENERAL_REPO_DIR/clientSide/entities $GENERAL_REPO_DIR/commonInfra
cp genclass.jar $GENERAL_REPO_DIR
cp serverSide/main/ServerGameOfRopeGeneralRepository.class serverSide/main/SimulParse.class $GENERAL_REPO_DIR/serverSide/main
cp serverSide/sharedRegions/GeneralRepository.class serverSide/sharedRegions/GeneralRepositoryInterface.class serverSide/sharedRegions/RefereeSiteInterface.class  $GENERAL_REPO_DIR/serverSide/sharedRegions
cp serverSide/entities/GeneralRepositoryClientProxy.class serverSide/entities/RefereeSiteClientProxy.class  $GENERAL_REPO_DIR/serverSide/entities
cp commonInfra/ServerCom.class commonInfra/Message.class commonInfra/MessageType.class commonInfra/MessageException.class commonInfra/View.class $GENERAL_REPO_DIR/commonInfra
cp clientSide/entities/RefereeState.class clientSide/entities/CoachState.class clientSide/entities/ContestantState.class clientSide/entities/RefereeCloning.class clientSide/entities/CoachCloning.class $GENERAL_REPO_DIR/clientSide/entities


echo "  Referee Site"
mkdir -p $REFEREE_SITE_DIR $REFEREE_SITE_DIR/serverSide/main $REFEREE_SITE_DIR/serverSide/sharedRegions $REFEREE_SITE_DIR/serverSide/entities $REFEREE_SITE_DIR/clientSide/stubs $REFEREE_SITE_DIR/clientSide/entities $REFEREE_SITE_DIR/commonInfra
cp genclass.jar $REFEREE_SITE_DIR
cp clientSide/stubs/GeneralRepositoryStub.class $REFEREE_SITE_DIR/clientSide/stubs
cp clientSide/entities/RefereeState.class clientSide/entities/RefereeCloning.class clientSide/entities/CoachCloning.class $REFEREE_SITE_DIR/clientSide/entities
cp commonInfra/ServerCom.class commonInfra/ClientCom.class commonInfra/Message.class commonInfra/MessageType.class commonInfra/MessageException.class commonInfra/View.class $REFEREE_SITE_DIR/commonInfra
cp serverSide/entities/RefereeSiteClientProxy.class $REFEREE_SITE_DIR/serverSide/entities
cp serverSide/sharedRegions/RefereeSite.class serverSide/sharedRegions/RefereeSiteInterface.class $REFEREE_SITE_DIR/serverSide/sharedRegions
cp serverSide/main/ServerGameOfRopeRefereeSite.class $REFEREE_SITE_DIR/serverSide/main

echo "  Contestant Bench"
mkdir -p $CONTESTANT_BENCH_DIR $CONTESTANT_BENCH_DIR/serverSide/main $CONTESTANT_BENCH_DIR/serverSide/sharedRegions $CONTESTANT_BENCH_DIR/serverSide/entities $CONTESTANT_BENCH_DIR/clientSide/entities $CONTESTANT_BENCH_DIR/clientSide/stubs $CONTESTANT_BENCH_DIR/commonInfra
cp genclass.jar $CONTESTANT_BENCH_DIR
cp clientSide/entities/CoachState.class clientSide/entities/RefereeCloning.class clientSide/entities/CoachCloning.class clientSide/entities/ContestantCloning.class clientSide/entities/ContestantState.class clientSide/entities/RefereeState.class $CONTESTANT_BENCH_DIR/clientSide/entities
cp clientSide/stubs/GeneralRepositoryStub.class $CONTESTANT_BENCH_DIR/clientSide/stubs
cp commonInfra/ServerCom.class commonInfra/ClientCom.class commonInfra/Message.class commonInfra/MessageType.class commonInfra/MessageException.class commonInfra/View.class $CONTESTANT_BENCH_DIR/commonInfra
cp serverSide/main/ServerGameOfRopeContestantBench.class serverSide/main/SimulParse.class $CONTESTANT_BENCH_DIR/serverSide/main
cp serverSide/entities/ContestantBenchClientProxy.class $CONTESTANT_BENCH_DIR/serverSide/entities
cp serverSide/sharedRegions/ContestantBench.class serverSide/sharedRegions/ContestantBenchInterface.class $CONTESTANT_BENCH_DIR/serverSide/sharedRegions

echo "  Playground"
mkdir -p $PLAYGROUND_DIR $PLAYGROUND_DIR/serverSide/main $PLAYGROUND_DIR/serverSide/sharedRegions $PLAYGROUND_DIR/serverSide/entities $PLAYGROUND_DIR/clientSide/entities $PLAYGROUND_DIR/commonInfra $PLAYGROUND_DIR/clientSide/stubs
cp genclass.jar $PLAYGROUND_DIR
cp clientSide/entities/CoachState.class clientSide/entities/ContestantState.class clientSide/entities/RefereeState.class clientSide/entities/RefereeCloning.class clientSide/entities/CoachCloning.class clientSide/entities/ContestantCloning.class $PLAYGROUND_DIR/clientSide/entities
cp clientSide/stubs/GeneralRepositoryStub.class $PLAYGROUND_DIR/clientSide/stubs
cp commonInfra/ServerCom.class commonInfra/ClientCom.class commonInfra/Message.class commonInfra/MessageType.class commonInfra/MessageException.class commonInfra/View.class $PLAYGROUND_DIR/commonInfra
cp serverSide/main/ServerGameOfRopePlayground.class serverSide/main/SimulParse.class $PLAYGROUND_DIR/serverSide/main
cp serverSide/entities/PlaygroundClientProxy.class $PLAYGROUND_DIR/serverSide/entities
cp serverSide/sharedRegions/Playground.class serverSide/sharedRegions/PlaygroundInterface.class $PLAYGROUND_DIR/serverSide/sharedRegions

echo "  Contestant"
mkdir -p $CONTESTANT_DIR $CONTESTANT_DIR/serverSide/main $CONTESTANT_DIR/serverSide/sharedRegions $CONTESTANT_DIR/serverSide/entities $CONTESTANT_DIR/clientSide/stubs $CONTESTANT_DIR/clientSide/entities $CONTESTANT_DIR/commonInfra $CONTESTANT_DIR/clientSide/main
cp genclass.jar $CONTESTANT_DIR
cp clientSide/main/ClientGameOfRopeContestant.class $CONTESTANT_DIR/clientSide/main
cp clientSide/stubs/ContestantBenchStub.class clientSide/stubs/PlaygroundStub.class clientSide/stubs/GeneralRepositoryStub.class $CONTESTANT_DIR/clientSide/stubs
cp clientSide/entities/Contestant.class clientSide/entities/Coach.class clientSide/entities/Referee.class clientSide/entities/ContestantState.class clientSide/entities/CoachState.class clientSide/entities/RefereeState.class $CONTESTANT_DIR/clientSide/entities
cp serverSide/main/SimulParse.class $CONTESTANT_DIR/serverSide/main
cp commonInfra/ClientCom.class commonInfra/Message.class commonInfra/MessageType.class commonInfra/View.class $CONTESTANT_DIR/commonInfra

echo "  Coach"
mkdir -p $COACH_DIR $COACH_DIR/serverSide/main $COACH_DIR/serverSide/sharedRegions $COACH_DIR/serverSide/entities $COACH_DIR/clientSide/stubs $COACH_DIR/clientSide/entities $COACH_DIR/commonInfra $COACH_DIR/clientSide/main
cp genclass.jar $COACH_DIR
cp clientSide/main/ClientGameOfRopeCoach.class $COACH_DIR/clientSide/main
cp clientSide/stubs/ContestantBenchStub.class clientSide/stubs/PlaygroundStub.class clientSide/stubs/GeneralRepositoryStub.class clientSide/stubs/RefereeSiteStub.class $COACH_DIR/clientSide/stubs
cp commonInfra/Strategy*.class commonInfra/View.class commonInfra/ClientCom.class commonInfra/Message.class commonInfra/MessageType.class  commonInfra/MemFIFO.class commonInfra/MemException.class commonInfra/MemObject.class $COACH_DIR/commonInfra
cp clientSide/entities/Coach.class clientSide/entities/CoachState.class clientSide/entities/Contestant.class clientSide/entities/ContestantState.class clientSide/entities/RefereeState.class clientSide/entities/Referee.class $COACH_DIR/clientSide/entities
cp serverSide/main/SimulParse.class $COACH_DIR/serverSide/main

echo "  Referee"
mkdir -p $REFEREE_DIR $REFEREE_DIR/serverSide/main $REFEREE_DIR/serverSide/sharedRegions $REFEREE_DIR/serverSide/entities $REFEREE_DIR/clientSide/stubs $REFEREE_DIR/clientSide/entities $REFEREE_DIR/commonInfra $REFEREE_DIR/clientSide/main
cp genclass.jar $REFEREE_DIR
cp clientSide/main/ClientGameOfRopeReferee.class $REFEREE_DIR/clientSide/main
cp clientSide/stubs/ContestantBenchStub.class clientSide/stubs/PlaygroundStub.class clientSide/stubs/GeneralRepositoryStub.class clientSide/stubs/RefereeSiteStub.class $REFEREE_DIR/clientSide/stubs
cp serverSide/main/SimulParse.class $REFEREE_DIR/serverSide/main
cp clientSide/entities/Referee.class clientSide/entities/RefereeState.class clientSide/entities/Contestant.class clientSide/entities/ContestantState.class clientSide/entities/Coach.class clientSide/entities/CoachState.class $REFEREE_DIR/clientSide/entities
cp commonInfra/ClientCom.class commonInfra/Message.class commonInfra/MessageType.class commonInfra/View.class $REFEREE_DIR/commonInfra

echo "Compressing execution environments."
echo "  General Repository of Information"
rm -f  $GENERAL_REPO_DIR.zip
zip -rq $GENERAL_REPO_DIR.zip $GENERAL_REPO_DIR
rm -rf  $GENERAL_REPO_DIR
echo "  Referee Site"
rm -f  $REFEREE_SITE_DIR.zip
zip -rq $REFEREE_SITE_DIR.zip $REFEREE_SITE_DIR
rm -rf  $REFEREE_SITE_DIR
echo "  Contestant Bench"
rm -f  $CONTESTANT_BENCH_DIR.zip
zip -rq $CONTESTANT_BENCH_DIR.zip $CONTESTANT_BENCH_DIR
rm -rf  $CONTESTANT_BENCH_DIR
echo "  Playground"
rm -f  $PLAYGROUND_DIR.zip
zip -rq $PLAYGROUND_DIR.zip $PLAYGROUND_DIR
rm -rf  $PLAYGROUND_DIR
echo "  Contestant"
rm -f  $CONTESTANT_DIR.zip
zip -rq $CONTESTANT_DIR.zip $CONTESTANT_DIR
rm -rf  $CONTESTANT_DIR
echo "  Coach"
rm -f  $COACH_DIR.zip
zip -rq $COACH_DIR.zip $COACH_DIR
rm -rf  $COACH_DIR
echo "  Referee"
rm -f  $REFEREE_DIR.zip
zip -rq $REFEREE_DIR.zip $REFEREE_DIR
rm -rf  $REFEREE_DIR

##############################################################

# Run
pkill xterm
sleep 1

xterm  -T "General Repository" -hold -e "sh ./DeployRun/GeneralRepositoryDeploy.sh" &
xterm  -T "Referee Site" -hold -e "sh ./DeployRun/RefereeSiteDeploy.sh" &
xterm  -T "Playground" -hold -e "sh ./DeployRun/PlaygroundDeploy.sh" &
xterm  -T "Contestant Bench" -hold -e "sh ./DeployRun/ContestantBenchDeploy.sh" &

sleep 5

xterm  -T "Contestants" -hold -e "sh ./DeployRun/ContestantDeploy.sh" &
sleep 5
xterm  -T "Coach" -hold -e "sh ./DeployRun/CoachDeploy.sh" &
sleep 5
xterm  -T "Referee" -hold -e "sh ./DeployRun/RefereeDeploy.sh" &


read -p "kill xterm? (y/n)" answer
if [ "$answer" = "n" ]; then
    exit 0
fi
pkill xterm