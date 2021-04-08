import xlrd

__version__='1.1'
__author__='marwanalabassiry'
################################################################################################

################################################################################################
print("================== Welcome To Test Class Maker v", __version__,"==================\n")
filepath = input("Please enter the path to the Excel workbook you wish to parse...\n\nExcel to parse: ")
filepath.replace("\\","\\\\")
workbook = xlrd.open_workbook(filepath)
worksheet = workbook.sheet_by_index(0)
tcname = worksheet.cell(1,0).value
steps = worksheet.col_values(13)
tcname = tcname.replace("-","_")
class_identifier = tcname + " {\n"      
templateclass_1 = open("templates\\1.java", "r")
templateclass_2 = open("templates\\2.java", "r")
templateclass_3 = open("templates\\3.java", "r")
testclass= open("..\\src\\test\\java\\com\\AdvancePlus\\Test\\"+tcname+".java","w+")
testclass.write(templateclass_1.read())
testclass.write(class_identifier)
testclass.write(templateclass_2.read())

for i in steps:
    if(i!="Test Script (Step-by-Step) - Step"):
        i = i.replace("\n"," ")
        i = i.replace("\t"," ")
        testclass.write("\n\n\t\t\tlogger.step("+'"'+ i +'"'+");\n")
        testclass.write("\t\t\t//Code goes here...")

testclass.write(templateclass_3.read())

