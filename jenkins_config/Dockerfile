# Starting off with the Jenkins base Image
FROM jenkins/jenkins:lts
USER root

# install openJdk 
RUN cd /opt && \
    apt-get update \
    && apt-get install -y --no-install-recommends \
        ca-certificates \
        curl \
        wget \
    && curl \
        -L \
        -o openjdk.tar.gz \
        https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_linux-x64_bin.tar.gz \
    && mkdir jdk \
    && tar zxf openjdk.tar.gz -C jdk --strip-components=1 \
    && rm -rf openjdk.tar.gz \
    && ln -sf /opt/jdk/bin/* /usr/local/bin/ \
    && rm -rf /var/lib/apt/lists/* \
# get maven 3.2.2
    && wget --no-verbose -O /tmp/apache-maven-3.6.3-bin.tar.gz http://apache.cs.utah.edu/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz \
# install maven
    && tar xzf /tmp/apache-maven-3.6.3-bin.tar.gz -C /opt/ && \
    ln -s /opt/apache-maven-3.6.3 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/local/bin && \
    rm -f /tmp/apache-maven-3.6.3-bin.tar.gz && \
    chown -R jenkins:jenkins /opt/maven && \
    apt-get clean && \
# Installing the plugins we need using the in-built install-plugins.sh script
    JENKINS_UC_DOWNLOAD=http://archives.jenkins-ci.org /usr/local/bin/install-plugins.sh git matrix-auth workflow-aggregator \
    docker-workflow blueocean job-dsl credentials-binding simple-theme-plugin \
    jquery gravatar

# Setting up environment variables for Jenkins admin user
ENV JENKINS_USER admin
ENV JENKINS_PASS admin
ENV MAVEN_HOME /opt/maven
ENV JAVA_HOME /opt/jdk

# Skip the initial setup wizard
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false -Dpermissive-script-security.enabled=true -Dorg.jenkinsci.plugins.durabletask.BourneShellScript.LAUNCH_DIAGNOSTICS=true"
# Start-up scripts to set number of executors and creating the admin user
COPY executors.groovy /usr/share/jenkins/ref/init.groovy.d/executors.groovy
COPY default-user.groovy /usr/share/jenkins/ref/init.groovy.d/default-user.groovy
COPY create-jobs.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY jobs.groovy /var/jenkins-jobs/
COPY jenkins-theme.groovy /usr/share/jenkins/ref/init.groovy.d/jenkins-theme.groovy

VOLUME /var/jenkins_home
RUN chmod -R 777 /var/jenkins_home