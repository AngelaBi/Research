#!/bin/csh
foreach i (`ls ed_edg_10[0-9].evt`)
#foreach i (`ls ed_edg_00[1-9].evt`)
    echo $i
    sed -i 's/\(1\s\+\)45/\11000010020/g' $i 
    sed -i 's/\(.*\)0.00000000\s\+$/\1-3\.00000000/g' $i
end
