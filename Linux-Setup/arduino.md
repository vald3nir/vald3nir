# Setup Arduino Serial

## Setup Permission [Linux]

    ls -l /dev/ttyACM*
    sudo usermod -a -G dialout <username>

## Read from serial [Linux]

    cat /dev/ttyACM0
