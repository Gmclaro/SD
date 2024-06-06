# TODO : change all this shit
echo "Transfering data to the RMIregistry node."
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'kill $(lsof -i :22147 -t)'
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'mkdir -p GameOfRope'
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'rm -rf GameOfRope/*'
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'mkdir -p Public/classes/interfaces'
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'rm -rf Public/classes/interfaces/*'
sshpass -f password scp dirRMIRegistry.zip sd105@l040101-ws07.ua.pt:GameOfRope
echo "Decompressing data sent to the RMIregistry node."
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'cd GameOfRope ; unzip -uq dirRMIRegistry.zip'
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'cd GameOfRope/dirRMIRegistry ; cp interfaces/*.class /home/sd105/Public/classes/interfaces ; cp set_rmiregistry_d.sh /home/sd105'
echo "Executing program at the RMIregistry node."
sshpass -f password ssh sd105@l040101-ws07.ua.pt 'sh set_rmiregistry_d.sh sd105 22147'
