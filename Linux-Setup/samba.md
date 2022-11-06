# Samba Config

    sudo apt-get install samba samba-common-bin -y
    sudo nano /etc/samba/smb.conf

## File smb.conf

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

## Enable users

    sudo smbpasswd -a pi
    to open -> \\192.168.10.199\PI
