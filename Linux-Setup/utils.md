# Softwares Utels

## Home Assistant IO

    sudo apt-get install -y python3 python3-dev python3-venv python3-pip bluez libffi-dev libssl-dev libjpeg-dev zlib1g-dev autoconf build-essential libopenjp2-7 libtiff5 libturbojpeg0-dev tzdata

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
