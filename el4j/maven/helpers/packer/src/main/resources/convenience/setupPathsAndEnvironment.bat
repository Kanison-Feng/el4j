:: This is not yet tested a lot 

set M2_HOME=D:\Projects\EL4J\tools\maven
set PATH=d:\Projects\EL4J\tools\maven\bin;d:\Projects\EL4J\tools\bin;%PATH%

set MAVEN_OPTS=-ea -Xmx1024M -Xss128k -XX:MaxPermSize=512M -Duser.language=en -Duser.region=US -Dcom.sun.management.jmxremote -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC
set CATALINA_OPTS=-ea -Xmx1024M -Xss128k -XX:MaxPermSize=256M

runMaven.bat
