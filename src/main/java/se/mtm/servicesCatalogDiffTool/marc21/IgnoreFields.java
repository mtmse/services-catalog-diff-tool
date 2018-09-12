package se.mtm.servicesCatalogDiffTool.marc21;

import java.util.ArrayList;
import java.util.List;

public class IgnoreFields {

    private LeaderFieldType leaderField;
    private List<ControlFieldType> controlFieldList;
    private List<DataFieldType> dataFieldList;

    public LeaderFieldType getLeaderField() {
        return leaderField;
    }

    public void setLeaderField(LeaderFieldType leaderField) {
        this.leaderField = leaderField;
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
