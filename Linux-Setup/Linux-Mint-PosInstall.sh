#!/usr/bin/env bash

# Removing occasional locks from apt
sudo rm /var/lib/dpkg/lock-frontend
sudo rm /var/cache/apt/archives/lock
# Adding/Confirming 32-bit architecture
sudo dpkg --add-architecture i386
# Enable SNAP
sudo rm /etc/apt/preferences.d/nosnap.pref
sudo apt update -y
sudo apt install snapd -y

# Download and install third-party software
DIRETORIO_DOWNLOADS="$HOME/Downloads/programas"
# VS Code
wget -P "$DIRETORIO_DOWNLOADS" -c https://az764295.vo.msecnd.net/stable/784b0177c56c607789f9638da7b6bf3230d47a8c/code_1.71.0-1662018389_amd64.deb 
# VS Code
wget -P "$DIRETORIO_DOWNLOADS" -c https://objects.githubusercontent.com/github-production-release-asset-2e65be/85733855/f2382800-5c01-11e9-9f4b-a4854ea802c1?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20220908%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220908T123746Z&X-Amz-Expires=300&X-Amz-Signature=9a5d08b67cdd06b1424b56b9f5039dd5879124f1e8d902af1f56aa98e41bd396&X-Amz-SignedHeaders=host&actor_id=0&key_id=0&repo_id=85733855&response-content-disposition=attachment%3B%20filename%3Ddockstation_1.5.1_amd64.deb&response-content-type=application%2Foctet-stream 

sudo dpkg -i $DIRETORIO_DOWNLOADS/*.deb

# Installing github-desktop
wget -qO - https://packagecloud.io/shiftkey/desktop/gpgkey | sudo tee /etc/apt/trusted.gpg.d/shiftkey-desktop.asc > /dev/null
sudo sh -c 'echo "deb [arch=amd64] https://packagecloud.io/shiftkey/desktop/any/ any main" > /etc/apt/sources.list.d/packagecloud-shiftkey-desktop.list'
sudo apt-get update
sudo apt install github-desktop

# Removing the software not used
# sudo apt purge firefox firefox-locale-* -y
sudo apt purge thunderbird celluloid hexchat hypnotix rhythmbox -y

# Installing APT packages
sudo apt install samba samba-common-bin -y
sudo apt install git curl fish -y
sudo apt install build-essential -y
sudo apt install python3-pip -y
sudo apt install arduino -y
sudo apt install vlc clementine -y
sudo apt install gparted -y
sudo apt install sqlitebrowser -y
# sudo apt install gimp -y
# sudo apt install filezilla -y
# sudo apt install hollywood -y

# Installing Snap packages
sudo snap install snap-store
sudo snap install docker
sudo snap install android-studio --classic
sudo snap install flutter --classic
sudo snap install pycharm-community --classic
# sudo snap install postman
# sudo snap install robo3t-snap
# sudo snap install dotnet-sdk --classic --channel=5.0
# sudo snap install eclipse --classic
# sudo snap install intellij-idea-community --classic
# sudo snap install spotify
# sudo snap install slack --classic
# sudo snap install skype --classic

# Setup SSH
sudo apt install openssh-server
sudo ufw allow ssh

## Finalização, atualização e limpeza##
sudo apt update && sudo apt dist-upgrade -y
flatpak update
sudo apt autoclean && apt autoremove -y