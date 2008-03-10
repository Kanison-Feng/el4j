#!/bin/bash -e

if [ $# -lt 1 ] ; then
	echo "No profile specified."
	exit 0
fi

case $1 in
	"external_nightly")
		cd external
		svn up
		mvn clean install -fae -B -Pauto,weblogic10x,oracle $2
		mvn -f site/pom.xml site-deploy $2
		;;
	"external_svn")
		cd external
		mvn clean install -fae -B -U $2
		;;
	"internal_nightly")
		cd internal
		svn up
		mvn clean install -fae -B -Pauto,weblogic10x,oracle $2
		mvn -f site/pom.xml site-deploy $2
		cd ..
		
		# test templates
		./internal/etc/release-scripts/createTemplates.sh
		./internal/etc/release-scripts/createTemplates.sh clean
		;;
	"internal_svn")
		cd internal
		mvn clean install -fae -B -U $2
		;;
	"release_tomcat")
		mvn -f external/pom.xml clean install -fae -B -Pauto,tomcat6x,db2 $2
		mvn -f internal/pom.xml clean install -fae -B -Pauto,tomcat6x,db2 $2
		;;
	"release_weblogic")
		mvn -f external/pom.xml clean install -fae -B -Pauto,weblogic10x,oracle $2
		mvn -f internal/pom.xml clean install -fae -B -Pauto,weblogic10x,oracle $2
		;;
	"release_website")
		cd external/site
		./site.sh
		cd ../..

		## internal is not necessary
		#cd internal/site
		#./site.sh
		#cd ../..
		;;
	"clean_checkout")
		rm -rf external
		rm -rf internal

		if [ $# -ge 3 ] ; then
			SVN_EXTERNAL=$2
			SVN_INTERNAL=$3
		else
			SVN_EXTERNAL=https://el4j.svn.sourceforge.net/svnroot/el4j/trunk/el4j
			SVN_INTERNAL=https://cvs.elca.ch/subversion/el4j-internal/trunk
		fi
		# Checkout the external sourcecode
		svn co -q $SVN_EXTERNAL external

		# Checkout the internal sourcecode
		svn co -q $SVN_INTERNAL internal
		;;
	"weekly")
		mvn -f external/pom.xml clean install -fae -B -Pauto,tomcat6x,oracle $2
		mvn -f internal/pom.xml clean install -fae -B -Pauto,tomcat6x,oracle $2
		mvn -f external/pom.xml clean install -fae -B -Pauto,weblogic10x,db2 $2
		mvn -f internal/pom.xml clean install -fae -B -Pauto,weblogic10x,db2 $2
		;;
	"archetype")
		version=$(cat external/maven/archetypes/module-template/pom.xml | grep "<version>" | tail -n 1 | tr -d ' \r\n<>version/' | sed 's/-SNAPSHOT//')

		mvn archetype:create -DarchetypeGroupId=ch.elca.el4j  \
			 -DarchetypeArtifactId=EL4JArchetypeCore -DarchetypeVersion=$version \
			 -DgroupId=ch.elca.test -DartifactId=testarchetype  \
			 -DremoteRepositories=http://el4.elca-services.ch/el4j/maven2repository \

		mvn -f testarchetype/pom.xml clean install $2
		rm -rf testarchetype
		;;
esac