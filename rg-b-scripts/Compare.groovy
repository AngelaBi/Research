import org.jlab.groot.ui.TCanvas;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
import org.jlab.jnp.physics.*;
import org.jlab.jnp.reader.*;

//import org.funp.dvcs.*;
//import org.funp.utilities.*;


import java.util.*;
import java.io.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jlab.groot.data.TDirectory;


//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
import org.jlab.groot.fitter.*;
import org.jlab.groot.math.*;
//---- imports for PHYSICS library
import org.jlab.jnp.physics.*;
import org.jlab.jnp.reader.*;

import org.jlab.groot.ui.TCanvas;
import org.jlab.groot.data.TDirectory;
def hi(){
	println("Hello World");
}

String fname1 =args[0]+".hipo";
String fname2 =args[1]+".hipo";
//System.out.println(fname);
String fileName1 = "/Users/biselli/Data/clas12/rg-b/pass1/AItrackingStudy/"+fname1;
String fileName2 = "/Users/biselli/Data/clas12/rg-b/pass1/AItrackingStudy/"+fname2;
//System.out.println(fileName);

System.out.println("Opening file: " + fileName1);
System.out.println("Opening file: " + fileName2);
TDirectory dir1 = new TDirectory();
TDirectory dir2 = new TDirectory();
dir1.readFile(fileName1);
dir2.readFile(fileName2);
//System.out.println(dir.getDirectoryList());
hi();
dir1.cd();
dir1.pwd();
System.out.println("\n");
H1F Q21 = dir1.getObject("Kinematics DVCS Cut FT", "Q2");
H1F Q23 = dir1.getObject("Kinematics Excl Cuts FT", "ConeAngleHist");


dir2.cd();                                                                                                         
dir2.pwd();         
System.out.println("\n");
H1F Q22 = dir2.getObject("Kinematics DVCS Cut FT", "Q2");
H1F Q24 = dir2.getObject("Kinematics Excl Cuts FT", "ConeAngleHist");
Q22.setLineColor(2);
Q24.setLineColor(2);
TCanvas crosses = new TCanvas("kinematics", 1000, 800);
	crosses.divide(2,3);
//h1p.setOptStat("1101");
crosses.cd(0)
crosses.draw(Q21)
crosses.draw(Q22,"same")
crosses.cd(1)
crosses.draw(Q23)
crosses.draw(Q24,"same")

