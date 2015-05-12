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
    mvn clean install -Pmessaging -Dmaven.test.skip=true
    mvn dependency:copy-dependencies
    mkdir bin

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
if [ "$1" = "" ];
then
    echo
    echo "#########################################"
    echo "#         MatchmakerClient.sh           #"
    echo "#########################################"
    echo
    echo "$ MatchmakerClient.sh <properties file>"
    echo
    exit 1
fi

echo
echo "Generate java source code based on" $1
echo

CP=:$CLASSPATH:$CLASSES:.
java -classpath $CP edu.indiana.d2i.sead.matchmaker.client. $1
' >> ./bin/codegen.sh
chmod 755 ./bin/codegen.sh




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
        clean
        build
        RETVAL=$?
        ;;
esac

exit $RETVAL

