package se.mtm.servicesCatalogDiffTool.mapper;

import se.mtm.servicesCatalogDiffTool.marc21.*;

import java.util.List;

public class ArgumentMapper {
	
	public IgnoreFields mapArgumentsToDataField(List<String> fieldsToIgnore) {
		IgnoreFields ignoreFields = new IgnoreFields();

		for (String field : fieldsToIgnore) {

			if (field.equals("leader") || field.equals("LEADER")) {
				ignoreFields.setLeaderField(new LeaderFieldType());
			} else if (field.length() == 3) {
				ControlFieldType controlField = new ControlFieldType();
				controlField.setTag(field.substring(0, 3));
				ignoreFields.getControlFieldList().add(controlField);
			} else if (field.length() > 3) {
				DataFieldType df = new DataFieldType();
				df.setTag(field.substring(0, 3));
				df.setInd1(field.substring(3, 4));
				df.setInd2(field.substring(4, 5));
				for (int i = 5; i < field.length(); i++) {
					SubfieldatafieldType sf = new SubfieldatafieldType();
					sf.setCode(String.valueOf(field.charAt(i)));
					df.getSubfield().add(sf);
				}
				ignoreFields.getDataFieldList().add(df);
			}

		}
		
		return ignoreFields;
	}

}
