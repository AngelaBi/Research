import org.jlab.clas.physics.GenericKinematicFitter;
import org.jlab.clas.physics.Particle;
import org.jlab.clas.physics.PhysicsEvent;
import org.jlab.clas.physics.RecEvent;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.math.F1D;
import org.jlab.groot.group.DataGroup;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.fitter.ParallelSliceFitter;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.*;
import org.jlab.clas.physics.Vector3;
import org.jlab.clas.detector.DetectorResponse;
import org.jlab.clas.detector.DetectorParticle;
import org.jlab.detector.base.DetectorType;
import org.jlab.service.ec.*;
import org.jlab.geom.prim.Vector3D;
import org.jlab.groot.data.TDirectory;


String fileName = "/Users/devita/NetBeansProjects/KPP-Plots/kpp_histos.hipo"

System.out.println("Opening file: " + fileName);
TDirectory dir = new TDirectory();
dir.readFile(fileName);
//System.out.println(dir.getDirectoryList());
dir.cd();
dir.pwd();


H1F h1p = dir.getObject("EC", "hi_pi0_mass");
double hAmp  = h1p.getBinContent(h1p.getMaximumBin());
double hMean = h1p.getAxis().getBinCenter(h1p.getMaximumBin());
double hRMS  = 10; 
F1D f1p = new F1D("fpi0", "[amp]*gaus(x,[mean],[sigma])+[p0]+[p1]*x+[p2]*x*x", 50.0,220.0);
f1p.setOptStat("1111111");
f1p.setLineColor(2);
f1p.setParameter(0, hAmp);
f1p.setParLimits(0, hAmp*0.8, hAmp*1.2);
f1p.setParameter(1, hMean);
f1p.setParLimits(1, hMean-hRMS, hMean+hRMS);
f1p.setParameter(2, 2.0);
f1p.setParameter(3, 0.0);
f1p.setParameter(4, 0.0);
DataFitter.fit(f1p,h1p,"LQ");

TCanvas crosses = new TCanvas("crosses", 1000, 800);
//crosses.divide(6,3);
crosses.getCanvas().setGridX(false); crosses.getCanvas().setGridY(false);
crosses.getCanvas().setAxisFontSize(18);
crosses.getCanvas().setAxisTitleSize(24);
crosses.getCanvas().draw(dir.getObject("EC", "hi_pi0_mass"));
