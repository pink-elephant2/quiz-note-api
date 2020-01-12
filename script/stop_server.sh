#!/bin/bash

javaPid=`pgrep java`
if [[ -n  ${javaPid} ]]; then
   kill -9 ${javaPid}
fi
