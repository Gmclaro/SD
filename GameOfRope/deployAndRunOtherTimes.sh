# TODO : change all this shit

xterm  -T "General Repository" -hold -e "sh GeneralReposDeployAndRun.sh" &
sleep 2
xterm  -T "Contestant Bench" -hold -e "sh ContestantBenchDeployAndRun.sh" &
sleep 1
xterm  -T "Playground" -hold -e "sh PlaygroundDeployAndRun.sh" &
sleep 1
xterm  -T "Referee Site" -hold -e "sh RefereeSiteDeployAndRun.sh" &
sleep 1

xterm  -T "Contestants" -hold -e "sh ContestantDeployAndRun.sh" &
xterm  -T "Referee" -hold -e "sh RefereeDeployAndRun.sh" &
xterm  -T "Coach" -hold -e "sh CoachDeployAndRun.sh" &
