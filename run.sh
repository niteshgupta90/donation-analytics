  

export LAST_DIR=`pwd`

cd ../.. && mvn clean install

java -jar target/donation-analytics-1.0-SNAPSHOT-jar-with-dependencies.jar input/itcont.txt input/percentile.txt

cd $LAST_DIR/..
pwd

