# TODO : change all this shit

echo "Transfering data to the registry node."
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'kill $(lsof -i :22140 -t)'
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'mkdir -p test/GameOfRope'
sshpass -f password scp dirRegistry.zip sd105@l040101-ws07.ua.pt:test/GameOfRope
echo "Decompressing data sent to the registry node."
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'cd test/GameOfRope ; unzip -uq dirRegistry.zip'
echo "Executing program at the registry node."
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'cd test/GameOfRope/dirRegistry ; sh registry_com_d.sh sd105'
