package se.mtm.servicesCatalogDiffTool.marc21;

import java.util.ArrayList;
import java.util.List;

public class MarcRecordHelper {

    public static String compareRecords(String mediaNumber, RecordType expected, RecordType actual) {
        if (expected == null || actual == null) {
            System.out.println("Missing record to compare " + mediaNumber);
            return null;
        }

        StringBuilder diffsInRecord = new StringBuilder();

        compareLeaderField(expected, actual, diffsInRecord);
        compareControlFields(expected, actual, diffsInRecord);
        compareDataFields(expected, actual, diffsInRecord);

        return diffsInRecord.toString();
    }

    private static void compareLeaderField(RecordType expected, RecordType actual, StringBuilder diffsInRecord) {

        LeaderFieldType leaderFieldMM2 = expected.getLeader();
        LeaderFieldType leaderFieldMM3 = actual.getLeader();
        if (leaderFieldMM2 != null && leaderFieldMM3 != null) {
            String valueMM2 = leaderFieldMM2.getValue();
            String valueMM3 = leaderFieldMM3.getValue();
            if (!valueMM2.equals(valueMM3)) {
                appendMessage(diffsInRecord,"Expected leader: \"" + valueMM2 + "\", but was: \"" + valueMM3 + "\"");
            }
        } else if (leaderFieldMM2 != null && leaderFieldMM3 == null) {
            appendMessage(diffsInRecord,"Leader field found in MM2 but not MM3");
        } else if (leaderFieldMM2 == null && leaderFieldMM3 != null) {
            appendMessage(diffsInRecord,"Leader field found in MM3 but not MM2");
        }

    }

    private static void compareControlFields(RecordType expected, RecordType actual, StringBuilder diffsInRecord) {

        List<ControlFieldType> controlFieldsMM2List = expected.getControlfield();
        List<ControlFieldType> controlFieldsMM3List = actual.getControlfield();

        if (!controlFieldsMM2List.isEmpty()) {
            for (ControlFieldType controlFieldMM2 : controlFieldsMM2List) {
                // if tag is null, it should be ignored
                String tag = controlFieldMM2.getTag();
                if (tag == null) {
                    continue;
                }

                ControlFieldType controlFieldMM3 = findControlField(tag, controlFieldsMM3List);

                if (controlFieldMM3 == null) {
                    appendMessage(diffsInRecord,"Expected control field \"" + tag + "\" exists in MM2 but not in MM3");
                } else {
                    String valueMM2 = controlFieldMM2.getValue();
                    String valueMM3 = controlFieldMM3.getValue();
                    if (!valueMM2.equals(valueMM3)) {
                        appendMessage(diffsInRecord,"Expected control field \"" + controlFieldMM2.getTag() + "\" value: \"" + valueMM2 + "\", but was: \"" + valueMM3 + "\"");
                    }
                    controlFieldsMM3List.remove(controlFieldMM3); // Remove control fields found
                }
            }
            // Control fields that matched should have been removed
            if (!controlFieldsMM3List.isEmpty()) {
                for (ControlFieldType controlFieldMM3 : controlFieldsMM3List) {
                    if (controlFieldMM3.getTag() != null) {
                        appendMessage(diffsInRecord,"Unexpected field: " + controlFieldMM3.getTag() + " exists in MM3 but not in MM2");
                    }
                }
            }
        }

    }

    private static void compareDataFields(RecordType expected, RecordType actual, StringBuilder diffsInRecord) {

        List<DataFieldType> dataFieldMM2List = expected.getDatafield();
        List<DataFieldType> dataFieldMM3List = actual.getDatafield();
        List<DataFieldType> fieldsCompared = new ArrayList<>();

        if (!dataFieldMM2List.isEmpty()) {
            for (DataFieldType dataFieldMM2 : dataFieldMM2List) {

                if (fieldsCompared.contains(dataFieldMM2)) {
                    continue;
                }
                // if tag is null, it should be ignored
                String tag = dataFieldMM2.getTag();
                if (tag == null) {
                    continue;
                }
                String ind1 = dataFieldMM2.getInd1();
                String ind2 = dataFieldMM2.getInd2();

                // Get all MM2 and MM3 data fields with the sam tag, ind1 and ind2
                List<DataFieldType> dataFieldsMatchedMM2 = findDataFields(dataFieldMM2List, tag, ind1, ind2);
                List<DataFieldType> dataFieldsMatchedMM3 = findDataFields(dataFieldMM3List, tag, ind1, ind2);

                if (dataFieldsMatchedMM3.isEmpty()) {
                    appendMessage(diffsInRecord,"Expected " + dataFieldsMatchedMM2.size() + " '" + tag + ind1 + ind2 + "' fields to exist in MM3");
                } else {
                    compareMatchingDataFields(diffsInRecord, dataFieldsMatchedMM2, dataFieldsMatchedMM3);
                }

                fieldsCompared.addAll(dataFieldsMatchedMM2);
            }
        }

    }

    private static void compareMatchingDataFields(StringBuilder diffsInRecord, List<DataFieldType> dataFieldsMatchedMM2, List<DataFieldType> dataFieldsMatchedMM3) {
        List<DataFieldType> dataFieldsCounted = new ArrayList<>();
        for (DataFieldType dfMM2: dataFieldsMatchedMM2) {
            List<SubfieldatafieldType> subFieldsMM2 = dfMM2.getSubfield();

            for (SubfieldatafieldType subFieldMM2 : subFieldsMM2) {

                boolean foundValue = existsSubFieldValue(subFieldMM2, subFieldsMM2.size(), dataFieldsMatchedMM3, diffsInRecord, dataFieldsCounted);
                if (!foundValue) {
                    appendMessage(diffsInRecord,"Subfield value not found in MM3: tag=" + dfMM2.getTag() + " ind1=" + dfMM2.getInd1()
                            + " ind2=" + dfMM2.getInd2() + " subfield code=" + subFieldMM2.getCode());
                }
            }

        }
    }

    private static boolean existsSubFieldValue(SubfieldatafieldType subFieldMM2, int numberOfSubFieldsMM2, List<DataFieldType> dataFieldsMatchedMM3, StringBuilder diffsInRecord, List<DataFieldType> dataFieldsCounted) {
        String valueMM2 = subFieldMM2.getValue();
        boolean foundValue = false;

        for (DataFieldType dfMM3 : dataFieldsMatchedMM3) {

            for (SubfieldatafieldType sfMM3 :  dfMM3.getSubfield()) {
                if (subFieldMM2.getCode().equals(sfMM3.getCode())) {
                    String valueMM3 = sfMM3.getValue();
                    if (valueMM3.contains(valueMM2)) {
                        foundValue = true;
                        if (!dataFieldsCounted.contains(dfMM3)) {
                            dataFieldsCounted.add(dfMM3);
                            if (dfMM3.getSubfield().size() != numberOfSubFieldsMM2) {
                                appendMessage(diffsInRecord,"Expected " + numberOfSubFieldsMM2 + " subfields in datafield: tag=" + dfMM3.getTag() + " ind1=" + dfMM3.getInd1()
                                        + " ind2=" + dfMM3.getInd2() + " but was: " + dfMM3.getSubfield().size());
                            }
                        }
                        break;
                    }
                }
            }
            if (foundValue) {
                break;
            }

        }

        return foundValue;
    }



    private static ControlFieldType findControlField(String tag, List<ControlFieldType> controlFields) {
        if (!controlFields.isEmpty()) {
            for(ControlFieldType c : controlFields) {
                if (tag.equals(c.getTag())) {
                    return c;
                }
            }
        }
        return null;
    }

    private static List<DataFieldType> findDataFields(List<DataFieldType> dataFields, String tag, String ind1, String ind2) {
        List<DataFieldType> dataFieldsFound = new ArrayList<>();

        if (dataFields != null) {
            for (DataFieldType d : dataFields) {
                if (tag.equals(d.getTag()) && ind1.equals(d.getInd1()) && ind2.equals(d.getInd2())) {
                    dataFieldsFound.add(d);
                }
            }
        }

        return dataFieldsFound;
    }

    private static List<SubfieldatafieldType> findSubFields(List<DataFieldType> dataFields, String code) {
        List<SubfieldatafieldType> subFieldsFound = new ArrayList<>();

        for (DataFieldType df : dataFields) {
            for (SubfieldatafieldType s : df.getSubfield()) {
                if (code.equals(s.getCode())) {
                    subFieldsFound.add(s);
                }
            }
        }

        return subFieldsFound;
    }

    private static void appendMessage(StringBuilder sb, String message) {
        sb.append(message + "\n");
    }

}
