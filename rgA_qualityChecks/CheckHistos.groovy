package org.jlab.groot.demo;

import java.util.Random;
import javax.swing.JFrame;
import org.jlab.groot.data.DataLine;

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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import org.jlab.groot.math.FunctionFactory;
import org.jlab.groot.ui.LatexText;
import org.jlab.groot.ui.PaveText;

//System.out.println("Input file number: ");
//Scanner hiponum = new Scanner(System.in);
//System.out.println(hiponum.nextLine());

String fname =args[0]+".hipo";
//System.out.println(fname);
String fileName = "/Users/einstein/hipoFilesKPP/kpp_histos."+fname;
//System.out.println(fileName);

//String fileName = "/Users/einstein/hipoFilesKPP/kpp_histos.4013.hipo"


System.out.println("Opening file: " + fileName);
TDirectory dir = new TDirectory();
dir.readFile(fileName);
//System.out.println(dir.getDirectoryList());
dir.cd();
dir.pwd();

	System.out.println("\n");

H1F h1p = dir.getObject("TBT", "hi_vz_neg");
h1p.setOptStat("1101");
double hAmp  = h1p.getBinContent(h1p.getMaximumBin());
double hMean = h1p.getAxis().getBinCenter(h1p.getMaximumBin());
double hRMS  = 10;
F1D f1p = new F1D("TBT Negative Vertex Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -20.0,12.0);
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
System.out.println("Peak of Negative TBT: ");
	//double Peak_of_Negative_TBT=f1p.getParameter(1);
System.out.println(f1p.getParameter(1)+"\n");

H1F h1p3 = dir.getObject("TBT", "hi_vz_pos");
h1p3.setOptStat("1101");
double hAmp3  = h1p3.getBinContent(h1p3.getMaximumBin());
double hMean3 = h1p3.getAxis().getBinCenter(h1p3.getMaximumBin());
double hRMS3  = 10;
F1D f1p3 = new F1D("TBT Postive Vertex Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -20.0,12.0);
f1p3.setOptStat("1111111");
f1p3.setLineColor(2);
f1p3.setParameter(0, hAmp3);
f1p3.setParLimits(0, hAmp3*0.8, hAmp3*1.2);
f1p3.setParameter(1, hMean3);
f1p3.setParLimits(1, hMean3-hRMS3, hMean3+hRMS3);
f1p3.setParameter(2, 2.0);
f1p3.setParameter(3, 0.0);
f1p3.setParameter(4, 0.0);
DataFitter.fit(f1p3,h1p3,"LQ");
System.out.println("Peak of Positive TBT: ");
	//double Peak_of_Positive_TBT=f1p3.getParameter(1);
System.out.println(f1p3.getParameter(1)+"\n");

	System.out.println("\n");

H1F h1p4 = dir.getObject("CVT", "hi_vz_pos");
h1p4.setOptStat("1101");
double hAmp4  = h1p4.getBinContent(h1p4.getMaximumBin());
double hMean4 = h1p4.getAxis().getBinCenter(h1p4.getMaximumBin());
//double hRMS4  = 10;
F1D f1p4 = new F1D("CVT Positive Vertex Track", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -5.0,5.0);
f1p4.setOptStat("0000001");
//f1p4.setOptStat("1111111");
f1p4.setLineColor(0);
//f1p4.setLineColor(2);
//f1p4.setParameter(0, hAmp4);
//f1p4.setParLimits(0, hAmp4*0.8, hAmp4*1.2);
//f1p4.setParameter(1, hMean4);
//f1p4.setParLimits(1, hMean4-hRMS, hMean4+hRMS);
//f1p4.setParameter(2, 2.0);
//f1p4.setParameter(3, 0.0);
//f1p4.setParameter(4, 0.0);
DataFitter.fit(f1p4,h1p4,"LQ");

double halfmax1= h1p4.getBinContent(h1p4.getMaximumBin())/2;
//System.out.println("halfmax1= "+halfmax1);

int xp1=1000;
int xp2=1000;
int found1=0;

for (int n=0; n<=100;n++){
	double h= h1p4.getBinContent(n);
	//System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
	if( halfmax1-(halfmax1*0.1) <= h && h <= halfmax1+(halfmax1*0.1) ){
		if(found1==0){
			xp1=n;
			found1=1;
			//System.out.println("xp1= "+xp1+" and: "+h1p4.getAxis().getBinCenter(xp1));	
		}
		else if(found1==1){
			xp2=n;
			found1=1;
			//System.out.println("xp2= "+xp2+" and: "+h1p4.getAxis().getBinCenter(xp2));
		}
		else{
			System.out.println("Check Script");	
		}
	}

}

//System.out.println("xp1= "+h1p4.getAxis().getBinCenter(xp1));
//System.out.println("xp2= "+h1p4.getAxis().getBinCenter(xp2));

int MidP=((xp1+xp2)/2);
//double PCenterCVT= h1p4.getAxis().getBinCenter(MidP);
double PCenterCVT=((h1p4.getAxis().getBinCenter(xp2)+h1p4.getAxis().getBinCenter(xp1))/2);
//System.out.println((h1p4.getAxis().getBinCenter(xp2)+h1p4.getAxis().getBinCenter(xp1))/2);


System.out.println("Center of Positive CVT: ");
System.out.println(PCenterCVT+"\n");
System.out.println("Extension of Positive CVT: ");
	//double Extension_of_Positive_CVT=(h1p4.getAxis().getBinCenter(xp2)-h1p4.getAxis().getBinCenter(xp1);
System.out.println(h1p4.getAxis().getBinCenter(xp2)-h1p4.getAxis().getBinCenter(xp1)+"\n");

H1F h1p5 = dir.getObject("CVT", "hi_vz_neg");
h1p5.setOptStat("1101");
double hAmp5  = h1p5.getBinContent(h1p5.getMaximumBin());
double hMean5 = h1p5.getAxis().getBinCenter(h1p5.getMaximumBin());
//double hRMS  = 10;
F1D f1p5 = new F1D("CVT Negative Vertex Track", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -5.0,5.0);
f1p5.setOptStat("0000001");
f1p5.setLineColor(0);
//f1p5.setParameter(0, hAmp5);
//f1p5.setParLimits(0, hAmp5*0.8, hAmp5*1.2);
//f1p5.setParameter(1, hMean5);
//f1p5.setParLimits(1, hMean5-hRMS, hMean5+hRMS);
//f1p5.setParameter(2, 2.0);
//f1p5.setParameter(3, 0.0);
//f1p5.setParameter(4, 0.0);
DataFitter.fit(f1p5,h1p5,"LQ");

double halfmax2= h1p5.getBinContent(h1p5.getMaximumBin())/2;
//System.out.println("halfmax2= "+halfmax2);

int xn1=1000;
int xn2=1000;
int found2=0;

for (int n=0; n<=100;n++){
        double h= h1p5.getBinContent(n);
        //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p5.getAxis().getBinCenter(n));
        if( halfmax2-(halfmax2*0.1) <= h && h <= halfmax2+(halfmax2*0.1) ){
                if(found2==0){
                        xn1=n;
                        found2=1;
                        //System.out.println("xn1= "+xn1+" and: "+h1p5.getAxis().getBinCenter(xn1));
                }
                else if(found2==1){
                        xn2=n;
                        found2=1;
                       	//System.out.println("xn2= "+xn2+" and: "+h1p5.getAxis().getBinCenter(xn2));
                }
                else{
                        System.out.println("Check Script");
                }
        }

}

//System.out.println("xn1= "+h1p5.getAxis().getBinCenter(xn1));
//System.out.println("xn2= "+h1p5.getAxis().getBinCenter(xn2));

//int MidN=((xn1+xn2)/2);
//double NCenterCVT= h1p5.getAxis().getBinCenter(MidN);
double NCenterCVT=((h1p5.getAxis().getBinCenter(xn2)+h1p5.getAxis().getBinCenter(xn1))/2);
//System.out.println((h1p5.getAxis().getBinCenter(xn2)+h1p5.getAxis().getBinCenter(xn1))/2);

System.out.println("Center of Negative CVT: ");
System.out.println(NCenterCVT+"\n");
System.out.println("Extension of Negative CVT: ");
	//double Extension_of_Negative_CVT=h1p5.getAxis().getBinCenter(xn2)-h1p5.getAxis().getBinCenter(xn1);
System.out.println(h1p5.getAxis().getBinCenter(xn2)-h1p5.getAxis().getBinCenter(xn1)+"\n");

	System.out.println("\n");

H1F h1p6 = dir.getObject("EC", "hi_sf_EC");
h1p6.setOptStat("1101");
double hAmp6  = h1p6.getBinContent(h1p6.getMaximumBin());
double hMean6 = h1p6.getAxis().getBinCenter(h1p6.getMaximumBin());
//double hRMS6 = 10;
double hRMS6 = 0.2;
F1D f1p6 = new F1D("EC Electron Sampling Fraction", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 0.20,0.25);
f1p6.setOptStat("1111111");
f1p6.setLineColor(2);
f1p6.setParameter(0, hAmp6);
f1p6.setParLimits(0, hAmp6*0.8, hAmp6*1.2);
f1p6.setParameter(1, hMean6);
f1p6.setParLimits(1, hMean6-hRMS6, hMean6+hRMS6);
f1p6.setParameter(2, 2.0);
f1p6.setParameter(3, 0.0);
f1p6.setParameter(4, 0.0);
DataFitter.fit(f1p6,h1p6,"LQ");
System.out.println("Peak of EC electron SF: ");
	//double Peak_of_EC_electron_SF=f1p6.getParameter(1);
System.out.println(f1p6.getParameter(1)+"\n");

H1F h1p2 = dir.getObject("EC", "hi_pi0_mass");
h1p2.setOptStat("1101");
double hAmp2  = h1p2.getBinContent(h1p2.getMaximumBin());
double hMean2 = h1p2.getAxis().getBinCenter(h1p2.getMaximumBin());
double hRMS2  = 10;
F1D f1p2 = new F1D("EC pi0 Mass", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 50.0,220.0);
f1p2.setOptStat("1111111");
f1p2.setLineColor(2);
f1p2.setParameter(0, hAmp2);
f1p2.setParLimits(0, hAmp2*0.8, hAmp2*1.2);
f1p2.setParameter(1, hMean2);
f1p2.setParLimits(1, hMean2-hRMS2, hMean2+hRMS2);
f1p2.setParameter(2, 2.0);
f1p2.setParameter(3, 0.0);
f1p2.setParameter(4, 0.0);
DataFitter.fit(f1p2,h1p2,"LQ");
System.out.println("Peak of EC pi0: ");
	//double Peak_of_EC_pi0=f1p2.getParameter(1);
System.out.println(f1p2.getParameter(1)+"\n");

	System.out.println("\n");

H1F h1p8 = dir.getObject("HTCC", "hi_nphe_ele");
h1p8.setOptStat("1101");
double hAmp8  = h1p8.getBinContent(h1p8.getMaximumBin());
double hMean8 = h1p8.getAxis().getBinCenter(h1p8.getMaximumBin());
//double hRMS  = 10;
F1D f1p8 = new F1D("HTCC Photoelectron Distribution Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 2.0,20.0);
f1p8.setOptStat("1111111");
f1p8.setLineColor(2);
f1p8.setParameter(0, hAmp8);
f1p8.setParLimits(0, hAmp8*0.8, hAmp8*1.2);
f1p8.setParameter(1, hMean8);
f1p8.setParLimits(1, hMean8-hRMS, hMean8+hRMS);
f1p8.setParameter(2, 2.0);
f1p8.setParameter(3, 0.0);
f1p8.setParameter(4, 0.0);
DataFitter.fit(f1p8,h1p8,"LQ");
System.out.println("Photoelectron Distribution Peak: ");
	//double Photoelectron_Distribution_Peak=f1p8.getParameter(1);
System.out.println(f1p8.getParameter(1)+"\n");

	System.out.println("\n");

H1F h1p9 = dir.getObject("CTOF", "hi_z_track");
h1p9.setOptStat("1101");
double hAmp9  = h1p9.getBinContent(h1p9.getMaximumBin());
double hMean9 = h1p9.getAxis().getBinCenter(h1p9.getMaximumBin());
//double hRMS  = 10;
F1D f1p9 = new F1D("CTOF residuals", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -10.0,38.0);
f1p9.setOptStat("1111111");
f1p9.setLineColor(2);
f1p9.setParameter(0, hAmp9);
f1p9.setParLimits(0, hAmp9*0.8, hAmp9*1.2);
f1p9.setParameter(1, hMean9);
f1p9.setParLimits(1, hMean9-hRMS, hMean9+hRMS);
f1p9.setParameter(2, 2.0);
f1p9.setParameter(3, 0.0);
f1p9.setParameter(4, 0.0);
DataFitter.fit(f1p9,h1p9,"LQ");
//System.out.println("Photoelectron Distribution Peak: ");
//System.out.println(f1p9.getParameter(1));

	//System.out.println("\n");

H1F h1p10 = dir.getObject("CND", "hi_z_track");
h1p10.setOptStat("1101");
double hAmp10  = h1p10.getBinContent(h1p10.getMaximumBin());
double hMean10 = h1p10.getAxis().getBinCenter(h1p10.getMaximumBin());
//double hRMS  = 10;
F1D f1p10 = new F1D("CND residuals", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -10.0,38.0);
f1p10.setOptStat("1111111");
f1p10.setLineColor(2);
f1p10.setParameter(0, hAmp10);
f1p10.setParLimits(0, hAmp10*0.8, hAmp10*1.2);
f1p10.setParameter(1, hMean10);
f1p10.setParLimits(1, hMean10-hRMS, hMean10+hRMS);
f1p10.setParameter(2, 2.0);
f1p10.setParameter(3, 0.0);
f1p10.setParameter(4, 0.0);
DataFitter.fit(f1p10,h1p10,"LQ");
//System.out.println("Photoelectron Distribution Peak: ");
//System.out.println(f1p10.getParameter(1));

	//System.out.println("\n");

H1F h1p11 = dir.getObject("FT", "hi_hodo_ematch_l1");
h1p11.setOptStat("1101");
double hAmp11  = h1p11.getBinContent(h1p11.getMaximumBin());
double hMean11 = h1p11.getAxis().getBinCenter(h1p11.getMaximumBin());
//double hRMS  = 10;
F1D f1p11 = new F1D("FT Hodoscope Energy Peak 1", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 0.4,3.0);
f1p11.setOptStat("1111111");
f1p11.setLineColor(2);
f1p11.setParameter(0, hAmp11);
f1p11.setParLimits(0, hAmp11*0.8, hAmp11*1.2);
f1p11.setParameter(1, hMean11);
f1p11.setParLimits(1, hMean11-hRMS, hMean11+hRMS);
f1p11.setParameter(2, 2.0);
f1p11.setParameter(3, 0.0);
f1p11.setParameter(4, 0.0);
DataFitter.fit(f1p11,h1p11,"LQ");
System.out.println("Hodoscope energy Peak 1: ")
	//double Hodoscope_energy_Peak_1=f1p11.getParameter(1);
System.out.println(f1p11.getParameter(1)+"\n");

H1F h1p12 = dir.getObject("FT", "hi_hodo_ematch_l2");
h1p12.setOptStat("1101");
double hAmp12  = h1p12.getBinContent(h1p12.getMaximumBin());
double hMean12 = h1p12.getAxis().getBinCenter(h1p12.getMaximumBin());
//double hRMS  = 10;
F1D f1p12 = new F1D("FT Hodoscope Energy Peak 2", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 1.6,4.0);
f1p12.setOptStat("1111111");
f1p12.setLineColor(2);
f1p12.setParameter(0, hAmp12);
f1p12.setParLimits(0, hAmp12*0.8, hAmp12*1.2);
f1p12.setParameter(1, hMean12);
f1p12.setParLimits(1, hMean12-hRMS, hMean12+hRMS);
f1p12.setParameter(2, 2.0);
f1p12.setParameter(3, 0.0);
f1p12.setParameter(4, 0.0);
DataFitter.fit(f1p12,h1p12,"LQ");
System.out.println("Hodoscope energy Peak 2: ");
	//double Hodoscope_energy_Peak_2=f1p12.getParameter(1);
System.out.println(f1p12.getParameter(1)+"\n");

H1F h1p13 = dir.getObject("FT", "hi_hodo_tmatch_l2");
h1p13.setOptStat("1101");
double hAmp13  = h1p13.getBinContent(h1p13.getMaximumBin());
double hMean13 = h1p13.getAxis().getBinCenter(h1p13.getMaximumBin());
//double hRMS  = 10;
F1D f1p13 = new F1D("FT Hodoscope Time Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -8.0,8.0);
f1p13.setOptStat("1111111");
f1p13.setLineColor(2);
f1p13.setParameter(0, hAmp13);
f1p13.setParLimits(0, hAmp13*0.8, hAmp13*1.2);
f1p13.setParameter(1, hMean13);
f1p13.setParLimits(1, hMean13-hRMS, hMean13+hRMS);
f1p13.setParameter(2, 2.0);
f1p13.setParameter(3, 0.0);
f1p13.setParameter(4, 0.0);
DataFitter.fit(f1p13,h1p13,"LQ");
System.out.println("Hodoscope time Peak: ");
	//double Hodoscope_time_Peak=f1p13.getParameter(1);
System.out.println(f1p13.getParameter(1)+"\n");

H1F h1p14 = dir.getObject("FT", "hi_cal_time_cut_ch");
h1p14.setOptStat("1101");
double hAmp14  = h1p14.getBinContent(h1p14.getMaximumBin());
double hMean14 = h1p14.getAxis().getBinCenter(h1p14.getMaximumBin());
//double hRMS  = 10;
F1D f1p14 = new F1D("FT Calorimeter Time Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -0.4,0.4);
f1p14.setOptStat("1111111");
f1p14.setLineColor(2);
f1p14.setParameter(0, hAmp14);
f1p14.setParLimits(0, hAmp14*0.8, hAmp14*1.2);
f1p14.setParameter(1, hMean14);
f1p14.setParLimits(1, hMean14-hRMS, hMean14+hRMS);
f1p14.setParameter(2, 2.0);
f1p14.setParameter(3, 0.0);
f1p14.setParameter(4, 0.0);
DataFitter.fit(f1p14,h1p14,"LQ");
System.out.println("Calorimeter time Peak: ");
	//double Calorimeter_time_Peak=f1p14.getParameter(1);
System.out.println(f1p14.getParameter(1)+"\n");

H1F h1p15 = dir.getObject("FT", "hpi0sum");
h1p15.setOptStat("1101");
double hAmp15 = h1p15.getBinContent(h1p15.getMaximumBin());
double hMean15 = h1p15.getAxis().getBinCenter(h1p15.getMaximumBin());
//double hRMS  = 10;
F1D f1p15 = new F1D("FT pi0 Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 115.0,150.0);
f1p15.setOptStat("1111111");
f1p15.setLineColor(2);
f1p15.setParameter(0, hAmp15);
f1p15.setParLimits(0, hAmp15*0.8, hAmp15*1.2);
f1p15.setParameter(1, hMean15);
f1p15.setParLimits(1, hMean15-hRMS, hMean15+hRMS);
f1p15.setParameter(2, 2.0);
f1p15.setParameter(3, 0.0);
f1p15.setParameter(4, 0.0);
DataFitter.fit(f1p15,h1p15,"LQ");
System.out.println("FT pi0 Peak: ");
	//double FT_pi0_Peak=f1p15.getParameter(1);
System.out.println(f1p15.getParameter(1)+"\n");

	System.out.println("\n");

H1F h1p16 = dir.getObject("EB", "hi_vt_el");
h1p16.setOptStat("1101");
double hAmp16 = h1p16.getBinContent(h1p16.getMaximumBin());
double hMean16 = h1p16.getAxis().getBinCenter(h1p16.getMaximumBin());
//double hRMS  = 10;
F1D f1p16 = new F1D("EB Start Time Electron Center", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -0.5,0.2);
f1p16.setOptStat("1111111");
f1p16.setLineColor(2);
f1p16.setParameter(0, hAmp16);
f1p16.setParLimits(0, hAmp16*0.8, hAmp16*1.2);
f1p16.setParameter(1, hMean16);
f1p16.setParLimits(1, hMean16-hRMS, hMean16+hRMS);
f1p16.setParameter(2, 2.0);
f1p16.setParameter(3, 0.0);
f1p16.setParameter(4, 0.0);
DataFitter.fit(f1p16,h1p16,"LQ");
//System.out.println("FT pi0 Peak: ");
//System.out.println(f1p16.getParameter(1));

H1F h1p17 = dir.getObject("EB", "hi_vt_pi");
h1p17.setOptStat("1101");
double hAmp17 = h1p17.getBinContent(h1p17.getMaximumBin());
double hMean17 = h1p17.getAxis().getBinCenter(h1p17.getMaximumBin());
//double hRMS  = 10;
F1D f1p17 = new F1D("EB Start Time Pion Center", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -1.0,0.5);
f1p17.setOptStat("1111111");
f1p17.setLineColor(2);
f1p17.setParameter(0, hAmp17);
f1p17.setParLimits(0, hAmp17*0.8, hAmp17*1.2);
f1p17.setParameter(1, hMean17);
f1p17.setParLimits(1, hMean17-hRMS, hMean17+hRMS);
f1p17.setParameter(2, 2.0);
f1p17.setParameter(3, 0.0);
f1p17.setParameter(4, 0.0);
DataFitter.fit(f1p17,h1p17,"LQ");
//System.out.println("FT pi0 Peak: ");
//System.out.println(f1p17.getParameter(1));

H1F h1p18 = dir.getObject("EB", "hi_vt_pr");
h1p18.setOptStat("1101");
double hAmp18 = h1p18.getBinContent(h1p18.getMaximumBin());
double hMean18 = h1p18.getAxis().getBinCenter(h1p18.getMaximumBin());
//double hRMS  = 10;
F1D f1p18 = new F1D("EB Start Time Proton Center", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -0.8,0.7);
f1p18.setOptStat("1111111");
f1p18.setLineColor(2);
f1p18.setParameter(0, hAmp18);
f1p18.setParLimits(0, hAmp18*0.8, hAmp18*1.2);
f1p18.setParameter(1, hMean18);
f1p18.setParLimits(1, hMean18-hRMS, hMean18+hRMS);
f1p18.setParameter(2, 2.0);
f1p18.setParameter(3, 0.0);
f1p18.setParameter(4, 0.0);
DataFitter.fit(f1p18,h1p18,"LQ");
//System.out.println("FT pi0 Peak: ");
//System.out.println(f1p18.getParameter(1));

H2F h2p = dir.getObject("FTOF","hi_beta_pos_1");
H2F h2p2 = dir.getObject("FTOF","hi_beta_pos_2");
H2F h2p3 = dir.getObject("FTOF","hi_beta_pos_3");
H2F h2p4 = dir.getObject("FTOF","hi_beta_neg_1");
H2F h2p5 = dir.getObject("FTOF","hi_beta_neg_2");
H2F h2p6 = dir.getObject("FTOF","hi_beta_neg_3");

H2F h2p7 = dir.getObject("FTOF","hi_x_residual_pos_1");
H2F h2p8 = dir.getObject("FTOF","hi_x_residual_pos_2");
H2F h2p9 = dir.getObject("FTOF","hi_x_residual_pos_3");
H2F h2p10 = dir.getObject("FTOF","hi_x_residual_neg_1");
H2F h2p11 = dir.getObject("FTOF","hi_x_residual_neg_2");
H2F h2p12 = dir.getObject("FTOF","hi_x_residual_neg_3");


H2F h2p13 = dir.getObject("CTOF","hi_beta_pos");
//h2p13.setOptStat("1");
H2F h2p14 = dir.getObject("CTOF","hi_beta_neg");
//h2p14.setOptStat("1");
H2F h2p15 = dir.getObject("CTOF","hi_z_residual");
//h2p15.setOptStat("1");

H2F h2p16 = dir.getObject("CND","hi_beta_pos");
//h2p16.setOptStat("1");
H2F h2p17 = dir.getObject("CND","hi_beta_neg");
//h2p17.setOptStat("1");
H2F h2p18 = dir.getObject("CND","hi_z_residual");
//h2p18.setOptStat("1");

H2F h2p19 = dir.getObject("EB","hi_beta_pos_ftof");
H2F h2p20 = dir.getObject("EB","hi_beta_neg_ftof");
H2F h2p21 = dir.getObject("EB","hi_beta_pos_ctof");
H2F h2p22 = dir.getObject("EB","hi_beta_neg_ctof");

//int
double x1CVTp= h1p4.getAxis().getBinCenter(xp1);
//int x1CVTp=xp1;
//int 
double x2CVTp= h1p4.getAxis().getBinCenter(xp2);
//int x2CVTp=xp2;
//int
double yCVTp= 2*halfmax1;
	DataLine line1 = new DataLine(x1CVTp,0.0,x1CVTp,yCVTp);
	line1.setLineColor(2);
	line1.setLineWidth(2);
	//line1.setArrowSizeOrigin(15);
	//line1.setArrowSizeEnd(15);
	//line1.setArrowAngle(25);
	
	DataLine line2 = new DataLine(x2CVTp,0.0,x2CVTp,yCVTp);
	line2.setLineColor(2);
	line2.setLineWidth(2);
	//line2.setArrowSizeOrigin(15);
	//line2.setArrowSizeEnd(15);
	//line2.setArrowAngle(25);
//int
double x1CVTn= h1p5.getAxis().getBinCenter(xn1);
//int x1CVTn=xn1;
//int
double x2CVTn= h1p5.getAxis().getBinCenter(xn2);
//int x2CVTn=xn2;
//int
double yCVTn= 2*halfmax2;
	DataLine line3 = new DataLine(x1CVTn,0.0,x1CVTn,yCVTn);
        line3.setLineColor(2);
        line3.setLineWidth(2);
        //line3.setArrowSizeOrigin(15);
        //line3.setArrowSizeEnd(15);
        //line3.setArrowAngle(25);

	DataLine line4 = new DataLine(x2CVTn,0.0,x2CVTn,yCVTn);
        line4.setLineColor(2);
        line4.setLineWidth(2);
        //line4.setArrowSizeOrigin(15);
        //line4.setArrowSizeEnd(15);
        //line4.setArrowAngle(25);

	TCanvas crosses = new TCanvas("TBT, CVT, and EC", 1000, 800);
	crosses.divide(2,3);
	crosses.getCanvas().setGridX(false); crosses.getCanvas().setGridY(false);
	crosses.getCanvas().setAxisFontSize(18);
	crosses.getCanvas().setAxisTitleSize(24);
	crosses.cd(1);
	crosses.getCanvas().draw(dir.getObject("TBT", "hi_vz_neg"));
	crosses.cd(5);
	crosses.getCanvas().draw(dir.getObject("EC", "hi_pi0_mass"));
	crosses.cd(4);
	crosses.getCanvas().draw(dir.getObject("EC", "hi_sf_EC"));
	crosses.cd(6);
	crosses.getCanvas().draw(dir.getObject("TBT", "hi_vz_pos"));
	crosses.cd(2);
	crosses.getCanvas().draw(dir.getObject("CVT", "hi_vz_pos"));
	//line1.drawLine(x1CVTp,0,x1CVTp,yCVTp);
	crosses.draw(line1);
	crosses.draw(line2);
	//crosses.getCanvas().drawLine(line1);
	//crosses.getCanvas().drawLine(x2CVTp,0,x2CVTp,yCVTp);
	//crosses.drawLine(x2CVTp,0,x2CVTp,yCVTp);
	crosses.cd(3);
	crosses.getCanvas().draw(dir.getObject("CVT", "hi_vz_neg"));
	crosses.draw(line3);
	crosses.draw(line4);
	//crosses.getCanvas().drawLine(xn1,xn1);
	//crosses.getCanvas().drawLine(xn2,xn2);

	TCanvas crosses2 = new TCanvas("FTOF beta vs. p", 1000, 800);
	crosses2.divide(3,2);
	crosses2.getCanvas().setGridX(false); crosses2.getCanvas().setGridY(false);
	crosses2.getCanvas().setAxisFontSize(18);
	crosses2.getCanvas().setAxisTitleSize(24);
	crosses2.cd(6);
	crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_pos_1"));
	crosses2.cd(1);
	crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_pos_2"));
	crosses2.cd(2);
	crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_pos_3"));
        crosses2.cd(3);
        crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_neg_1"));
        crosses2.cd(4);
        crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_neg_2"));
        crosses2.cd(5);
        crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_neg_3"));


	TCanvas crosses3 = new TCanvas("FTOF residuals", 1000, 800);
	crosses3.divide(3,2);
	crosses3.getCanvas().setGridX(false); crosses3.getCanvas().setGridY(false);
	crosses3.getCanvas().setAxisFontSize(18);
	crosses3.getCanvas().setAxisTitleSize(24);
	crosses3.cd(6);
	crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_pos_1"));
	crosses3.cd(1);
	crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_pos_2"));
	crosses3.cd(2);
	crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_pos_3"));
	crosses3.cd(3);
	crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_neg_1"));
	crosses3.cd(4);
	crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_neg_2"));
	crosses3.cd(5);
	crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_neg_3"));

	TCanvas crosses4 = new TCanvas("HTCC and FT", 1000, 800);
	crosses4.divide(3,2);
	crosses4.getCanvas().setGridX(false); crosses4.getCanvas().setGridY(false);
	crosses4.getCanvas().setAxisFontSize(18);
	crosses4.getCanvas().setAxisTitleSize(24);
	crosses4.cd(1);
	crosses4.getCanvas().draw(dir.getObject("HTCC", "hi_nphe_ele"));
	crosses4.cd(2);
	crosses4.getCanvas().draw(dir.getObject("FT", "hpi0sum"));
	//crosses4.getCanvas().draw(dir.getObject("CTOF", "hi_z_track"));
	crosses4.cd(6);
	crosses4.getCanvas().draw(dir.getObject("FT", "hi_cal_time_cut_ch"));
	//crosses4.getCanvas().draw(dir.getObject("CND", "hi_z_track"));
	crosses4.cd(4);
	crosses4.getCanvas().draw(dir.getObject("FT", "hi_hodo_ematch_l1"));
	crosses4.cd(5);
	crosses4.getCanvas().draw(dir.getObject("FT", "hi_hodo_ematch_l2"));
	crosses4.cd(3);
	crosses4.getCanvas().draw(dir.getObject("FT", "hi_hodo_tmatch_l2"));

 	TCanvas crosses6 = new TCanvas("CTOF and CND (beta vs. p and residuals)", 1000, 800);
        crosses6.divide(3,2);
        crosses6.getCanvas().setGridX(false); crosses6.getCanvas().setGridY(false);
        crosses6.getCanvas().setAxisFontSize(18);
        crosses6.getCanvas().setAxisTitleSize(24);
        crosses6.cd(6);
        crosses6.getCanvas().draw(dir.getObject("CTOF","hi_beta_pos"));
        crosses6.cd(1);
        crosses6.getCanvas().draw(dir.getObject("CTOF","hi_beta_neg"));
        crosses6.cd(2);
        crosses6.getCanvas().draw(dir.getObject("CTOF","hi_z_residual"));
        crosses6.cd(3);
        crosses6.getCanvas().draw(dir.getObject("CND","hi_beta_pos"));
        crosses6.cd(4);
        crosses6.getCanvas().draw(dir.getObject("CND","hi_beta_neg"));
        crosses6.cd(5);
        crosses6.getCanvas().draw(dir.getObject("CND","hi_z_residual"));

	TCanvas crosses5 = new TCanvas("EB", 1000, 800);
	crosses5.divide(3,1);
	crosses5.getCanvas().setGridX(false); crosses5.getCanvas().setGridY(false);
	crosses5.getCanvas().setAxisFontSize(18);
	crosses5.getCanvas().setAxisTitleSize(24);
	//crosses5.cd(6);
	//crosses5.getCanvas().draw(dir.getObject("FT", "hi_cal_time_cut_ch"));
	//crosses5.cd(2);
	//crosses5.getCanvas().draw(dir.getObject("FT", "hpi0sum"));
	//crosses5.cd(1);
	//crosses5.getCanvas().draw(dir.getObject("CND", "hi_z_track"));
	crosses5.cd(1);
	crosses5.getCanvas().draw(dir.getObject("EB", "hi_vt_el"));
	crosses5.cd(2);
	crosses5.getCanvas().draw(dir.getObject("EB", "hi_vt_pi"));
	crosses5.cd(3);
	crosses5.getCanvas().draw(dir.getObject("EB", "hi_vt_pr"));

        TCanvas crosses7 = new TCanvas("EB Beta", 1000, 800);
        crosses7.divide(2,2);
        crosses7.getCanvas().setGridX(false); crosses6.getCanvas().setGridY(false);
        crosses7.getCanvas().setAxisFontSize(18);
        crosses7.getCanvas().setAxisTitleSize(24);
        crosses7.cd(4);
        crosses7.getCanvas().draw(dir.getObject("EB","hi_beta_pos_ftof"));
        crosses7.cd(1);
        crosses7.getCanvas().draw(dir.getObject("EB","hi_beta_neg_ftof"));
        crosses7.cd(2);
        crosses7.getCanvas().draw(dir.getObject("EB","hi_beta_pos_ctof"));
        crosses7.cd(3);
        crosses7.getCanvas().draw(dir.getObject("EB","hi_beta_neg_ctof"));


String foldername ="Images_for_Run_"+args[0];
String foldercommand="mkdir "+foldername;
String movecommand1="mv TBT_CVT_and_EC_"+args[0]+".png Images_for_Run_"+args[0];
String movecommand2="mv FTOF_beta_vs_p_"+args[0]+".png Images_for_Run_"+args[0];
String movecommand3="mv FTOF_residuals_"+args[0]+".png Images_for_Run_"+args[0];
String movecommand4="mv HTCC_and_FT_"+args[0]+".png Images_for_Run_"+args[0];
String movecommand5="mv CTOF_and_CND_beta_vs_p_and_residuals_"+args[0]+".png Images_for_Run_"+args[0];
String movecommand6="mv EB_"+args[0]+".png Images_for_Run_"+args[0];
String movecommand7="mv EB_Beta_"+args[0]+".png Images_for_Run_"+args[0];
String movetext="mv Data_Output_Run_"+args[0]+".txt Images_for_Run_"+args[0];

println foldercommand.execute().text;
//println "ls".execute().text;


	crosses.save("TBT_CVT_and_EC_"+args[0]+".png");
	crosses2.save("FTOF_beta_vs_p_"+args[0]+".png");
	crosses3.save("FTOF_residuals_"+args[0]+".png");
        crosses4.save("HTCC_and_FT_"+args[0]+".png");
        crosses6.save("CTOF_and_CND_beta_vs_p_and_residuals_"+args[0]+".png");
        crosses5.save("EB_"+args[0]+".png");
        crosses7.save("EB_Beta_"+args[0]+".png");

PrintWriter writer = new PrintWriter("Data_Output_Run_"+args[0]+".txt", "UTF-8");

writer.println("\n TBT Data");

writer.println("Peak of Negative TBT: ");
writer.println(f1p.getParameter(1)+"\n");

writer.println("Peak of Positive TBT: ");
writer.println(f1p3.getParameter(1)+"\n");

writer.println("\n CVT Data");

writer.println("Center of Positive CVT: ");
writer.println(PCenterCVT+"\n");
writer.println("Extension of Positive CVT: ");
writer.println(h1p4.getAxis().getBinCenter(xp2)-h1p4.getAxis().getBinCenter(xp1)+"\n");

writer.println("Center of Negative CVT: ");
writer.println(NCenterCVT+"\n");
writer.println("Extension of Negative CVT: ");
writer.println(h1p5.getAxis().getBinCenter(xn2)-h1p5.getAxis().getBinCenter(xn1)+"\n");

writer.println("\n EC Data");

writer.println("Peak of EC electron SF: ");
writer.println(f1p6.getParameter(1)+"\n");

writer.println("Peak of EC pi0: ");
writer.println(f1p2.getParameter(1)+"\n");

writer.println("\n HTCC Data");

writer.println("Photoelectron Distribution Peak: ");
writer.println(f1p8.getParameter(1)+"\n");

writer.println("\n FT Data");

writer.println("Hodoscope energy Peak 1: ");
writer.println(f1p11.getParameter(1)+"\n");

writer.println("Hodoscope energy Peak 2: ");
writer.println(f1p12.getParameter(1)+"\n");

writer.println("Hodoscope time Peak: ");
writer.println(f1p13.getParameter(1)+"\n");

writer.println("Calorimeter time Peak: ");
writer.println(f1p14.getParameter(1)+"\n");

writer.println("FT pi0 Peak: ");
writer.println(f1p15.getParameter(1)+"\n");

writer.println("\n");

writer.close();


println movecommand1.execute().text;
println movecommand2.execute().text;
println movecommand3.execute().text;
println movecommand4.execute().text;
println movecommand5.execute().text;
println movecommand6.execute().text;
println movecommand7.execute().text;
println movetext.execute().text; 
//println "ls".execute().text;


System.out.println("These results are for: "+fileName);
