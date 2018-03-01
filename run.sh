if [ ! -f target/mtfc-1.0-SNAPSHOT-jar-with-dependencies.jar ]; then
    sh ./install.sh
fi
java -jar target/mtfc-1.0-SNAPSHOT-jar-with-dependencies.jar