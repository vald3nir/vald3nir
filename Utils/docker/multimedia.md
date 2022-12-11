# Favorite Docker Images for Multimedia

## Jellyfin Server

    docker pull jellyfin/jellyfin:latest
    mkdir -p /home/dev/Documentos/jellyfin/{config,cache}   
    docker run --name jellyfin -p 8096:8096 -d -v /home/dev/Documentos/jellyfin/config:/config -v /home/dev/Documentos/jellyfin/cache:/cache -v /mnt/Vald3flix:/media jellyfin/jellyfin:latest

## Plex Server

    docker pull lscr.io/linuxserver/plex:latest
    mkdir -p /home/dev/Documentos/plex/config   
    docker run --name plex --net=host -d -v /home/dev/Documentos/plex/config:/config -v /mnt/Vald3flix:/media lscr.io/linuxserver/plex:latest
