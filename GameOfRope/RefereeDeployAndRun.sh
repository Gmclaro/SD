

echo "Transfering data to the Referee node."
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'kill $(lsof -i :22145 -t)'
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'mkdir -p Referee/GameOfRope'
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'rm -rf Referee/GameOfRope/*'
sshpass -f password scp dirReferee.zip sd105@l040101-ws05.ua.pt:Referee/GameOfRope
echo "Decompressing data sent to the Referee node."
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'cd Referee/GameOfRope ; unzip -uq dirReferee.zip'
echo "Executing program at the Referee node."
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'cd Referee/GameOfRope/dirReferee ; sh referee_com_d.sh'
