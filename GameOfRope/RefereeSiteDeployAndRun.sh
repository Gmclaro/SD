# TODO : change all this shit

echo "Transfering data to the referee Site node."
sshpass -f password ssh sd105@l040101-ws08.ua.pt 'kill $(lsof -i :22148 -t)'
sshpass -f password ssh sd105@l040101-ws08.ua.pt 'mkdir -p GameOfRope' 
sshpass -f password ssh sd105@l040101-ws08.ua.pt 'rm -rf GameOfRope/*'
sshpass -f password scp dirRefereeSite.zip sd105@l040101-ws08.ua.pt:GameOfRope
echo "Decompressing data sent to the referee Site node."
sshpass -f password ssh sd105@l040101-ws08.ua.pt 'cd GameOfRope ; unzip -uq dirRefereeSite.zip'
echo "Executing program at the referee Site node."
sshpass -f password ssh sd105@l040101-ws08.ua.pt 'cd GameOfRope/dirRefereeSite ;cat refereeSite_com_d.sh sd105;  sh refereeSite_com_d.sh sd105'
