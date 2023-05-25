microserviceSchema = "MCSR"


commonUrlPrefix = ":3003"

methodNames = ["Catalog", "Account", "Cart", "Order"]

urlPrefix = {
    "TargetPlan": {
        "Catalog": "1",
        "Account": "2",
        "Cart": "3",
        "Order": "3",
    },
    "TargetPlan-MEM": {
        "Catalog": "1",
        "Account": "2",
        "Cart": "1",
        "Order": "1",
    },
    "TargetPlan-DEneural": {
        "Catalog": "2",
        "Account": "1",
        "Cart": "4",
        "Order": "4",
    },
    "MCSR": {
        "Catalog": "2",
        "Account": "2",
        "Cart": "2",
        "Order": "2",
    },
    "PRBME-JADE": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "1",
        "Order": "1",
    },
    "PRBME-JADE-2": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "1",
        "Order": "1",
    },
    "PRBME-JADE-3": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "1",
        "Order": "1",
    },
    "PRBME-JADE-4": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "1",
        "Order": "1",
    },
    "PRBME-JADE-5": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "1",
        "Order": "1",
    },
    "PRBME-JADE-noM": {
        "Catalog": "2",
        "Account": "2",
        "Cart": "2",
        "Order": "2",
    },
    "PRBME-JADE-noM-2": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "1",
        "Order": "1",
    },
    "PRBME-JADE-noM-3": {
        "Catalog": "2",
        "Account": "2",
        "Cart": "2",
        "Order": "2",
    },
    "PRBME-JADE-noM-4": {
        "Catalog": "3",
        "Account": "2",
        "Cart": "2",
        "Order": "2",
    },
    "PRBME-JADE-noM-5": {
        "Catalog": "2",
        "Account": "2",
        "Cart": "2",
        "Order": "2",
    },
    "Monolithic": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "1",
        "Order": "1",
    },
    "MECE-SLO-0": {
        "Catalog": "1",
        "Account": "2",
        "Cart": "2",
        "Order": "2",
    },
    "MECE-SLO-1": {
        "Catalog": "1",
        "Account": "2",
        "Cart": "2",
        "Order": "1",
    },
    "MECE-SLO-2": {
        "Catalog": "1",
        "Account": "3",
        "Cart": "2",
        "Order": "2",
    },
    "MECE-SLO-3": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "2",
        "Order": "1",
    },
    "MECE-SLO-4": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "3",
        "Order": "2",
    },
    "MECE-SLO-5": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "1",
        "Order": "3",
    },
    "MECE-SLO-6": {
        "Catalog": "1",
        "Account": "3",
        "Cart": "1",
        "Order": "3",
    },
    "MECE-SLO-noM-1": {
        "Catalog": "1",
        "Account": "2",
        "Cart": "2",
        "Order": "3",
    },
    "MECE-SLO-noM-2": {
        "Catalog": "1",
        "Account": "2",
        "Cart": "2",
        "Order": "2",
    },
    "MECE-SLO-noM-3": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "2",
        "Order": "3",
    },
    "MECE-SLO-noM-4": {
        "Catalog": "1",
        "Account": "2",
        "Cart": "1",
        "Order": "2",
    },
    "MECE-SLO-noM-5": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "1",
        "Order": "2",
    },
    "MECE-SLO-noM-6": {
        "Catalog": "1",
        "Account": "1",
        "Cart": "1",
        "Order": "1",
    },
}

def getUrlPrefix(methodName):
    if (microserviceSchema not in urlPrefix or
       methodName not in urlPrefix[microserviceSchema]):
        return commonUrlPrefix+"1"
    return commonUrlPrefix+urlPrefix[microserviceSchema][methodName]

def buildTestCMD(method):
    global commonUrlPrefix,microserviceSchema
    # commonUrlPrefix = ":803"
    # microserviceSchema="MECE-SLO-0"
    CMDPrefix = "curl "
    host = "localhost"
    host = "http://vm-4c8g-node1"
    CMD = CMDPrefix+host+getUrlPrefix(method)+"/"+method+"Action/testAllFunction"
    print(CMD,end="; ")
    print()
    return CMD

def main():
    for method in methodNames:
        buildTestCMD(method)
    print()

if __name__ == '__main__':
    main()
