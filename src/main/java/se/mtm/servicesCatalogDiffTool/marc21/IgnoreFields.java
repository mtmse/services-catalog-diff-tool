package se.mtm.servicesCatalogDiffTool.marc21;

import java.util.ArrayList;
import java.util.List;

public class IgnoreFields {

    private boolean ignoreLeaderField = false;
    private List<ControlFieldType> controlFieldList;
    private List<DataFieldType> dataFieldList;

    public boolean getIgnoreLeaderField() {
        return ignoreLeaderField;
    }

    public void setIgnoreLeaderField(boolean ignoreLeaderField) {
        this.ignoreLeaderField = ignoreLeaderField;
    }

    public List<ControlFieldType> getControlFieldList() {
        if (controlFieldList == null) {
            controlFieldList = new ArrayList<>();
        }
        return controlFieldList;
    }

    public List<DataFieldType> getDataFieldList() {
        if (dataFieldList == null) {
            dataFieldList = new ArrayList<>();
        }
        return dataFieldList;
    }

}
