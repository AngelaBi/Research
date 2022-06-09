#!/usr/bin/groovy
import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.physics.*;
import org.root.histogram.*;
import org.root.pad.*;

System.err.println(new Date())


//EvioDataChain reader = new EvioDataChain();
//reader.addFile("file1.evio");
//reader.addFile("file2.evio");
//reader.addFile("file3.evio");
//reader.open();

EvioDataChain hiporeader = new EvioDataChain();
//EvioDataChain dvcsreader = new EvioDataChain();
//EvioDataChain pi0reader = new EvioDataChain();
//
for(fname in args){
  hiporeader.addFile(fname);
}
//	if(fname.contains("dvcs"))
//		dvcsreader.addFile(fname);
//	else
//		pi0reader.addFile(fname);
//dvcsreader.open()
//pi0reader.open()
hiporeader.open()

//
//GenericKinematicFitter fitter = new GenericKinematicFitter(11,"11:22:X+:X-:Xn");
//EventFilter filter = new EventFilter("11:22:2212:X+:X-:Xn");
GenericKinematicFitter fitter = new GenericKinematicFitter(11,"11:X+:X-:Xn");
EventFilter filter = new EventFilter("11:X+:X-:Xn");
//
//H1D hthpi0 = new H1D("hthpi0","hth",100,0,5);
//H1D hthdvcs = new H1D("hthdvcs","hth",100,0,5);
//
H1D hp=new H1D("hp","hp",100,1,10);
H1D hprec=new H1D("hp","hp",100,1,10);

int counter = 0 ;
while(hiporeader.hasEvent()){
counter++;
println counter;
EvioDataEvent event = hiporeader.getNextEvent();
PhysicsEvent  genEvent  = fitter.getGeneratedEvent(event);
PhysicsEvent  recEvent  = fitter.getPhysicsEvent(event);
//rev = fitter.getPhysicsEvent(event)
if(filter.isValid(genEvent)==true){
  Particle el = genEvent.getParticleByPid(11,0);
  Particle elrec = recEvent.getParticleByPid(11,0);
//  Particle rx = rev.getParticle("[b]+[t]-[11]-[2212]");
  hp.fill(el.p());
  hprec.fill(elrec.p());
}
}
TGCanvas c1 = new TGCanvas("c1","c1",500,500,1,1)
c1.cd(0)
c1.draw(hp)
hprec.setLineColor(2);
//g_pz_rec.setLineColor(2);
c1.draw(hprec, "same")

//int counter = 0;
//while(dvcsreader.hasEvent()){
// counter++;
// if(counter%2000 == 0) println counter;
// EvioDataEvent event = dvcsreader.getNextEvent();
// rev = fitter.getPhysicsEvent(event)
//
// if(filter.isValid(rev)==true){
//	Particle rg = rev.getParticleByPid(22,0);
//	Particle rx = rev.getParticle("[b]+[t]-[11]-[2212]");
//	ang = Math.acos(rg.cosTheta(rx))*53.72;
//	hthdvcs.fill(ang);
// }
//}
//
//counter = 0;
//while(pi0reader.hasEvent()){
// counter++;
// if(counter%2000 == 0) println counter;
// EvioDataEvent event = pi0reader.getNextEvent();
// rev = fitter.getPhysicsEvent(event)
//
// if(filter.isValid(rev)==true){
//	Particle rg = rev.getParticleByPid(22,0);
//	Particle rx = rev.getParticle("[b]+[t]-[11]-[2212]");
//	ang = Math.acos(rg.cosTheta(rx))*53.72;
//	hthpi0.fill(ang);
// }
//}
//
//System.err.println(new Date())
//
//TGCanvas c1 = new TGCanvas("c1","c1",500,500,1,1)
//c1.cd(0)
//c1.draw(hthdvcs)
//c1.draw(hthpi0, "same")
