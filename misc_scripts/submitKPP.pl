#!/apps/bin/perl
#usage example ./submitKPP.pl 3407 3410 Inbending 
#print FILE "/home/biselli/local/src/KPP-Plots/bin/kpp-batch \n";
#require "ctime.pl";          
$num1 = $ARGV[0]; 
$num2 = $ARGV[1]; 
$conf = $ARGV[2];
system("cp KPPjob /home/biselli/farm/submitKPP/KPPjob$conf");
for ($i = $num1; $i <= $num2; $i++) {
    @array=`ls -1 /work/clas12/rga/spring18/pass0/v0/Ebeam10p6_negative$conf/cooked_5bp7p1/out_clas_00$i.evio.[2][3-5].hipo`;
    chomp @array;
    $inputfiles=join(" ",@array);
    print $inputfiles;
    system("ls");
    $filename = "KPPjob_$i";
    open (FILE, ">/home/biselli/farm/submitKPP/$filename");
    print FILE "PROJECT: clas12\n";
    print FILE "TRACK: analysis\n";
    print FILE "JOBNAME: rgAhisto\n";
    print FILE "COMMAND: KPPjob$conf $i $conf\n";
    print FILE "OS: centos7\n";
    print FILE "MAIL: biselli\n";
    print FILE "INPUT_FILES: $inputfiles \n";
    print FILE "OTHER_FILES: ";
    print FILE "/home/biselli/farm/submitKPP/KPPjob$conf \n";
    print FILE "TIME: 1440 \n";
    print FILE "MEMORY: 5000 MB\n";
    print FILE "DISK_SPACE: 5000 MB\n";
    close (FILE);
    #uncomment the following to submit jobs
    $cmd = "jsub /home/biselli/farm/submitKPP/$filename";
    system($cmd); 
    system("sleep 10");
}




