# TODO : change all this shit

echo "Transfering data to the contestant node."
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'kill $(lsof -i :22150 -t)'

sshpass -f password ssh sd105@l040101-ws05.ua.pt 'rm -rf GameOfRope/*'
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'mkdir -p GameOfRope'
sshpass -f password scp dirContestant.zip sd105@l040101-ws05.ua.pt:GameOfRope
echo "Decompressing data sent to the contestant node."
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'cd GameOfRope ; unzip -uq dirContestant.zip'
echo "Executing program at the contestant node."
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'cd GameOfRope/dirContestant ; cat contestant_com_d.sh ; sh contestant_com_d.sh'
