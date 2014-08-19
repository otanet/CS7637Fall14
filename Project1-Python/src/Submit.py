def Solve(p):
    return "A"

def initialize_problems():
    p=[]

    p1=Problem()
    p1.ProblemType="2x1"
    p1.Label="1"

    p1fao1=Object()
    p1fao1.Attributes.append(Attribute("Shape","Pac-Man"))
    p1fao1.Attributes.append(Attribute("Angle","0"))
    p1fao1.Label="A"
    p1fa=Figure()
    p1fa.Label="A"
    p1fa.Objects.append(p1fao1)
    p1.Figures.append(p1fa)

    p1fbo1=Object()
    p1fbo1.Attributes.append(Attribute("Shape","Pac-Man"))
    p1fbo1.Attributes.append(Attribute("Angle","90"))
    p1fbo1.Label="A"
    p1fb=Figure()
    p1fb.Label="B"
    p1fb.Objects.append(p1fbo1)
    p1.Figures.append(p1fb)

    p1fco1=Object()
    p1fco1.Attributes.append(Attribute("Shape","Pac-Man"))
    p1fco1.Attributes.append(Attribute("Angle","270"))
    p1fco1.Label="A"
    p1fc=Figure()
    p1fc.Label="C"
    p1fc.Objects.append(p1fco1)
    p1.Figures.append(p1fc)

    p1f1o1=Object()
    p1f1o1.Attributes.append(Attribute("Shape","Pac-Man"))
    p1f1o1.Attributes.append(Attribute("Angle","270"))
    p1f1o1.Label="A"
    p1f1=Figure()
    p1f1.Label="1"
    p1f1.Objects.append(p1f1o1)
    p1.Figures.append(p1f1)

    p1f2o1=Object()
    p1f2o1.Attributes.append(Attribute("Shape","Pac-Man"))
    p1f2o1.Attributes.append(Attribute("Angle","0"))
    p1f2o1.Label="A"
    p1f2=Figure()
    p1f2.Label="2"
    p1f2.Objects.append(p1f2o1)
    p1.Figures.append(p1f2)

    p1f3o1=Object()
    p1f3o1.Attributes.append(Attribute("Shape","Pac-Man"))
    p1f3o1.Attributes.append(Attribute("Angle","90"))
    p1f3o1.Label="A"
    p1f3=Figure()
    p1f3.Label="3"
    p1f3.Objects.append(p1f3o1)
    p1.Figures.append(p1f3)

    p1f4o1=Object()
    p1f4o1.Attributes.append(Attribute("Shape","Pac-Man"))
    p1f4o1.Attributes.append(Attribute("Angle","315"))
    p1f4o1.Label="A"
    p1f4=Figure()
    p1f4.Label="4"
    p1f4.Objects.append(p1f4o1)
    p1.Figures.append(p1f4)

    p1f5o1=Object()
    p1f5o1.Attributes.append(Attribute("Shape","Pac-Man"))
    p1f5o1.Attributes.append(Attribute("Angle","180"))
    p1f5o1.Label="A"
    p1f5=Figure()
    p1f5.Label="5"
    p1f5.Objects.append(p1f5o1)
    p1.Figures.append(p1f5)

    p1f6o1=Object()
    p1f6o1.Attributes.append(Attribute("Shape","Pac-Man"))
    p1f6o1.Attributes.append(Attribute("Angle","135"))
    p1f6o1.Label="A"
    p1f6=Figure()
    p1f6.Label="6"
    p1f6.Objects.append(p1f6o1)
    p1.Figures.append(p1f6)

    p.append(p1)

    """
    for problem in self.p:
        print(problem.ProblemType)
        for figure in problem.Figures:
            print("  "+figure.Label)
            for object in figure.Objects:
                print("    "+object.Label)
                for attribute in object.Attributes:
                    print("      "+attribute.Name + ": " + attribute.Value)
    """
    return p

def strip_comments(usercode):
    stripped = ""
    marker = "#"
    for line in usercode.splitlines():
        if line.find(marker)!=-1:
            line = line[:line.index(marker)]
        stripped += line + "\n"
    return stripped

def preprocess(s):
    slist = filter((lambda x : x.strip()!=""),s.splitlines())
    s = ""
    for e in slist:
        s+=e+"\n"
    s += "\n\n\n\n\n\n\n"
    s = s.replace("\r\n","\n")
    return s.replace("\t","    ")

class Problem:
    def __init__(self):
        self.ProblemType=""
        self.Figures=[]
        self.Label=""

    def addFigure(self, b):
        self.Figures.append(self, b)


class Figure:
    def __init__(self):
        self.Label=""
        self.Objects=[]


class Object:
    def __init__(self):
        self.Label=""
        self.Attributes=[]


class Attribute:
    def __init__(self):
        self.Name=""
        self.Value=""

    def __init__(self,name,value):
        self.Name=name
        self.Value=value

p=initialize_problems()
print("Beginning test...")
for problem in p:
    print(problem.ProblemType + ", " + problem.Label + ": " + Solve(problem))