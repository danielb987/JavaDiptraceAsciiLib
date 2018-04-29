#!/bin/bash

# Abort on error
set -e

# Don't create javadoc and JavaDiptraceAsciiLib.jar if the repository is a fork, or if it is a pull request or if the branch is not master

# if [ "$TRAVIS_REPO_SLUG" == "danielb987/JavaDiptraceAsciiLib" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

echo -e "Create jar file and run javadoc"

# Create jar file
ant jar

# Create javadoc
ant javadoc

# Check if this project's javadoc follows coding standard
# We have a lot of errors at the moment, so don't stop on error at this point.
# Later, change this to stop on error.
ant checkstyle
#  ant checkstyle || true

# Get a summary of the checkstyle report
java -cp dist/JavaDiptraceAsciiLib.jar util.CheckStyleAnalyzer checkstyle build/checkstyle_errors.xml build/checkstyle_report.html

# Check if any findbugs errors
php --file $DIR/travis_utility/num_findbugs_errors.php -- findbugs.html




# Don't publish anything if the repository is a fork, or if it is a pull request or if the branch is not master

if [ "$TRAVIS_REPO_SLUG" == "danielb987/JavaDiptraceAsciiLib" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  echo -e "Publishing javadoc...\n"

  export DIR=$(pwd)

  # Create a temp directory
  cd $HOME
  mkdir temp
  cd temp

  # Clone the master branch of the git repository
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=master https://${GH_TOKEN}@github.com/danielb987/JavaDiptraceAsciiLib.Documentation > /dev/null

  # Change directory to the repository
  cd JavaDiptraceAsciiLib.Documentation

  # Change directory to the docs directory
  cd docs

  # Remove the old distribution folder, create a new one, and
  # copy the JavaDiptraceAsciiLib.jar file to the distribution folder.
  rm -Rf distribution
  mkdir distribution
  cp $DIR/dist/*.jar distribution/

  # Remove the javadoc folder and copy the generated javadoc folder to current folder
  rm -Rf javadoc
  cp -R $DIR/dist/javadoc .

  # Remove the checkstyle folder and copy the checkstyle report to the checkstyle folder
  rm -Rf checkstyle
  mkdir checkstyle
  cp $DIR/build/checkstyle_errors.xml checkstyle/
  cp $DIR/build/checkstyle_report.html checkstyle/

  echo -e "ls -al checkstyle"
  ls -al checkstyle/

  # Copy the checkstyle report
  cp $DIR/build/findbugs/findbugs.html .

  # Upload the distribution and javadoc to the master branch at GitHub
  git add -f .
  git commit -m "Latest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to master"
  git push -fq origin master > /dev/null

fi
