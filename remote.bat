@Echo off
set BASEDIR=%~dp0%
echo BASEDIR = %BASEDIR%
start java -jar "%BASEDIR%remotessh1.0.jar" %*