
#   dependencies:
#       pre:
#        - source environmentSetup.sh && copyEnvVarsToGradleProperties

#!/usr/bin/env bash

function copyEnvVarsToGradleProperties {
    GRADLE_PROPERTIES=$HOME"/.gradle/gradle.properties"
    export GRADLE_PROPERTIES
    echo "Gradle Properties should exist at $GRADLE_PROPERTIES"

    if [ ! -f "$GRADLE_PROPERTIES" ]; then
        echo "Gradle Properties does not exist"

        echo "Creating Gradle Properties file..."
        touch $GRADLE_PROPERTIES

        echo "Writing TEST_API_KEY to gradle.properties..."
        echo "sonatypeUser=$SONATYPE_USER" >> $GRADLE_PROPERTIES
        echo "sonatypePass=$SONATYPE_PASS" >> $GRADLE_PROPERTIES
        echo "bintrayUser=$BINTRAY_USER" >> $GRADLE_PROPERTIES
        echo "bintrayPass=$BINTRAY_PASS" >> $GRADLE_PROPERTIES
    fi
}