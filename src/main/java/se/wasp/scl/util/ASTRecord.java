package se.wasp.scl.util;

public class ASTRecord {
    String elementName;
    String location;
    String parentType;
    String parentLocation;
    String filePath;
    String extraInfo;

    public ASTRecord(String elementName, String location, String parentType,
                     String parentLocation, String filePath, String extraInfo) {
        this.elementName = elementName;
        this.location = location;
        this.parentType = parentType;
        this.parentLocation = parentLocation;
        this.filePath = filePath;
        this.extraInfo = extraInfo;
    }

    public String getElementName() {
        return elementName;
    }

    public String getLocation() {
        return location;
    }

    public String getParentType() {
        return parentType;
    }

    public String getParentLocation() {
        return parentLocation;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

}