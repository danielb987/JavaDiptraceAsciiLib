#!/bin/bash

# Abort on error
set -e

# "ant test" compiles the project and runs the tests.
# travis_utility/push-javadoc-to-docs.sh generates the javadoc and publish it.
# travis_utility/run_coverage.sh runs the jacoco test, generates the report and and upload it to Coveralls.

ant test && \
    travis_utility/run_findbugs.sh && \
    travis_utility/push-javadoc-to-docs.sh && \
    travis_utility/run_coverage.sh
