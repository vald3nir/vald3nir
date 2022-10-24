# Initial Setup

    sudo apt-get update && sudo apt-get upgrade && sudo apt-get install apt-transport-https build-essential git curl fish -y

## Casa OS

    curl -fsSL https://get.casaos.io | bash

## Home Assistant IO

    sudo apt-get install -y python3 python3-dev python3-venv python3-pip bluez libffi-dev libssl-dev libjpeg-dev zlib1g-dev autoconf build-essential libopenjp2-7 libtiff5 libturbojpeg0-dev tzdata

### Home Assistant Install from Docker

    docker run -d --name home-assistant --privileged --restart=unless-stopped -e TZ=America/Fortaleza -v D://src//home-assistant//config:/config --network=host ghcr.io/home-assistant/home-assistant:stable

## Node-red

    bash <(curl -sL https://raw.githubusercontent.com/node-red/linux-installers/master/deb/update-nodejs-and-nodered)
    sudo systemctl enable nodered.service

## Node-red [Docker]

    docker pull nodered/node-red
    docker run --name node-red -p 1880:1880 -v nodered/node-red
    docker run --name node-red -p 1880:1880 -v node_red_data:/data nodered/node-red

## Jellyfin Server

    docker pull jellyfin/jellyfin:latest
    mkdir -p /home/dev/Documentos/jellyfin/{config,cache}   
    docker run --name jellyfin -p 8096:8096 -d -v /home/dev/Documentos/jellyfin/config:/config -v /home/dev/Documentos/jellyfin/cache:/cache -v /media/vald3flix:/media jellyfin/jellyfin:latest

## MongoDB

    docker run --name mongodb -d --restart unless-stopped -p 27017:27017 -d mongo:4.4.6
    docker run --name mongodb -p 27017:27017 -d mongo:4.4.6
    docker run --name mongodb -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=balta -e MONGO_INITDB_ROOT_PASSWORD=e296cd9f -d mongo:latest

## openmediavault [PI]

    sudo apt-get update -y && sudo apt-get upgrade -y
    sudo rm -f /etc/systemd/network/99-default.link
    sudo reboot

    wget -O - https://github.com/OpenMediaVault-Plugin-Developers/installScript/raw/master/install | sudo bash
    sudo omv-firstaid
    
    http://192.168.10.196/#/login
    The user name is admin and default password is openmediavault

## HD Utils

    sudo apt-get install ntfs-3g
    
    lsblk 

    sudo blkid
    /dev/sda2: LABEL="TV" BLOCK_SIZE="512" UUID="307EC3967EC352F0" TYPE="ntfs" PARTLABEL="Basic data partition" PARTUUID="8e0d71b4-88f4-4af6-bfdb-522b2ec422db"  

    sudo mount /dev/sda2 /home/pi/vald3flix
    
    sudo nano /etc/fstab    
    PARTUUID=8e0d71b4-88f4-4af6-bfdb-522b2ec422db /dev/sda2 /home/pi/vald3flix ntfs defaults 0 0

## Shared Folder

### setup

    sudo apt-get install samba samba-common-bin -y
    sudo nano /etc/samba/smb.conf

### smb.conf

[global]
workgroup = WORKGROUP
wins support = yes
usershare owner only = false

[PI]
comment=PI Home
path=/home/pi
browseable=Yes
writeable=Yes
only guest=no
create mask=0777
directory mask=0777
public=yes

### enable users

    sudo smbpasswd -a pi
    to open -> \\192.168.10.199\PI
