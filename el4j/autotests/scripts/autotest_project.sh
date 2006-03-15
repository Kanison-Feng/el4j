#!/bin/bash

# $URL$
# $Revision$
# $Date$
# $Author$

DOTASK="${1}"
PROJECTFOLDER="${2}"
REPORTNAME="${3}"
TESTSET="${4}"
TESTPROFILES="${5}"
shift 5

if [ -z "$BASEDIR" ] || ! [ -d "$BASEDIR" ] ; then
    echo "Env variable BASEDIR must be set and point "
    echo "to the directory where the EL4J autotest "
    echo "configuration and EL4J projects are!"
    exit 1
fi

if [ -z "$REPORTDIR" ] || ! [ -d "$REPORTDIR" ] ; then
    echo "Env variable REPORTDIR must be set and point "
    echo "to the directory where the generated website "
    echo "should be copied!"
    exit 1
fi

if [ -z "$REPORTURL" ] ; then
    echo "Env variable REPORTURL must be set with the "
    echo "URL the directory ${REPORTDIR} is reachable!"
    exit 1
fi

SCRIPTDIR="`pwd`"

MAILPROPERTIES="${BASEDIR}/autotests/etc/mail.properties"
ANTMAILLOGGERPARAMS="-logger org.apache.tools.ant.listener.MailLogger -propertyfile ${MAILPROPERTIES}"
MAILERANTCMD="${BASEDIR}/autotests/mailer/ant/bin/ant -f ${BASEDIR}/autotests/mailer/build.xml ${ANTMAILLOGGERPARAMS}"
ANTCMD="${BASEDIR}/external-tools/ant/bin/ant"
XANTCMD="${ANTCMD} ${ANTMAILLOGGERPARAMS}"

PROJECTDIR="${BASEDIR}/${PROJECTFOLDER}"
ENVDIR="${BASEDIR}/autotests/env"

STARTTIME="`date +%Y%m%d_%H%M%S`"

# performs junit tests for the profile hold in variable $1. Any other variable is added to the Ant call.
testProfile () {
    local PROFILE=${1}
    local LOGDIR="${LOGBASEDIR}/junit/${REPORTNAME}/${PROFILE}"
    local LOGFILE="log_output_${STARTTIME}.log"
    local JUNITFILE="junit_testsuites_${STARTTIME}.xml"
    local LOGPATH="${LOGDIR}/${LOGFILE}"
    local JUNITPATH="${LOGDIR}/${JUNITFILE}"
    local AUTODEPLOYPATH="${ENVDIR}/${REPORTNAME}/autodeploy-${PROFILE}"

    # reassign parameters: $1 <-- $2, $2 <-- $3, ...
    shift
    
    # clean autodeploy directory if exists
    if [ -d "$AUTODEPLOYPATH" ]; then
        rm -r "$AUTODEPLOYPATH/*"
    fi
          

    # copy profile
    cp "${ENVDIR}/${REPORTNAME}/env-${PROFILE}.properties" env/env.properties

    # make log dir
    mkdir -p "${LOGDIR}"
    
    #Delete old log files
    ${SCRIPTDIR}/delete_old_log_files.sh "${LOGDIR}"
    
    # invoke all junit tests
    ${XANTCMD} -f bootstrap.xml distclean 
    ${XANTCMD} -f bootstrap.xml configure -Dwebsite.donotclean=true $*
    ${XANTCMD} jars
    ${XANTCMD} junit.start.all -Ddisttest=true $* &> ${LOGPATH}
    cat "${LOGPATH}"
    ${XANTCMD} junit.report
    ${XANTCMD} junit.report $*

    # move to / create profile-specific reports
    mv "dist/website/junit/${TESTSET}" "dist/website/junit/${TESTSET}-${PROFILE}"
    echo "junit/${TESTSET}-${PROFILE}/index.html" > "dist/website/report-${TESTSET}-junit-${PROFILE}.log"
    
    sed "s/${TESTSET}/${TESTSET}-${PROFILE}/" \
        "dist/website/overview-${TESTSET}-junit.log" > "dist/website/overview-${TESTSET}-junit-${PROFILE}.log"
    
    rm "dist/website/report-${TESTSET}-junit.log"
    rm "dist/website/overview-${TESTSET}-junit.log"

    # copy the testsuites xml report file to log path
    cp "dist/website/junit/${TESTSET}-${PROFILE}/TESTS-TestSuites.xml" "${JUNITPATH}"
}

#sends a test mail for the given profile hold in variable $1.
testProfileMail () {
    local PROFILE=${1}
    local LOGDIR="${LOGBASEDIR}/junit/${REPORTNAME}/${PROFILE}"
    local LOGFILE="log_output_${STARTTIME}.log"
    local JUNITFILE="junit_testsuites_${STARTTIME}.xml"
    local LOGPATH="${LOGDIR}/${LOGFILE}"
    local JUNITPATH="${LOGDIR}/${JUNITFILE}"

    # send mail if test case contains errors or not
    ${MAILERANTCMD} -debug checkJunit \
        -Dlog.output.file="${LOGFILE}" \
        -Dlog.output.dir="${LOGDIR}" \
        -Dtest.set="${REPORTNAME}" \
        -Dtest.profile="${PROFILE}" \
        -Dtest.report.website.url="${REPORTURL}/${REPORTNAME}/" \
        -Dtest.report.xml="${JUNITPATH}"
}

if !([ "${DOTASK}" = "UpdateAndTest" ] || [ "${DOTASK}" = "TestOnly" ] || [ "${DOTASK}" = "UpdateOnly" ] || [ "${DOTASK}" = "CleanOnly" ]); then
    echo "Usage: ./your-script.sh DOTASK"
    echo "Available DOTASKs: UpdateAndTest, TestOnly, UpdateOnly, CleanOnly"
else
    cd ${PROJECTDIR}

    # Find out the repository type. Default is SVN.
    REPOTYPE="SVN"
    if [ -d CVS ]; then
        REPOTYPE="CVS"
    fi

    # Clean framework if asked for.
    if [ "${DOTASK}" = "UpdateAndTest" ] || [ "${DOTASK}" = "CleanOnly" ]; then
        echo "Clean project in directory ${PROJECTDIR}."

        # HACK! Remove unmeant files from EL4Ant checkstyle plugin in
        #       project directory.
        rm -f allclasses-frame.html
        rm -f coverage.ec
        rm -rf files/
        rm -f index.html
        rm -f overview-frame.html
        rm -f stylesheet.css

        if [ "${REPOTYPE}" = "CVS" ]; then
	    # Remove cvs backup files.
            find -name .#* -exec rm {} \;
        else
	    # Remove conflict files.
            find -name *.mine -exec rm {} \;
            find -name *.r* -exec rm {} \;
        fi
    else
        echo "Project in directory ${PROJECTDIR} not cleaned!"
    fi


    # Update repository if asked for.
    if [ "${DOTASK}" = "UpdateAndTest" ] || [ "${DOTASK}" = "UpdateOnly" ]; then
        echo "${REPOTYPE} update project in directory ${PROJECTDIR}."

        # Get a clean copy
        if [ "${REPOTYPE}" = "CVS" ]; then
            cvs update -d -C
	    find -name .#* -exec rm {} \;
        else
	    svn revert -R .
            svn update
	    svn cleanup

            find -name *.mine -exec rm {} \;
	    find -name *.r* -exec rm {} \;
	    
	    svn status
	fi
    else
        echo "No ${REPOTYPE} update for project in directory ${PROJECTDIR}!"
    fi


    # Execute tests if asked for.
    if [ "${DOTASK}" = "UpdateAndTest" ] || [ "${DOTASK}" = "TestOnly" ]; then
        echo "Testing project in directory ${PROJECTDIR}."

        # clean the old website
        rm -rf "${PROJECTDIR}/dist/website"

        # test with all profiles
        for TESTPROFILE in ${TESTPROFILES}
        do
            testProfile "${TESTPROFILE}" $*
        done

        # generate emma reports
        ${XANTCMD} -f bootstrap.xml distclean 
        ${XANTCMD} -f bootstrap.xml configure -Dwebsite.donotclean=true
        ${XANTCMD} jars junit.start.all emma.report -Demma=true -Ddisttest=true

        # generate reports (javadoc, checkstyle, website)
        ${XANTCMD} javadoc 
        ${XANTCMD} checkstyle 
        ${XANTCMD} website

        # copy website
        rm -rf "${REPORTDIR}/${REPORTNAME}"
        cp -r "${PROJECTDIR}/dist/website" "${REPORTDIR}/${REPORTNAME}"

        # mail for all tested profiles
        for TESTPROFILE in ${TESTPROFILES}
        do
            testProfileMail "${TESTPROFILE}"
        done
    else
        echo "Testing disabled for project in directory ${PROJECTDIR}!"
    fi
fi



