# influx_gatling_shootout

Performance testing of influxDB with gatling

##InfluxDB
[Getting started](https://docs.influxdata.com/influxdb/v1.3/introduction/getting_started/)

## Run influxDB in a docker container
```bash
$ docker pull influxdb

$ docker run -p 8086:8086 \
        -v $PWD:/var/lib/influxdb \
        influxdb
```

## Create database
```bash
curl -i -XPOST http://localhost:8086/query --data-urlencode "q=CREATE DATABASE mydb"
```

## Write via a point HTTP
```bash
curl -i -XPOST 'http://localhost:8086/write?db=mydb' --data-binary 'cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000'
```

## Write multiple points via HTTP
```bash
curl -i -XPOST 'http://localhost:8086/write?db=mydb' --data-binary 'cpu_load_short,host=server02 value=0.67
cpu_load_short,host=server02,region=us-west value=0.55 1422568543702900257
cpu_load_short,direction=in,host=server01,region=us-west value=2.0 1422568543702900257'
```
# Benchmarks

## Compile and run benchmarks
```sh
sbt gatling:test
```

Find results in `results` directory.