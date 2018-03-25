### FutureBit MoonLander 2 hash rate monitor

This is a web app for monitoring hash rate performance of FutureBit MoonLander 2 LiteCoin scrypt USB miner (https://www.amazon.com/FutureBit-MoonLander-LiteCoin-Scrypt-Miner/dp/B0781D19S2).
Web app monitors log output from bfgminer_5.4.2-futurebit2_linux_armv6, pushes hash rate to redis store and displays on page with chartjs charts.
For now there are 3 default charts:
- last hour hash rate
- last 24 hours hash rate
- last 7 days hash rate

### Required software/tools

- Redis (tested with 3.2.6)
- Maven (tested with 3.5.2)
- Java 1.8

### Installation

- setup redis locally: https://redis.io/topics/quickstart
- update redis hostname/port in src/main/resources/application-prod.properties
- start bfgminer with "-L" option to print output to a log file e.g.
```
./bfgminer --scrypt -o stratum+tcp://us.litecoinpool.org:3333 -u xxxx.1 -p 1,d=128 -S ALL --set MLD:clock=756 -L output.log
```

- update src/main/resources/application-prod.properties with full path to bfgminer log file
- build war file with following command:
```
mvn clean package -P prod
```
- run it:
```
java -jar ./moonlander-monitor-0.0.1-SNAPSHOT.war
```

### sample screen

![sample screen](https://github.com/waso/moonlander-monitor/blob/master/src/main/resources/moonlander-monitor-dashboard.png)

### open bugs/issues

When you rotate bfgminer log file (or remove its content in any way), you have to restart the war file so it can pick up the changes.
