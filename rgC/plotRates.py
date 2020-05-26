'''Reading CVS file to plot rates


'''

import csv
import matplotlib.pyplot as plt

rga,rgashift=[],[]
rgb,rgbshift=[],[]
lh2fton,lh2ftonshift=[],[]
lh2ftoff,lh2ftoffshift=[],[]
apolloftoff,apolloftoffshift=[],[]
apolloelmo,apolloelmoshift=[],[]

with open('/Users/biselli/Downloads/rates.csv') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    for row in csv_reader:
        if line_count == 0:
            print(f'Column names are {", ".join(row)}')
            line_count += 1
        else:
            if row[1]=="RGA" and row[2]=="FTon":
                rga.append(float(row[7]))
                rgashift.append(float(row[6]))
            elif row[1]=="RGB" and row[2]=="FTon":
                rgb.append(float(row[7]))
                rgbshift.append(float(row[6]))
            elif row[1]=="LH2" and row[2]=="FToff":
                lh2ftoff.append(float(row[7]))
                lh2ftoffshift.append(float(row[6]))
                #print(f'\t{row[0]} works in the {row[1]} department, and was born in {row[2]}.')
            elif row[1]=="APOLLO" and row[2]=="FTOFF":
                apolloftoff.append(float(row[7]))
                apolloftoffshift.append(float(row[6]))
            elif row[1]=="APOLLO" and row[2]=="ELMO":
                apolloelmo.append(float(row[7]))
                apolloelmoshift.append(float(row[6]))
            line_count += 1
    print(f'Processed {line_count} lines.')
    print(lh2ftoffshift,lh2ftoff)
    
    
    
    fig, ax = plt.subplots(figsize=(7, 5))
    
    ax.set_xlabel("Region 1 occupancies [%]")
    ax.set_ylabel("Shift [mm]")
    
    ax.set_xlim(-1, 11.)
    ax.set_ylim(-1., 14.)
    d1=ax.scatter(rgashift,rga,facecolors='none', edgecolors='g',marker="s")
    
    d2=ax.scatter(rgbshift,rgb,facecolors='none', edgecolors='b',marker="D")
    #ax.scatter(lh2ftoffshift,lh2ftoff,facecolors='none', edgecolors='r')
    s1=ax.scatter(apolloftoffshift,apolloftoff,marker="v")
    s2=ax.scatter(apolloelmoshift,apolloelmo,marker="o")
    ax.legend((d1, d2, s1,s2), ('RGA - lh2 50nA FTON', 'RGB - ld2 50nA FTON', 'RGC - APOLLO 10nA FTOFF','APOLLO 10nA ELMO'))
    plt.savefig("results.pdf",format='pdf')
    plt.show()