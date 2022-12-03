#!/usr/bin/env bash

# Removing the software not used
# sudo apt purge firefox firefox-locale-* thunderbird celluloid hexchat hypnotix rhythmbox -y

# Update repositories
sudo apt update && sudo apt dist-upgrade -y

# Installing APT packages
sudo apt install samba samba-common-bin -y
sudo apt install git -y
sudo apt install curl -y
sudo apt install fish -y
sudo apt install build-essential -y
sudo apt install python3-pip -y
sudo apt install apt-transport-https -y
sudo apt install ntfs-3g -y

# Casa OS
curl -fsSL https://get.casaos.io | bash

# Node-red
bash <(curl -sL https://raw.githubusercontent.com/node-red/linux-installers/master/deb/update-nodejs-and-nodered)
sudo systemctl enable nodered.service

# Setup SSH
sudo apt install openssh-server -y
sudo ufw allow ssh

# Finish 
sudo apt update && sudo apt dist-upgrade -y
sudo apt autoclean && apt autoremove -y