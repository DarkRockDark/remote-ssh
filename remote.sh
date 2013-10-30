#!/bin/bash
BASEDIR=$(dirname $0)
echo BASEDIR $BASEDIR
java -jar "$BASEDIR/remotessh1.0.jar" $*