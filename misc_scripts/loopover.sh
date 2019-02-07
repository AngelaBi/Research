#!/bin/bash

echo $1
echo $2

directoryIN="/work/clas12/rga/spring18/pass0/v0/Ebeam10p6_negativeInbending/cooked_5bp7p1/"
directoryOUT="/work/clas12/rga/spring18/pass0/v0/Ebeam10p6_negativeOutbending/cooked_5bp7p1/"
directory=$directoryIN
program=/home/biselli/local/src/KPP-Plots/bin/kpp-batch
for i in `seq $1 $2`; 
do
    echo item: $i
    #files=$(ls -A $directory/out_clas_00$i.evio.[2-3][0-9].hipo)
    files=$(ls -A $directory/out_clas_00$i.evio.[2][3-4].hipo)
    echo $files
    if [[ $? != 0 ]]; then
        echo "Command failed."
    elif [[ $files ]]; then
        echo "Files found."
        $program $files > out$i.log 2>error$i.log 
        mv kpp_histos.hipo kpp_histos.$i.hipo
        
    else
        echo "No files found."
    fi
done

#ls /work/clas12/rga/spring18/pass0/v0/Ebeam10p6_negativeInbending/cooked_5bp7p1/out_clas_00$1.evio.[2-3][0-9].hipo



