# TODO : change all this shit

echo "Transfering data to the coach node."
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'kill $(lsof -i :22144 -t)'
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'mkdir -p GameOfRope'
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'rm -rf GameOfRope/*'
sshpass -f password scp dirCoach.zip sd105@l040101-ws04.ua.pt:GameOfRope
echo "Decompressing data sent to the coach node."
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'cd GameOfRope ; unzip -uq dirCoach.zip'
echo "Executing program at the coach node."
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'cd GameOfRope/dirCoach; sh coach_com_d.sh'
