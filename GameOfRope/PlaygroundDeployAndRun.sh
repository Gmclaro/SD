# TODO : change all this shit

echo "Transfering data to the playground node."
sshpass -f password ssh sd105@l040101-ws06.ua.pt 'kill $(lsof -i :22146 -t)'
sshpass -f password ssh sd105@l040101-ws06.ua.pt 'mkdir -p GameOfRope'
sshpass -f password ssh sd105@l040101-ws06.ua.pt 'rm -rf GameOfRope/*'
sshpass -f password scp dirPlayground.zip sd105@l040101-ws06.ua.pt:GameOfRope
echo "Decompressing data sent to the playground node."
sshpass -f password ssh sd105@l040101-ws06.ua.pt 'cd GameOfRope ; unzip -uq dirPlayground.zip'
echo "Executing program at the playground node."
sshpass -f password ssh sd105@l040101-ws06.ua.pt 'cd GameOfRope/dirPlayground ;cat playground_com_d.sh; sh playground_com_d.sh sd105'
