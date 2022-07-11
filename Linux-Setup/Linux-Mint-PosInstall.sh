#!/usr/bin/env bash
# ----------------------------- VARIÁVEIS ----------------------------- #
PPA_LIBRATBAG="ppa:libratbag-piper/piper-libratbag-git"
PPA_LUTRIS="ppa:lutris-team/lutris"
PPA_GRAPHICS_DRIVERS="ppa:graphics-drivers/ppa"

URL_WINE_KEY="https://dl.winehq.org/wine-builds/winehq.key"
URL_PPA_WINE="https://dl.winehq.org/wine-builds/ubuntu/"
URL_GOOGLE_CHROME="https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb"
URL_SIMPLE_NOTE="https://github.com/Automattic/simplenote-electron/releases/download/v1.8.0/Simplenote-linux-1.8.0-amd64.deb"
URL_4K_VIDEO_DOWNLOADER="https://dl.4kdownload.com/app/4kvideodownloader_4.9.2-1_amd64.deb"
URL_INSYNC="https://d2t3ff60b2tol4.cloudfront.net/builds/insync_3.0.20.40428-bionic_amd64.deb"

DIRETORIO_DOWNLOADS="$HOME/Downloads/programas"

PROGRAMAS_PARA_INSTALAR=(
  snapd
  mint-meta-codecs
  winff
  guvcview
  virtualbox
  flameshot
  nemo-dropbox
  steam-installer
  steam-devices
  steam:i386
  ratbagd
  piper
  lutris
  libvulkan1
  libvulkan1:i386
  libgnutls30:i386
  libldap-2.4-2:i386
  libgpg-error0:i386
  libxml2:i386
  libasound2-plugins:i386
  libsdl2-2.0-0:i386
  libfreetype6:i386
  libdbus-1-3:i386
  libsqlite3-0:i386
)
# ---------------------------------------------------------------------- #

# ----------------------------- REQUISITOS ----------------------------- #
## Removendo travas eventuais do apt ##
sudo rm /var/lib/dpkg/lock-frontend
sudo rm /var/cache/apt/archives/lock

## Adicionando/Confirmando arquitetura de 32 bits ##
sudo dpkg --add-architecture i386

## Atualizando o repositório ##
sudo apt update -y

## Adicionando repositórios de terceiros e suporte a Snap (Driver Logitech, Lutris e Drivers Nvidia)##
sudo apt-add-repository "$PPA_LIBRATBAG" -y
sudo add-apt-repository "$PPA_LUTRIS" -y
sudo apt-add-repository "$PPA_GRAPHICS_DRIVERS" -y
wget -nc "$URL_WINE_KEY"
sudo apt-key add winehq.key
sudo apt-add-repository "deb $URL_PPA_WINE bionic main"
# ---------------------------------------------------------------------- #

# ----------------------------- EXECUÇÃO ----------------------------- #
## Atualizando o repositório depois da adição de novos repositórios ##
sudo apt update -y

## Download e instalaçao de programas externos ##
mkdir "$DIRETORIO_DOWNLOADS"
wget -c "$URL_GOOGLE_CHROME"       -P "$DIRETORIO_DOWNLOADS"
wget -c "$URL_SIMPLE_NOTE"         -P "$DIRETORIO_DOWNLOADS"
wget -c "$URL_4K_VIDEO_DOWNLOADER" -P "$DIRETORIO_DOWNLOADS"
wget -c "$URL_INSYNC"              -P "$DIRETORIO_DOWNLOADS"

## Instalando pacotes .deb baixados na sessão anterior ##
sudo dpkg -i $DIRETORIO_DOWNLOADS/*.deb

# Instalar programas no apt
for nome_do_programa in ${PROGRAMAS_PARA_INSTALAR[@]}; do
  if ! dpkg -l | grep -q $nome_do_programa; then # Só instala se já não estiver instalado
    apt install "$nome_do_programa" -y
  else
    echo "[INSTALADO] - $nome_do_programa"
  fi
done

sudo apt install --install-recommends winehq-stable wine-stable wine-stable-i386 wine-stable-amd64 -y

## Instalando pacotes Flatpak ##
flatpak install flathub com.obsproject.Studio -y

## Instalando pacotes Snap ##
sudo snap install spotify
sudo snap install slack --classic
sudo snap install skype --classic
sudo snap install photogimp
# ---------------------------------------------------------------------- #

# ----------------------------- PÓS-INSTALAÇÃO ----------------------------- #
## Finalização, atualização e limpeza##
sudo apt update && sudo apt dist-upgrade -y
flatpak update
sudo apt autoclean
sudo apt autoremove -y
# ---------------------------------------------------------------------- #











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