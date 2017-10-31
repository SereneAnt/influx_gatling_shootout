# Load tests with Gatling

Http endpoint load testing with gatling.


## 1. Configuration
`src/test/resources/test.conf`

```
            Load, QPS
                ^
                |                           
    low-qps     +. . . . . . . . . . . . . **
X stress-factor |                         * *
                |                        *  *
                |                       *   *
                |                      *    *
                |                     *     *
                |                    *      *
                |                   *       *
                |                  *        *
        low-qps +. . . . .*********. . . . .*. . . . . . . . **
                |       * .        .        *              *  .
                |     *   .        .        *            *    .
                |   *     .        .        *          *      .
                | *       .        .        *        *        .  
                +---------+--------+--------+********+--------+-> Time
                   part-duration
```

## 2. InfluxDB
[Getting started](https://docs.influxdata.com/influxdb/v1.3/introduction/getting_started/)

**Run influxDB in a docker container**
```bash
$ docker pull influxdb

$ docker run -p 8086:8086 \
        -v $PWD:/var/lib/influxdb \
        influxdb
```

**Create database**
```bash
curl -i -XPOST http://localhost:8086/query --data-urlencode "q=CREATE DATABASE mydb"
```

**Write TO DB via a point HTTP**
```bash
curl -i -XPOST 'http://localhost:8086/write?db=mydb' --data-binary 'cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000'
```

**Write multiple points via HTTP**
```bash
curl -i -XPOST 'http://localhost:8086/write?db=mydb' --data-binary 'cpu_load_short,host=server02 value=0.67
cpu_load_short,host=server02,region=us-west value=0.55 1422568543702900257
cpu_load_short,direction=in,host=server01,region=us-west value=2.0 1422568543702900257'
```
**Run Benchmarks**
```sh
sbt "gatling:testOnly *InfluxTest"
```

## 3. REST service

**Run Benchmarks**
```sh
sbt "gatling:testOnly *RestServiceTest"
```

## 4. Results
Find simulation log and generated reports in `/target/gatling/<test-name-epoch>/` directory, 
open `index.html` file.
