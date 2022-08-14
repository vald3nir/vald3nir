# Setup PI

    sudo apt-get update && sudo apt-get upgrade && sudo apt-get install apt-transport-https build-essential git curl fish -y

## Casa OS

    curl -fsSL https://get.casaos.io | bash

## Node-red

    bash <(curl -sL https://raw.githubusercontent.com/node-red/linux-installers/master/deb/update-nodejs-and-nodered)
    sudo systemctl enable nodered.service

## openmediavault

    sudo apt-get update -y && sudo apt-get upgrade -y
    sudo rm -f /etc/systemd/network/99-default.link
    sudo reboot

    wget -O - https://github.com/OpenMediaVault-Plugin-Developers/installScript/raw/master/install | sudo bash
    sudo omv-firstaid
    
    http://192.168.10.196/#/login
    The user name is admin and default password is openmediavault

## Plex Server

    http://192.168.10.196:32400/manage

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
