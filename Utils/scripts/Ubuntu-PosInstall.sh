#!/usr/bin/env bash

# Removing the software not used
sudo snap remove firefox

# Update repositories
sudo apt update && sudo apt dist-upgrade -y

# Installing Snap packages
# sudo snap install snap-store
# sudo snap install docker
# sudo snap install android-studio --classic
# sudo snap install flutter --classic
# sudo snap install pycharm-community --classic
# sudo snap install postman

# Installing APT packages
sudo apt install samba samba-common-bin -y
sudo apt install net-tools -y
sudo apt install git -y
sudo apt install curl -y
sudo apt install fish -y
sudo apt install build-essential -y
sudo apt install arduino -y
sudo apt install vlc clementine -y
sudo apt install gparted -y
sudo apt install sqlitebrowser -y
# sudo apt install gimp -y
# sudo apt install filezilla -y
# sudo apt install hollywood -y

# Install spotify-dl
sudo apt install python3-pip -y
sudo apt install ffmpeg -y
sudo pip3 install spotify-dl

# Setup SSH
sudo apt-get install openssh-server -y
sudo systemctl enable ssh --now
sudo ufw allow ssh

# Installing github-desktop
wget -qO - https://packagecloud.io/shiftkey/desktop/gpgkey | sudo tee /etc/apt/trusted.gpg.d/shiftkey-desktop.asc > /dev/null
sudo sh -c 'echo "deb [arch=amd64] https://packagecloud.io/shiftkey/desktop/any/ any main" > /etc/apt/sources.list.d/packagecloud-shiftkey-desktop.list'
sudo apt-get update 
sudo apt install github-desktop -y

# Download and install third-party software
FOLDER_DOWNLOADS="$HOME/Downloads/programas"
programs=(
    # VS Code
    "https://az764295.vo.msecnd.net/stable/784b0177c56c607789f9638da7b6bf3230d47a8c/code_1.71.0-1662018389_amd64.deb"
    # Microsoft Edge
    "https://packages.microsoft.com/repos/edge/pool/main/m/microsoft-edge-stable/microsoft-edge-stable_106.0.1370.52-1_amd64.deb"
)
for p in ${programs[@]}; do
   wget -P "$FOLDER_DOWNLOADS" -c "$p"
done
sudo dpkg -i $FOLDER_DOWNLOADS/*.deb

# Finish 
sudo apt update && sudo apt dist-upgrade -y
flatpak update
sudo apt autoclean && apt autoremove -y