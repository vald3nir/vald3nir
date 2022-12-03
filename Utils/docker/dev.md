# Favorite Docker images for developers

## Node-red
  
    docker run --name node-red -d --restart unless-stopped -p 1880:1880 -d nodered/node-red
    npm i node-red-node-mongodb
    npm i node-red/node-red-dashboard

## Mosquitto [MQTT Broker]

    docker run --name mosquitto -d --restart unless-stopped -p 1883:1883 -p 9001:9001 -v D:\src\mosquitto\mosquitto.conf:/mosquitto/config/mosquitto.conf eclipse-mosquitto

### Example

    mosquitto_pub -h localhost -p 1881 -t my-mqtt-topic -m "sample-msg-1"

## MongoDB [Not Safe]

    docker run --name mongodb -d --restart unless-stopped -p 27017:27017 -d mongo:4.4.6

## MongoDB [Authenticated]

    docker run --name mongodb -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=dev -e MONGO_INITDB_ROOT_PASSWORD=12345678 -d mongo:4.4.6
