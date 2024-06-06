# TODO : change all this shit

echo "Transfering data to the general repository node."
sshpass -f password ssh sd105@l040101-ws01.ua.pt 'kill $(lsof -i :22141 -t)'
sshpass -f password ssh sd105@l040101-ws01.ua.pt 'mkdir -p GameOfRope'
sshpass -f password ssh sd105@l040101-ws01.ua.pt 'rm -rf GameOfRope/*'
sshpass -f password scp dirGeneralRepository.zip sd105@l040101-ws01.ua.pt:GameOfRope
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh sd105@l040101-ws01.ua.pt 'cd GameOfRope ; unzip -uq dirGeneralRepository.zip'
echo "Executing program at the general repository node."
sshpass -f password ssh sd105@l040101-ws01.ua.pt 'cd GameOfRope/dirGeneralRepository ; sh repo_com_d.sh sd105'
echo "Server shutdown."
sshpass -f password ssh sd105@l040101-ws01.ua.pt 'cd GameOfRope/dirGeneralRepository ; less log.txt'
