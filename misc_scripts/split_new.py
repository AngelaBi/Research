import math
import sys
import os

import time
start_time = time.time()
prevtime=start_time
now=start_time
if len(sys.argv)!=2:
    print("Usage: python split.py filename")
    exit()

filename,extension=os.path.splitext(sys.argv[1])
#filename='ed_edpi0_001'
with open(filename+extension) as f:
    lines = f.readlines()
#    print(lines)
count=0
noEvents=0
prevoutfilename=""
option='a'
for line in lines:
    count += 1
    noColumns=len(line.split())
    if noColumns==10:
        noEvents += 1
    outfilename=filename + "_"+str(math.ceil(noEvents/10000))+".txt";
    if outfilename == prevoutfilename:
        option='a'
    else:
        option='w'
        prevoutfilename=outfilename
        now=time.time()
        print("--- %s seconds ---" % (now-prevtime))
        prevtime=now
    with open(outfilename, option) as f:
        f.write(line)
        f.close()

print(noEvents)
noFiles=math.ceil(noEvents/10000)
print("splitting in Files+",noFiles)

print("--- %s seconds ---" % (time.time() - start_time))
#for filen in range(noFiles):

#    with open(filename + str(), 'w') as f:
#        f.write('readme')
