
# TODO : change all this shit
xterm  -T "RMI registry" -hold -e "sh RMIRegistryDeployAndRun.sh" &
sleep 5
xterm  -T "Registry" -hold -e "sh RegistryDeployAndRun.sh" &
sleep 5
xterm  -T "General Repository" -hold -e "sh GeneralReposDeployAndRun.sh" &
sleep 5
xterm  -T "Contestant Bench" -hold -e "sh ContestantBenchDeployAndRun.sh" &
sleep 5
xterm  -T "Playground" -hold -e "sh PlaygroundDeployAndRun.sh" &
sleep 5
xterm  -T "Referee Site" -hold -e "sh RefereeSiteDeployAndRun.sh" &
sleep 5

xterm  -T "Contestants" -hold -e "sh ContestantDeployAndRun.sh" &
sleep 5

xterm  -T "Coach" -hold -e "sh CoachDeployAndRun.sh" &
sleep 5
xterm  -T "Referee" -hold -e "sh RefereeDeployAndRun.sh" &