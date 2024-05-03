#!/bin/bash

LOCALHOST="127.0.0.1"

# Servers
GENERAL_REPO_IP=$LOCALHOST
GENERAL_REPO_PORT=10000
REFEREE_SITE_IP=$LOCALHOST
REFEREE_SITE_PORT=10010
CONTESTANT_BENCH_IP=$LOCALHOST
CONTESTANT_BENCH_PORT=10020
PLAYGROUND_IP=$LOCALHOST
PLAYGROUND_PORT=10030




read -p "rm .class? (y/n)" answer

if [ "$answer" = "y" ] || [ -z "$answer" ]; then
    rm ./*/*/*.class
    rm ./commonInfra/*.class
    rm log
fi

read -p "compile? (y/n)" answer
if [ "$answer" = "n" ]; then
    exit 0
fi

javac -cp ".:./genclass.jar" ./*/*/*.java

read -p "run? (y/n)" answer
if [ "$answer" = "n" ]; then
    exit 0
fi


# Create a new tmux session 
tmux new-session -d -s GameOfRope -n GameOfRopeServer

# Split the window horizontally and vertically
tmux split-window -v -t GameOfRope:GameOfRopeServer
tmux split-window -h -t GameOfRope:GameOfRopeServer
tmux split-window -h -t GameOfRope:GameOfRopeServer.1

# Create a second new window
tmux new-window -n GameOfRopeClient

# Split the window horizontally and vertically
tmux split-window -v -t GameOfRope:GameOfRopeClient
tmux split-window -h -t GameOfRope:GameOfRopeClient

# send command to the GameOfRopeServer window
tmux send-keys -t GameOfRope:GameOfRopeServer.1 "java -cp ".:./genclass.jar" serverSide.main.ServerGameOfRopeGeneralRepository $GENERAL_REPO_PORT" C-m
tmux send-keys -t GameOfRope:GameOfRopeServer.2 "java -cp ".:./genclass.jar" serverSide.main.ServerGameOfRopeRefereeSite $REFEREE_SITE_PORT $GENERAL_REPO_IP $GENERAL_REPO_PORT" C-m
tmux send-keys -t GameOfRope:GameOfRopeServer.3 "java -cp ".:./genclass.jar" serverSide.main.ServerGameOfRopePlayground $PLAYGROUND_PORT $GENERAL_REPO_IP $GENERAL_REPO_PORT" C-m
tmux send-keys -t GameOfRope:GameOfRopeServer.4 "java -cp ".:./genclass.jar" serverSide.main.ServerGameOfRopeContestantBench $CONTESTANT_BENCH_PORT $GENERAL_REPO_IP $GENERAL_REPO_PORT" C-m

# wait for the servers to be ready
sleep 0.1

# send command to the GameOfRopeClient window
tmux send-keys -t GameOfRope:GameOfRopeClient.1 "java -cp ".:./genclass.jar" clientSide.main.ClientGameOfRopeReferee $REFEREE_SITE_IP $REFEREE_SITE_PORT $PLAYGROUND_IP $PLAYGROUND_PORT $CONTESTANT_BENCH_IP $CONTESTANT_BENCH_PORT $GENERAL_REPO_IP $GENERAL_REPO_PORT" C-m
tmux send-keys -t GameOfRope:GameOfRopeClient.3 "java -cp ".:./genclass.jar" clientSide.main.ClientGameOfRopeContestant $PLAYGROUND_IP $PLAYGROUND_PORT $CONTESTANT_BENCH_IP $CONTESTANT_BENCH_PORT $GENERAL_REPO_IP $GENERAL_REPO_PORT" C-m
tmux send-keys -t GameOfRope:GameOfRopeClient.2 "java -cp ".:./genclass.jar" clientSide.main.ClientGameOfRopeCoach $REFEREE_SITE_IP $REFEREE_SITE_PORT $PLAYGROUND_IP $PLAYGROUND_PORT $CONTESTANT_BENCH_IP $CONTESTANT_BENCH_PORT $GENERAL_REPO_IP $GENERAL_REPO_PORT" C-m


tmux attach-session
