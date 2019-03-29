#!/bin/sh
# Checks that all text files end with a newline.

ret=0

# Preserve csvFile with spaces by only splitting on newlines.
IFS='
'

for csvFile in $(git grep --cached -I -l -e '' -- ':/'); do
    if [ "$(tail -c 1 "./$csvFile")" != '' ]; then
        line="$(wc -l "./$csvFile" | cut -d' ' -f1)"
        echo "ERROR:$csvFile:$line: no newline at EOF."
        ret=1
    fi
done

exit $ret
