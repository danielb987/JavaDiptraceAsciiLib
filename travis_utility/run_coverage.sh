#!/bin/bash

# Abort on error
set -e

# Don't run coverage if the repository is a fork, or if it is a pull request or if the branch is not master

if [ "$TRAVIS_REPO_SLUG" == "danielb987/JavaDiptraceAsciiLib" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  echo -e "Run coverage"

  # Generate report
#  ant -v jacoco_report

  # Publish coverage report to https://coveralls.io/github/danielb987/JavaDiptraceAsciiLib
#  mvn coveralls:report

fi
