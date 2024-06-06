# TODO : change all this shit

echo "Transfering data to the contestant bench node."
sshpass -f password ssh sd105@l040101-ws09.ua.pt 'kill $(lsof -i :22149 -t)'

sshpass -f password ssh sd105@l040101-ws09.ua.pt 'mkdir -p GameOfRope'
sshpass -f password ssh sd105@l040101-ws09.ua.pt 'rm -rf GameOfRope/*'
sshpass -f password scp dirContestantBench.zip sd105@l040101-ws09.ua.pt:GameOfRope
echo "Decompressing data sent to the contestant bench node."
sshpass -f password ssh sd105@l040101-ws09.ua.pt 'cd GameOfRope ; unzip -uq dirContestantBench.zip'
echo "Executing program at the contestant bench node."
sshpass -f password ssh sd105@l040101-ws09.ua.pt 'cd GameOfRope/dirContestantBench ; sh contestantbench_com_d.sh sd105'
