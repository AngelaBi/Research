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

//String fname =args[0]+".hipo";
//System.out.println(fname);
//String fileName = "/Users/einstein/hipoFilesKPP/kpp_histos."+fname;
//String fileName = "/Users/biselli/Data/clas12/rgA/kpp_histos."+fname;
//System.out.println(fileName);

//String fileName = "/Users/einstein/hipoFilesKPP/kpp_histos.4013.hipo"


File folder = new File("/Users/einstein/hipoFilesKPP/");
//File folder = new File("/home/richcap/Desktop/KppHistosFiles/KPPpass0_fullMap_alignprod_hipo_files/");
File[] listOfFiles = folder.listFiles();


String directoryName="/Users/einstein/hipoFilesKPP/";
//String directoryName="/home/richcap/Desktop/KppHistosFiles/KPPpass0_fullMap_alignprod_hipo_files/";
String fileNames;

//output hipo files
def TBTvertex = new TDirectory()
def TBTvertexPeak = new GraphErrors[2]
TBTvertexPeak[0] = new GraphErrors('Positive Peak')
TBTvertexPeak[1] = new GraphErrors('Negative Peak')


def TBTsectors = new TDirectory()
def TBTsectorsFill = new GraphErrors[2]
TBTsectorsFill[0] = new GraphErrors('Positive Sector')
TBTsectorsFill[1] = new GraphErrors('Negative Sector')


def CVTextention = new TDirectory()
def CVTextentionVertex = new GraphErrors[2]
CVTextentionVertex[0] = new GraphErrors('Positive Vertex')
CVTextentionVertex[1] = new GraphErrors('Negative Vertex')


def CVTcenter = new TDirectory()
def CVTcenterVertex = new GraphErrors[2]
CVTcenterVertex[0] = new GraphErrors('Positive Vertex')
CVTcenterVertex[1] = new GraphErrors('Negative Vertex')


def ECsf = new TDirectory()
def ECsfelectron= new GraphErrors[1]
ECsfelectron[0] = new GraphErrors('Sampling Fraction')


def ECpi0 = new TDirectory()
def ECpi0Peak= new GraphErrors[1]
ECpi0Peak[0] = new GraphErrors('Mass Spectrum Peak')


def HTCCnpe = new TDirectory()
def HTCCpeak= new GraphErrors[1]
HTCCpeak[0] = new GraphErrors('Photoelectron Distribution Peak')

def FThodoEnergy = new TDirectory()
def FThodoEnergyPeak = new GraphErrors[2]
FThodoEnergyPeak[0] = new GraphErrors('Peak 1')
FThodoEnergyPeak[1] = new GraphErrors('Peak 2')


def FThodoTime = new TDirectory()
def FThodoTimePeak = new GraphErrors[1]
FThodoTimePeak[0] = new GraphErrors('Peak')


def FTcalorTime = new TDirectory()
def FTcalorTimePeak = new GraphErrors[1]
FTcalorTimePeak[0] = new GraphErrors('Peak')

def FTpi0 = new TDirectory()
def FTpi0Peak = new GraphErrors[1]
FTpi0Peak[0] = new GraphErrors('Peak')


def EBstartTime = new TDirectory()
def EBstartTimeCenter = new GraphErrors[3]
EBstartTimeCenter[0] = new GraphErrors('Electron Center')
EBstartTimeCenter[1] = new GraphErrors('Pion Center')
EBstartTimeCenter[2] = new GraphErrors('Proton Center')


def FTOFall = new TDirectory()
//def FTOFallCount = new GraphErrors[3]
//FTOFallCount[0] = new GraphErrors('Peak Counts E')
def FTOFallExist = new GraphErrors[1]
FTOFallExist[0] = new GraphErrors('Use to search run numbers')

def CTOFall = new TDirectory()
//def CTOFallCount = new GraphErrors[3]
//CTOFallCount[0] = new GraphErrors('Peak Counts E')
def CTOFallExist = new GraphErrors[1]
CTOFallExist[0] = new GraphErrors('Use to search run numbers')

def CNDall = new TDirectory()
//def CNDallCount = new GraphErrors[1]
//CNDallCount[0] = new GraphErrors('Peak Counts Residuals')
def CNDallExist = new GraphErrors[1]
CNDallExist[0] = new GraphErrors('Use to search run numbers')

def FTcalorEnergyTimeline = new TDirectory()
def FTcalorEnergyTLCount = new GraphErrors[3]
FTcalorEnergyTLCount[0] = new GraphErrors('Peak Counts E')
FTcalorEnergyTLCount[1] = new GraphErrors('Peak Counts theta')
FTcalorEnergyTLCount[2] = new GraphErrors('Peak Counts phi')

def EBbetaAll = new TDirectory()
//def EBbetaAllCount = new GraphErrors[4]
//EBbetaAllCount[0] = new GraphErrors('Peak Counts E')
def EBbetaAllExist = new GraphErrors[1]
EBbetaAllExist[0] = new GraphErrors('Use to search run numbers')

int fileNumber;
int filecount=0;
for (int i = 0; i < listOfFiles.length; i++) {
  //System.out.println(i);
  if (listOfFiles[i].isFile()) {
    System.out.println("File " + listOfFiles[i].getName());
    fileNames = directoryName+listOfFiles[i].getName();
    
    filecount=filecount+1;
   
    fileNumber=Integer.parseInt((listOfFiles[i].getName()).substring(11,15)); 
    
    TBTvertex.mkdir('/'+fileNumber)
    TBTsectors.mkdir('/'+fileNumber)
    CVTextention.mkdir('/'+fileNumber)
    CVTcenter.mkdir('/'+fileNumber)
    ECsf.mkdir('/'+fileNumber)
    ECpi0.mkdir('/'+fileNumber)
    HTCCnpe.mkdir('/'+fileNumber)
    FThodoEnergy.mkdir('/'+fileNumber)
    FThodoTime.mkdir('/'+fileNumber)
    FTcalorTime.mkdir('/'+fileNumber)
    FTpi0.mkdir('/'+fileNumber)
    EBstartTime.mkdir('/'+fileNumber)
    FTOFall.mkdir('/'+fileNumber)
    CTOFall.mkdir('/'+fileNumber)
    CNDall.mkdir('/'+fileNumber)
    FTcalorEnergyTimeline.mkdir('/'+fileNumber)
    EBbetaAll.mkdir('/'+fileNumber)

    //System.out.println("Opening file: " + fileName;
    //TDirectory dir = new TDirectory();
    //dir.readFile(fileName);
    //System.out.println(dir.getDirectoryList());
    //dir.cd();
    //dir.pwd();

    System.out.println("Opening file: " + fileNames);
        TDirectory dir = new TDirectory();
        dir.readFile(fileNames);
        //System.out.println(dir.getDirectoryList());
        dir.cd();
        dir.pwd();

        System.out.println("\n");

        H1F h1p = dir.getObject("TBT", "hi_vz_neg");
        h1p.setOptStat("1101");
               TBTvertex.cd('/'+fileNumber)
               TBTvertex.addDataSet(h1p)
        double hAmp  = h1p.getBinContent(h1p.getMaximumBin());
        double hMean = h1p.getAxis().getBinCenter(h1p.getMaximumBin());
        double hRMS  = 10;
        //F1D f1p = new F1D("TBT Negative Vertex Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -20.0,12.0);
        F1D f1p = new F1D('fit:'+h1p.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -20.0,12.0);
        f1p.setOptStat("1111111");
        f1p.setLineColor(2);
        f1p.setParameter(0, hAmp);
        f1p.setParLimits(0, hAmp*0.8, hAmp*1.2);
        f1p.setParameter(1, hMean);
        f1p.setParLimits(1, hMean-hRMS, hMean+hRMS);
        f1p.setParameter(2, 2.0);
        f1p.setParameter(3, 0.0);
        f1p.setParameter(4, 0.0);
               TBTvertex.cd('/'+fileNumber)
               TBTvertex.addDataSet(f1p)
        DataFitter.fit(f1p,h1p,"LQ");
        System.out.println("Peak of Negative TBT: ");
        //String Peak_of_Negative_TBT=f1p.getParameter(1)+"\t";
        double Peak_of_Negative_TBT_Num=f1p.getParameter(1);
        System.out.println(f1p.getParameter(1)+"\n");
               TBTvertexPeak[1].addPoint(fileNumber, Peak_of_Negative_TBT_Num, 0, 0) 

        H1F h1p3 = dir.getObject("TBT", "hi_vz_pos");
        h1p3.setOptStat("1101");
               TBTvertex.cd('/'+fileNumber)
               TBTvertex.addDataSet(h1p3)
        double hAmp3  = h1p3.getBinContent(h1p3.getMaximumBin());
        double hMean3 = h1p3.getAxis().getBinCenter(h1p3.getMaximumBin());
        double hRMS3  = 10;
        //F1D f1p3 = new F1D("TBT Postive Vertex Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -20.0,12.0);
        F1D f1p3 = new F1D('fit:'+h1p3.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -20.0,12.0);
        f1p3.setOptStat("1111111");
        f1p3.setLineColor(2);
        f1p3.setParameter(0, hAmp3);
        f1p3.setParLimits(0, hAmp3*0.8, hAmp3*1.2);
        f1p3.setParameter(1, hMean3);
        f1p3.setParLimits(1, hMean3-hRMS3, hMean3+hRMS3);
        f1p3.setParameter(2, 2.0);
        f1p3.setParameter(3, 0.0);
        f1p3.setParameter(4, 0.0);
               TBTvertex.cd('/'+fileNumber)
               TBTvertex.addDataSet(f1p3)
        DataFitter.fit(f1p3,h1p3,"LQ");
        System.out.println("Peak of Positive TBT: ");
        String Peak_of_Positive_TBT=f1p3.getParameter(1)+"\t";
        double Peak_of_Positive_TBT_Num=f1p3.getParameter(1);
        System.out.println(f1p3.getParameter(1)+"\n");
               TBTvertexPeak[0].addPoint(fileNumber, Peak_of_Positive_TBT_Num, 0, 0)  

        System.out.println("\n");

        H1F h1p4 = dir.getObject("CVT", "hi_vz_pos");
        h1p4.setOptStat("1101");
               CVTextention.cd('/'+fileNumber)
               CVTextention.addDataSet(h1p4)
               CVTcenter.cd('/'+fileNumber)
               CVTcenter.addDataSet(h1p4)
        double hAmp4  = h1p4.getBinContent(h1p4.getMaximumBin());
        double hMean4 = h1p4.getAxis().getBinCenter(h1p4.getMaximumBin());
        //double hRMS4  = 10;
        //F1D f1p4 = new F1D("CVT Positive Vertex Track", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -5.0,5.0);
        F1D f1p4 = new F1D('fit:'+h1p4.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -5.0,5.0);
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

        //     CVTextention.addDataSet(f1p4)
        //     CVTcenter.addDataSet(f1p4)

        DataFitter.fit(f1p4,h1p4,"LQ");

        double halfmax1= h1p4.getBinContent(h1p4.getMaximumBin())/2;
        //System.out.println("halfmax1= "+halfmax1);

        int xp1=1000;
        int xp2=1000;
        int found1=0;

        for (int n=0; n<=100;n++){
          double h= h1p4.getBinContent(n);
          //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
          if( halfmax1-(halfmax1*0.20) <= h && h <= halfmax1+(halfmax1*0.20) ){
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
               CVTcenterVertex[0].addPoint(fileNumber, PCenterCVT, 0, 0)
        System.out.println("Extension of Positive CVT: ");
        String Extension_of_Positive_CVT=(h1p4.getAxis().getBinCenter(xp2)-h1p4.getAxis().getBinCenter(xp1)+"\t");
        double Extension_of_Positive_CVT_Num=(h1p4.getAxis().getBinCenter(xp2)-h1p4.getAxis().getBinCenter(xp1));
        //System.out.println(h1p4.getAxis().getBinCenter(xp2)-h1p4.getAxis().getBinCenter(xp1)+"\n");
        System.out.println(Extension_of_Positive_CVT+"\n");
               CVTextentionVertex[0].addPoint(fileNumber, Extension_of_Positive_CVT_Num, 0, 0)

        H1F h1p5 = dir.getObject("CVT", "hi_vz_neg");
        h1p5.setOptStat("1101");
               CVTextention.cd('/'+fileNumber)
               CVTextention.addDataSet(h1p5)
               CVTcenter.cd('/'+fileNumber)
               CVTcenter.addDataSet(h1p5)
        double hAmp5  = h1p5.getBinContent(h1p5.getMaximumBin());
        double hMean5 = h1p5.getAxis().getBinCenter(h1p5.getMaximumBin());
        //double hRMS  = 10;
        //F1D f1p5 = new F1D("CVT Negative Vertex Track", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -5.0,5.0);
        F1D f1p5 = new F1D('fit:'+h1p5.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -5.0,5.0);
        f1p5.setOptStat("0000001");
        f1p5.setLineColor(0);
        //f1p5.setParameter(0, hAmp5);
        //f1p5.setParLimits(0, hAmp5*0.8, hAmp5*1.2);
        //f1p5.setParameter(1, hMean5);
        //f1p5.setParLimits(1, hMean5-hRMS, hMean5+hRMS);
        //f1p5.setParameter(2, 2.0);
        //f1p5.setParameter(3, 0.0);
        //f1p5.setParameter(4, 0.0);

        //     CVTextention.addDataSet(f1p5)
        //     CVTcenter.addDataSet(f1p5)

        
        DataFitter.fit(f1p5,h1p5,"LQ");

        double halfmax2= h1p5.getBinContent(h1p5.getMaximumBin())/2;
        //System.out.println("halfmax2= "+halfmax2);

        int xn1=1000;
        int xn2=1000;
        int found2=0;

        for (int n=0; n<=100;n++){
          double h= h1p5.getBinContent(n);
          //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p5.getAxis().getBinCenter(n));
          if( halfmax2-(halfmax2*0.20) <= h && h <= halfmax2+(halfmax2*0.20) ){
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
               CVTcenterVertex[1].addPoint(fileNumber, NCenterCVT, 0, 0)
        System.out.println("Extension of Negative CVT: ");
        String Extension_of_Negative_CVT=h1p5.getAxis().getBinCenter(xn2)-h1p5.getAxis().getBinCenter(xn1)+"\t";
        double Extension_of_Negative_CVT_Num=h1p5.getAxis().getBinCenter(xn2)-h1p5.getAxis().getBinCenter(xn1);
        System.out.println(h1p5.getAxis().getBinCenter(xn2)-h1p5.getAxis().getBinCenter(xn1)+"\n");
               CVTextentionVertex[1].addPoint(fileNumber, Extension_of_Negative_CVT_Num, 0, 0)

        System.out.println("\n");

        H1F h1p6 = dir.getObject("EC", "hi_sf_EC");
        h1p6.setOptStat("1101");
               ECsf.cd('/'+fileNumber)
               ECsf.addDataSet(h1p6)
        double hAmp6  = h1p6.getBinContent(h1p6.getMaximumBin());
        double hMean6 = h1p6.getAxis().getBinCenter(h1p6.getMaximumBin());
        double hRMS6 = 0.001;
        //double hRMS6 = 0.1;
        //F1D f1p6 = new F1D("EC Electron Sampling Fraction", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x",0.0,0.6);
        //F1D f1p6 = new F1D("EC Electron Sampling Fraction", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x",0.0,hMean6+0.2);
        F1D f1p6 = new F1D('fit:'+h1p6.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x",0.0,hMean6+0.2);
        f1p6.setOptStat("1111111");
        f1p6.setLineColor(2);
        f1p6.setParameter(0, hAmp6);
        //f1p6.setParLimits(0, hAmp6*0.8, hAmp6*1.2);
        f1p6.setParLimits(0, hAmp6*0.95, hAmp6*1.05);
        f1p6.setParameter(1, hMean6);
        f1p6.setParLimits(1, hMean6-hRMS6, hMean6+hRMS6);
        //f1p6.setParameter(2, 2.0);
        f1p6.setParameter(2, 0.1);
        f1p6.setParameter(3, 0.0);
        f1p6.setParameter(4, 0.0);
               ECsf.cd('/'+fileNumber)
               ECsf.addDataSet(f1p6)
        DataFitter.fit(f1p6,h1p6,"LQ");
        System.out.println("Peak of EC electron SF: ");
        String Peak_of_EC_electron_SF=f1p6.getParameter(1)+"\t";
        double Peak_of_EC_electron_SF_Num=f1p6.getParameter(1);
        System.out.println(f1p6.getParameter(1)+"\n");
               ECsfelectron[0].addPoint(fileNumber, Peak_of_EC_electron_SF_Num, 0, 0)

        H1F h1p2 = dir.getObject("EC", "hi_pi0_mass");
        h1p2.setOptStat("1101");
               ECpi0.cd('/'+fileNumber)
               ECpi0.addDataSet(h1p2)
        double hAmp2  = h1p2.getBinContent(h1p2.getMaximumBin());
        double hMean2 = h1p2.getAxis().getBinCenter(h1p2.getMaximumBin());
        double hRMS2  = 10;
        //F1D f1p2 = new F1D("EC pi0 Mass", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 50.0,220.0);
        //F1D f1p2 = new F1D("EC pi0 Mass", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 60.0,210.0);
        //F1D f1p2 = new F1D("EC pi0 Mass", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean2-80,hMean2+80);
        F1D f1p2 = new F1D('fit:'+h1p2.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 50.0,220.0);
        f1p2.setOptStat("1111111");
        f1p2.setLineColor(2);
        f1p2.setParameter(0, hAmp2);
        f1p2.setParLimits(0, hAmp2*0.8, hAmp2*1.2);
        //f1p2.setParLimits(0, hAmp2*0.95, hAmp2*1.05);
        f1p2.setParameter(1, hMean2);
        f1p2.setParLimits(1, hMean2-hRMS2, hMean2+hRMS2);
        f1p2.setParameter(2, 2.0);
        f1p2.setParameter(3, 0.0);
        f1p2.setParameter(4, 0.0);
               ECpi0.cd('/'+fileNumber)
               ECpi0.addDataSet(f1p2)
        DataFitter.fit(f1p2,h1p2,"LQ");
        System.out.println("Peak of EC pi0: ");
        String Peak_of_EC_pi0=f1p2.getParameter(1)+"\t";
        double Peak_of_EC_pi0_Num=f1p2.getParameter(1);
        System.out.println(f1p2.getParameter(1)+"\n");
               ECpi0Peak[0].addPoint(fileNumber, Peak_of_EC_pi0_Num, 0, 0)

        System.out.println("\n");

        H1F h1p8 = dir.getObject("HTCC", "hi_nphe_ele");
        h1p8.setOptStat("1101");
               HTCCnpe.cd('/'+fileNumber)
               HTCCnpe.addDataSet(h1p8)
        double hAmp8  = h1p8.getBinContent(h1p8.getMaximumBin());
        double hMean8 = h1p8.getAxis().getBinCenter(h1p8.getMaximumBin());
        double hRMS8  = 8;
        //F1D f1p8 = new F1D("HTCC Photoelectron Distribution Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 2.0,20.0);
        //F1D f1p8 = new F1D("HTCC Photoelectron Distribution Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 2.0,hMean8+10);
        F1D f1p8 = new F1D('fit:'+h1p8.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 2.0,hMean8+10);
        f1p8.setOptStat("1111111");
        f1p8.setLineColor(2);
        f1p8.setParameter(0, hAmp8);
        f1p8.setParLimits(0, hAmp8*0.8, hAmp8*1.2);
        f1p8.setParameter(1, hMean8);
        f1p8.setParLimits(1, hMean8-hRMS8, hMean8+hRMS8);
        f1p8.setParameter(2, 2.0);
        f1p8.setParameter(3, 0.0);
        f1p8.setParameter(4, 0.0);
               HTCCnpe.cd('/'+fileNumber)
               HTCCnpe.addDataSet(f1p8)
        DataFitter.fit(f1p8,h1p8,"LQ");
        System.out.println("Photoelectron Distribution Peak: ");
        String Photoelectron_Distribution_Peak=f1p8.getParameter(1)+"\t";
        double Photoelectron_Distribution_Peak_Num=f1p8.getParameter(1);
        System.out.println(f1p8.getParameter(1)+"\n");
               HTCCpeak[0].addPoint(fileNumber, Photoelectron_Distribution_Peak_Num, 0, 0)

        System.out.println("\n");

        H1F h1p9 = dir.getObject("CTOF", "hi_z_track");
        h1p9.setOptStat("1101");
               CTOFall.cd('/'+fileNumber)
               CTOFall.addDataSet(h1p9)
        double hAmp9  = h1p9.getBinContent(h1p9.getMaximumBin());
        double hMean9 = h1p9.getAxis().getBinCenter(h1p9.getMaximumBin());
        //double hRMS  = 10;
        //F1D f1p9 = new F1D("CTOF residuals", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -10.0,38.0);
        F1D f1p9 = new F1D('fit:'+h1p9.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -10.0,38.0);
        f1p9.setOptStat("1111111");
        f1p9.setLineColor(2);
        f1p9.setParameter(0, hAmp9);
        f1p9.setParLimits(0, hAmp9*0.8, hAmp9*1.2);
        f1p9.setParameter(1, hMean9);
        f1p9.setParLimits(1, hMean9-hRMS, hMean9+hRMS);
        f1p9.setParameter(2, 2.0);
        f1p9.setParameter(3, 0.0);
        f1p9.setParameter(4, 0.0);
               CTOFall.cd('/'+fileNumber)
               CTOFall.addDataSet(f1p9)
        DataFitter.fit(f1p9,h1p9,"LQ");
        //System.out.println("Photoelectron Distribution Peak: ");
        //System.out.println(f1p9.getParameter(1));

        //System.out.println("\n");

//        H1F h1p10 = dir.getObject("CND", "hi_z_track");
//        h1p10.setOptStat("1101");
//               CNDall.cd('/'+fileNumber)
//               CNDall.addDataSet(h1p10)
//        double hAmp10  = h1p10.getBinContent(h1p10.getMaximumBin());
//        double hMean10 = h1p10.getAxis().getBinCenter(h1p10.getMaximumBin());
//        //double hRMS  = 10;
//        //F1D f1p10 = new F1D("CND residuals", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -10.0,38.0);
//        F1D f1p10 = new F1D('fit:'+h1p10.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -10.0,38.0);
//        f1p10.setOptStat("1111111");
//        f1p10.setLineColor(2);
//        f1p10.setParameter(0, hAmp10);
//        f1p10.setParLimits(0, hAmp10*0.8, hAmp10*1.2);
//        f1p10.setParameter(1, hMean10);
//        f1p10.setParLimits(1, hMean10-hRMS, hMean10+hRMS);
//        f1p10.setParameter(2, 2.0);
//        f1p10.setParameter(3, 0.0);
//        f1p10.setParameter(4, 0.0);
//               CNDall.cd('/'+fileNumber)
//               CNDall.addDataSet(f1p10)
//        DataFitter.fit(f1p10,h1p10,"LQ");
//        //System.out.println("Photoelectron Distribution Peak: ");
//        //System.out.println(f1p10.getParameter(1));
//               CNDallCount[0].addPoint(fileNumber, hAmp10, 0, 0)

        //System.out.println("\n");

        H1F h1p11 = dir.getObject("FT", "hi_hodo_ematch_l1");
        h1p11.setOptStat("1101");
               FThodoEnergy.cd('/'+fileNumber)
               FThodoEnergy.addDataSet(h1p11)
        double hAmp11  = h1p11.getBinContent(h1p11.getMaximumBin());
        double hMean11 = h1p11.getAxis().getBinCenter(h1p11.getMaximumBin());
        double hRMS11  = 1;
        //F1D f1p11 = new F1D("FT Hodoscope Energy Peak 1", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 0.4,3.0);
        //F1D f1p11 = new F1D("FT Hodoscope Energy Peak 1", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 0.4,hMean11+2);
        F1D f1p11 = new F1D('fit:'+h1p11.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 0.4,hMean11+2);
        f1p11.setOptStat("1111111");
        f1p11.setLineColor(2);
        f1p11.setParameter(0, hAmp11);
        f1p11.setParLimits(0, hAmp11*0.8, hAmp11*1.2);
        //f1p11.setParLimits(0, hAmp11*0.8, hAmp11*1.1);
        f1p11.setParameter(1, hMean11);
        //f1p11.setParLimits(1, hMean11-hRMS11, hMean11+hRMS11);
        f1p11.setParLimits(1, 0, hMean11+hRMS11);
        f1p11.setParameter(2, 2.0);
        f1p11.setParameter(3, 0.0);
        f1p11.setParameter(4, 0.0);
        DataFitter.fit(f1p11,h1p11,"LQ");
               FThodoEnergy.cd('/'+fileNumber)
               FThodoEnergy.addDataSet(f1p11)
        System.out.println("Hodoscope energy Peak 1: ")
          String Hodoscope_energy_Peak_1=f1p11.getParameter(1)+"\t";
        double Hodoscope_energy_Peak_1_Num=f1p11.getParameter(1);
        System.out.println(f1p11.getParameter(1)+"\n");
               FThodoEnergyPeak[0].addPoint(fileNumber, Hodoscope_energy_Peak_1_Num, 0, 0)

        H1F h1p12 = dir.getObject("FT", "hi_hodo_ematch_l2");
        h1p12.setOptStat("1101");
               FThodoEnergy.cd('/'+fileNumber)
               FThodoEnergy.addDataSet(h1p12)
        double hAmp12  = h1p12.getBinContent(h1p12.getMaximumBin());
        double hMean12 = h1p12.getAxis().getBinCenter(h1p12.getMaximumBin());
        double hRMS12  = 1;
        //F1D f1p12 = new F1D("FT Hodoscope Energy Peak 2", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 1.4,4.0);
        //F1D f1p12 = new F1D("FT Hodoscope Energy Peak 2", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean12-1,hMean12+2);
        F1D f1p12 = new F1D('fit:'+h1p12.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean12-1,hMean12+2);
        f1p12.setOptStat("1111111");
        f1p12.setLineColor(2);
        f1p12.setParameter(0, hAmp12);
        f1p12.setParLimits(0, hAmp12*0.8, hAmp12*1.2);
        f1p12.setParameter(1, hMean12);
        f1p12.setParLimits(1, hMean12-hRMS12, hMean12+hRMS12);
        f1p12.setParameter(2, 2.0);
        f1p12.setParameter(3, 0.0);
        f1p12.setParameter(4, 0.0);
               FThodoEnergy.cd('/'+fileNumber)
               FThodoEnergy.addDataSet(f1p12)
        DataFitter.fit(f1p12,h1p12,"LQ");
        System.out.println("Hodoscope energy Peak 2: ");
        String Hodoscope_energy_Peak_2=f1p12.getParameter(1)+"\t";
        double Hodoscope_energy_Peak_2_Num=f1p12.getParameter(1);
        System.out.println(f1p12.getParameter(1)+"\n");
               FThodoEnergyPeak[1].addPoint(fileNumber, Hodoscope_energy_Peak_2_Num, 0, 0)

        H1F h1p13 = dir.getObject("FT", "hi_hodo_tmatch_l2");
        h1p13.setOptStat("1101");
               FThodoTime.cd('/'+fileNumber)
               FThodoTime.addDataSet(h1p13)
        double hAmp13  = h1p13.getBinContent(h1p13.getMaximumBin());
        double hMean13 = h1p13.getAxis().getBinCenter(h1p13.getMaximumBin());
        double hRMS13  = 10;
        //F1D f1p13 = new F1D("FT Hodoscope Time Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -8.0,8.0);
        //F1D f1p13 = new F1D("FT Hodoscope Time Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean13-5.0,hMean13+5.0);
        F1D f1p13 = new F1D('fit:'+h1p13.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean13-5.0,hMean13+5.0);
        f1p13.setOptStat("1111111");
        f1p13.setLineColor(2);
        f1p13.setParameter(0, hAmp13);
        f1p13.setParLimits(0, hAmp13*0.8, hAmp13*1.2);
        //f1p13.setParLimits(0, hAmp13*0.85, hAmp13*1.05);
        f1p13.setParameter(1, hMean13);
        f1p13.setParLimits(1, hMean13-hRMS13, hMean13+hRMS13);
        f1p13.setParameter(2, 2.0);
        f1p13.setParameter(3, 0.0);
        f1p13.setParameter(4, 0.0);
               FThodoTime.cd('/'+fileNumber)
               FThodoTime.addDataSet(f1p13)
        DataFitter.fit(f1p13,h1p13,"LQ");
        System.out.println("Hodoscope time Peak: ");
        String Hodoscope_time_Peak=f1p13.getParameter(1)+"\t";
        double Hodoscope_time_Peak_Num=f1p13.getParameter(1);
        System.out.println(f1p13.getParameter(1)+"\n");
               FThodoTimePeak[0].addPoint(fileNumber, Hodoscope_time_Peak_Num, 0, 0)

        H1F h1p14 = dir.getObject("FT", "hi_cal_time_cut_ch");
        h1p14.setOptStat("1101");
               FTcalorTime.cd('/'+fileNumber)
               FTcalorTime.addDataSet(h1p14)
        double hAmp14  = h1p14.getBinContent(h1p14.getMaximumBin());
        double hMean14 = h1p14.getAxis().getBinCenter(h1p14.getMaximumBin());
        double hRMS14  = 0.5;
        //F1D f1p14 = new F1D("FT Calorimeter Time Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -5.0,5.0);
        //F1D f1p14 = new F1D("FT Calorimeter Time Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -2.0,2.0);
        //F1D f1p14 = new F1D("FT Calorimeter Time Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean14-1.0,hMean14+1.0);
        F1D f1p14 = new F1D('fit:'+h1p14.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", -2.0,2.0);
        f1p14.setOptStat("1111111");
        f1p14.setLineColor(2);
        f1p14.setParameter(0, hAmp14);
        f1p14.setParLimits(0, hAmp14*0.8, hAmp14*1.2);
        //f1p14.setParLimits(0, hAmp14*0.95, hAmp14*1.15);
        f1p14.setParameter(1, hMean14);
        f1p14.setParLimits(1, hMean14-hRMS14, hMean14+hRMS14);
        f1p14.setParameter(2, 2.0);
        f1p14.setParameter(3, 0.0);
        f1p14.setParameter(4, 0.0);
               FTcalorTime.cd('/'+fileNumber)
               FTcalorTime.addDataSet(f1p14)
        DataFitter.fit(f1p14,h1p14,"LQ");
        System.out.println("Calorimeter time Peak: ");
        String Calorimeter_time_Peak=f1p14.getParameter(1)+"\t";
        double Calorimeter_time_Peak_Num=f1p14.getParameter(1);
        System.out.println(f1p14.getParameter(1)+"\n");
               FTcalorTimePeak[0].addPoint(fileNumber, Calorimeter_time_Peak_Num, 0, 0)

        H1F h1p15 = dir.getObject("FT", "hpi0sum");
        h1p15.setOptStat("1101");
               FTpi0.cd('/'+fileNumber)
               FTpi0.addDataSet(h1p15)
        double hAmp15 = h1p15.getBinContent(h1p15.getMaximumBin());
        double hMean15 = h1p15.getAxis().getBinCenter(h1p15.getMaximumBin());
        double hRMS15  = 5;
        //F1D f1p15 = new F1D("FT pi0 Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", 115.0,150.0);
        //F1D f1p15 = new F1D("FT pi0 Peak", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean15-20,hMean15+20);
        F1D f1p15 = new F1D('fit:'+h1p15.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean15-20,hMean15+20);
        f1p15.setOptStat("1111111");
        f1p15.setLineColor(2);
        f1p15.setParameter(0, hAmp15);
        f1p15.setParLimits(0, hAmp15*0.8, hAmp15*1.2);
        f1p15.setParameter(1, hMean15);
        f1p15.setParLimits(1, hMean15-hRMS15, hMean15+hRMS15);
        f1p15.setParameter(2, 2.0);
        f1p15.setParameter(3, 0.0);
        f1p15.setParameter(4, 0.0);
               FTpi0.cd('/'+fileNumber)
               FTpi0.addDataSet(f1p15)
        DataFitter.fit(f1p15,h1p15,"LQ");
        System.out.println("FT pi0 Peak: ");
        String FT_pi0_Peak=f1p15.getParameter(1)+"\t";
        double FT_pi0_Peak_Num=f1p15.getParameter(1);
        System.out.println(f1p15.getParameter(1)+"\n");
               FTpi0Peak[0].addPoint(fileNumber, FT_pi0_Peak_Num, 0, 0)

        System.out.println("\n");

        H1F h1p16 = dir.getObject("EB", "hi_vt_el");
        h1p16.setOptStat("1101");
               EBstartTime.cd('/'+fileNumber)
               EBstartTime.addDataSet(h1p16)
        double hAmp16 = h1p16.getBinContent(h1p16.getMaximumBin());
        double hMean16 = h1p16.getAxis().getBinCenter(h1p16.getMaximumBin());
        double hRMS16  = 0.5;
        //F1D f1p16 = new F1D("EB Start Time Electron Center", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean16-0.7,hMean16+0.7);
        F1D f1p16 = new F1D('fit:'+h1p16.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean16-0.7,hMean16+0.7);
        f1p16.setOptStat("1111111");
        f1p16.setLineColor(2);
        f1p16.setParameter(0, hAmp16);
        f1p16.setParLimits(0, hAmp16*0.8, hAmp16*1.2);
        //f1p16.setParLimits(0, hAmp16*0.6, hAmp16*1.1);
        System.out.println("\n\n\nWARNING(amp electron): "+hAmp16+", "+hAmp16*0.8+", "+hAmp16*1.2+"\n\n\n");
        f1p16.setParameter(1, hMean16);
        //System.out.println("\n\n\nWARNING: "+hMean16+" "+hRMS16+"\n\n\n");
        f1p16.setParLimits(1, hMean16-hRMS16, hMean16+hRMS16);
        f1p16.setParameter(2, 2.0);
        f1p16.setParameter(3, 0.0);
        f1p16.setParameter(4, 0.0);
               EBstartTime.cd('/'+fileNumber)
               EBstartTime.addDataSet(f1p16)
        DataFitter.fit(f1p16,h1p16,"LQ");
        String EB_Electron_Center=f1p16.getParameter(1)+"\t";
        double EB_Electron_Center_Num=f1p16.getParameter(1);
        //System.out.println(f1p16.getParameter(1));
               EBstartTimeCenter[0].addPoint(fileNumber, EB_Electron_Center_Num, 0, 0)

        H1F h1p17 = dir.getObject("EB", "hi_vt_pi");
        h1p17.setOptStat("1101");
               EBstartTime.cd('/'+fileNumber)
               EBstartTime.addDataSet(h1p17)
        double hAmp17 = h1p17.getBinContent(h1p17.getMaximumBin());
        double hMean17 = h1p17.getAxis().getBinCenter(h1p17.getMaximumBin());
        double hRMS17  = 0.5;
        //F1D f1p17 = new F1D("EB Start Time Pion Center", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean17-0.7,hMean17+0.7);
        F1D f1p17 = new F1D('fit:'+h1p17.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x", hMean17-0.7,hMean17+0.7);
        f1p17.setOptStat("1111111");
        f1p17.setLineColor(2);
        f1p17.setParameter(0, hAmp17);
        f1p17.setParLimits(0, hAmp17*0.8, hAmp17*1.2);
        //f1p17.setParLimits(0, hAmp17*0.6, hAmp17*1.1);
        //System.out.println("\n\n\nWARNING(amp pion): "+hAmp17+", "+hAmp17*0.8+", "+hAmp17*1.2+"\n\n\n");
        f1p17.setParameter(1, hMean17);
        f1p17.setParLimits(1, hMean17-hRMS17, hMean17+hRMS17);
        f1p17.setParameter(2, 2.0);
        f1p17.setParameter(3, 0.0);
        f1p17.setParameter(4, 0.0);
               EBstartTime.cd('/'+fileNumber)
               EBstartTime.addDataSet(f1p17)
        DataFitter.fit(f1p17,h1p17,"LQ");
        String EB_Pion_Center=f1p17.getParameter(1)+"\t";
        double EB_Pion_Center_Num=f1p17.getParameter(1);
        //System.out.println(f1p17.getParameter(1));
               EBstartTimeCenter[1].addPoint(fileNumber, EB_Pion_Center_Num, 0, 0)

        H1F h1p18 = dir.getObject("EB", "hi_vt_pr");
        h1p18.setOptStat("1101");
               EBstartTime.cd('/'+fileNumber)
               EBstartTime.addDataSet(h1p18)
        double hAmp18 = h1p18.getBinContent(h1p18.getMaximumBin());
        double hMean18 = h1p18.getAxis().getBinCenter(h1p18.getMaximumBin());
        double hRMS18  = 0.5;
        //F1D f1p18 = new F1D("EB Start Time Proton Center", "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x",hMean18-0.7,hMean18+0.7);
        F1D f1p18 = new F1D('fit:'+h1p18.getName(), "[amp]*gaus(x,[peak],[sigma])+[p0]+[p1]*x+[p2]*x*x",hMean18-0.7,hMean18+0.7);
        f1p18.setOptStat("1111111");
        //f1p18.setLineColor(2);
        f1p18.setLineColor(3);
        f1p18.setParameter(0, hAmp18);
        f1p18.setParLimits(0, hAmp18*0.8, hAmp18*1.2);
        System.out.println("\n\n\nWARNING(amp proton): "+hAmp18+", "+hAmp18*0.8+", "+hAmp18*1.2+"\n\n\n");
        f1p18.setParameter(1, hMean18);
        f1p18.setParLimits(1, hMean18-hRMS18, hMean18+hRMS18);
        f1p18.setParameter(2, 2.0);
        f1p18.setParameter(3, 0.0);
        f1p18.setParameter(4, 0.0);
               EBstartTime.cd('/'+fileNumber)
               EBstartTime.addDataSet(f1p18)
        DataFitter.fit(f1p18,h1p18,"LQ");
        String EB_Proton_Center=f1p18.getParameter(1)+"\t";
        double EB_Proton_Center_Num=f1p18.getParameter(1);
        //System.out.println("\n\n\n"+f1p18.getParameter(0)*gaus(hMean18,f1p18.getParameter(1),f1p18.getParameter(2))+f1p18.getParameter(3)+f1p18.getParameter(4)*hMean18+f1p18.getParameter(5)*hMean18*hMean18+"\n\n\n");
               EBstartTimeCenter[2].addPoint(fileNumber, EB_Proton_Center_Num, 0, 0)


        //H2F h2p26 = dir.getObject("TBT","hi_phi_neg");

                          H1F h1p26 = dir.getObject("TBT","hi_phi_neg");
                                    TBTsectors.cd('/'+fileNumber)
                                    TBTsectors.addDataSet(h1p26)
                          double hAmp26 = h1p26.getBinContent(h1p26.getMaximumBin());
                          
                          int xTBT1n;
                          
                          int foundTBT1n=0;
                          int foundTBT2n=0;
                          int foundTBT3n=0;
                          int foundTBT4n=0;
                          int foundTBT5n=0;
                          int foundTBT6n=0;
                          int totfoundTBTn=0;
                          
                          double rangeTBT=0.2;
                          int NextSector=17;
                          int nrange=2; //double this number and multiply by 3.6 degrees to know the angle range that is being checked on either side of xTBT#
                          //int ErrorRangeTBT=3; //same idea as above but if this number is exceeded then there may be an error
                          //double xTBTerrorP;
                          //double xTBTerrorM;
                          
                          String TBTResultsN;
                          String ErrorTBTSn="";
                          int nErrorTBTcheck=0;
                           
                          double TBTsecNegStart = h1p26.getAxis().getBinCenter(0); 
                          double TBTsecNegEnd = h1p26.getAxis().getBinCenter(99);
                          double fillLimitn = hAmp26-(hAmp26*rangeTBT);
                          System.out.println("\n\n\n TBTsecNegStart is: "+TBTsecNegStart+" and TBTsecNegEnd is: "+TBTsecNegEnd+"\n\n\n");                           
                          F1D f1p26 = new F1D('fit:'+h1p26.getName(), "[amp]",TBTsecNegStart,TBTsecNegEnd);
                          f1p26.setParameter(0, fillLimitn);
                          f1p26.setParLimits(0, fillLimitn*0.8, fillLimitn*1.2);
                          
                          TBTsectors.cd('/'+fileNumber)
                          TBTsectors.addDataSet(f1p26)
                          
                          
                          
                          for (int n=0; n<=100;n++){
                            double h= h1p26.getBinContent(n);
                            //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                            if( hAmp26-(hAmp26*rangeTBT) <= h && h <= hAmp26+(hAmp26*rangeTBT) ){
                                foundTBT1n=1;
                                xTBT1n=n;
                             }
                            }
                          
                          //int NextSector=17;
                          
                          int xTBT2n=xTBT1n+NextSector;
                          
                            if(100<=xTBT2n){
                              xTBT2n=xTBT2n-100;
                            }
                          
                          int xTBT3n=xTBT2n+NextSector;
                          
                            if(100<=xTBT3n){
                              xTBT3n=xTBT3n-100;
                            }
                          
                          int xTBT4n=xTBT3n+NextSector;
                          
                            if(100<=xTBT4n){
                              xTBT4n=xTBT4n-100;
                            }
                          
                          int xTBT5n=xTBT4n+NextSector;
                          
                            if(100<=xTBT5n){
                              xTBT5n=xTBT5n-100;
                            }
                          
                          int xTBT6n=xTBT5n+NextSector;
                          
                            if(100<=xTBT6n){
                              xTBT6n=xTBT6n-100;
                            }
                          
                          //System.out.println("peaks at: "+xTBT1n+", "+xTBT2n+", "+xTBT3n+", "+xTBT4n+", "+xTBT5n+", and "+xTBT6n+".\n\n");
                          
                          
                          int n2;
                          
                          for (int n=(xTBT2n-nrange); n<=(xTBT2n+nrange);n++){
                          
                              if(100<=n){
                                n2=n-100;
                            }
                              else if(n<0){
                                n2=n+100;
                            }
                              else{
                                n2=n;
                              }
                            double h= h1p26.getBinContent(n2);
                            //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                            if( hAmp26-(hAmp26*rangeTBT) <= h && h <= hAmp26+(hAmp26*rangeTBT) ){
                                foundTBT2n=1;
                          
                             }
                            }
                          
                          if(foundTBT2n==0){
                            ErrorTBTSn="The sector at "+h1p26.getAxis().getBinCenter(xTBT2n)+" degrees is missing. "+ErrorTBTSn;
                            nErrorTBTcheck=1;
                            }
                          
                          for (int n=(xTBT3n-nrange); n<=(xTBT3n+nrange);n++){
                          
                              if(100<=n){
                                n2=n-100;
                            }
                              else if(n<0){
                                n2=n+100;
                            }
                              else{
                                n2=n;
                              }
                            double h= h1p26.getBinContent(n2);
                            //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                              if( hAmp26-(hAmp26*rangeTBT) <= h && h <= hAmp26+(hAmp26*rangeTBT) ){
                                  foundTBT3n=1;
                               }
                              }
                            
                            if(foundTBT3n==0){
                              ErrorTBTSn="The sector at "+h1p26.getAxis().getBinCenter(xTBT3n)+" degrees is missing. "+ErrorTBTSn;
                              nErrorTBTcheck=1;
                              }
                            
                            for (int n=(xTBT4n-nrange); n<=(xTBT4n+nrange);n++){
                            
                                if(100<=n){
                                  n2=n-100;
                              }
                                else if(n<0){
                                  n2=n+100;
                              }
                                else{
                                  n2=n;
                                }
                              double h= h1p26.getBinContent(n2);
                              //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                              if( hAmp26-(hAmp26*rangeTBT) <= h && h <= hAmp26+(hAmp26*rangeTBT) ){
                                  foundTBT4n=1;
                               }
                              }
                            
                            if(foundTBT4n==0){
                              ErrorTBTSn="The sector at "+h1p26.getAxis().getBinCenter(xTBT4n)+" degrees is missing. "+ErrorTBTSn;
                              nErrorTBTcheck=1;
                              }
                            
                            for (int n=(xTBT5n-nrange); n<=(xTBT5n+nrange);n++){
                            
                                if(100<=n){
                                  n2=n-100;
                              }
                                else if(n<0){
                                  n2=n+100;
                              }
                                else{
                                  n2=n;
                                }
                              double h= h1p26.getBinContent(n2);
                              //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                              if( hAmp26-(hAmp26*rangeTBT) <= h && h <= hAmp26+(hAmp26*rangeTBT) ){
                                  foundTBT5n=1;
                               }
                              }
                            
                            if(foundTBT5n==0){
                              ErrorTBTSn="The sector at "+h1p26.getAxis().getBinCenter(xTBT5n)+" degrees is missing. "+ErrorTBTSn;
                                nErrorTBTcheck=1;
                                }
                              
                              for (int n=(xTBT6n-nrange); n<=(xTBT6n+nrange);n++){
                              
                                  if(100<=n){
                                    n2=n-100;
                                }
                                  else if(n<0){
                                    n2=n+100;
                                }
                                  else{
                                    n2=n;
                                  }
                                double h= h1p26.getBinContent(n2);
                                //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                                if( hAmp26-(hAmp26*rangeTBT) <= h && h <= hAmp26+(hAmp26*rangeTBT) ){
                                    foundTBT6n=1;
                                 }
                                }
                              
                              if(foundTBT6n==0){
                                ErrorTBTSn="The sector at "+h1p26.getAxis().getBinCenter(xTBT6n)+" degrees is missing. "+ErrorTBTSn;
                                nErrorTBTcheck=1;
                                }
                              
                              if(nErrorTBTcheck==1){
                                ErrorTBTSn="For the Negative TBT Sectors: "+ErrorTBTSn;
                              }
                              
                              TBTResultsN=foundTBT1n+foundTBT2n+foundTBT3n+foundTBT4n+foundTBT5n+foundTBT6n;
                              totfoundTBTn=foundTBT1n+foundTBT2n+foundTBT3n+foundTBT4n+foundTBT5n+foundTBT6n;
                              TBTsectorsFill[1].addPoint(fileNumber, totfoundTBTn, 0, 0)
                              
                              
                              
                              
                              
                              H1F h1p27 = dir.getObject("TBT","hi_phi_pos");
                                    TBTsectors.cd('/'+fileNumber)
                                    TBTsectors.addDataSet(h1p27)
                              double hAmp27 = h1p27.getBinContent(h1p27.getMaximumBin());
                              
                              int xTBT1;
                              
                              int foundTBT1=0;
                              int foundTBT2=0;
                              int foundTBT3=0;
                              int foundTBT4=0;
                              int foundTBT5=0;
                              int foundTBT6=0;
                              int totfoundTBT=0;
                              
                              String TBTResults;
                              String ErrorTBTS="";
                              int pErrorTBTcheck=0;
                              
                              double TBTsecPosStart = h1p27.getAxis().getBinCenter(0);
                              double TBTsecPosEnd = h1p27.getAxis().getBinCenter(99);
                              double fillLimit = hAmp27-(hAmp27*rangeTBT);
                              System.out.println("\n\n\n TBTsecPosStart is: "+TBTsecPosStart+" and TBTsecPosEnd is: "+TBTsecPosEnd+"\n\n\n"); 
                              //F1D f1p27 = new F1D('fit:'+h1p27.getName(), hAmp27-(hAmp27*rangeTBT),TBTsecPosStart,TBTsecPosEnd);
                              F1D f1p27 = new F1D('fit:'+h1p27.getName(), "[amp]",TBTsecPosStart,TBTsecPosEnd);
                              f1p27.setParameter(0, fillLimit);
                              f1p27.setParLimits(0, fillLimit*0.8, fillLimit*1.2);
                               
                              TBTsectors.cd('/'+fileNumber)
                              TBTsectors.addDataSet(f1p27)
                              
                              for (int n=0; n<=100;n++){
                                double h= h1p27.getBinContent(n);
                                //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                                if( hAmp27-(hAmp27*rangeTBT) <= h && h <= hAmp27+(hAmp27*rangeTBT) ){
                                    foundTBT1=1;
                                    xTBT1=n;
                                 }
                                }
                              
                              int xTBT2=xTBT1+NextSector;
                              
                                if(100<=xTBT2){
                                  xTBT2=xTBT2-100;
                                }
                              
                              int xTBT3=xTBT2+NextSector;
                              
                                if(100<=xTBT3){
                                  xTBT3=xTBT3-100;
                                }
                              
                              int xTBT4=xTBT3+NextSector;
                              
                                if(100<=xTBT4){
                                  xTBT4=xTBT4-100;
                                }
                              
                              int xTBT5=xTBT4+NextSector;
                              
                                if(100<=xTBT5){
                                  xTBT5=xTBT5-100;
                                }
                              
                              int xTBT6=xTBT5+NextSector;
                              
                                if(100<=xTBT6){
                                  xTBT6=xTBT6-100;
                                }
                              
                              //System.out.println("peaks at: "+xTBT1+", "+xTBT2+", "+xTBT3+", "+xTBT4+", "+xTBT5+", and "+xTBT6+".\n\n");
                              
                              
                              //int n2;
                              //int nrange=4; //double this number and multiply by 3.6 degrees to know the angle range that is being checked on either side of xTBT#
                              //int ErrorRangeTBT=3; //same idea as above but if this number is exceeded then there may be an error
                              //double xTBTerrorP;
                              //double xTBTerrorM;
                              
                              for (int n=(xTBT2-nrange); n<=(xTBT2+nrange);n++){
                              
                                  if(100<=n){
                                    n2=n-100;
                                }
                                  else if(n<0){
                                    n2=n+100;
                                }
                                  else{
                                    n2=n;
                                  }
                                double h= h1p27.getBinContent(n2);
                                //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                                if( hAmp27-(hAmp27*rangeTBT) <= h && h <= hAmp27+(hAmp27*rangeTBT) ){
                                    foundTBT2=1;
                              
                                 }
                                }
                              
                              if(foundTBT2==0){
                                ErrorTBTS="The sector at "+h1p27.getAxis().getBinCenter(xTBT2)+" degrees is missing. "+ErrorTBTS;
                                pErrorTBTcheck=1;
                                }
                              
                              for (int n=(xTBT3-nrange); n<=(xTBT3+nrange);n++){
                              
                                  if(100<=n){
                                    n2=n-100;
                                }
                                  else if(n<0){
                                    n2=n+100;
                                }
                                  else{
                                    n2=n;
                                  }
                                double h= h1p27.getBinContent(n2);
                                //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                                if( hAmp27-(hAmp27*rangeTBT) <= h && h <= hAmp27+(hAmp27*rangeTBT) ){
                                    foundTBT3=1;
                                 }
                                }
                              
                              if(foundTBT3==0){
                                ErrorTBTS="The sector at "+h1p27.getAxis().getBinCenter(xTBT3)+" degrees is missing. "+ErrorTBTS;
                                pErrorTBTcheck=1;
                                }
                              
                              for (int n=(xTBT4-nrange); n<=(xTBT4+nrange);n++){
                              
                                  if(100<=n){
                                    n2=n-100;
                                }
                                  else if(n<0){
                                    n2=n+100;
                                }
                                  else{
                                    n2=n;
                                  }
                                double h= h1p27.getBinContent(n2);
                                //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                                if( hAmp27-(hAmp27*rangeTBT) <= h && h <= hAmp27+(hAmp27*rangeTBT) ){
                                    foundTBT4=1;
                                 }
                                }
                              
                              if(foundTBT4==0){
                                ErrorTBTS="The sector at "+h1p27.getAxis().getBinCenter(xTBT4)+" degrees is missing. "+ErrorTBTS;
                                pErrorTBTcheck=1;
                                }
                              
                              for (int n=(xTBT5-nrange); n<=(xTBT5+nrange);n++){
                              
                                  if(100<=n){
                                    n2=n-100;
                                }
                                  else if(n<0){
                                    n2=n+100;
                                }
                                  else{
                                    n2=n;
                                  }
                                double h= h1p27.getBinContent(n2);
                                //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                                if( hAmp27-(hAmp27*rangeTBT) <= h && h <= hAmp27+(hAmp27*rangeTBT) ){
                                    foundTBT5=1;
                                 }
                                }
                              
                              
                              if(foundTBT5==0){
                                ErrorTBTS="The sector at "+h1p27.getAxis().getBinCenter(xTBT5)+" degrees is missing. "+ErrorTBTS;
                                pErrorTBTcheck=1;
                                }
                              
                              for (int n=(xTBT6-nrange); n<=(xTBT6+nrange);n++){
                              
                                  if(100<=n){
                                    n2=n-100;
                                }
                                  else if(n<0){
                                    n2=n+100;
                                }
                                  else{
                                    n2=n;
                                  }
                                double h= h1p27.getBinContent(n2);
                                //System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p4.getAxis().getBinCenter(n));
                                if( hAmp27-(hAmp27*rangeTBT) <= h && h <= hAmp27+(hAmp27*rangeTBT) ){
                                    foundTBT6=1;
                                 }
                                }
                              
                              if(foundTBT6==0){
                                ErrorTBTS="The sector at "+h1p27.getAxis().getBinCenter(xTBT6)+" degrees is missing. "+ErrorTBTS;
                                pErrorTBTcheck=1;
                                }
                              
                              if(pErrorTBTcheck==1){
                                ErrorTBTS="For the Positive TBT Sectors: "+ErrorTBTS;
                              }
                              TBTResults=foundTBT1+foundTBT2+foundTBT3+foundTBT4+foundTBT5+foundTBT6;
                              totfoundTBT=foundTBT1+foundTBT2+foundTBT3+foundTBT4+foundTBT5+foundTBT6;
                              TBTsectorsFill[0].addPoint(fileNumber, totfoundTBT, 0, 0)


    //        H1F h1p27 = dir.getObject("TBT","hi_phi_pos");
    //              TBTsectors.cd(‘/‘+fileNumber)
    //              TBTvertex.addDataSet(h1p27)
    //        double hAmp27 = h1p27.getBinContent(h1p27.getMaximumBin());
    //
    //        int xTBT1=1000;
    //        int xTBT2=1000;
    //        int foundTBT1=0;
    //        int foundTBT2=0;
    //        String TBTReport;
    //        double rangeTBT=0.15;
    //
    //        for (int n=0; n<=100;n++){
    //          double h= h1p27.getBinContent(n);
    //          System.out.println("n is:"+n+" and hn= "+h+" and xn= "+h1p27.getAxis().getBinCenter(n));
    //          if( hAmp27-(hAmp27*rangeTBT) <= h && h <= hAmp27+(hAmp27*rangeTBT) ){
    //            if(foundTBT1==0){
    //              xTBT1=n;
    //              foundTBT1=1;
    //              //System.out.println("xp1= "+xp1+" and: "+h1p27.getAxis().getBinCenter(xp1));  
    //            }
    //            else if(foundTBT1==1){
    //              xTBT2=n;
    //              foundTBT1=1;
    //              //System.out.println("xp2= "+xp2+" and: "+h1p27.getAxis().getBinCenter(xp2));
    //            }
    //            else{
    //              System.out.println("Check Script");
    //            }
    //          }
    //
    //          else if(foundTBT1==1){
    //            foundTBT2=foundTBT2+1;
    //            TBTReport="Number found: "+foundTBT2+"; Range at this point is: "+xTBT1+" to "+xTBT2+". The last results were: "+TBTReport;
    //            System.out.println(TBTReport);
    //            xTBT1=0;
    //            xTBT2=0;
    //            foundTBT1=0;
    //          }
    //
    //        }

        //H2F h2p28 = dir.getObject("TBT","hi_theta_phi_neg");
        //H2F h2p29 = dir.getObject("TBT","hi_theta_phi_pos");

        H2F h2p = dir.getObject("FTOF","hi_beta_pos_1");
        H2F h2p2 = dir.getObject("FTOF","hi_beta_pos_2");
        H2F h2p3 = dir.getObject("FTOF","hi_beta_pos_3");
        H2F h2p4 = dir.getObject("FTOF","hi_beta_neg_1");
        H2F h2p5 = dir.getObject("FTOF","hi_beta_neg_2");
        H2F h2p6 = dir.getObject("FTOF","hi_beta_neg_3");
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p)
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p2)
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p3)
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p4)
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p5)
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p6)

        H2F h2p7 = dir.getObject("FTOF","hi_x_residual_pos_1");
        H2F h2p8 = dir.getObject("FTOF","hi_x_residual_pos_2");
        H2F h2p9 = dir.getObject("FTOF","hi_x_residual_pos_3");
        H2F h2p10 = dir.getObject("FTOF","hi_x_residual_neg_1");
        H2F h2p11 = dir.getObject("FTOF","hi_x_residual_neg_2");
        H2F h2p12 = dir.getObject("FTOF","hi_x_residual_neg_3");
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p7)
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p8)
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p9)
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p10)
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p11)
             FTOFall.cd('/'+fileNumber)
             FTOFall.addDataSet(h2p12)

        //H2F h2p23 = dir.getObject("FTOF","hi_rf_neg_paddle_1");
        //H2F h2p24 = dir.getObject("FTOF","hi_rf_neg_paddle_2");
        //H2F h2p25 = dir.getObject("FTOF","hi_rf_neg_paddle_3");

        H1F h1p30 = dir.getObject("FT","hi_cal_e_ch");
        double hAmp30 = h1p30.getBinContent(h1p30.getMaximumBin());
        H1F h1p31 = dir.getObject("FT","hi_cal_theta_ch");
        double hAmp31 = h1p31.getBinContent(h1p31.getMaximumBin());
        H1F h1p32 = dir.getObject("FT","hi_cal_phi_ch");
        double hAmp32 = h1p32.getBinContent(h1p32.getMaximumBin());

             FTcalorEnergyTimeline.cd('/'+fileNumber)
             FTcalorEnergyTimeline.addDataSet(h1p30)
             FTcalorEnergyTLCount[0]

             FTcalorEnergyTimeline.cd('/'+fileNumber)
             FTcalorEnergyTimeline.addDataSet(h1p31)

             FTcalorEnergyTimeline.cd('/'+fileNumber)
             FTcalorEnergyTimeline.addDataSet(h1p32)


             FTcalorEnergyTLCount[0].addPoint(fileNumber, hAmp30, 0, 0)
             FTcalorEnergyTLCount[1].addPoint(fileNumber, hAmp31, 0, 0)
             FTcalorEnergyTLCount[2].addPoint(fileNumber, hAmp32, 0, 0)

        H2F h2p13 = dir.getObject("CTOF","hi_beta_pos");
        //h2p13.setOptStat("1");
        H2F h2p14 = dir.getObject("CTOF","hi_beta_neg");
        //h2p14.setOptStat("1");
        H2F h2p15 = dir.getObject("CTOF","hi_z_residual");
        //h2p15.setOptStat("1");

        H2F h2p33 = dir.getObject("CTOF","hi_rf_neg_paddle");


             CTOFall.cd('/'+fileNumber)
             CTOFall.addDataSet(h2p13)
             CTOFall.cd('/'+fileNumber)
             CTOFall.addDataSet(h2p14)
             CTOFall.cd('/'+fileNumber)
             CTOFall.addDataSet(h2p15)
             CTOFall.cd('/'+fileNumber)
             CTOFall.addDataSet(h2p33)


        H2F h2p16 = dir.getObject("CND","hi_beta_pos");
        //h2p16.setOptStat("1");
        H2F h2p17 = dir.getObject("CND","hi_beta_neg");
        //h2p17.setOptStat("1");
        H2F h2p18 = dir.getObject("CND","hi_z_residual");
        //h2p18.setOptStat("1");

        H2F h2p34 = dir.getObject("CND","hi_rf_neg_paddle");

             CNDall.cd('/'+fileNumber)
             CNDall.addDataSet(h2p16)
             CNDall.cd('/'+fileNumber)
             CNDall.addDataSet(h2p17)
             CNDall.cd('/'+fileNumber)
             CNDall.addDataSet(h2p18)
             CNDall.cd('/'+fileNumber)
             CNDall.addDataSet(h2p34)
             
             CNDallExist[0].addPoint(fileNumber, 1, 0, 0)
             
        H2F h2p19 = dir.getObject("EB","hi_beta_pos_ftof");
        H2F h2p20 = dir.getObject("EB","hi_beta_neg_ftof");
        H2F h2p21 = dir.getObject("EB","hi_beta_pos_ctof");
        H2F h2p22 = dir.getObject("EB","hi_beta_neg_ctof");
              
             EBbetaAll.cd('/'+fileNumber)
             EBbetaAll.addDataSet(h2p19)
             EBbetaAll.cd('/'+fileNumber)
             EBbetaAll.addDataSet(h2p20)
             EBbetaAll.cd('/'+fileNumber)
             EBbetaAll.addDataSet(h2p21)
             EBbetaAll.cd('/'+fileNumber)
             EBbetaAll.addDataSet(h2p22)

        double x1CVTp= h1p4.getAxis().getBinCenter(xp1);
        //int x1CVTp=xp1;
        double x2CVTp= h1p4.getAxis().getBinCenter(xp2);
        //int x2CVTp=xp2;
        double yCVTp= 2*halfmax1;
//        DataLine line1 = new DataLine(x1CVTp,0.0,x1CVTp,yCVTp);
//        line1.setLineColor(2);
//        line1.setLineWidth(2);
//        //line1.setArrowSizeOrigin(15);
//        //line1.setArrowSizeEnd(15);
//        //line1.setArrowAngle(25);
//
//        DataLine line2 = new DataLine(x2CVTp,0.0,x2CVTp,yCVTp);
//        line2.setLineColor(2);
//        line2.setLineWidth(2);
//        //line2.setArrowSizeOrigin(15);
//        //line2.setArrowSizeEnd(15);
//        //line2.setArrowAngle(25);
//
//        double x1CVTn= h1p5.getAxis().getBinCenter(xn1);
//        //int x1CVTn=xn1;
//        double x2CVTn= h1p5.getAxis().getBinCenter(xn2);
//        //int x2CVTn=xn2;
//        double yCVTn= 2*halfmax2;
//        DataLine line3 = new DataLine(x1CVTn,0.0,x1CVTn,yCVTn);
//        line3.setLineColor(2);
//        line3.setLineWidth(2);
//        //line3.setArrowSizeOrigin(15);
//        //line3.setArrowSizeEnd(15);
//        //line3.setArrowAngle(25);
//
//        DataLine line4 = new DataLine(x2CVTn,0.0,x2CVTn,yCVTn);
//        line4.setLineColor(2);
//        line4.setLineWidth(2);
//        //line4.setArrowSizeOrigin(15);
//        //line4.setArrowSizeEnd(15);
//        //line4.setArrowAngle(25);
//
//      TCanvas crosses = new TCanvas("TBT, CVT, and EC", 1000, 800);
//        crosses.divide(2,3);
//      crosses.getCanvas().setGridX(false); crosses.getCanvas().setGridY(false);
//      crosses.getCanvas().setAxisFontSize(18);
//      crosses.getCanvas().setAxisTitleSize(24);
//        crosses.cd(1);
//      crosses.getCanvas().draw(dir.getObject("TBT", "hi_vz_neg"));
//        crosses.cd(5);
//        crosses.getCanvas().draw(dir.getObject("EC", "hi_pi0_mass"));
//        crosses.cd(4);
//        crosses.getCanvas().draw(dir.getObject("EC", "hi_sf_EC"));
//        crosses.cd(6);
//        crosses.getCanvas().draw(dir.getObject("TBT", "hi_vz_pos"));
//        crosses.cd(2);
//        crosses.getCanvas().draw(dir.getObject("CVT", "hi_vz_pos"));
//        crosses.draw(line1);
//        crosses.draw(line2);
//        crosses.cd(3);
//        crosses.getCanvas().draw(dir.getObject("CVT", "hi_vz_neg"));
//        crosses.draw(line3);
//        crosses.draw(line4);
//
//        TCanvas crosses9 = new TCanvas("TBT Sector Population", 1000, 800);
//        crosses9.divide(2,2);
//        crosses9.getCanvas().setGridX(false); crosses9.getCanvas().setGridY(false);
//        crosses9.getCanvas().setAxisFontSize(18);
//        crosses9.getCanvas().setAxisTitleSize(24);
//        crosses9.cd(3);
//        crosses9.getCanvas().draw(dir.getObject("TBT","hi_theta_phi_pos"));
//        crosses9.cd(2);
//        crosses9.getCanvas().draw(dir.getObject("TBT","hi_theta_phi_neg"));
//        crosses9.cd(1);
//        crosses9.getCanvas().draw(dir.getObject("TBT","hi_phi_pos"));
//        crosses9.cd(4);
//        crosses9.getCanvas().draw(dir.getObject("TBT","hi_phi_neg"));
//
//        TCanvas crosses2 = new TCanvas("FTOF beta vs. p", 1000, 800);
//        crosses2.divide(3,2);
//        crosses2.getCanvas().setGridX(false); crosses2.getCanvas().setGridY(false);
//        crosses2.getCanvas().setAxisFontSize(18);
//        crosses2.getCanvas().setAxisTitleSize(24);
//        crosses2.cd(6);
//        crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_pos_1"));
//        crosses2.cd(1);
//        crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_pos_2"));
//        crosses2.cd(2);
//        crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_pos_3"));
//        crosses2.cd(3);
//        crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_neg_1"));
//        crosses2.cd(4);
//        crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_neg_2"));
//        crosses2.cd(5);
//        crosses2.getCanvas().draw(dir.getObject("FTOF","hi_beta_neg_3"));
//
//
//        TCanvas crosses3 = new TCanvas("FTOF residuals", 1000, 800);
//        crosses3.divide(3,2);
//        crosses3.getCanvas().setGridX(false); crosses3.getCanvas().setGridY(false);
//        crosses3.getCanvas().setAxisFontSize(18);
//        crosses3.getCanvas().setAxisTitleSize(24);
//        crosses3.cd(6);
//        crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_pos_1"));
//        crosses3.cd(1);
//        crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_pos_2"));
//        crosses3.cd(2);
//        crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_pos_3"));
//        crosses3.cd(3);
//        crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_neg_1"));
//        crosses3.cd(4);
//        crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_neg_2"));
//        crosses3.cd(5);
//        crosses3.getCanvas().draw(dir.getObject("FTOF","hi_x_residual_neg_3"));
//
//        TCanvas crosses8 = new TCanvas("FTOF RF negative offset", 1000, 800);
//        crosses8.divide(3,1);
//        crosses8.getCanvas().setGridX(false); crosses8.getCanvas().setGridY(false);
//        crosses8.getCanvas().setAxisFontSize(18);
//        crosses8.getCanvas().setAxisTitleSize(24);
//        crosses8.cd(3);
//        crosses8.getCanvas().draw(dir.getObject("FTOF","hi_rf_neg_paddle_1"));
//        crosses8.cd(1);
//        crosses8.getCanvas().draw(dir.getObject("FTOF","hi_rf_neg_paddle_2"));
//        crosses8.cd(2);
//        crosses8.getCanvas().draw(dir.getObject("FTOF","hi_rf_neg_paddle_3"));
//
//        TCanvas crosses4 = new TCanvas("HTCC and FT", 1000, 800);
//        crosses4.divide(3,2);
//        crosses4.getCanvas().setGridX(false); crosses4.getCanvas().setGridY(false);
//        crosses4.getCanvas().setAxisFontSize(18);
//        crosses4.getCanvas().setAxisTitleSize(24);
//        crosses4.cd(1);
//        crosses4.getCanvas().draw(dir.getObject("HTCC", "hi_nphe_ele"));
//        crosses4.cd(2);
//        crosses4.getCanvas().draw(dir.getObject("FT", "hpi0sum"));
//        crosses4.cd(6);
//        crosses4.getCanvas().draw(dir.getObject("FT", "hi_cal_time_cut_ch"));
//        crosses4.cd(4);
//        crosses4.getCanvas().draw(dir.getObject("FT", "hi_hodo_ematch_l1"));
//        crosses4.cd(5);
//        crosses4.getCanvas().draw(dir.getObject("FT", "hi_hodo_ematch_l2"));
//        crosses4.cd(3);
//        crosses4.getCanvas().draw(dir.getObject("FT", "hi_hodo_tmatch_l2"));
//
//        TCanvas crosses10 = new TCanvas("FT Calorimeter", 1000, 800);
//        crosses10.divide(3,1);
//        crosses10.getCanvas().setGridX(false); crosses10.getCanvas().setGridY(false);
//        crosses10.getCanvas().setAxisFontSize(18);
//        crosses10.getCanvas().setAxisTitleSize(24);
//        crosses10.cd(3);
//        crosses10.getCanvas().draw(dir.getObject("FT","hi_cal_e_ch"));
//        crosses10.cd(1);
//        crosses10.getCanvas().draw(dir.getObject("FT", "hi_cal_theta_ch"));
//        crosses10.cd(2);
//        crosses10.getCanvas().draw(dir.getObject("FT", "hi_cal_phi_ch"));
//
//        TCanvas crosses6 = new TCanvas("CTOF and CND (beta vs. p and residuals)", 1000, 800);
//        crosses6.divide(3,2);
//        crosses6.getCanvas().setGridX(false); crosses6.getCanvas().setGridY(false);
//        crosses6.getCanvas().setAxisFontSize(18);
//        crosses6.getCanvas().setAxisTitleSize(24);
//        crosses6.cd(6);
//        crosses6.getCanvas().draw(dir.getObject("CTOF","hi_beta_pos"));
//        crosses6.cd(1);
//        crosses6.getCanvas().draw(dir.getObject("CTOF","hi_beta_neg"));
//        crosses6.cd(2);
//        crosses6.getCanvas().draw(dir.getObject("CTOF","hi_z_residual"));
//        crosses6.cd(3);
//        crosses6.getCanvas().draw(dir.getObject("CND","hi_beta_pos"));
//        crosses6.cd(4);
//        crosses6.getCanvas().draw(dir.getObject("CND","hi_beta_neg"));
//        crosses6.cd(5);
//        crosses6.getCanvas().draw(dir.getObject("CND","hi_z_residual"));
//
//        TCanvas crosses11 = new TCanvas("CTOF and CND (RF offsets)", 1000, 800);
//        crosses11.divide(2,1);
//        crosses11.getCanvas().setGridX(false); crosses11.getCanvas().setGridY(false);
//        crosses11.getCanvas().setAxisFontSize(18);
//        crosses11.getCanvas().setAxisTitleSize(24);
//        crosses11.cd(1);
//        crosses11.getCanvas().draw(dir.getObject("CTOF","hi_rf_neg_paddle"));
//        crosses11.cd(2);
//        crosses11.getCanvas().draw(dir.getObject("CND","hi_rf_neg_paddle"));
//
//        TCanvas crosses5 = new TCanvas("EB", 1000, 800);
//        crosses5.divide(3,1);
//        crosses5.getCanvas().setGridX(false); crosses5.getCanvas().setGridY(false);
//        crosses5.getCanvas().setAxisFontSize(18);
//        crosses5.getCanvas().setAxisTitleSize(24);
//        crosses5.cd(1);
//        crosses5.getCanvas().draw(dir.getObject("EB", "hi_vt_el"));
//        crosses5.cd(2);
//        crosses5.getCanvas().draw(dir.getObject("EB", "hi_vt_pi"));
//        crosses5.cd(3);
//        crosses5.getCanvas().draw(dir.getObject("EB", "hi_vt_pr"));
//
//        TCanvas crosses7 = new TCanvas("EB Beta", 1000, 800);
//        crosses7.divide(2,2);
//        crosses7.getCanvas().setGridX(false); crosses6.getCanvas().setGridY(false);
//        crosses7.getCanvas().setAxisFontSize(18);
//        crosses7.getCanvas().setAxisTitleSize(24);
//        crosses7.cd(4);
//        crosses7.getCanvas().draw(dir.getObject("EB","hi_beta_pos_ftof"));
//        crosses7.cd(1);
//        crosses7.getCanvas().draw(dir.getObject("EB","hi_beta_neg_ftof"));
//        crosses7.cd(2);
//        crosses7.getCanvas().draw(dir.getObject("EB","hi_beta_pos_ctof"));
//        crosses7.cd(3);
//        crosses7.getCanvas().draw(dir.getObject("EB","hi_beta_neg_ctof"));
//
//
//        String foldername ="Images_for_Run_"+args[0];
//        String foldercommand="mkdir "+foldername;
//        String movecommand1="mv TBT_CVT_and_EC_"+args[0]+".png Images_for_Run_"+args[0];
//        String movecommand2="mv FTOF_beta_vs_p_"+args[0]+".png Images_for_Run_"+args[0];
//        String movecommand3="mv FTOF_residuals_"+args[0]+".png Images_for_Run_"+args[0];
//        String movecommand4="mv HTCC_and_FT_"+args[0]+".png Images_for_Run_"+args[0];
//        String movecommand5="mv CTOF_and_CND_beta_vs_p_and_residuals_"+args[0]+".png Images_for_Run_"+args[0];
//        String movecommand6="mv EB_"+args[0]+".png Images_for_Run_"+args[0];
//        String movecommand7="mv EB_Beta_"+args[0]+".png Images_for_Run_"+args[0];
//        String movecommand8="mv FTOF_RF_Offset_"+args[0]+".png Images_for_Run_"+args[0];
//        String movecommand9="mv TBT_Sector_Population_"+args[0]+".png Images_for_Run_"+args[0];
//        String movecommand10="mv FT_Calorimeter_"+args[0]+".png Images_for_Run_"+args[0];
//        String movecommand11="mv CTOF_and_CND_RF_offsets_"+args[0]+".png Images_for_Run_"+args[0];
//        String movetext="mv Data_Output_Run_"+args[0]+".txt Images_for_Run_"+args[0];
//
//        println foldercommand.execute().text;
//        //println "ls".execute().text;
//
//
//        crosses.save("TBT_CVT_and_EC_"+args[0]+".png");
//        crosses2.save("FTOF_beta_vs_p_"+args[0]+".png");
//        crosses3.save("FTOF_residuals_"+args[0]+".png");
//        crosses4.save("HTCC_and_FT_"+args[0]+".png");
//        crosses6.save("CTOF_and_CND_beta_vs_p_and_residuals_"+args[0]+".png");
//        crosses5.save("EB_"+args[0]+".png");
//        crosses7.save("EB_Beta_"+args[0]+".png");
//        crosses8.save("FTOF_RF_Offset_"+args[0]+".png");
//        crosses9.save("TBT_Sector_Population_"+args[0]+".png");
//        crosses10.save("FT_Calorimeter_"+args[0]+".png");
//        crosses11.save("CTOF_and_CND_RF_offsets_"+args[0]+".png");
//
//        String NotesTBT;
//        if( Peak_of_Positive_TBT_Num <= -5 || 5 <= Peak_of_Positive_TBT_Num || Peak_of_Negative_TBT_Num <= -5 || 5 <= Peak_of_Negative_TBT_Num  ){
//          NotesTBT="Unexpected Data - Check fit/data"+"\t\t";
//        } 
//        else {
//          NotesTBT="\t\t";
//        }
//
//        String NotesCVT;
//        if( PCenterCVT <= -2 || 2 <= PCenterCVT || NCenterCVT <= -2 || 2 <= NCenterCVT || Extension_of_Positive_CVT_Num <= 2 || 8 <= Extension_of_Positive_CVT_Num || Extension_of_Negative_CVT_Num <=2 || 8 <= Extension_of_Negative_CVT_Num ){
//          NotesCVT="Unexpected Data - Check fit/data"+"\t\t";
//        }
//        else {  
//          NotesCVT="\t\t";
//        }
//
//        String NotesEC;
//        if( Peak_of_EC_electron_SF_Num <= 0.2 || 0.3 <= Peak_of_EC_electron_SF_Num || Peak_of_EC_pi0_Num <= 110 || 160 <= Peak_of_EC_pi0_Num ){
//          NotesEC="Unexpected Data - Check fit/data"+"\t\t";
//        }
//        else {
//          NotesEC="\t\t";
//        }
//
//        String NotesHTCC;
//        if( Photoelectron_Distribution_Peak_Num <= 5 || 15 <= Photoelectron_Distribution_Peak_Num ){
//          NotesHTCC="Unexpected Data - Check fit/data"+"\t\t";
//        }
//        else {
//          NotesHTCC="\t\t";
//        }
//
//        String NotesFT;
//        if( Hodoscope_energy_Peak_1_Num <= 0.5 || 2.5 <= Hodoscope_energy_Peak_1_Num || Hodoscope_energy_Peak_2_Num <= 2 || 4 <= Hodoscope_energy_Peak_2_Num || Hodoscope_time_Peak_Num <= -4 || 4 <= Hodoscope_time_Peak_Num || Calorimeter_time_Peak_Num <= -1 || 1 <= Calorimeter_time_Peak_Num || FT_pi0_Peak_Num <= 115 || 145 <= FT_pi0_Peak_Num){
//          NotesFT="Unexpected Data - Check fit/data"+"\t\t";
//        }
//        else {
//          NotesFT="\t\t";
//        }
//
//        String NotesEB;
//        if( EB_Electron_Center_Num <= -0.5 || 0.5 <= EB_Electron_Center_Num || EB_Pion_Center_Num <= -0.5 || 0.5 <= EB_Pion_Center_Num || EB_Proton_Center_Num <= -0.5 || 0.5 <= EB_Proton_Center_Num){
//          NotesEB="Unexpected Data - Check fit/data"+"\t\t";
//        }
//        else {
//          NotesEB="\t";
//        }
//
//        String writeTBT = Peak_of_Positive_TBT+Peak_of_Negative_TBT+"\t"+"See TBT histos\t\t"+NotesTBT;
//        String writeCVT = "See CVT histos\t"+Extension_of_Positive_CVT+PCenterCVT+"\t\t"+"See CVT histos\t"+Extension_of_Negative_CVT+NCenterCVT+"\t\t"+NotesCVT;
//        String writeEC = Peak_of_EC_electron_SF+"\t"+Peak_of_EC_pi0+"\t"+NotesEC;
//        String writeFTOF = "\t\t\t\t\t\t\t\t"+"See RF offsets P1A\t"+"See RF offsets P1B\t"+"\t"+"\t\t";
//        String writeHTCC = Photoelectron_Distribution_Peak+"\t"+NotesHTCC;
//        String writeCTOF = "\t\t\t"+"See CTOF histos\t\t"+"See CTOF histos\t\t"+"\t\t";
//        String writeCND = "\t\t\t"+"See CND histos\t\t"+"See CND histos\t\t"+"\t\t";
//        String writeFT = Hodoscope_energy_Peak_1+Hodoscope_energy_Peak_2+"\t"+Hodoscope_time_Peak+"\t"+"See FT histos\t"+"See FT histos\t"+"See FT histos\t\t"+Calorimeter_time_Peak+"\t"+FT_pi0_Peak+"\t"+NotesFT;
//        String writeEB = EB_Electron_Center+EB_Pion_Center+EB_Proton_Center+"\t"+"See EB Beta histos\t"+"See EB Beta histos\t"+"See EB Beta histos\t"+"See EB Beta histos\t\t"+NotesEB;
//
//        PrintWriter writer = new PrintWriter("Data_Output_Run_"+args[0]+".txt", "UTF-8");
//
//        writer.println(args[0]+"\t\t\t\t"+writeTBT+writeCVT+writeEC+writeFTOF+writeHTCC+writeCTOF+writeCND+writeFT+writeEB);
//
//
//        //writer.println("\n TBT Data");
//
//        //writer.println("Peak of Negative TBT: ");
//        //writer.println(f1p.getParameter(1)+"\n");
//
//        //writer.println("Peak of Positive TBT: ");
//        //writer.println(f1p3.getParameter(1)+"\n");
//
//        //writer.println("\n CVT Data");
//
//        //writer.println("Center of Positive CVT: ");
//        //writer.println(PCenterCVT+"\n");
//        //writer.println("Extension of Positive CVT: ");
//        //writer.println(h1p4.getAxis().getBinCenter(xp2)-h1p4.getAxis().getBinCenter(xp1)+"\n");
//
//        //writer.println("Center of Negative CVT: ");
//        //writer.println(NCenterCVT+"\n");
//        //writer.println("Extension of Negative CVT: ");
//        //writer.println(h1p5.getAxis().getBinCenter(xn2)-h1p5.getAxis().getBinCenter(xn1)+"\n");
//
//        //writer.println("\n EC Data");
//
//        //writer.println("Peak of EC electron SF: ");
//        //writer.println(f1p6.getParameter(1)+"\n");
//
//        //writer.println("Peak of EC pi0: ");
//        //writer.println(f1p2.getParameter(1)+"\n");
//
//        //writer.println("\n HTCC Data");
//
//        //writer.println("Photoelectron Distribution Peak: ");
//        //writer.println(f1p8.getParameter(1)+"\n");
//
//        //writer.println("\n FT Data");
//
//        //writer.println("Hodoscope energy Peak 1: ");
//        //writer.println(f1p11.getParameter(1)+"\n");
//
//        //writer.println("Hodoscope energy Peak 2: ");
//        //writer.println(f1p12.getParameter(1)+"\n");
//
//        //writer.println("Hodoscope time Peak: ");
//        //writer.println(f1p13.getParameter(1)+"\n");
//
//        //writer.println("Calorimeter time Peak: ");
//        //writer.println(f1p14.getParameter(1)+"\n");
//
//        //writer.println("FT pi0 Peak: ");
//        //writer.println(f1p15.getParameter(1)+"\n");
//
//        //writer.println("\n");
//
//
//        writer.close();
//
//
//        println movecommand1.execute().text;
//        println movecommand2.execute().text;
//        println movecommand3.execute().text;
//        println movecommand4.execute().text;
//        println movecommand5.execute().text;
//        println movecommand6.execute().text;
//        println movecommand7.execute().text;
//        println movecommand8.execute().text;
//        println movecommand9.execute().text;
//        println movecommand10.execute().text;
//        println movecommand11.execute().text;
//        //println movetext.execute().text; 
//        //println "ls".execute().text;
//
        System.out.println("The final TBT sector results were: "+TBTResults+"\n");
        System.out.println("These results are for: "+fileNames);

  }
 else if (listOfFiles[i].isDirectory()) {
    System.out.println("Directory " + listOfFiles[i].getName());
  }
}


    String foldercommand="mkdir Hipo_Files_For_Timeline";
    println foldercommand.execute().text;
    
    String movecommand1="mv TBTvertex.hipo Hipo_Files_For_Timeline";
    String movecommand2="mv TBTsectors.hipo Hipo_Files_For_Timeline";
    String movecommand3="mv CVTextention.hipo Hipo_Files_For_Timeline";
    String movecommand4="mv CVTcenter.hipo Hipo_Files_For_Timeline";
    String movecommand5="mv ECsf.hipo Hipo_Files_For_Timeline";
    String movecommand6="mv HTCCnpe.hipo Hipo_Files_For_Timeline";
    String movecommand7="mv FThodoEnergy.hipo Hipo_Files_For_Timeline";
    String movecommand8="mv FThodoTime.hipo Hipo_Files_For_Timeline";
    String movecommand9="mv FTcalorTime.hipo Hipo_Files_For_Timeline";
    String movecommand10="mv FTpi0.hipo Hipo_Files_For_Timeline";
    String movecommand11="mv EBstartTime.hipo Hipo_Files_For_Timeline";
    String movecommand12="mv ECpi0.hipo Hipo_Files_For_Timeline";
    String movecommand13="mv FTOFall.hipo Hipo_Files_For_Timeline";
    String movecommand14="mv CTOFall.hipo Hipo_Files_For_Timeline";
    String movecommand15="mv CNDall.hipo Hipo_Files_For_Timeline";
    String movecommand16="mv FTcalorEnergyTimeline.hipo Hipo_Files_For_Timeline";
    String movecommand17="mv EBbetaAll.hipo Hipo_Files_For_Timeline";

    String movecommandworking="mv Work_in_progress_CNDall.hipo Hipo_Files_For_Timeline";

            //FTOFall.writeFile('FTOFall.hipo')
            
            //CTOFall.writeFile('CTOFall.hipo')

            CNDall.mkdir('/timelines')
            CNDall.cd('/timelines')
            //CNDall.addDataSet(CNDallCount[0])
            CNDall.addDataSet(CNDallExist[0])
            
            //CNDall.writeFile('CNDall.hipo')
            CNDall.writeFile('Work_in_progress_CNDall.hipo')

            FTcalorEnergyTimeline.mkdir('/timelines')
            FTcalorEnergyTimeline.cd('/timelines')
            FTcalorEnergyTimeline.addDataSet(FTcalorEnergyTLCount[0])
            FTcalorEnergyTimeline.addDataSet(FTcalorEnergyTLCount[1])
            FTcalorEnergyTimeline.addDataSet(FTcalorEnergyTLCount[2])
            
            FTcalorEnergyTimeline.writeFile('FTcalorEnergyTimeline.hipo')
            
            //EBbetaAll.writeFile('EBbetaAll.hipo')
            
            
            TBTvertex.mkdir('/timelines')
            TBTvertex.cd('/timelines')
            TBTvertex.addDataSet(TBTvertexPeak[0])
            TBTvertex.addDataSet(TBTvertexPeak[1])
            
            TBTvertex.writeFile('TBTvertex.hipo')
            
            
            TBTsectors.mkdir('/timelines')
            TBTsectors.cd('/timelines')
            TBTsectors.addDataSet(TBTsectorsFill[0])
            TBTsectors.addDataSet(TBTsectorsFill[1])
            
            TBTsectors.writeFile('TBTsectors.hipo')
            
            CVTextention.mkdir('/timelines')
            CVTextention.cd('/timelines')
            CVTextention.addDataSet(CVTextentionVertex[0])
            CVTextention.addDataSet(CVTextentionVertex[1]) 
            
            CVTextention.writeFile('CVTextention.hipo')
            
            CVTcenter.mkdir('/timelines')
            CVTcenter.cd('/timelines')
            CVTcenter.addDataSet(CVTcenterVertex[0])
            CVTcenter.addDataSet(CVTcenterVertex[1])
            
            CVTcenter.writeFile('CVTcenter.hipo')
            
            ECsf.mkdir('/timelines')
            ECsf.cd('/timelines')
            ECsf.addDataSet(ECsfelectron[0])
            
            ECsf.writeFile('ECsf.hipo')
            
            ECpi0.mkdir('/timelines')
            ECpi0.cd('/timelines')
            ECpi0.addDataSet(ECpi0Peak[0]) 
            
            ECpi0.writeFile('ECpi0.hipo')
            
            HTCCnpe.mkdir('/timelines')
            HTCCnpe.cd('/timelines')
            HTCCnpe.addDataSet(HTCCpeak[0])
            
            HTCCnpe.writeFile('HTCCnpe.hipo')
            
            FThodoEnergy.mkdir('/timelines')
            FThodoEnergy.cd('/timelines')
            FThodoEnergy.addDataSet(FThodoEnergyPeak[0])
            FThodoEnergy.addDataSet(FThodoEnergyPeak[1])
            
            FThodoEnergy.writeFile('FThodoEnergy.hipo')
            
            FThodoTime.mkdir('/timelines')
            FThodoTime.cd('/timelines')
            FThodoTime.addDataSet(FThodoTimePeak[0])
            
            FThodoTime.writeFile('FThodoTime.hipo')
            
            FTcalorTime.mkdir('/timelines')
            FTcalorTime.cd('/timelines')
            FTcalorTime.addDataSet(FTcalorTimePeak[0])
            
            FTcalorTime.writeFile('FTcalorTime.hipo')
            
            FTpi0.mkdir('/timelines')
            FTpi0.cd('/timelines')
            FTpi0.addDataSet(FTpi0Peak[0]) 
            
            FTpi0.writeFile('FTpi0.hipo')
            
            EBstartTime.mkdir('/timelines')
            EBstartTime.cd('/timelines')
            EBstartTime.addDataSet(EBstartTimeCenter[0])
            EBstartTime.addDataSet(EBstartTimeCenter[1])
            EBstartTime.addDataSet(EBstartTimeCenter[2])
            
            EBstartTime.writeFile('EBstartTime.hipo')
            
                  println movecommand1.execute().text;
                  println movecommand2.execute().text;
                  println movecommand3.execute().text;
                  println movecommand4.execute().text;
                  println movecommand5.execute().text;
                  println movecommand6.execute().text;
                  println movecommand7.execute().text;
                  println movecommand8.execute().text;
                  println movecommand9.execute().text;
                  println movecommand10.execute().text;
                  println movecommand11.execute().text;
                  println movecommand12.execute().text;
                  println movecommand13.execute().text;
                  println movecommand14.execute().text;
                  println movecommand15.execute().text;
                  println movecommand16.execute().text;
                  println movecommand17.execute().text;
                  println movecommandworking.execute().text;

System.out.println("\n"+" The number of files analyzed was: "+filecount);

//System.out.println("This line is meant to fail while running this program through a loop. Comment this line out if you don't want the program to crash after saving the images"+ScriptFail);
