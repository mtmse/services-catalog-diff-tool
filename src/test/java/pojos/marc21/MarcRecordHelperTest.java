package pojos.marc21;

import org.junit.Test;
import se.mtm.servicesCatalogDiffTool.marc21.*;

public class MarcRecordHelperTest {

    @Test
    public void compareRecords_withDifferentLeaderFields_returnsDifferences() {
        String mediaNumber = "C16626";

        RecordType mm2Record = new RecordType();
        LeaderFieldType leaderFieldTypeMM2 = new LeaderFieldType();
        leaderFieldTypeMM2.setValue("some value");
        mm2Record.setLeader(leaderFieldTypeMM2);

        RecordType mm3Record = new RecordType();
        LeaderFieldType leaderFieldTypeMM3 = new LeaderFieldType();
        leaderFieldTypeMM3.setValue("some other value");
        mm3Record.setLeader(leaderFieldTypeMM3);

        String diffs = MarcRecordHelper.compareRecords(mediaNumber, mm2Record, mm3Record);
        System.out.println(diffs);
    }

    @Test
    public void compareRecords_withDifferentControlFields_returnsDifferences() {
        String mediaNumber = "C16626";

        RecordType mm2Record = new RecordType();
        mm2Record.getControlfield().add(createControlFieldType("001", "value"));
        mm2Record.getControlfield().add(createControlFieldType("007", "to be something"));
        mm2Record.getControlfield().add(createControlFieldType("008", "value"));
        mm2Record.getControlfield().add(createControlFieldType("010", "value"));

        RecordType mm3Record = new RecordType();
        mm3Record.getControlfield().add(createControlFieldType("007", "something else"));
        mm3Record.getControlfield().add(createControlFieldType("008", "value"));
        mm3Record.getControlfield().add(createControlFieldType("009", "value"));

        String diffs = MarcRecordHelper.compareRecords(mediaNumber, mm2Record, mm3Record);
        System.out.println(diffs);
    }

    @Test
    public void compareRecords_withDifferentDataFields_returnsDifferences() {
        String mediaNumber = "C16626";

        RecordType mm2Record = new RecordType();

        DataFieldType df500 = createDataFieldType("500", " ", " ");
        df500.getSubfield().add(createSubfield("a", "Böcker"));
        mm2Record.getDatafield().add(df500);

        RecordType mm3Record = new RecordType();

        df500 = createDataFieldType("500", " ", " ");
        df500.getSubfield().add(createSubfield("a", "Böcker"));
        df500.getSubfield().add(createSubfield("a", "Punktskrift"));
        mm3Record.getDatafield().add(df500);

        String diffs = MarcRecordHelper.compareRecords(mediaNumber, mm2Record, mm3Record);
        System.out.println(diffs);
    }

    private ControlFieldType createControlFieldType(String tag, String value) {
        ControlFieldType controlFieldType = new ControlFieldType();
        controlFieldType.setTag(tag);
        controlFieldType.setValue(value);
        return controlFieldType;
    }

    private DataFieldType createDataFieldType(String tag, String ind1, String ind2) {
        DataFieldType dataFieldType = new DataFieldType();
        dataFieldType.setTag(tag);
        dataFieldType.setInd1(ind1);
        dataFieldType.setInd2(ind2);
        return dataFieldType;
    }

    private SubfieldatafieldType createSubfield(String key, String value) {
        SubfieldatafieldType subfieldatafieldType = new SubfieldatafieldType();
        subfieldatafieldType.setCode(key);
        subfieldatafieldType.setValue(value);
        return subfieldatafieldType;
    }
}