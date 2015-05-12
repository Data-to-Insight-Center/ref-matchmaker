#!/bin/sh

SRC_JAVA=./src
BUILD=$(pwd)/target
LIB=$(pwd)/target/dependency
CLASSES=$BUILD/classes:$BUILD/generated-code/resources
BIN=$(pwd)/bin
RETVAL=0
prog="build.sh"

###################################################################

build() {
    mvn install -Pmessaging -Dmaven.test.skip=true
    mvn dependency:copy-dependencies
    #mkdir bin

for i in $(ls $LIB |grep ".jar"); do
        CLASSES=$CLASSES:$LIB/$i
done

for i in $(ls $BUILD |grep ".jar"); do
        CLASSES=$CLASSES:$BUILD/$i
done

echo "
#!/bin/sh
CLASSES=$CLASSES
" > ./bin/MatchmakerClient.sh

echo '
if [ "$2" = "" ];
then
    echo
    echo "#########################################"
    echo "#         MatchmakerClient.sh           #"
    echo "#########################################"
    echo
    echo "$ MatchmakerClient.sh <properties file> <input file>"
    echo
    exit 1
fi

CP=:$CLASSPATH:$CLASSES:.
java -classpath $CP edu.indiana.d2i.sead.matchmaker.client.SynchronizedClient $1 $2
' >> ./bin/MatchmakerClient.sh
chmod 755 ./bin/MatchmakerClient.sh




        return $RETVAL
}
###################################################################
clean(){
        rm -rf $BIN
        mvn clean
        return $RETVAL
}
###################################################################
case "$1" in
  clean)
        clean
        ;;
  *)
        #clean
        build
        RETVAL=$?
        ;;
esac

exit $RETVAL

