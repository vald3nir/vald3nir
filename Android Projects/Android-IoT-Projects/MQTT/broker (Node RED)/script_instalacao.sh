
sudo apt-get update && sudo apt-get upgrade -y
sudo apt-get install nodejs npm -y
echo "Java version"
node -v 
echo "NPM version"
npm -v
sudo apt-get install mosquitto mosquitto-clients libmosquitto-dev -y
sudo npm install -g --unsafe-perm node-red