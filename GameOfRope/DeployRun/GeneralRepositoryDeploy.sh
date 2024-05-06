HOSTNAME_SD="sd105"

GENERAL_REPO_IP="l040101-ws01.ua.pt"
GENERAL_REPO_PORT=22141
REFEREE_SITE_IP="l040101-ws02.ua.pt"
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

echo "Transfering data to the General Repository of Information."
sshpass -f password ssh $HOSTNAME_SD@$GENERAL_REPO_IP 'kill $(lsof -i :22141 -t) '
sshpass -f password ssh $HOSTNAME_SD@$GENERAL_REPO_IP 'mkdir -p test/GameOfRope '
sshpass -f password ssh $HOSTNAME_SD@$GENERAL_REPO_IP 'rm -rf test/GameOfRope/* '
sshpass -f password scp $GENERAL_REPO_DIR.zip $HOSTNAME_SD@$GENERAL_REPO_IP:test/GameOfRope
echo "Decompressing data sent to the General Repository of Information."
sshpass -f password ssh $HOSTNAME_SD@$GENERAL_REPO_IP "cd test/GameOfRope ; unzip -uq dirGeneralRepository.zip "
echo "Executing program at the General Repository of Information node."
sshpass -f password ssh $HOSTNAME_SD@$GENERAL_REPO_IP "cd test/GameOfRope/bin/dirGeneralRepository ; java -cp ".:./genclass.jar" serverSide.main.ServerGameOfRopeGeneralRepository $GENERAL_REPO_PORT ; cat log ; "