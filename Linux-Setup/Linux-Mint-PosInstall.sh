# Installing github-desktop
wget -qO - https://packagecloud.io/shiftkey/desktop/gpgkey | sudo tee /etc/apt/trusted.gpg.d/shiftkey-desktop.asc > /dev/null
sudo sh -c 'echo "deb [arch=amd64] https://packagecloud.io/shiftkey/desktop/any/ any main" > /etc/apt/sources.list.d/packagecloud-shiftkey-desktop.list'
sudo apt-get update
sudo apt install github-desktop

# Setup SSH
sudo apt install openssh-server
sudo ufw allow ssh

# Installing SNAP
sudo rm /etc/apt/preferences.d/nosnap.pref
sudo apt install nodejs snapd

# Removing the software not used
sudo apt purge firefox firefox-locale-* thunderbird celluloid hexchat hypnotix rhythmbox -y

# Installing core software

# sudo apt install python3-pip -y
sudo apt install nodejs npm -y
sudo npm install -g --unsafe-perm node-red 
sudo apt install arduino -y
sudo apt install build-essential -y
sudo apt install git curl fish -y
sudo apt install gimp -y
sudo apt install vlc clementine -y
sudo apt install gparted -y
sudo apt install sqlitebrowser -y
sudo snap install docker
sudo snap install android-studio --classic
sudo snap install flutter --classic
sudo snap install postman
sudo snap install code --classic
sudo snap install robo3t-snap
sudo snap install pycharm-community --classic
# sudo snap install dotnet-sdk --classic --channel=5.0
# sudo snap install eclipse --classic
# sudo snap install intellij-idea-community --classic
# sudo apt install filezilla -y
# sudo apt install hollywood -y

# Update libraries
sudo apt-get update && sudo apt-get upgrade -y