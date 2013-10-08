remote-ssh
==========

Installation
------------
Compile the code using ANT(http://ant.apache.org/). The build.xml and the build.properties are provided part of the project.

For installing and other ANT related stuff please refer http://ant.apache.org/manual/install.html

***Note:***The compiled distribution binaries will be found inside `build/dist` folder.

**Running**
~~~
remote [OPTIONS]
~~~
**See options**:

***REQUIRED***
~~~
-h <hostname> - Supply the hostname to SSH
-c <command>  - The command to be executed
~~~
***OPTIONAL***: It will be prompted if not supplied
~~~
-u <username> - The username to SSH
-p <password> - The password for currently login
~~~

**Example**

The following will fire `ls-ltr` on `wwww.hostname.com`

***WINDOWS***
~~~
remote -h wwww.hostname.com -c "ls -ltr" 
~~~

***UNIX/LINUX***
~~~
./remote.sh -h wwww.hostname.com -c "ls -ltr" 
~~~
